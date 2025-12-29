package ista.M4A2.models.services.impl;

import ista.M4A2.dto.GeminiRequest;
import ista.M4A2.dto.GeminiResponse;
import ista.M4A2.models.dao.Sala_LactanciaDao;
import ista.M4A2.models.entity.Sala_Lactancia;
import ista.M4A2.models.services.serv.IDocumentosChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class GeminiService {

    
    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestClient restClient;

    @Autowired
    private IDocumentosChatBotService documentosService;

    @Autowired
    private Sala_LactanciaDao salaDao;

    public GeminiService() {
        this.restClient = RestClient.create("https://generativelanguage.googleapis.com");
    }

    private String obtenerContextoBaseDatos() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("LISTA DE SALAS DISPONIBLES:\n");

        Iterable<Sala_Lactancia> salas = salaDao.findAll();
        boolean haySalas = false;

        for (Sala_Lactancia sala : salas) {
            sb.append(sala.aTexto()).append("\n");
            haySalas = true;
        }

        if (!haySalas) {
            return "No hay salas registradas en la base de datos.";
        }

        return sb.toString();
    }

    private String obtenerContextoPDF() {
        return documentosService.obtenerTextoUnificadoDeTodosLosPDFs();
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000;
    }

    private Sala_Lactancia encontrarSalaMasCercana(double latUsuario, double lonUsuario) {
        Iterable<Sala_Lactancia> todasLasSalas = salaDao.findAll();
        Sala_Lactancia masCercana = null;
        double menorDistancia = Double.MAX_VALUE;

        for (Sala_Lactancia sala : todasLasSalas) {
            try {
                if (sala.getLatitudCMedico() != null && sala.getLongitudCMedico() != null) {
                    double latSala = Double.parseDouble(sala.getLatitudCMedico());
                    double lonSala = Double.parseDouble(sala.getLongitudCMedico());
                    double distancia = calcularDistancia(latUsuario, lonUsuario, latSala, lonSala);

                    if (distancia < menorDistancia) {
                        menorDistancia = distancia;
                        masCercana = sala;
                    }
                }
            } catch (NumberFormatException e) {
                continue;
            }
        }
        return masCercana;
    }

    public String consultarIA(String preguntaUsuario, Double latUsuario, Double lonUsuario) {

        String infoEducativa = obtenerContextoPDF();
        String infoOperativa = obtenerContextoBaseDatos();
        String contextoUbicacion = "";

        if (latUsuario != null && lonUsuario != null) {
            Sala_Lactancia cercana = encontrarSalaMasCercana(latUsuario, lonUsuario);
            if (cercana != null) {
                contextoUbicacion = "DATO GPS: La sala más cercana calculada es: " + cercana.getNombreCMedico() +
                        " ubicada en " + cercana.getDireccionCMedico() + ".";
            }
        }

        // --- PROMPT OPTIMIZADO PARA RESPUESTAS CONCISAS Y NATURALES ---
        String prompt = """
                ACTÚA COMO: 'LactaBot', el asistente oficial de la App LactaCare. Tu tono es amable, profesional y MUY CONCISO.
                
                TUS REGLAS ESTRICTAS:
                1. NUNCA menciones 'Fuente A', 'Fuente B', 'PDF', 'Base de datos' ni 'Manuales'. La información es tuya.
                2. SI LA PREGUNTA ES SOBRE UBICACIONES, CENTROS O DÓNDE IR:
                   - Usa EXCLUSIVAMENTE la 'LISTA DE SALAS DISPONIBLES' que te doy abajo.
                   - Si esa lista dice "No hay salas registradas" o está vacía, tu respuesta debe ser ÚNICAMENTE: "Lo siento, actualmente no tengo salas de lactancia registradas en el sistema."
                   - NO inventes direcciones ni menciones que busquen en el Ministerio de Salud.
                   - NO mezcles información de los manuales educativos cuando te pregunten "dónde".
                3. SI LA PREGUNTA ES EDUCATIVA (dolor, producción, consejos):
                   - Usa la 'INFORMACIÓN EDUCATIVA'.
                   - Responde en máximo 3 frases cortas y directas.
                
                --- DATOS PARA TU RESPUESTA ---
                
                [INFORMACIÓN EDUCATIVA]
                %s
                
                [LISTA DE SALAS DISPONIBLES]
                %s
                
                %s
                
                -------------------------------
                PREGUNTA DEL USUARIO: %s
                RESPUESTA (Concisa):
                """.formatted(infoEducativa, infoOperativa, contextoUbicacion, preguntaUsuario);

        GeminiRequest request = new GeminiRequest(
                List.of(new GeminiRequest.Content(
                        List.of(new GeminiRequest.Part(prompt))
                ))
        );

        try {
           
            String modelo = "gemini-2.5-flash";

            GeminiResponse response = restClient.post()
                    .uri("/v1beta/models/" + modelo + ":generateContent?key=" + this.apiKey)
                    .body(request)
                    .retrieve()
                    .body(GeminiResponse.class);

            if (response != null && !response.candidates().isEmpty()) {
                return response.candidates().get(0).content().parts().get(0).text();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Lo siento, estoy teniendo problemas técnicos momentáneos. Por favor intenta más tarde.";
        }
        return "Sin respuesta.";
    }
}
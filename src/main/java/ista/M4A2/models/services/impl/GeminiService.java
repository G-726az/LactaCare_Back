package ista.M4A2.models.services.impl;

import ista.M4A2.dto.GeminiRequest;
import ista.M4A2.dto.GeminiResponse;
import ista.M4A2.models.dao.Sala_LactanciaDao;
import ista.M4A2.models.entity.Sala_Lactancia;
import ista.M4A2.models.services.serv.IDocumentosChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class GeminiService {


    private static final String API_KEY = "AIzaSyCUAielgVWHSFgq5p-yBAJXIaeMIJ5ruZc"; 

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
        sb.append("INFORMACIÓN OPERATIVA (HORARIOS Y UBICACIONES DE SALAS):\n");
        
        Iterable<Sala_Lactancia> salas = salaDao.findAll();
        
        for (Sala_Lactancia sala : salas) {
            
            sb.append(sala.aTexto()).append("\n");
        }
        
        if (sb.toString().equals("INFORMACIÓN OPERATIVA (HORARIOS Y UBICACIONES DE SALAS):\n")) {
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
        return R * c * 1000; // 
    }

    private Sala_Lactancia encontrarSalaMasCercana(double latUsuario, double lonUsuario) {
        Iterable<Sala_Lactancia> todasLasSalas = salaDao.findAll();
        Sala_Lactancia masCercana = null;
        double menorDistancia = Double.MAX_VALUE;

        for (Sala_Lactancia sala : todasLasSalas) {
            try {
                if(sala.getLatitudCMedico() != null && sala.getLongitudCMedico() != null) {
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
                contextoUbicacion = "\n[SISTEMA GPS]: La sala más cercana es " + cercana.getNombreCMedico() + 
                                    " en " + cercana.getDireccionCMedico() + ".\n";
            }
        }


        String prompt = """
                Instrucciones: Eres un asistente experto en lactancia (LactaCare).
                
                FUENTE A (DOCUMENTOS/PDF):
                %s
                
                FUENTE B (BASE DE DATOS - SALAS):
                %s
                
                %s
                
                Pregunta del usuario: %s
                """.formatted(infoEducativa, infoOperativa, contextoUbicacion, preguntaUsuario);

        GeminiRequest request = new GeminiRequest(
            List.of(new GeminiRequest.Content(
                List.of(new GeminiRequest.Part(prompt))
            ))
        );

        try {
            String modelo = "gemini-2.5-flash";
            GeminiResponse response = restClient.post()
                .uri("/v1beta/models/" + modelo + ":generateContent?key=" + API_KEY)
                .body(request)
                .retrieve()
                .body(GeminiResponse.class);

            if (response != null && !response.candidates().isEmpty()) {
                return response.candidates().get(0).content().parts().get(0).text();
            }
        } catch (Exception e) {
            return "Error IA: " + e.getMessage();
        }
        return "Sin respuesta.";
    }
}


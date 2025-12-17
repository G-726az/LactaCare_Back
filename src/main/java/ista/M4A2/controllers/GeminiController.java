package ista.M4A2.controllers;

import ista.M4A2.dto.PreguntaRequest;
import ista.M4A2.models.services.impl.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(originPatterns = "*") 
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/preguntar")
    public String preguntar(@RequestBody PreguntaRequest request) {
        

        return geminiService.consultarIA(
            request.getPregunta(), 
            request.getLatitud(), 
            request.getLongitud()
        );
    }
}
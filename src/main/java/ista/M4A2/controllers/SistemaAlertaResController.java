package ista.M4A2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ista.M4A2.dto.SistemaAlertaRequest;
import ista.M4A2.models.entity.SistemaAlerta;
import ista.M4A2.models.services.serv.ISistemaAlertaService;

@RestController
@RequestMapping("/api")
public class SistemaAlertaResController {

	@Autowired
	private ISistemaAlertaService sistemaAlertaService;
	
	public SistemaAlertaResController(ISistemaAlertaService sistemaAlertaService) {
		this.sistemaAlertaService = sistemaAlertaService;
	}
	
	@PostMapping("/sistema-alertas")
	public ResponseEntity<?> recibirEventoAlerta(@RequestBody SistemaAlertaRequest alerta) {
	    SistemaAlerta sa = new SistemaAlerta();
	    sa.setTipoAlerta(alerta.getTipo_alerta());
	    sa.setTemperaturaAlerta(alerta.getTemperatura_alerta());
	    sa.setFechaHoraAlerta(alerta.getFecha_hora_alerta());
	    sistemaAlertaService.save(sa);
	    return ResponseEntity.status(HttpStatus.CREATED).body("Alerta recibida y procesada");
	}
	
	@GetMapping("/sistema-alertas")
	public ResponseEntity<?> obtenerAlertas() {
	    return ResponseEntity.ok(sistemaAlertaService.findAll());
	}
}

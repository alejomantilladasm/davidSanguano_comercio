package ec.com.comercio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.comercio.services.ClienteService;

@RestController
@RequestMapping("cliente")
public class ClienteController {
	
	@Autowired
	ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<?> recuperarTodo(){
		return ResponseEntity.ok().body(clienteService.recuperarTodo());
	}

}

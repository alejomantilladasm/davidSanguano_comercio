package ec.com.comercio.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.comercio.common.CommonController;
import ec.com.comercio.entity.Cliente;
import ec.com.comercio.services.ClienteService;

@RestController
@RequestMapping("cliente")
public class ClienteController extends CommonController<Cliente, ClienteService> {

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Cliente cliente, @PathVariable Long id) {
		Optional<Cliente> o = service.recuperarById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Cliente cDB = o.get();
		cDB.setCorreo(cliente.getCorreo());
		cDB.setDireccion(cliente.getDireccion());
		cDB.setCelular(cliente.getCelular());
		cDB.setFoto(cliente.getFoto());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cDB));
	}
	
	@GetMapping("cedula/{ci}")
	public ResponseEntity<?> recuperarPorCi(@PathVariable String ci ) {
		Cliente cli= service.recuperarPorCedula(ci);
		if (null==cli) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(cli);
	}
	
	@PostMapping
	@Override
	public ResponseEntity<?> guardar(@RequestBody Cliente cliente){
		if(null==cliente || null==cliente.getNombres()|| null==cliente.getApellidos()|| null==cliente.getCi()
				|| "".equals(cliente.getNombres())|| "".equals(cliente.getApellidos())|| "".equals(cliente.getCi())  ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Los campos de nombres, apellidos y cedula son obligatorias...! ");
		}
		Cliente clientedb=service.guardar(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clientedb);
	}

}

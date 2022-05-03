package ec.com.comercio.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.comercio.common.CommonController;
import ec.com.comercio.entity.Cliente;
import ec.com.comercio.services.ClienteService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("cliente")
public class ClienteController extends CommonController<Cliente, ClienteService> {

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Cliente cliente, @PathVariable Long id) {
		try {
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
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("cedula/{ci}")
	public ResponseEntity<?> recuperarPorCi(@PathVariable String ci) {
		try {
			Cliente cli = service.recuperarPorCedula(ci);
			if (null == cli) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(cli);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}

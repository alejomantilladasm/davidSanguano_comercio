package ec.com.comercio.controllers;



import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.comercio.common.CommonController;
import ec.com.comercio.entity.Producto;
import ec.com.comercio.services.ProductoService;


@RestController
@RequestMapping("producto")
public class ProductoController extends CommonController<Producto, ProductoService>{

	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Producto producto, @PathVariable Long id) {
		Optional<Producto> o = service.recuperarById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Producto pDB = o.get();
		pDB.setNombre(producto.getNombre());
		pDB.setPrecio(producto.getPrecio());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(pDB));
	}
	
	
}

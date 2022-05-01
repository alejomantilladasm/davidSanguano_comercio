package ec.com.comercio.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.comercio.common.CommonController;
import ec.com.comercio.entity.Producto;
import ec.com.comercio.entity.Tienda;
import ec.com.comercio.services.TiendaService;

@RestController
@RequestMapping("tienda")
public class TiendaController extends CommonController<Tienda, TiendaService> {

	@PutMapping("/asignar-productos/{idTienda}")
	public ResponseEntity<?> agregarProductos(@RequestBody List<Producto> productos, @PathVariable Long idTienda) {
		Optional<Tienda> oT=service.recuperarById(idTienda);
		if(oT.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Tienda tiendaDb=oT.get();
		for(Producto p:productos) {
			if(!service.existeProductoTienda(idTienda, p)) {
				tiendaDb.agregarProducto(p);
			}
		}
		return ResponseEntity.ok().body(service.guardar(tiendaDb));
	}
	
	@PutMapping("/eliminar-producto/{idTienda}")
	public ResponseEntity<?> quitarProductos(@RequestBody Producto producto, @PathVariable Long idTienda) {
		Optional<Tienda> oT=service.recuperarById(idTienda);
		if(oT.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Tienda tiendaDb=oT.get();
		tiendaDb.eliminarProducto(producto);
		return ResponseEntity.ok().body(service.guardar(tiendaDb));
	}
	
	
}

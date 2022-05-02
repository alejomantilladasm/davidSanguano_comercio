package ec.com.comercio.controllers;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.comercio.common.CommonController;
import ec.com.comercio.entity.Transaccion;
import ec.com.comercio.services.TransaccionService;
import ec.com.comercio.util.Carrito;
import ec.com.comercio.util.Item;

@RestController
@RequestMapping("transaccion")
public class TransaccionController extends CommonController<Transaccion, TransaccionService> {

	@PutMapping("/pedido/{idCliente}")
	public ResponseEntity<?> realizarPedido(@RequestBody Carrito carrito, @PathVariable Long idCliente) {
		/* Control si existe cliente */
		if (null == service.validarCliente(idCliente)) {
			return ResponseEntity.badRequest()
					.body("El cliente que quiere realizar el pedido no se encuentra registrado...!");
		}
		/* Control de tiendas */
		if (service.validarTiendas(carrito.getItems())) {
			return ResponseEntity.badRequest().body("Una de las tiendas del pedido no existe...!");
		}
		/* Control Productos */
		if (service.validarProductos(carrito.getItems())) {
			return ResponseEntity.badRequest().body("Uno de los productos del pedido no existe...!");
		}
		/* Validar Existencia */
		if (service.validarExistencia(carrito.getItems())) {
			return ResponseEntity.badRequest().body("Uno o varios productos no constan con stock...!");
		}

		for (Item i : carrito.getItems()) {
			service.descontarStock(i.getIdProducto(), i.getCantidad());
			service.guardar(new Transaccion(i.getIdTienda(), idCliente, i.getIdProducto(), i.getCantidad(), new Date()));
		}

		return ResponseEntity.ok().body(service.recuperarTodo());

	}

}
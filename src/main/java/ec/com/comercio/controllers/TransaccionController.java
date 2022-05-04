package ec.com.comercio.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.comercio.common.CommonController;
import ec.com.comercio.entity.Transaccion;
import ec.com.comercio.services.TransaccionService;
import ec.com.comercio.util.Carrito;
import ec.com.comercio.util.FechasReporte;
import ec.com.comercio.util.Item;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("transaccion")
public class TransaccionController extends CommonController<Transaccion, TransaccionService> {

	@PutMapping("/pedido/{idCliente}")
	public ResponseEntity<?> realizarPedido(@RequestBody Carrito carrito, @PathVariable Long idCliente) {
		try {
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
				return ResponseEntity.badRequest().body("Unidades no disponibles (> 10)...!");
			}

			Date fechaPedido = new Date();
			for (Item i : carrito.getItems()) {
				/* Cuando el valor esta entre */
				if (service.validacionReposicionStock(i.getIdProducto(), i.getCantidad())) {
					service.reponerStock(Long.valueOf(i.getIdProducto()));
				}
				service.descontarStock(i.getIdProducto(), i.getCantidad());
				service.guardar(
						new Transaccion(i.getIdTienda(), idCliente, i.getIdProducto(), i.getCantidad(), fechaPedido));
			}
			return ResponseEntity.ok().body(service.recuperarTodo());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@GetMapping("/reporte")
	public ResponseEntity<?> reporteAgrupado() {
		try {
			return ResponseEntity.ok().body(service.reporteNumeroTransacciones());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/reporte/productos")
	public ResponseEntity<?> reporteProductos() {
		try {
			return ResponseEntity.ok().body(service.reporteProductos());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/reporte/cliente/{idCliente}")
	public ResponseEntity<?> reportePorCliente(@Valid @RequestBody FechasReporte fechasReporte, BindingResult bidResult,
			@PathVariable Long idCliente) {
		try {
			if (bidResult.hasErrors()) {
				return this.validar(bidResult);
			}
			if (null == service.validarCliente(idCliente)) {
				return ResponseEntity.badRequest().body("El cliente no se encuentra registrado...!");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			return ResponseEntity.ok().body(service.reporteTotalPorClienteEntreFechas(idCliente,
					sdf.parse(fechasReporte.getFechaInicio()), sdf.parse(fechasReporte.getFechaFin())));
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.badRequest().body("El el formato de las fechas debe ser yyyy-MM-dd...!");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	
	
	@GetMapping("/reporte/tienda/productos")
	public ResponseEntity<?> reporteTiendaProductos() {
		try {
			return ResponseEntity.ok().body(service.reportePorTiendaProducto());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	
	
	@GetMapping(value = "/reporte/csv/{idCliente}", produces = "text/csv")
	public ResponseEntity<?> reporteCsv(@Valid @RequestBody FechasReporte fechasReporte, BindingResult bidResult,
			@PathVariable Long idCliente) {
		try {
			if (bidResult.hasErrors()) {
				return this.validar(bidResult);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			InputStreamResource fileInputStream = new InputStreamResource(service.generarCsv(idCliente, sdf.parse(fechasReporte.getFechaInicio()), sdf.parse(fechasReporte.getFechaFin())));
			
			String csvFileName = "reporte.csv";
			
			// setting HTTP headers
		    HttpHeaders headers = new HttpHeaders();
		    headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
		    // defining the custom Content-Type
		    headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
			
		    return new ResponseEntity<>(fileInputStream,headers, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
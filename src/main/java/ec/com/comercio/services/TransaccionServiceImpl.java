package ec.com.comercio.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ec.com.comercio.common.CommonServiceImpl;
import ec.com.comercio.entity.Cliente;
import ec.com.comercio.entity.Producto;
import ec.com.comercio.entity.Tienda;
import ec.com.comercio.entity.Transaccion;
import ec.com.comercio.repository.ClienteRepository;
import ec.com.comercio.repository.ProductoRepository;
import ec.com.comercio.repository.TiendaRepository;
import ec.com.comercio.repository.TransaccionRepository;
import ec.com.comercio.util.Item;
import ec.com.comercio.util.ReporteProducto;
import ec.com.comercio.util.ReporteTienda;
import ec.com.comercio.util.ReporteTiendaProducto;
import ec.com.comercio.util.ReporteTransacciones;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransaccionServiceImpl extends CommonServiceImpl<Transaccion, TransaccionRepository>
		implements TransaccionService {

	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	ProductoRepository productoRepository;
	@Autowired
	TiendaRepository tiendaRepository;

	@Override
	public Cliente validarCliente(Long idCliente) {
		Optional<Cliente> oC = clienteRepository.findById(idCliente);
		if (oC.isEmpty()) {
			return null;
		}
		return oC.get();
	}

	@Override
	public boolean validarTiendas(List<Item> items) {
		boolean controlTiendas = false;
		for (Item item : items) {
			Optional<Tienda> oT = tiendaRepository.findById(item.getIdTienda());
			if (oT.isEmpty()) {
				controlTiendas = true;
			}
		}
		return controlTiendas;
	}

	@Override
	public boolean validarProductos(List<Item> items) {
		boolean controlProductos = false;
		for (Item item : items) {
			Optional<Producto> oP = productoRepository.findById(item.getIdProducto());
			if (oP.isEmpty()) {
				controlProductos = true;
			}
		}
		return controlProductos;
	}

	@Override
	public boolean validarExistencia(List<Item> items) {
		boolean controlExistencia = false;
		for (Item item : items) {
			Optional<Producto> prOptional = productoRepository.findById(item.getIdProducto());
			int stockResultante = prOptional.get().getStock() - item.getCantidad();
			if (stockResultante < -10) {
				controlExistencia = true;
			}
		}
		return controlExistencia;
	}

	@Override
	public void descontarStock(Long idProducto, int valor) {
		Optional<Producto> proOptional = productoRepository.findById(idProducto);
		proOptional.get().setStock(proOptional.get().getStock() - valor);
		productoRepository.save(proOptional.get());
	}

	@Override
	public Producto reponerStock(Long idProducto) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Producto> response = restTemplate
				.getForEntity("https://626c16f25267c14d566cb9b0.mockapi.io/api/v1/stock-extra", Producto.class);
		Producto productoResponse = response.getBody();
		Producto productoDb = productoRepository.findById(idProducto).get();
		productoDb.setStock(productoDb.getStock() + productoResponse.getStock());
		return productoRepository.save(productoDb);
	}

	@Override
	public boolean validacionReposicionStock(Long idProducto, int valor) {
		Producto producto = productoRepository.findById(idProducto).get();
		int stockResultado = producto.getStock() - valor;
		/* Consume reposicione de 5 */
		if (stockResultado <= 0 && stockResultado >= -5) {
			this.reponerStockAsync(idProducto);
		}
		/* Consume reposicione entre 5 y 10 */
		if (stockResultado < -5 && stockResultado >= -10) {
			return true;
		}
		return false;
	}

	@Override
	@Async
	public void reponerStockAsync(Long idProducto) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Producto> response = restTemplate
				.getForEntity("https://626c16f25267c14d566cb9b0.mockapi.io/api/v1/stock-extra-async", Producto.class);
		Producto productoResponse = response.getBody();
		Producto productoDb = productoRepository.findById(idProducto).get();
		productoDb.setStock(productoDb.getStock() + productoResponse.getStock());
		productoDb = productoRepository.save(productoDb);
		System.out.println("Reposicion a " + productoDb.getNombre() + " Async 5");
	}

	@Override
	public List<ReporteTransacciones> reporteNumeroTransacciones() {
		List<ReporteTransacciones> rTransacciones = new ArrayList<>();
		for (Object[] o : repository.reporteNumeroTransacciones()) {
			rTransacciones.add(new ReporteTransacciones(Long.valueOf(o[0].toString()),
					tiendaRepository.findById(Long.valueOf(o[0].toString())).get().getNombre(),
					Integer.valueOf(o[1].toString()), new Date()));
		}

		return rTransacciones;
	}

	@Override
	public List<ReporteProducto> reporteProductos() {
		List<ReporteProducto> rTransacciones = new ArrayList<>();
		for (Object[] o : repository.reporteTotalPorProducto()) {
			Producto producto = productoRepository.findById(Long.valueOf(o[0].toString())).get();
			BigDecimal precioPorProducto = producto.getPrecio();
			rTransacciones.add(new ReporteProducto(Long.valueOf(o[0].toString()), producto.getNombre(),
					precioPorProducto.multiply(new BigDecimal(o[1].toString())), Integer.valueOf(o[1].toString())));
		}

		return rTransacciones;
	}

	@Override
	public List<Transaccion> reporteTotalPorClienteEntreFechas(Long idCliente, Date fechaInicio, Date fechaFin) {
		return repository.reporteTotalPorClienteEntreFechas(idCliente, fechaInicio, fechaFin);
	}

	@Override
	public List<ReporteTienda> reportePorTiendaProducto() {
		List<ReporteTienda> reporteTiendas = new ArrayList<ReporteTienda>();
		List<Object[]> tiendas = repository.recuperarTiendas();
		Tienda tienda;
		Producto producto;
		ReporteTienda rt;
		ReporteTiendaProducto rtp;
		for (Object[] o : tiendas) {
			tienda = tiendaRepository.findById(Long.valueOf(o[0].toString())).get();
			rt = new ReporteTienda();
			rt.setIdTienda(tienda.getId());
			rt.setNombreTienda(tienda.getNombre());
			List<Object[]> productos = repository.reporteProductoPorTienda(tienda.getId());
			rt.setProductos(new ArrayList<>());
			BigDecimal totalPorTienda = BigDecimal.ZERO;
			for (Object[] op : productos) {
				producto = productoRepository.findById(Long.valueOf(op[0].toString())).get();
				rtp = new ReporteTiendaProducto();
				rtp.setIdProducto(producto.getId());
				rtp.setNombreProducto(producto.getNombre());
				int cantidad = Integer.valueOf(op[1].toString());
				rtp.setCantidad(cantidad);
				BigDecimal totalPorProducto = producto.getPrecio().multiply(new BigDecimal(cantidad));
				rtp.setTotal(totalPorProducto);
				rt.getProductos().add(rtp);
				totalPorTienda = totalPorTienda.add(totalPorProducto);
			}
			rt.setTotal(totalPorTienda);
			reporteTiendas.add(rt);
		}

		return reporteTiendas;
	}

	/*
	 * @Override public ByteArrayInputStream generarCsv(Long idCliente, Date
	 * fechaInicio, Date fechaFin) { List<Transaccion> transacciones =
	 * repository.reporteTotalPorClienteEntreFechas(idCliente,
	 * fechaInicio,fechaFin); List<String[]> lista = new ArrayList<String[]>();
	 * String[] header = { "cantidad", "cliente", "fecha", "producto", "tienda" };
	 * lista.add(header); for (Transaccion t : transacciones) { lista.add(new
	 * String[] { "" + t.getCantidad(), "" + t.getCliente(),
	 * t.getFecha().toString(), "" + t.getProducto(), "" + t.getTienda() }); } try
	 * (CSVWriter writer = new CSVWriter(new FileWriter("test.csv"))) {
	 * writer.writeAll(lista); log.info("La cadena es {}",lista.toString()); } catch
	 * (IOException e) { log.error(e.getMessage(), e); } return null; }
	 */

	@Override
	public ByteArrayInputStream generarCsv(Long idCliente, Date fechaInicio, Date fechaFin) {
		List<Transaccion> transacciones = repository.reporteTotalPorClienteEntreFechas(idCliente, fechaInicio,
				fechaFin);
		List<List<String>> lista = new ArrayList<>();
		String[] header = { "cantidad", "cliente", "fecha", "producto", "tienda" };
		for (Transaccion t : transacciones) {
			lista.add(Arrays.asList("" + t.getCantidad(), "" + t.getCliente(), "" + t.getFecha().toString(),
					"" + t.getProducto(), "" + t.getTienda()));
		}
		ByteArrayInputStream byteArrayOutputStream;
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT.withHeader(header));) {
			for (List<String> record : lista)
				csvPrinter.printRecord(record);
			csvPrinter.flush();

			byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
		return byteArrayOutputStream;
	}

}

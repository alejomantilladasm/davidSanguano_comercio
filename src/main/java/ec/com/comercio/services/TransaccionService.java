package ec.com.comercio.services;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

import ec.com.comercio.common.CommonService;
import ec.com.comercio.entity.Cliente;
import ec.com.comercio.entity.Producto;
import ec.com.comercio.entity.Transaccion;
import ec.com.comercio.util.Item;
import ec.com.comercio.util.ReporteProducto;
import ec.com.comercio.util.ReporteTienda;
import ec.com.comercio.util.ReporteTransacciones;

public interface TransaccionService extends CommonService<Transaccion> {

	public Cliente validarCliente(Long idCliente);

	public boolean validarTiendas(List<Item> items);

	public boolean validarProductos(List<Item> items);

	public boolean validarExistencia(List<Item> items);

	public void descontarStock(Long idProducto, int valor);

	public Producto reponerStock(Long idProducto);

	public boolean validacionReposicionStock(Long idProducto, int valor);

	public void reponerStockAsync(Long idProducto);

	public List<ReporteTransacciones> reporteNumeroTransacciones();

	public List<ReporteProducto> reporteProductos();

	public List<Transaccion> reporteTotalPorClienteEntreFechas(Long idCliente, Date fechaInicio, Date fechaFin);

	public List<ReporteTienda> reportePorTiendaProducto();

	public ByteArrayInputStream generarCsv(Long idCliente, Date fechaInicio, Date fechaFin);

}

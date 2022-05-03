package ec.com.comercio.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ec.com.comercio.entity.Transaccion;

public interface TransaccionRepository extends CrudRepository<Transaccion, Long> {

	@Query("select t.tienda, count(t), t.fecha from Transaccion t group by t.tienda,t.fecha")
	public List<Object[]> reporteNumeroTransacciones();	
	
	@Query("select t.producto, sum(t.cantidad) from Transaccion t group by t.producto")
	public List<Object[]> reporteTotalPorProducto();	
	
	@Query(value = "select * from transacciones where cliente=?1 and DATE(fecha) BETWEEN DATE(?2) and DATE(?3)",nativeQuery = true)
	public List<Transaccion> reporteTotalPorClienteEntreFechas(Long idCliente,Date fechaInicio, Date fechaFin);	
	
}

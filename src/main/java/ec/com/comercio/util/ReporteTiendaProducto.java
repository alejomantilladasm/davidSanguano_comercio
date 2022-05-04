package ec.com.comercio.util;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ReporteTiendaProducto {

	private Long idProducto;
	private String nombreProducto;
	private int cantidad;
	private BigDecimal total;
	
}

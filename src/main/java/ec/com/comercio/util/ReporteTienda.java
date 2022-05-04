package ec.com.comercio.util;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ReporteTienda {
	private Long idTienda;
	private String nombreTienda;
	private BigDecimal total;
	private List<ReporteTiendaProducto> productos;
}

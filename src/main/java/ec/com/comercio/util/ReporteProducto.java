package ec.com.comercio.util;

import java.math.BigDecimal;

public class ReporteProducto {

	private Long idPorducto;
	private String nombreProducto;
	private int cantidad;
	private BigDecimal total;
	public ReporteProducto() {
	}
	public ReporteProducto(Long idPorducto, String nombreProducto, BigDecimal total,int cantidad) {
		this.idPorducto = idPorducto;
		this.nombreProducto = nombreProducto;
		this.total = total;
		this.cantidad=cantidad;
	}
	public Long getIdPorducto() {
		return idPorducto;
	}
	public void setIdPorducto(Long idPorducto) {
		this.idPorducto = idPorducto;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
}

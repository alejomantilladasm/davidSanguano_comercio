package ec.com.comercio.util;

public class Item {
	
	private Long idTienda;
	private Long idProducto;
	private int cantidad;
	
	public Item() {
	}
	public Item(Long idTienda, Long idProducto, int cantidad) {
		this.idTienda = idTienda;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
	}
	public Long getIdTienda() {
		return idTienda;
	}
	public void setIdTienda(Long idTienda) {
		this.idTienda = idTienda;
	}
	public Long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}

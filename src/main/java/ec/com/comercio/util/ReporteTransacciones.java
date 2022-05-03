package ec.com.comercio.util;

import java.util.Date;

public class ReporteTransacciones {

	
	private Long idTienda;
	private String nombreTienda;
	private int numeroTransacciones;
	private Date fecha;
	
	public ReporteTransacciones() {
	}
	
	public ReporteTransacciones(Long idTienda, String nombreTienda, int numeroTransacciones,Date fecha) {
		this.idTienda = idTienda;
		this.nombreTienda = nombreTienda;
		this.numeroTransacciones = numeroTransacciones;
		this.fecha=fecha;
	}

	public Long getIdTienda() {
		return idTienda;
	}

	public void setIdTienda(Long idTienda) {
		this.idTienda = idTienda;
	}

	public String getNombreTienda() {
		return nombreTienda;
	}

	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}

	public int getNumeroTransacciones() {
		return numeroTransacciones;
	}

	public void setNumeroTransacciones(int numeroTransacciones) {
		this.numeroTransacciones = numeroTransacciones;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}

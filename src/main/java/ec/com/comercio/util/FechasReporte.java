package ec.com.comercio.util;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FechasReporte {
	
	@NotNull
	@NotEmpty
	private String fechaInicio;
	@NotNull
	@NotEmpty
	private String fechaFin;
	
	public FechasReporte() {
	}
	public FechasReporte(String fechaInicio, String fechaFin) {
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
	
}

package ec.com.comercio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "cedula_identidad")
	@NotEmpty
	private String ci;
	@Column(name = "nombres")
	@NotEmpty
	private String nombres;
	@Column(name = "apellidos")
	@NotEmpty
	private String apellidos;
	@Column(name = "correo_electronico")
	@Email
	private String correo;
	@Column(name = "direccion")
	private String direccion;
	@Column(name = "celular")
	private String celular;
	@Column(name = "foto")
	private String foto;
	public Cliente() {
	}
	public Cliente(String ci, String nombres, String apellidos, String correo, String direccion, String celular, String foto) {
		this.ci = ci;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.correo = correo;
		this.direccion = direccion;
		this.celular = celular;
		this.foto = foto;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}

	
}

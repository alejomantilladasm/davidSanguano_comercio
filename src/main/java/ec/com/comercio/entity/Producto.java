package ec.com.comercio.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "productos")
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonProperty("cod")
	@Column(name = "codigo")
	@NotEmpty
	private String codigo;
	@JsonProperty("name")
	@Column(name = "nombre")
	@NotEmpty
	private String nombre;
	@JsonProperty("price")
	@Column(name = "precio")
	@NotNull
	private BigDecimal precio;
	@Column(name = "stock")
	@NotNull
	private Integer stock;
	
	public Producto() {
	}
	public Producto( String codigo,  String nombre,  BigDecimal precio, Integer stock) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	@Override
	public boolean equals(Object obj) {
		if(this==obj) {
			return true;
		}
		if(!(obj instanceof Producto)) {
			return false;
		}
		Producto p=(Producto)obj;
		
		return this.id!=null && this.id.equals(p.getId());
	}

}

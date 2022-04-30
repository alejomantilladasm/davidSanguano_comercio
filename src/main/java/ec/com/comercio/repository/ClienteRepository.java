package ec.com.comercio.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ec.com.comercio.entity.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{

	@Query("select c from Cliente c where c.ci=?1")
	public Cliente recuperarClientePorCedula(String ci);
	
}

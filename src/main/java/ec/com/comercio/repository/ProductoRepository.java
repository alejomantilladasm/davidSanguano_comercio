package ec.com.comercio.repository;

import org.springframework.data.repository.CrudRepository;

import ec.com.comercio.entity.Producto;

public interface ProductoRepository extends CrudRepository<Producto,Long> {

}

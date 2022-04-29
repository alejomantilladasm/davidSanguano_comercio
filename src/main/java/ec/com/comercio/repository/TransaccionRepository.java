package ec.com.comercio.repository;

import org.springframework.data.repository.CrudRepository;

import ec.com.comercio.entity.Transaccion;

public interface TransaccionRepository extends CrudRepository<Transaccion, Long> {

}

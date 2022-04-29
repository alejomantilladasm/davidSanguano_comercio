package ec.com.comercio.common;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class CommonServiceImpl<T,R extends CrudRepository<T, Long>> implements CommonService<T> {

	@Autowired
	protected R repository;
	
	@Override
	public List<T> recuperarTodo() {
		 return (List<T>)repository.findAll();
	}

	@Override
	public Optional<T> recuperarById(Long id) {
		return repository.findById(id);
	}

	@Override
	public T guardar(T t) {
		return repository.save(t);
	}

	@Override
	public void eliminarById(Long id) {
		repository.deleteById(id);
	}

}

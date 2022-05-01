package ec.com.comercio.common;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public class CommonServiceImpl<T,R extends CrudRepository<T, Long>> implements CommonService<T> {

	@Autowired
	protected R repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<T> recuperarTodo() {
		 return (List<T>)repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<T> recuperarById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public T guardar(T t) {
		return repository.save(t);
	}

	@Override
	@Transactional
	public void eliminarById(Long id) {
		repository.deleteById(id);
	}

}

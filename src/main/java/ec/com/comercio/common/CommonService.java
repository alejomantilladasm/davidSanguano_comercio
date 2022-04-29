package ec.com.comercio.common;

import java.util.List;
import java.util.Optional;

public interface CommonService<T> {

	public List<T> recuperarTodo();
	public Optional<T> recuperarById(Long id);
	public T guardar(T t);
	public void eliminarById(Long id);
	
	
	
}

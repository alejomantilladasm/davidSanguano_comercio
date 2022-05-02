package ec.com.comercio.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class CommonController<T,S extends CommonService<T>> {
	
	@Autowired
	protected S service;
	
	@GetMapping
	public ResponseEntity<?> recuperarTodo(){
		return ResponseEntity.ok().body(service.recuperarTodo());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> recuperarById(@PathVariable Long id){
		Optional<T> o=service.recuperarById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(o.get());
	}
	
	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody T t, BindingResult bidResult){
		if(bidResult.hasErrors()) {
			return this.validar(bidResult);
		}
		
		T tDb=service.guardar(t);
		return ResponseEntity.status(HttpStatus.CREATED).body(tDb);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarById(@PathVariable Long id){
		service.eliminarById(id);
		return ResponseEntity.noContent().build();
	}
	
	protected ResponseEntity<?> validar(BindingResult bidResult) {
		Map<String,Object> errors=new HashMap<>();
		bidResult.getFieldErrors().forEach(e->{
			errors.put(e.getField(),"El campo "+e.getField()+" "+ e.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errors);		
	}

}

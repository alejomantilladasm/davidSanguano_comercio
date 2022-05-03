package ec.com.comercio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ec.com.comercio.entity.Cliente;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerClienteTests {

	@Autowired private TestRestTemplate restTemplate;

	
	@Test
	void validarStatusRespuestaServicio() {
		ResponseEntity<Cliente[]> responseEntity=restTemplate.getForEntity("/cliente",Cliente[].class );
		assertEquals(responseEntity.getStatusCodeValue(), 200);
	}
	

}

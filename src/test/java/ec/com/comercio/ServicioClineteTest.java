package ec.com.comercio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ec.com.comercio.entity.Cliente;
import ec.com.comercio.services.ClienteService;

@SpringBootTest()
class ServicioClineteTest {

	@Autowired private ClienteService clienteService;


	@Test
	void recuperarClientePorCedula() {
		String ci="1717274847";
		//ResponseEntity<Cliente> responseEntity=restTemplate.getForEntity("/cliente",Cliente.class );
		Cliente cliente=clienteService.recuperarPorCedula(ci);
		assertEquals(cliente.getCi(), ci);
	}

	
	@Test
	void validarActualziacionCliente() {
		
		Long idCliente=Long.valueOf(1);
		String correoMod="abcde@abc.com";
		Cliente cliente=clienteService.recuperarById(idCliente).get();
		String correoOrg=cliente.getCorreo();
		cliente.setCorreo(correoMod);
		cliente=clienteService.guardar(cliente);
		assertEquals(correoMod, cliente.getCorreo());
		cliente.setCorreo(correoOrg);
		cliente=clienteService.guardar(cliente);
		assertEquals(correoOrg, cliente.getCorreo());
		
	}

}

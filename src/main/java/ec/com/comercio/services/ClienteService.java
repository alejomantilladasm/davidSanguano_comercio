package ec.com.comercio.services;


import ec.com.comercio.common.CommonService;
import ec.com.comercio.entity.Cliente;

public interface ClienteService extends CommonService<Cliente> {
	
	public Cliente recuperarPorCedula(String ci);
	
}

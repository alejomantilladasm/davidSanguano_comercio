package ec.com.comercio.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.comercio.common.CommonServiceImpl;
import ec.com.comercio.entity.Cliente;
import ec.com.comercio.repository.ClienteRepository;

@Service
public class ClienteServiceImpl extends CommonServiceImpl<Cliente, ClienteRepository> implements ClienteService {

	@Override
	@Transactional(readOnly = true)
	public Cliente recuperarPorCedula(String ci) {
		return repository.recuperarClientePorCedula(ci);
	}
	
}

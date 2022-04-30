package ec.com.comercio.services;

import org.springframework.stereotype.Service;

import ec.com.comercio.common.CommonServiceImpl;
import ec.com.comercio.entity.Tienda;
import ec.com.comercio.repository.TiendaRepository;

@Service
public class TiendaServiceImpl extends CommonServiceImpl<Tienda, TiendaRepository> implements TiendaService {

}

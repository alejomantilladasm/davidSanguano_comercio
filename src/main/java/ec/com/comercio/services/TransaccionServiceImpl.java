package ec.com.comercio.services;

import org.springframework.stereotype.Service;

import ec.com.comercio.common.CommonServiceImpl;
import ec.com.comercio.entity.Transaccion;
import ec.com.comercio.repository.TransaccionRepository;

@Service
public class TransaccionServiceImpl extends CommonServiceImpl<Transaccion, TransaccionRepository> implements TransaccionService {

}

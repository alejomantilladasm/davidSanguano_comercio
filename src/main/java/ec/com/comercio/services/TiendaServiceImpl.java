package ec.com.comercio.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.comercio.common.CommonServiceImpl;
import ec.com.comercio.entity.Producto;
import ec.com.comercio.entity.Tienda;
import ec.com.comercio.repository.TiendaRepository;

@Service
public class TiendaServiceImpl extends CommonServiceImpl<Tienda, TiendaRepository> implements TiendaService {

	@Override
	@Transactional(readOnly = true)
	public boolean existeProductoTienda(Long idTienda, Producto producto) {
		Optional<Tienda> o=recuperarById(idTienda);
		Tienda tiendaDb=o.get();
		for(Producto p:tiendaDb.getProductos()) {
			if(p.equals(producto)) {
				return true;
			}
		}
		return false;
	}

}

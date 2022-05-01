package ec.com.comercio.services;

import ec.com.comercio.common.CommonService;
import ec.com.comercio.entity.Producto;
import ec.com.comercio.entity.Tienda;

public interface TiendaService extends CommonService<Tienda>{

	public boolean existeProductoTienda(Long idTienda, Producto producto);
	
}

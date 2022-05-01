package ec.com.comercio.services;

import ec.com.comercio.common.CommonService;
import ec.com.comercio.entity.Producto;

public interface ProductoService extends CommonService<Producto> {
	
	public Producto actualizarStock(Producto producto, int valor);

}

package ec.com.comercio.services;

import java.util.List;

import ec.com.comercio.common.CommonService;
import ec.com.comercio.entity.Cliente;
import ec.com.comercio.entity.Transaccion;
import ec.com.comercio.util.Item;

public interface TransaccionService extends CommonService<Transaccion>{
	
	
	public Cliente validarCliente(Long idCliente);	
	public boolean validarTiendas(List<Item> items);	
	public boolean validarProductos(List<Item> items);
	public boolean validarExistencia(List<Item> items);
	public void descontarStock(Long idProducto, int valor);



}

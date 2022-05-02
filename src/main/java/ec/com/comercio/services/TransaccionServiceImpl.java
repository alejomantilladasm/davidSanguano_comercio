package ec.com.comercio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.comercio.common.CommonServiceImpl;
import ec.com.comercio.entity.Cliente;
import ec.com.comercio.entity.Producto;
import ec.com.comercio.entity.Tienda;
import ec.com.comercio.entity.Transaccion;
import ec.com.comercio.repository.ClienteRepository;
import ec.com.comercio.repository.ProductoRepository;
import ec.com.comercio.repository.TiendaRepository;
import ec.com.comercio.repository.TransaccionRepository;
import ec.com.comercio.util.Item;

@Service
public class TransaccionServiceImpl extends CommonServiceImpl<Transaccion, TransaccionRepository>
		implements TransaccionService {

	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	ProductoRepository productoRepository;
	@Autowired
	TiendaRepository tiendaRepository;

	@Override
	public Cliente validarCliente(Long idCliente) {
		Optional<Cliente> oC = clienteRepository.findById(idCliente);
		if (oC.isEmpty()) {
			return null;
		}
		return oC.get();
	}

	@Override
	public boolean validarTiendas(List<Item> items) {
		boolean controlTiendas=false;
		for(Item item:items) {
			Optional<Tienda> oT = tiendaRepository.findById(item.getIdTienda());
			if (oT.isEmpty()) {
				controlTiendas=true;
			}
		}
		return controlTiendas;
	}

	@Override
	public boolean validarProductos(List<Item> items) {
		boolean controlProductos=false;
		for(Item item:items) {
			Optional<Producto> oP = productoRepository.findById(item.getIdProducto());
			if (oP.isEmpty()) {
				controlProductos=true;
			}
		}
		return controlProductos;
	}

	@Override
	public boolean validarExistencia(List<Item> items) {
		boolean controlExistencia=false;
		for(Item item:items) {
			Optional<Producto> prOptional = productoRepository.findById(item.getIdProducto());
			if(prOptional.get().getStock()-item.getCantidad()<0) {
				controlExistencia=true;
			}
		}
		return controlExistencia;
	}

	@Override
	public void descontarStock(Long idProducto, int valor) {
		Optional<Producto> proOptional = productoRepository.findById(idProducto);
		proOptional.get().setStock(proOptional.get().getStock()-valor);
		productoRepository.save(proOptional.get());
	}
	
	

}

package ec.com.comercio.services;

import org.springframework.stereotype.Service;

import ec.com.comercio.common.CommonServiceImpl;
import ec.com.comercio.entity.Producto;
import ec.com.comercio.repository.ProductoRepository;

@Service
public class ProductoServiceImpl extends CommonServiceImpl<Producto, ProductoRepository> implements ProductoService {

}

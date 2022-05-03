package ec.com.comercio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ec.com.comercio.entity.Producto;
import ec.com.comercio.services.ProductoService;

@SpringBootTest()
class ServicioProductoTests {

	@Autowired private ProductoService productoService;

	@Test
	void actualziarStockProducto() {
		Long idProducto=Long.valueOf(1);
		int valor=-1;
		Producto producto=productoService.recuperarById(idProducto).get();
		int stockOrg=producto.getStock();
		productoService.actualizarStock(producto, valor);
		producto=productoService.recuperarById(idProducto).get();
		int stockMod=stockOrg+valor;
		assertEquals(stockMod, producto.getStock());
		producto.setStock(stockOrg);
		producto=productoService.guardar(producto);
		assertEquals(stockOrg, producto.getStock());
	}
	
}

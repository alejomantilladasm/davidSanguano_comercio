package ec.com.comercio;

import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ec.com.comercio.entity.Producto;
import ec.com.comercio.services.ProductoService;

@SpringBootApplication
public class ComercioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComercioApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner(ProductoService productoService){
		return args->{			
			try {	
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<Producto[]> response=restTemplate.getForEntity("https://626c16f25267c14d566cb9b0.mockapi.io/api/v1/productos",Producto[].class);
				Producto[] productos=response.getBody();
				for(Producto p:productos) {
					productoService.guardar(p);
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		};
	}

}

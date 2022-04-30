package ec.com.comercio.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.comercio.common.CommonController;
import ec.com.comercio.entity.Tienda;
import ec.com.comercio.services.TiendaService;

@RestController
@RequestMapping("tienda")
public class TiendaController extends CommonController<Tienda, TiendaService> {

	
	
}

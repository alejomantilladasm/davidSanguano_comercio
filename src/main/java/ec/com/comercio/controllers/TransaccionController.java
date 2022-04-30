package ec.com.comercio.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.comercio.common.CommonController;
import ec.com.comercio.entity.Transaccion;
import ec.com.comercio.services.TransaccionService;

@RestController
@RequestMapping("transaccion")
public class TransaccionController extends CommonController<Transaccion, TransaccionService> {

}

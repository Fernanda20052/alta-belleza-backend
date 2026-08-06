package com.maquillaje.maquillajebackend.controller;

import com.maquillaje.maquillajebackend.dto.PedidoRequest;
import com.maquillaje.maquillajebackend.entity.Pedido;
import com.maquillaje.maquillajebackend.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://alta-belleza-frontend.vercel.app"
})
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public Pedido guardarPedido(@RequestBody PedidoRequest request) {

        return pedidoService.guardarPedido(request);

    }

    @GetMapping
    public List<Pedido> obtenerPedidos() {

        return pedidoService.obtenerPedidos();

    }

    @GetMapping("/usuario/{usuario}")
    public List<Map<String, Object>> obtenerPedidosPorUsuario(

            @PathVariable String usuario

    ) {

        return pedidoService.obtenerPedidosConProductos(usuario);

    }

}
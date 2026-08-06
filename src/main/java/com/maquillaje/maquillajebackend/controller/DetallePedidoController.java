package com.maquillaje.maquillajebackend.controller;

import com.maquillaje.maquillajebackend.entity.DetallePedido;
import com.maquillaje.maquillajebackend.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
@CrossOrigin(origins = "http://localhost:5173")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @GetMapping("/{pedidoId}")
    public List<DetallePedido> obtenerDetalles(

            @PathVariable Long pedidoId

    ) {

        return detallePedidoRepository.findByPedidoId(pedidoId);

    }

}
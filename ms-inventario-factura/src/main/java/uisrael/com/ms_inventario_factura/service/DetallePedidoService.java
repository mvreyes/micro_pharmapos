package uisrael.com.ms_inventario_factura.service;

import uisrael.com.ms_inventario_factura.entity.DetallePedido;

import java.util.List;

public interface DetallePedidoService {
    List<DetallePedido> listaDetallesPedidos();
    List<DetallePedido> listaDetallesPedido(String idCabeceraPedido);
    DetallePedido crearDetallePedido(DetallePedido detallePedido);
}

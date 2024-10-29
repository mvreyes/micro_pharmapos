package uisrael.com.ms_inventario_factura.service;

import uisrael.com.ms_inventario_factura.entity.DetalleInventario;

import java.util.List;

public interface DetalleInventarioService {
    List<DetalleInventario> listaDetallesInventarios();
    List<DetalleInventario> listaDetallesInventario(String idDetalleInventario);
    DetalleInventario crearDetalleInventario(DetalleInventario detalleInventario);
}

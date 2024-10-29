package uisrael.com.ms_inventario_factura.service;

import uisrael.com.ms_inventario_factura.entity.DetalleFactura;

import java.util.List;

public interface DetalleFacturaService {
    List<DetalleFactura> listaDetallesFacturas();
    List<DetalleFactura> listaDetallesFactura(String idCabeceraFactura);
    DetalleFactura crearDetalleFactura(DetalleFactura detalleFactura);
}

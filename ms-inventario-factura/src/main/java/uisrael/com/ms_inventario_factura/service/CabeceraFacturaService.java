package uisrael.com.ms_inventario_factura.service;

import uisrael.com.ms_inventario_factura.entity.CabeceraFactura;

import java.util.Date;
import java.util.List;

public interface CabeceraFacturaService {
    List<CabeceraFactura> listaCabecerasFacturas();
    CabeceraFactura obtenerPorId(String idCabeceraFactura);
    CabeceraFactura crearCabeceraFactura(CabeceraFactura cabeceraFactura);
    Long contarFacturas();
    List<CabeceraFactura> listaVentaDetallado(Date fechaDesde, Date fechaHasta, Long idAgencia);
}

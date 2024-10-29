package uisrael.com.ms_inventario_factura.service;

import uisrael.com.ms_inventario_factura.entity.CabeceraInventario;

import java.util.List;

public interface CabeceraInventarioService {
    List<CabeceraInventario> listaCabecerasInventarios();
    CabeceraInventario obtenerPorId(String id);
    CabeceraInventario crearCabeceraInventario(CabeceraInventario cabeceraInventario);
    Long contarInventarios();
}

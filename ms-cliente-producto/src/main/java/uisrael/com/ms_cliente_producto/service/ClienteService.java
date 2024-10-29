package uisrael.com.ms_cliente_producto.service;

import uisrael.com.ms_cliente_producto.entity.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> listaClientes();
    Cliente obtenerPorId(Long id);
    Cliente crearCliente(Cliente cliente);
    Cliente actualizarCliente(Cliente cliente);
    Cliente estadoCliente(Long id);
}

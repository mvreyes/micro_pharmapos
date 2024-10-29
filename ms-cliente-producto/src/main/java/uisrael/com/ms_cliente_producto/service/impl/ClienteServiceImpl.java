package uisrael.com.ms_cliente_producto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uisrael.com.ms_cliente_producto.entity.Cliente;
import uisrael.com.ms_cliente_producto.repository.ClienteRepository;
import uisrael.com.ms_cliente_producto.service.ClienteService;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listaClientes(){
        return clienteRepository.findAll(Sort.by(Sort.Direction.DESC, "idCliente"));
    }

    @Override
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public Cliente actualizarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public Cliente estadoCliente(Long id) {
        Cliente clientedb = clienteRepository.findById(id).orElse(null);
        if(clientedb != null) {
            if(clientedb.getEstado() == 1) {
                clientedb.setEstado(0);
            }else {
                clientedb.setEstado(1);
            }
            return clienteRepository.save(clientedb);
        }
        return null;
    }
}

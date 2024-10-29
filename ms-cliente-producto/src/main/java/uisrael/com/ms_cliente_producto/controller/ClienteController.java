package uisrael.com.ms_cliente_producto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uisrael.com.ms_cliente_producto.entity.Cliente;
import uisrael.com.ms_cliente_producto.service.ClienteService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @CrossOrigin
    @GetMapping
    public Map<String, List<Cliente>> listaClientes() {
        return Collections.singletonMap("clientes", clienteService.listaClientes());
    }

    @CrossOrigin
    @PostMapping("/nuevo")
    public ResponseEntity<?> guardarCliente(@RequestBody Cliente cliente, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return validar(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crearCliente(cliente));
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> detalleCliente(@PathVariable Long id){
        Cliente cliente = clienteService.obtenerPorId(id);
        if (cliente != null){
            return ResponseEntity.ok(cliente);
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @PutMapping("editar/{id}")
    public ResponseEntity<?> actualizarcliente(@PathVariable Long id, @RequestBody Cliente cliente, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return validar(bindingResult);
        }

        Cliente clienteDB = clienteService.obtenerPorId(id);
        if(clienteDB != null){
            clienteDB.setNombre(cliente.getNombre());
            clienteDB.setApellido(cliente.getApellido());
            clienteDB.setCedulaRuc(cliente.getCedulaRuc());
            clienteDB.setEmail(cliente.getEmail());
            clienteDB.setDireccion(cliente.getDireccion());
            clienteDB.setTelefono(cliente.getTelefono());
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.actualizarCliente(clienteDB));
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @GetMapping ("/cambiar/estado/{id}")
    public ResponseEntity<?> cambiarEstadoCliente(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.estadoCliente(id));
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errores);
    }
}

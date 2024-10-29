package uisrael.com.ms_cliente_producto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uisrael.com.ms_cliente_producto.entity.Cliente;
import uisrael.com.ms_cliente_producto.entity.Producto;
import uisrael.com.ms_cliente_producto.entity.StockProducto;
import uisrael.com.ms_cliente_producto.service.ProductoService;
import uisrael.com.ms_cliente_producto.service.StockProductoService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private StockProductoService stockProductoService;

    @CrossOrigin
    @GetMapping
    public Map<String, List<Producto>> listaProductos() {
        return Collections.singletonMap("productos", productoService.listaProductos());
    }

    @CrossOrigin
    @PostMapping("/nuevo")
    public ResponseEntity<?> guardarProducto(@RequestBody Producto producto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return validar(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crearProducto(producto));
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> detalleProducto(@PathVariable Long id){
        Producto producto = productoService.obtenerPorId(id);
        if (producto != null){
            return ResponseEntity.ok(producto);
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @PutMapping("editar/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return validar(bindingResult);
        }

        Producto productodb = productoService.obtenerPorId(id);
        if(productodb != null){
            productodb.setCodigoPrincipal(producto.getCodigoPrincipal());
            productodb.setNombre(producto.getNombre());
            productodb.setCategoria(producto.getCategoria());
            productodb.setPrecio(producto.getPrecio());
            productodb.setObservacion(producto.getObservacion());
            return ResponseEntity.status(HttpStatus.CREATED).body(productoService.actualizarProducto(productodb));
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @GetMapping ("/cambiar/estado/{id}")
    public ResponseEntity<?> cambiarEstadoProducto(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productoService.estadoProducto(id));
    }

    @CrossOrigin
    @GetMapping("/buscar/{busqueda}")
    public Map<String, List<Producto>> cargarProductos(@PathVariable String busqueda){
        List<Producto> productos = productoService.findByCodigoPrincipalOrNombre(busqueda);
        return Collections.singletonMap("productos", productos);
    }

    @CrossOrigin
    @GetMapping("/stock/producto/agencia/{idProducto}/{idAgencia}")
    public @ResponseBody StockProducto stockProductoAgencia(@PathVariable Long idProducto, @PathVariable Long idAgencia){
        StockProducto stockProducto = stockProductoService.findByIdProductoAndIdAgencia(idProducto, idAgencia);
        return stockProducto;
    }

    @CrossOrigin
    @GetMapping("/generar/stock/producto/agencia/{idProducto}/{idAgencia}/{cantidad}/{tipo}")
    public @ResponseBody StockProducto generarStockProductoAgencia(@PathVariable Long idProducto, @PathVariable Long idAgencia, @PathVariable int cantidad, @PathVariable String tipo){
        StockProducto stockProducto = stockProductoService.generarStockProducto(idProducto, idAgencia, cantidad, tipo);
        return stockProducto;
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errores);
    }
}

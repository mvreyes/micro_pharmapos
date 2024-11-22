package uisrael.com.ms_inventario_factura.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import uisrael.com.ms_inventario_factura.dto.ProductoDTO;
import uisrael.com.ms_inventario_factura.entity.CabeceraPedido;
import uisrael.com.ms_inventario_factura.entity.DetallePedido;
import uisrael.com.ms_inventario_factura.model.Producto;
import uisrael.com.ms_inventario_factura.model.StockProducto;
import uisrael.com.ms_inventario_factura.service.CabeceraPedidoService;
import uisrael.com.ms_inventario_factura.service.DetallePedidoService;
import uisrael.com.ms_inventario_factura.service.S3Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private CabeceraPedidoService cabeceraPedidoService;
    @Autowired
    private DetallePedidoService detallePedidoService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private S3Service s3Service;

    @CrossOrigin
    @GetMapping
    public Map<String, List<CabeceraPedido>> listaPedidos() {
        return Collections.singletonMap("pedidos", cabeceraPedidoService.listaCabecerasPedidos());
    }

    @CrossOrigin
    @PostMapping("/nuevo")
    public ResponseEntity<?> guardarPedido(@RequestParam("idAgencia") Long idAgencia,
            @RequestParam("idCliente") Long idCliente,
            @RequestParam("total") double total,
            @RequestParam("imagenPago") File imagenPago,
            @RequestParam("productos") String productos) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<ProductoDTO> listaProductos = mapper.readValue(productos, new TypeReference<List<ProductoDTO>>() {});
            LocalDate currentDate = LocalDate.now();
            Long secuencialPedido = cabeceraPedidoService.contarPedidos();
            String numeroPedido = String.format("%0" + 9 + "d", (secuencialPedido+1));
            for (ProductoDTO detalleProducto : listaProductos) {
                //Validar stock
                StockProducto stockProducto = restTemplate.getForObject("http://localhost:8002/productos/stock/producto/agencia/"+detalleProducto.getIdProducto()+"/"+idAgencia, StockProducto.class);
                Producto producto = restTemplate.getForObject("http://localhost:8002/productos/"+detalleProducto.getIdProducto(), Producto.class);
                if(stockProducto == null || stockProducto.getCantidad() == 0) {
                    return ResponseEntity.badRequest().body(Collections.singletonMap("message", "El producto "+producto.getNombre()+" no tiene stock disponible"));
                }
                if(detalleProducto.getCantidad() > stockProducto.getCantidad()) {
                    return ResponseEntity.badRequest().body(Collections.singletonMap("message", "El producto "+producto.getNombre()+" no tiene stock disponible, solo tiene: "+stockProducto.getCantidad()));
                }
            }
            Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            CabeceraPedido cabeceraPedidodb = new CabeceraPedido();
            cabeceraPedidodb.setIdAgencia(idAgencia);
            cabeceraPedidodb.setIdCliente(idCliente);
            cabeceraPedidodb.setFechaEmision(date);
            cabeceraPedidodb.setNumeroPedido(numeroPedido);
            cabeceraPedidodb.setTotal(total);
            String imageUrl = null;
            imageUrl = s3Service.subirArchivo(imagenPago);
            cabeceraPedidodb.setImagen(imageUrl);
            cabeceraPedidoService.crearCabeceraPedido(cabeceraPedidodb);
            for (ProductoDTO detalleProducto : listaProductos) {
                DetallePedido detallePedidodb = new DetallePedido();
                detallePedidodb.setIdCabeceraPedido(cabeceraPedidodb.getIdCabeceraPedido());
                detallePedidodb.setIdProducto(detalleProducto.getIdProducto());
                detallePedidodb.setPrecio(detalleProducto.getPrecio());
                detallePedidodb.setCantidad(detalleProducto.getCantidad());
                detallePedidodb.setTotal(detalleProducto.getTotal());
                detallePedidoService.crearDetallePedido(detallePedidodb);
                // Guardar stock
                restTemplate.getForObject("http://localhost:8002/productos/generar/stock/producto/agencia/"+detallePedidodb.getIdProducto()+"/"+cabeceraPedidodb.getIdAgencia()+"/"+detallePedidodb.getCantidad()+"/F", StockProducto.class);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(cabeceraPedidodb);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin
    @GetMapping("/ver/{idCabeceraPedido}")
    public ResponseEntity<?> verPedido(@PathVariable String idCabeceraPedido) {
        CabeceraPedido pedido = cabeceraPedidoService.obtenerPorId(idCabeceraPedido);
        if (pedido != null){
            return ResponseEntity.ok(pedido);
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @GetMapping("/secuencial")
    public ResponseEntity<?> secuencialPedido() {
        return ResponseEntity.ok(cabeceraPedidoService.contarPedidos());
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errores);
    }
}

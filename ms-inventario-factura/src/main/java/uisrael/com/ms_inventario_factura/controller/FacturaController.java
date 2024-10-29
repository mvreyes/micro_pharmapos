package uisrael.com.ms_inventario_factura.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import uisrael.com.ms_inventario_factura.entity.CabeceraFactura;
import uisrael.com.ms_inventario_factura.entity.DetalleFactura;
import uisrael.com.ms_inventario_factura.model.Producto;
import uisrael.com.ms_inventario_factura.model.StockProducto;
import uisrael.com.ms_inventario_factura.service.CabeceraFacturaService;
import uisrael.com.ms_inventario_factura.service.DetalleFacturaService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/facturas")
public class FacturaController {
    @Autowired
    private CabeceraFacturaService cabeceraFacturaService;
    @Autowired
    private DetalleFacturaService detalleFacturaService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;

    @CrossOrigin
    @GetMapping
    public Map<String, List<CabeceraFactura>> listaFacturas() {
        return Collections.singletonMap("facturas", cabeceraFacturaService.listaCabecerasFacturas());
    }

    @CrossOrigin
    @PostMapping("/nueva")
    public ResponseEntity<?> guardarFactura(@RequestBody Object cabeceraFactura, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return validar(bindingResult);
        }

        JsonNode jsonNode = objectMapper.valueToTree(cabeceraFactura);
        LocalDate currentDate = LocalDate.now();
        Long secuencialFactura = cabeceraFacturaService.contarFacturas();
        String numeroFactura = String.format("%0" + 9 + "d", (secuencialFactura+1));
        JsonNode productosNode = jsonNode.get("productos");
        for (JsonNode productoNode : productosNode) {
            //Validar stock
            StockProducto stockProducto = restTemplate.getForObject("http://localhost:8002/productos/stock/producto/agencia/"+productoNode.get("idProducto").asLong()+"/"+jsonNode.get("idAgencia").asLong(), StockProducto.class);
            Producto producto = restTemplate.getForObject("http://localhost:8002/productos/"+productoNode.get("idProducto").asLong(), Producto.class);
            if(stockProducto == null || stockProducto.getCantidad() == 0) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "El producto "+producto.getNombre()+" no tiene stock disponible"));
            }
            if(productoNode.get("cantidad").asInt() > stockProducto.getCantidad()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "El producto "+producto.getNombre()+" no tiene stock disponible, solo tiene: "+stockProducto.getCantidad()));
            }
        }
        Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        CabeceraFactura cabeceraFacturadb = new CabeceraFactura();
        cabeceraFacturadb.setIdAgencia(jsonNode.get("idAgencia").asLong());
        cabeceraFacturadb.setIdCliente(jsonNode.get("idCliente").asLong());
        cabeceraFacturadb.setFechaEmision(date);
        cabeceraFacturadb.setNumeroFactura(numeroFactura);
        cabeceraFacturadb.setTotal(jsonNode.get("total").asDouble());
        cabeceraFacturaService.crearCabeceraFactura(cabeceraFacturadb);
        for (JsonNode productoNode : productosNode) {
            DetalleFactura detalleFacturadb = new DetalleFactura();
            detalleFacturadb.setIdCabeceraFactura(cabeceraFacturadb.getIdCabeceraFactura());
            detalleFacturadb.setIdProducto(productoNode.get("idProducto").asLong());
            detalleFacturadb.setPrecio(productoNode.get("precio").asDouble());
            detalleFacturadb.setCantidad(productoNode.get("cantidad").asInt());
            detalleFacturadb.setTotal(productoNode.get("total").asDouble());
            detalleFacturaService.crearDetalleFactura(detalleFacturadb);
            // Guardar stock
            restTemplate.getForObject("http://localhost:8002/productos/generar/stock/producto/agencia/"+detalleFacturadb.getIdProducto()+"/"+cabeceraFacturadb.getIdAgencia()+"/"+detalleFacturadb.getCantidad()+"/F", StockProducto.class);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cabeceraFacturadb);
    }

    @CrossOrigin
    @GetMapping("/ver/{idCabeceraFactura}")
    public ResponseEntity<?> verFactura(@PathVariable String idCabeceraFactura) {
        CabeceraFactura factura = cabeceraFacturaService.obtenerPorId(idCabeceraFactura);
        if (factura != null){
            return ResponseEntity.ok(factura);
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @GetMapping("/secuencial")
    public ResponseEntity<?> secuencialFactura() {
        return ResponseEntity.ok(cabeceraFacturaService.contarFacturas());
    }

    @CrossOrigin
    @GetMapping("/ventas-detallado")
    public ResponseEntity<?> listaVentaDetallado(@RequestParam(name="fechaDesde", required=false) String fechaDesde,
                                      @RequestParam(name="fechaHasta", required=false) String fechaHasta,
                                      @RequestParam(name="idAgencia", required=false) Long idAgencia) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date desde = null;
        Date hasta = null;
        if (fechaDesde != null && !fechaDesde.isEmpty()) {
            desde = formatter.parse(fechaDesde);
        }

        if (fechaHasta != null && !fechaHasta.isEmpty()) {
            hasta = formatter.parse(fechaHasta);
        }
        if (fechaDesde != null && !fechaDesde.isEmpty() && fechaHasta != null && !fechaHasta.isEmpty()) {
            List<CabeceraFactura> cabecerasFacturas = cabeceraFacturaService.listaVentaDetallado(desde, hasta, idAgencia);
            return ResponseEntity.ok(Collections.singletonMap("facturas", cabecerasFacturas));
        }

        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errores);
    }
}

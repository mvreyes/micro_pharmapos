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
import uisrael.com.ms_inventario_factura.entity.CabeceraInventario;
import uisrael.com.ms_inventario_factura.entity.DetalleInventario;
import uisrael.com.ms_inventario_factura.model.StockProducto;
import uisrael.com.ms_inventario_factura.service.CabeceraInventarioService;
import uisrael.com.ms_inventario_factura.service.DetalleInventarioService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/inventarios")
public class InventarioController {
    @Autowired
    CabeceraInventarioService cabeceraInventarioService;
    @Autowired
    DetalleInventarioService detalleInventarioService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;

    @CrossOrigin
    @GetMapping
    public Map<String, List<CabeceraInventario>> listaInventarios() {
        return Collections.singletonMap("inventarios", cabeceraInventarioService.listaCabecerasInventarios());
    }

    @CrossOrigin
    @PostMapping("/nuevo")
    public ResponseEntity<?> guardarInventario(@RequestBody Object cabeceraInventario, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return validar(bindingResult);
        }

        JsonNode jsonNode = objectMapper.valueToTree(cabeceraInventario);
        LocalDate currentDate = LocalDate.now();
        Long secuencialInventario = cabeceraInventarioService.contarInventarios();
        String numeroInventario = String.format("%0" + 9 + "d", (secuencialInventario+1));
        Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        CabeceraInventario cabeceraInventariodb = new CabeceraInventario();
        cabeceraInventariodb.setIdAgencia(jsonNode.get("idAgencia").asLong());
        cabeceraInventariodb.setFechaInventario(date);
        cabeceraInventariodb.setNumeroInventario(numeroInventario);
        cabeceraInventarioService.crearCabeceraInventario(cabeceraInventariodb);
        JsonNode productosNode = jsonNode.get("productos");
        for (JsonNode productoNode : productosNode) {
            DetalleInventario detalleInventariodb = new DetalleInventario();
            detalleInventariodb.setIdCabeceraInventario(cabeceraInventariodb.getIdCabeceraInventario());
            detalleInventariodb.setIdProducto(productoNode.get("idProducto").asLong());
            detalleInventariodb.setCantidad(productoNode.get("cantidad").asInt());
            detalleInventarioService.crearDetalleInventario(detalleInventariodb);
            // Guardar stock
            restTemplate.getForObject("http://localhost:8002/productos/generar/stock/producto/agencia/"+detalleInventariodb.getIdProducto()+"/"+cabeceraInventariodb.getIdAgencia()+"/"+detalleInventariodb.getCantidad()+"/I", StockProducto.class);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cabeceraInventariodb);
    }

    @CrossOrigin
    @GetMapping("/secuencial")
    public ResponseEntity<?> secuencialInventario() {
        return ResponseEntity.ok(cabeceraInventarioService.contarInventarios());
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errores);
    }
}

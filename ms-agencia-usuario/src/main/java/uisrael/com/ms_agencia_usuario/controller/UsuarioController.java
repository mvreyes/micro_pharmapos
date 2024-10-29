package uisrael.com.ms_agencia_usuario.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uisrael.com.ms_agencia_usuario.entity.Agencia;
import uisrael.com.ms_agencia_usuario.entity.AgenciaUsuario;
import uisrael.com.ms_agencia_usuario.entity.Usuario;
import uisrael.com.ms_agencia_usuario.service.AgenciaService;
import uisrael.com.ms_agencia_usuario.service.AgenciaUsuarioService;
import uisrael.com.ms_agencia_usuario.service.UsuarioService;

import java.util.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AgenciaService agenciaService;
    @Autowired
    private AgenciaUsuarioService agenciaUsuarioService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @CrossOrigin
    @GetMapping
    public Map<String, List<Usuario>> listaUsuarios() {
        return Collections.singletonMap("usuarios", usuarioService.listaUsuarios());
    }

    @CrossOrigin
    @PostMapping("/nuevo")
    public ResponseEntity<?> guardarUsuario(@RequestBody Object usuario, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return validar(bindingResult);
        }
        JsonNode jsonNode = objectMapper.valueToTree(usuario);
        Usuario usuariodb = new Usuario();
        usuariodb.setNombre(jsonNode.get("nombre").asText());
        usuariodb.setApellido(jsonNode.get("apellido").asText());
        usuariodb.setRol(jsonNode.get("rol").asText());
        usuariodb.setEmail(jsonNode.get("email").asText());
        usuariodb.setPassword(passwordEncoder.encode(jsonNode.get("password").asText()));
        //usuariodb.setPassword(jsonNode.get("password").asText());
        usuarioService.crearUsuario(usuariodb);
        JsonNode agenciasNode = jsonNode.get("agencias");
        if (agenciasNode != null && agenciasNode.isArray()) {
            for (JsonNode agenciaNode : agenciasNode) {
                AgenciaUsuario agenciaUsuariodb = new AgenciaUsuario();
                agenciaUsuariodb.setIdUsuario(usuariodb.getIdUsuario());
                agenciaUsuariodb.setIdAgencia(agenciaNode.get("idAgencia").asLong());
                agenciaUsuarioService.crearAgenciaUsuario(agenciaUsuariodb);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuariodb);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> detalleUsuario(@PathVariable Long id){
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario != null){
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @PutMapping("editar/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Object usuario, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return validar(bindingResult);
        }
        JsonNode jsonNode = objectMapper.valueToTree(usuario);
        Usuario usuariodb = usuarioService.obtenerPorId(id);
        usuariodb.setNombre(jsonNode.get("nombre").asText());
        usuariodb.setApellido(jsonNode.get("apellido").asText());
        usuariodb.setRol(jsonNode.get("rol").asText());
        usuariodb.setEmail(jsonNode.get("email").asText());
        if(jsonNode.get("email").asText() != "") {
            usuariodb.setPassword(passwordEncoder.encode(jsonNode.get("password").asText()));
            //usuariodb.setPassword(jsonNode.get("password").asText());
        }
        usuarioService.actualizarUsuario(id, usuariodb);
        JsonNode agenciasNode = jsonNode.get("agencias");
        if (agenciasNode != null && agenciasNode.isArray()) {
            for (JsonNode agenciaNode : agenciasNode) {
                AgenciaUsuario agenciaUsuariodb = new AgenciaUsuario();
                agenciaUsuariodb.setIdUsuario(id);
                agenciaUsuariodb.setIdAgencia(agenciaNode.get("idAgencia").asLong());
                agenciaUsuarioService.agregarUsuarioAgencia(id, agenciaNode.get("idAgencia").asLong(), agenciaUsuariodb);
            }
        }
        return ResponseEntity.ok().body(usuariodb);
    }

    @CrossOrigin
    @GetMapping ("/cambiar/estado/{id}")
    public ResponseEntity<?> cambiarEstadoUsuario(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.estadoUsuario(id));
    }

    @CrossOrigin
    @GetMapping ("/agencia/cambiar/estado/{idAgenciaUsuario}")
    public ResponseEntity<?> cambiarEstadoUsuarioAgencia(@PathVariable Long idAgenciaUsuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agenciaUsuarioService.estadoAgenciaUsuario(idAgenciaUsuario));
    }

    @CrossOrigin
    @GetMapping("buscar/{email}")
    public ResponseEntity<?> buscarUsuarioEmail(@PathVariable String email){
        Usuario usuario = usuarioService.buscarUsuarioEmail(email);
        if (usuario != null){
            return ResponseEntity.ok(usuario);
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

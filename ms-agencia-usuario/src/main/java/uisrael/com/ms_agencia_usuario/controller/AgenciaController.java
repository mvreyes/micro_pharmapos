package uisrael.com.ms_agencia_usuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uisrael.com.ms_agencia_usuario.entity.Agencia;
import uisrael.com.ms_agencia_usuario.service.AgenciaService;
import uisrael.com.ms_agencia_usuario.service.AgenciaUsuarioService;
import uisrael.com.ms_agencia_usuario.service.UsuarioService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agencias")
public class AgenciaController {
    @Autowired
    private AgenciaService agenciaService;
    @Autowired
    private AgenciaUsuarioService agenciaUsuarioService;
    @Autowired
    private UsuarioService usuarioService;

    @CrossOrigin
    @GetMapping
    public Map<String, List<Agencia>> listaAgencias() {
        return Collections.singletonMap("agencias", agenciaService.listaAgencias());
    }

    @CrossOrigin
    @PostMapping("/nueva")
    public ResponseEntity<?> guardarAgencia(@RequestBody Agencia agencia, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return validar(bindingResult);
        }

        Agencia agenciadb = new Agencia();
        agenciadb.setNombre(agencia.getNombre());
        agenciadb.setDireccion(agencia.getDireccion());
        return ResponseEntity.status(HttpStatus.CREATED).body(agenciaService.crearAgencia(agenciadb));
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> detalleAgencia(@PathVariable Long id){
        Agencia agencia = agenciaService.obtenerPorId(id);
        if (agencia != null){
            return ResponseEntity.ok(agencia);
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @PutMapping("editar/{id}")
    public ResponseEntity<?> actualizaragencia(@PathVariable Long id, @RequestBody Agencia agencia, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return validar(bindingResult);
        }

        Agencia clienteDB = agenciaService.obtenerPorId(id);
        if(clienteDB != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(agenciaService.actualizarAgencia(id, agencia));
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @GetMapping ("/cambiar/estado/{id}")
    public ResponseEntity<?> cambiarEstadoAgencia(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agenciaService.estadoAgencia(id));
    }

    /*@GetMapping("/seleccionar")
    public String seleccionarAgenciaUsuario() {
        List<AgenciaUsuario> agenciasUsuario = agenciaUsuarioService.listaAgenciasUsuario(usuario.get().getIdUsuario());
        return "agencias/seleccionar_agencia";
    }

    @GetMapping("/seleccionada/{idAgencia}")
    public String agenciaSeleccionada(@PathVariable Long idAgencia) {
        List<AgenciaUsuario> agenciasUsuario = agenciaUsuarioService.listaAgenciasUsuario(usuario.get().getIdUsuario());
        for (AgenciaUsuario agenciaUsuario : agenciasUsuario) {
            agenciaUsuarioService.inactivarSeleccionAgencia(agenciaUsuario.getIdAgenciaUsuario());
        }
        agenciaUsuarioService.seleccionarUsuarioAgencia(usuario.get().getIdUsuario(), idAgencia);
        return "redirect:/home";
    }*/

    private static ResponseEntity<Map<String, String>> validar(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errores);
    }
}

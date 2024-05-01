package com.example.Project.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Project.models.UsuarioModel;
import com.example.Project.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping()
    public ArrayList<UsuarioModel> obtenerUsuarios(){
        return usuarioService.obtenerUsuarios();
    }

     @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> obtenerUsuarioPorId(@PathVariable("id") Long id) {
        Optional<UsuarioModel> usuario = usuarioService.obtenerPorId(id);
        return usuario.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> actualizarUsuario(@PathVariable("id") Long id,
                                                      @RequestBody UsuarioModel usuarioActualizar) {
        Optional<UsuarioModel> usuario = usuarioService.obtenerPorId(id);
        if (usuario.isPresent()) {
            UsuarioModel usuarioExistente = usuario.get();
            usuarioExistente.setNombre(usuarioActualizar.getNombre());
            usuarioExistente.setEmail(usuarioActualizar.getEmail());
            usuarioExistente.setPrioridad(usuarioActualizar.getPrioridad());
            usuarioExistente.setPassword(usuarioActualizar.getPassword());
            usuarioExistente.setTelefono(usuarioActualizar.getTelefono());
            
            UsuarioModel usuarioActualizado = usuarioService.guardarUsuario(usuarioExistente);
            return ResponseEntity.ok(usuarioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public UsuarioModel guardarUsuario(@RequestBody UsuarioModel usuario){
        return this.usuarioService.guardarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable("id") Long id) {
        if (usuarioService.eliminarUsuario(id)) {
            return ResponseEntity.ok("Se eliminó el usuario con ID: " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

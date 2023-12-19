package com.sapatariasmota.MotAccountWeb.controllers;

import com.sapatariasmota.MotAccountWeb.dtos.UsuarioRecordDto;
import com.sapatariasmota.MotAccountWeb.exception.UsuarioNotAuthorizedException;
import com.sapatariasmota.MotAccountWeb.exception.UsuarioNotFoundException;
import com.sapatariasmota.MotAccountWeb.models.UsuarioModel;
import com.sapatariasmota.MotAccountWeb.repositories.UsuarioRepository;
import com.sapatariasmota.MotAccountWeb.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.experimental.FieldNameConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioModel> createUsuario(@Valid @RequestBody UsuarioRecordDto usuarioRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUsuario(usuarioRecordDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUsuario(@PathVariable(value = "id") UUID id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getUsuarioById(id));
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> getAllUsuarios() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getAllUsuarios());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUsuario(@PathVariable(value = "id") UUID id, @Valid @RequestBody UsuarioRecordDto usuarioRecordDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.updateUsuario(id, usuarioRecordDto));
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable(value = "id") UUID id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário deleted successfully.");
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> validarSenha(@Valid @RequestBody UsuarioModel usuarioModel) {
        try {
            usuarioService.validarSenha(usuarioModel);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UsuarioNotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

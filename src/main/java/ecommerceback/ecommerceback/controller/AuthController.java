package ecommerceback.ecommerceback.controller;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import ecommerceback.ecommerceback.model.AutenticacaoModel;
import ecommerceback.ecommerceback.model.AuthenticationDTO;
import ecommerceback.ecommerceback.model.RegisterDTO;
import ecommerceback.ecommerceback.model.LoginResponseDTO;
import ecommerceback.ecommerceback.model.UsuarioEntity;
import ecommerceback.ecommerceback.repository.UsuarioRepository;
import ecommerceback.ecommerceback.service.TokenService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthController {

    @Autowired
    public UsuarioRepository repositorio;

    @Autowired
    public AuthenticationManager auth_manager;

    @Autowired
    private TokenService token_service;


    @PostMapping(value="/auth")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        System.out.println(usernamePassword);
        var auth = this.auth_manager.authenticate(usernamePassword);
        var token = token_service.generateToken((UsuarioEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


    @GetMapping(value="/auth/verifytoken")
    public boolean verifyToken(@RequestParam String token){
        String _token = token_service.validateToken(token);
        return _token == "" ? false : true;
    }

}
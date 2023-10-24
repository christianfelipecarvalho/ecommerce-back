package ecommerceback.ecommerceback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import ecommerceback.ecommerceback.model.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    UserDetails findByEmail(String email);
    
}
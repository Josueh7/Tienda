package com.Tienda.service;

import com.Tienda.domain.Usuario;
import org.springframework.security.core.userdetails.*;


public interface UsuarioDetailsService {
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    
    public Usuario getUsuarioForUsername(String username);
}

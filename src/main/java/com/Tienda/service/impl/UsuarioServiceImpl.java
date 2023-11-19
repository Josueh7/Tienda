
package com.Tienda.service.impl;

import com.Tienda.dao.UsuarioDao;
import com.Tienda.domain.Rol;
import com.Tienda.domain.Usuario;
import com.Tienda.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserDetailsService")
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {
    
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private HttpSession session;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Buscar usuario po0r el username en la BD
        Usuario usuario = usuarioDao.findByUsername(username);
        
        //Si no existe el usuario
        if (usuario == null) {
            throw new UsernameNotFoundException(username);            
        }
        //Si llega aca es por que el usuario existe en la base de datos
        //Remover atributos de la sesion
        session.removeAttribute("usuarioImagen");
        session.setAttribute("usuarioImagen", usuario.getRutaImagen());
        //Transformar roles a Granted Authority
        var roles = new ArrayList<GrantedAuthority>();
        for(Rol item : usuario.getRoles()){
            roles.add(new SimpleGrantedAuthority(item.getNombre()));
        }
        //Se retorna el user (Clase UserDetails)
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }

    @Override
    public Usuario getUsuarioForUsername(String username) {
        return usuarioDao.findByUsername(username);
    }
    
}

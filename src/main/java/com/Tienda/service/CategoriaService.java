
package com.Tienda.service;

import com.Tienda.domain.Categoria;
import java.util.List;

public interface CategoriaService {
    
    public List<Categoria> getCategorias(boolean activos);//firma de un metodo, metodos que retornan listas en plural
    
    public Categoria getCategoria(Categoria categoria);
    
}

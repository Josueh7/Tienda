package com.Tienda.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data //Para la creacion automatica de los set y get de los atributos.
@Entity 
@Table(name = "categoria")
public class Categoria implements Serializable{
    
    private static final long serialVersionUID = 1L; 
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column (name = "id_categoria")
    private Long idCategoria; //se trannsforma en id_categoria
    private String descripcion;
    private String rutaImagen;
    private boolean activo;

    public Categoria() {}

    public Categoria(String descripcion, String rutaImagen, boolean activo) {
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.activo = activo;
    }
    
    
    
}

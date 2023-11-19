
package com.Tienda.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "rol")
public class Rol implements Serializable {
    
    private static final long serialVersionUID =1L;
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private long idRol;
    
    @NotEmpty
    private String nombre;
    @NotEmpty
    private int id_Usuario;
    

}

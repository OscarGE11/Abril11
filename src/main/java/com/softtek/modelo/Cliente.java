package com.softtek.modelo;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
//@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cliente {
    @EqualsAndHashCode.Include
    //@ToString.Include
    private Integer idCliente;

    // @ToString.Include
    private String nombre;
    private String trabajo;
    private LocalDate fNacimiento;
    private double salario;
    private String pais;
}
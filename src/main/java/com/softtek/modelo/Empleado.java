package com.softtek.modelo;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String apellido;
    private int jefe;
    private LocalDate fechaNacimiento;
    private String ciudad;



}

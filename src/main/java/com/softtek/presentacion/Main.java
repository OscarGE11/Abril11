package com.softtek.presentacion;

import com.softtek.modelo.Empleado;
import com.softtek.persistencia.AccesoEmpleado;
import com.softtek.persistencia.Conexion;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args)  {
        Conexion c1 = new Conexion();

        AccesoEmpleado prueba = new AccesoEmpleado();
        Empleado e1 = new Empleado(201,"Evaristo","Baraja",2, LocalDate.now(),"Madrid");
        Empleado e2 = new Empleado(201,"manolo","perez",2,LocalDate.now(),"Barcelona");

        try {

            prueba.insertarEmpleado(e1);
            System.out.println(prueba.obtenerUno(201));
            prueba.actualizarEmpleado(e2);
            System.out.println(prueba.obtenerUno(201));
            prueba.borrarEmpleado(201);
            System.out.println(prueba.obtenerTodos());

        } catch (ClassNotFoundException e){
            System.out.println(e.toString());
        } catch (SQLException e){
            System.out.println(e.toString());
        }

    }

}

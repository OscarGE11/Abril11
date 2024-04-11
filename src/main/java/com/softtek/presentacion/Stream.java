package com.softtek.presentacion;

import com.softtek.modelo.Empleado;
import com.softtek.persistencia.AccesoEmpleado;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Stream {
    public static void main(String[] args) {
        empleadosJubiladosMayores();
        ciudadesEmpleadosDistintas();

    }

    public static void empleadosJubiladosMayores(){
        AccesoEmpleado aP1 = new AccesoEmpleado();

        try {
            List<Empleado> listaEmpleados = aP1.obtenerTodos();

            List<Empleado> jubiladosMayores= listaEmpleados.stream().filter(e->Period.between(e.getFechaNacimiento(), LocalDate.now()).getYears()>68).collect(Collectors.toList());


            jubiladosMayores.forEach(System.out::println);

        }
        catch(ClassNotFoundException |
                SQLException e)
        {
            System.out.println(e);
        }
    }

    public static void ciudadesEmpleadosDistintas(){

        AccesoEmpleado aP1 = new AccesoEmpleado();

        try {
            List<Empleado> listaEmpleados = aP1.obtenerTodos();

            Set<String> listaCiudades= listaEmpleados.stream().map(Empleado::getCiudad).collect(Collectors.toSet());

            listaCiudades.forEach(System.out::println);



        }
        catch(ClassNotFoundException |
                SQLException e)
        {
            System.out.println(e);
        }

    }
}

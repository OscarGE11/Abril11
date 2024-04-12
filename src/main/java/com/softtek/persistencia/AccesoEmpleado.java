package com.softtek.persistencia;

import com.softtek.modelo.Empleado;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccesoEmpleado extends Conexion {

    public Empleado obtenerUno(int id) throws ClassNotFoundException, SQLException {

        PreparedStatement sentencia;
        ResultSet resultado;

        String sql = "Select employee_id,first_name,last_name,reports_to,birth_date,city from employees " +
                "where employee_id=?";


        abrirConexion();
        sentencia = miConexion.prepareStatement(sql);
        sentencia.setInt(1,id);
        resultado = sentencia.executeQuery();

        resultado.next();

        Empleado empleado = new Empleado(resultado.getInt("employee_id"),
                resultado.getString("first_name"),
                resultado.getString("last_name"),
                resultado.getInt("reports_to"),
                resultado.getDate("birth_date").toLocalDate(),
                resultado.getString("city")

        );
        return empleado;
    }

    public List<Empleado> obtenerTodos() throws ClassNotFoundException, SQLException {
        PreparedStatement sentencia;
        ResultSet resultado;

        String sql = "Select employee_id,first_name,last_name,reports_to,birth_date,city from employees;";
        List<Empleado> empleados = new ArrayList<>();
        abrirConexion();
        sentencia = miConexion.prepareStatement(sql);
        resultado = sentencia.executeQuery();

        while (resultado.next()) {
            empleados.add(new Empleado(resultado.getInt("employee_id"),
                    resultado.getString("first_name"),
                    resultado.getString("last_name"),
                    resultado.getInt("reports_to"),
                            resultado.getDate("birth_date").toLocalDate(),
                    resultado.getString("city"))



            );
        }
        return empleados;
    }

    public void insertarEmpleado(Empleado empleado) throws SQLException, ClassNotFoundException {

        int idEmpleado = empleado.getIdEmpleado();
        int jefe = empleado.getJefe();
        String nombre = empleado.getNombre();
        String apellido = empleado.getApellido();


        PreparedStatement sentencia;
        int resultado = 0;

        if (!comprobarEmpleado(idEmpleado)) {
            String sql = "INSERT INTO employees (employee_id, first_name, last_name, reports_to) " +
                    "VALUES (?,?,?,?)";


            abrirConexion();
            sentencia = miConexion.prepareStatement(sql);
            sentencia.setInt(1, idEmpleado);
            sentencia.setString(2, nombre);
            sentencia.setString(3, apellido);
            sentencia.setInt(4, jefe);

            resultado = sentencia.executeUpdate();

            System.out.println("Correctamente añadido");
            sentencia.close();

            miConexion.close();

        } else {
            System.out.println("No se puede insertar porque ya existe");

        }


    }

    public boolean comprobarEmpleado(int idEmpleado) throws ClassNotFoundException, SQLException {
        abrirConexion();

        String sql = "Select COUNT(*) from employees where employee_id= ?";

        PreparedStatement sentencia;
        ResultSet resultado;
        sentencia = miConexion.prepareStatement(sql);
        sentencia.setInt(1, idEmpleado);
        resultado = sentencia.executeQuery();
        resultado.next();

        int cantidadEncontrados = resultado.getInt(1);
        return cantidadEncontrados > 0;


    }

    public void actualizarEmpleado(Empleado empleado) throws SQLException, ClassNotFoundException {
        abrirConexion();

        int idEmpleado = empleado.getIdEmpleado();
        int jefe = empleado.getJefe();
        String nombre = empleado.getNombre();
        String apellido = empleado.getApellido();

        if (comprobarEmpleado(idEmpleado)) {
            PreparedStatement sentencia;
            int resultado = 0;

            String sql = "UPDATE employees SET employee_id=?, first_name=?, last_name=?, reports_to=? WHERE " +
                    "employee_id=?";

            sentencia = miConexion.prepareStatement(sql);
            sentencia.setInt(1, idEmpleado);
            sentencia.setString(2, nombre);
            sentencia.setString(3, apellido);
            sentencia.setInt(4, jefe);
            sentencia.setInt(5, idEmpleado);

            resultado = sentencia.executeUpdate();
            System.out.println("Actualización exitosa");
        } else {
            System.out.println("Usuario no existe");
        }
    }

    public void borrarEmpleado(int id) throws SQLException, ClassNotFoundException {
        PreparedStatement sentencia;
        abrirConexion();
        if (comprobarEmpleado(id)) {
            String sql = "DELETE FROM employees WHERE employee_id=?";

            sentencia = miConexion.prepareStatement(sql);
            sentencia.setInt(1,id);
            int resultado = sentencia.executeUpdate();

            System.out.println("Borrado correctamente");
        }
        else {
            System.out.println("Usuario no existente");
        }
    }


}


package com.mycompany.holamundo_3erperiodo;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class HolaMundo_3erPeriodo {

    public static void main(String[] args) throws SQLException {
        
        System.out.println("== Ejecutando operaciones en tabla Alumno ==");
        
        //obtener la conexion
        Connection cn = ConexionDB.getConexion();
        if(cn == null){
            System.out.println("No se puede establecer la conexion");
            return;
        }
        
        //declarar objetos de SQL para BD
        PreparedStatement psInsertar = null;
        PreparedStatement psSeleccionar = null;
        PreparedStatement psBuscar = null;
        PreparedStatement psActualizar = null;
        PreparedStatement psEliminar = null;
        ResultSet rs = null;
        
        //Interactuando con los datos de la BD
      
        try{
            System.out.println("\n 1. Intentando insertar nuevo Alumno...");
            
            // Restriccion de datos duplicados
            
            String nombreAlumno = "Ronaldo Pele";
            String correoAlumno = "ronaldo.pele@uth.hn";
            
            psBuscar = cn.prepareStatement("SELECT nombre FROM Alumnos WHERE nombre = ? OR correo = ?");
            psBuscar.setString(1, nombreAlumno);
            psBuscar.setString(2, correoAlumno);
            
            if (psBuscar.executeQuery().next()) {
                System.out.println("\nEl nombre y/o correo ya existen en la base de datos!");
                
            }else{
                
                String psInsert = "INSERT INTO Alumnos(nombre,correo) VALUES(?,?)";
                psInsertar = cn.prepareStatement(psInsert);

                //asignamos valores a nuestros parametros
                psInsertar.setString(1, nombreAlumno); 
                psInsertar.setString(2, correoAlumno);

                //ejecutar insercion
                int filasAfectadas = psInsertar.executeUpdate();
                if(filasAfectadas > 0){
                    System.out.println("\nAlumno insertado correctamente!");
                }
            }
            
            // Actualizar alumno
            
            System.out.println("\n 2. Intentando actualizar Alumno...");
            String sqlUpdate = "UPDATE Alumnos SET nombre = ?, correo = ? WHERE id = ?";
            psActualizar = cn.prepareStatement(sqlUpdate);
            
            psActualizar.setString(1, "Cristiano Ronaldo");    // Nuevo nombre
            psActualizar.setString(2, "cristiano.ronaldo@uth.hn");    // Nuevo correo
            psActualizar.setInt(3, 2);  // ID 

            int filasActualizadas = psActualizar.executeUpdate();
            
            if(filasActualizadas > 0){
                System.out.println("\nAlumno actualizado correctamente!");
            }

            // Eliminar alumno
            
            System.out.println("\n 3. Intentando eliminar Alumno...");
            String sqlDelete = "DELETE FROM Alumnos WHERE id = ?";
            psEliminar = cn.prepareStatement(sqlDelete);
            
            psEliminar.setInt(1,8); // ID para borrar

            int filasEliminadas = psEliminar.executeUpdate();
            if(filasEliminadas > 0){
                System.out.println("\nAlumno eliminado correctamente!");
            }
            
            //Parte B: Seleccionar y visualizar nuestros parametros
            
            System.out.println("\n4. Consulta de lista de Alumnos en la Base de Datos...");
            String sqlSelect = "SELECT id,nombre,correo from Alumnos";
            psSeleccionar = cn.prepareStatement(sqlSelect);
            rs = psSeleccionar.executeQuery();
            
            System.out.println("\n===================================================");
            System.out.println("ID       Nombre                   Correo              ");
            System.out.println("===================================================");
            
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                
                //Imprimimos la tabla virtual
                
                System.out.printf("%-3d | %-20s | %s\n", id, nombre, correo);           
            }
            System.out.println("=====================================================");
        }catch(SQLException e){
            System.err.println("Ocurrio un error con la base de datos" + e.getMessage());
        }finally{
            
            //cerrar la conexion de manera obligatoria
            if(rs != null){
                rs.close();
            }
            if(psInsertar != null)psInsertar.close();
            if(psSeleccionar != null)psSeleccionar.close();
            if(psBuscar != null) psBuscar.close();
            if(psActualizar != null) psActualizar.close();
            if(psEliminar != null) psEliminar.close();
            ConexionDB.cerrarConexion();
        }
    }
}


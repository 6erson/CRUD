package com.mycompany.holamundo_3erperiodo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB{

    private static final String URL = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=AvanzadaII;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASSWORD = "1234";
    private static Connection conexion = null;

    private ConexionDB(){}

    public static Connection getConexion() {
        
        try{           
            if(conexion == null || conexion.isClosed()){
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                //establecer conexion
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                 System.out.println("\n--> Conexion exitosa en SQL");
                    }
            
            }catch(ClassNotFoundException e){
                    System.err.println("Error no se encontro el Driver de MSSQL-JDBC" + e.getMessage());

            }catch(SQLException e){
                    System.err.println("Error al conectar con SQL" + e.getMessage());
            }
           return conexion;             
        }

    public static void cerrarConexion(){
        try{
            if(conexion != null && !conexion.isClosed()){
                conexion.close();
                System.out.println("\n--> La conexion con SQL fue cerrada");
            }
            
        }catch(SQLException e){
            System.err.println("Error al cerrar la conexion" + e.getMessage());
        }
    }   
    
}

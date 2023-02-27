package org.example;

import java.sql.*;

public class CrearTaules {
    public static Connection conexio() throws SQLException {

        Connection con = null;
        String sURL = "jdbc:mariadb://localhost:3306/mp03uf6";
        con = DriverManager.getConnection(sURL, "estudiant", "Admin1234");
        return con;

    }
    public static void crearTaules() {
        try {
            String sentenciaSQL = "CREATE TABLE IF NOT EXISTS productes" +
                    "(idProducte INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nom VARCHAR(100) NOT NULL, preu FLOAT NOT NULL DEFAULT 0);";

            Connection con = conexio();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String sentenciaSQL = "CREATE TABLE IF NOT EXISTS movimentProductes" +
                    "(idMoviment INT AUTO_INCREMENT PRIMARY KEY," +
                    "idProducte INT, " +
                    "nom VARCHAR(100) NOT NULL," +
                    "data DATE, " +
                    "quantitat FLOAT NOT NULL DEFAULT 0," +
                    "preu FLOAT NOT NULL DEFAULT 0," +
                    "FOREIGN KEY (idProducte) REFERENCES productes (idProducte));";

            Connection con = conexio();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

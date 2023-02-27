package org.example;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Moviments {
    public static Connection conexio() throws SQLException {

        Connection con = null;
        String sURL = "jdbc:mariadb://localhost:3306/mp03uf6";
        con = DriverManager.getConnection(sURL, "estudiant", "Admin1234");
        return con;

    }
    public static void movimentProductes(){
        Locale.setDefault(new Locale("en", "US"));
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("");
            System.out.println("1.Compra producte \n2.Vendre un producte \n3.Calcular l'estoc d'un producte i llistar per pantalla amb el seu preu mitjà\n4.Calcular l'estoc de tots els productes i llistar per pantalla amb el seu preu mitjà.\n5.Llistar els moviments que ha tingut un producte ordenats per data.\n6.Sortir del programa.");
            System.out.print("Opció:");
            int resultat = sc.nextInt();

            if (resultat == 1) {
                comprarProducte();
                continue;
            } else if (resultat == 2) {
                vendreProducte();
                continue;
            } else if (resultat == 3) {
                calcularEstoc();
                continue;
            } else if (resultat == 4) {
                calcularEstocTots();
                continue;
            } else if (resultat == 5) {
                llistarMoviments();
                continue;
            } else if (resultat == 6) {
                break;
            }
            break;
        }

    }
    public static void comprarProducte(){
        Connection con = null;
        Statement stmt = null;
        Scanner sc = new Scanner(System.in);
        System.out.print("ID del producte que vols comprar:");
        String idProducte = sc.nextLine();
        int idProducteint = Integer.parseInt(idProducte);
        System.out.print("Data de la compra: (aaaa-mm-dd)");
        String dataProducte = sc.nextLine();
        System.out.print("Quantitat que vols comprar:");
        String quantitatProducte = sc.nextLine();
        Float quantitatProducteFloat = Float.parseFloat(quantitatProducte);
        System.out.print("Preu per el que vols comprar:");
        String preuProducte = sc.nextLine();
        Float preuProducteFloat = Float.parseFloat(preuProducte);
        String sentenciaSQL = MessageFormat.format("INSERT INTO movimentProductes(idProducte,data,quantitat,preu) VALUES({0} ,\'\'{1}\'\',{2},{3} );", idProducteint,dataProducte,quantitatProducteFloat,preuProducteFloat);
        try {

            con = conexio();
            stmt = con.createStatement();
            stmt.executeUpdate(sentenciaSQL);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void vendreProducte(){
        Connection con = null;
        Statement stmt = null;
        Scanner sc = new Scanner(System.in);
        System.out.print("ID del producte que vols vendre:");
        String idProducte = sc.nextLine();
        int idProducteint = Integer.parseInt(idProducte);
        System.out.print("Data de la venta: (aaaa-mm-dd)");
        String dataProducte = sc.nextLine();
        System.out.print("Quantitat que vols vendre:");
        String quantitatProducte = sc.nextLine();
        String quantitatNegativa = ("-"+quantitatProducte);
        Float quantitatProducteFloat = Float.parseFloat(quantitatNegativa);
        System.out.print("Preu per el que vols vendre:");
        String preuProducte = sc.nextLine();
        Float preuProducteFloat = Float.parseFloat(preuProducte);
        String sentenciaSQL = MessageFormat.format("INSERT INTO movimentProductes(idProducte,data,quantitat,preu) VALUES({0} ,\'\'{1}\'\',{2},{3} );", idProducteint,dataProducte,quantitatProducteFloat,preuProducteFloat);
        try {

            con = conexio();
            stmt = con.createStatement();
            stmt.executeUpdate(sentenciaSQL);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void calcularEstoc() {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Indica la ip del producte que vols caluclar l'estoc:");
                String idProducte = sc.nextLine();
                String sentenciaSQL = "SELECT * from movimentProductes WHERE idProducte="+idProducte+";";
                Connection con = conexio();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sentenciaSQL);
                List<Float> arraySum = new ArrayList<>();
                List<Float> quantitatSum = new ArrayList<>();
                float totalPreu = 0;
                float totalQuantitat = 0;
                while (rs.next()) {
                    String quantitat = rs.getString("quantitat");
                    Float quantitatF = Float.parseFloat(quantitat);
                    String preu = rs.getString("preu");
                    Float preuF = Float.parseFloat(preu);
                    Float calcul = quantitatF*preuF;
                    arraySum.add(calcul);
                    quantitatSum.add(quantitatF);
                }
                for (float n : arraySum) {
                    totalPreu += n;
                }
                for (float n : quantitatSum) {
                    totalQuantitat += n;
                }
                System.out.println("L'estoc total del producte es "+totalQuantitat+" i la mitja del preu es "+totalPreu/totalQuantitat);
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    public static void calcularEstocTots() {
            try {
                String sentenciaSQL = "SELECT * from movimentProductes;";
                Connection con = conexio();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sentenciaSQL);
                List<Float> arraySum = new ArrayList<>();
                List<Float> quantitatSum = new ArrayList<>();
                float totalPreu = 0;
                float totalQuantitat = 0;
                while (rs.next()) {
                    String quantitat = rs.getString("quantitat");
                    Float quantitatF = Float.parseFloat(quantitat);
                    String preu = rs.getString("preu");
                    Float preuF = Float.parseFloat(preu);
                    Float calcul = quantitatF*preuF;
                    arraySum.add(calcul);
                    quantitatSum.add(quantitatF);
                }
                for (float n : arraySum) {
                    totalPreu += n;
                }
                for (float n : quantitatSum) {
                    totalQuantitat += n;
                }
                System.out.println("L'estoc total del producte es "+totalQuantitat+" i la mitja del preu es "+totalPreu/totalQuantitat);
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    public static void llistarMoviments() {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Indica la ip del producte que vols llistar els seus moviments:");
                String idProducte = sc.nextLine();
                String sentenciaSQL = "SELECT * from movimentProductes WHERE idProducte= "+idProducte+" ORDER BY data;";
                Connection con = conexio();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sentenciaSQL);
                while (rs.next()) {
                    String idMov = rs.getString("idMoviment");
                    String idPro = rs.getString("idProducte");
                    String data = rs.getString("data");
                    String quantitat = rs.getString("quantitat");
                    String preu = rs.getString("preu");
                    String registre = MessageFormat.format("Moviment id: {0}, producte id: {1}, data: {2}, quantitat: {3}, preu: {4}.", idMov, idPro, data, quantitat, preu);
                    System.out.println(registre);
                }

                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}




package org.example;


import java.sql.*;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static Connection conexio() throws SQLException {

        Connection con = null;
        String sURL = "jdbc:mariadb://localhost:3306/mp03uf6";
        con = DriverManager.getConnection(sURL, "estudiant", "Admin1234");
        return con;

    }

    public static void main(String[] args) {
        CrearTaules.crearTaules();
        menuPrincipal();
    }

    public static void menuPrincipal() {
        Locale.setDefault(new Locale("en", "US"));
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("");
            System.out.println("1.Productes\n2.Moviment de productes\n3.Sortir del programa.");
            System.out.print("Opció:");
            int resultat = sc.nextInt();

            if (resultat == 1) {
                menuProducte();
                continue;
            } else if (resultat == 2) {
                Moviments.movimentProductes();
                continue;
            } else if (resultat == 6) {
                break;
            }
            break;
        }
    }
    public static void menuProducte(){
        Locale.setDefault(new Locale("en", "US"));
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("");
            System.out.println("1.Crear Producte \n2.Modificar un producte \n3.Eliminar un producte\n4.Llista productes\n5.Mostrar un producte específic per id.\n6.Sortir del programa.");
            System.out.print("Opció:");
            int resultat = sc.nextInt();

            if (resultat == 1) {
                crearProductes();
                continue;
            } else if (resultat == 2) {
                modificarProductes();
                continue;
            } else if (resultat == 3) {
                eliminarProductes();
                continue;
            } else if (resultat == 4) {
                menuLlistar();
                continue;
            } else if (resultat == 5) {
                mostrarProductes();
                continue;
            } else if (resultat == 6) {
                break;
            }
            break;
        }
    }

    public static void menuLlistar() {
        Locale.setDefault(new Locale("en", "US"));
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("");
            System.out.println("1.Ordenar per id \n2.Ordenar per nom \n3.Ordenar per preu\n4.Que continguin una cadena de caràcters al nom\n5.Tornar al menu principal.");
            System.out.print("Opció:");
            int resultat = sc.nextInt();

            if (resultat == 1) {
                llistarProductesId();
            } else if (resultat == 2) {
                llistarProductesNom();
            } else if (resultat == 3) {
                llistarProductesPreu();
            } else if (resultat == 4) {
                llistarProductesCadena();
            } else if (resultat == 5) {
                menuPrincipal();
                break;
            }
        }
    }

    public static void crearProductes() {
        Scanner sc = new Scanner(System.in);
        Connection con = null;
        Statement stmt = null;
        System.out.print("Nombre del producto:");
        String name = sc.nextLine();
        System.out.print("Precio del producto:");
        String priceaux = sc.nextLine();
        Float price = Float.parseFloat(priceaux);
        String sentenciaSQL = MessageFormat.format("INSERT INTO productes(nom,preu) VALUES(\'\'{0}\'\' , {1} );", name, price);
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

    public static void modificarProductes() {
        Scanner sc = new Scanner(System.in);
        Connection con = null;
        Statement stmt = null;
        System.out.println("ID del producto que quieres modificar:");
        String idProduct = sc.nextLine();
        System.out.print("Nuevo nombre del producto:");
        String name = sc.nextLine();
        System.out.print("Nuevo precio del producto:");
        String priceaux = sc.nextLine();
        Float price = Float.parseFloat(priceaux);
        String sentenciaSQL = MessageFormat.format("UPDATE productes SET nom = \'\'{0}\'\', preu = {1} WHERE idProducte = {2}", name, price, idProduct);
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


    public static void eliminarProductes() {
        Connection con = null;
        Statement stmt = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("ID del producto que quieres eliminar:");
        String idProduct = sc.nextLine();
        String sentenciaSQL = MessageFormat.format("DELETE FROM productes WHERE idProducte={0};", idProduct);

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
    public static void llistarProductesId() {
        try {
            String sentenciaSQL = "SELECT * from productes ORDER BY idProducte;";
            Connection con = conexio();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);

            while (rs.next()) {
                String id = rs.getString("idProducte");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");
                String registre = MessageFormat.format("Producte id: {0}, nom: {1}, preu: {2} euros.", id, nom, preu);
                System.out.println(registre);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void llistarProductesNom() {
        try {
            String sentenciaSQL = "SELECT * from productes ORDER BY nom;";
            Connection con = conexio();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);

            while (rs.next()) {
                String id = rs.getString("idProducte");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");
                String registre = MessageFormat.format("Producte id: {0}, nom: {1}, preu: {2} euros.", id, nom, preu);
                System.out.println(registre);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void llistarProductesPreu() {
        try {
            String sentenciaSQL = "SELECT * from productes ORDER BY preu DESC;";
            Connection con = conexio();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);

            while (rs.next()) {
                String id = rs.getString("idProducte");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");
                String registre = MessageFormat.format("Producte id: {0}, nom: {1}, preu: {2} euros.", id, nom, preu);
                System.out.println(registre);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void llistarProductesCadena() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Paraula que vols buscar:");
            String paraula = sc.nextLine();
            String sentenciaSQL = "SELECT * from productes WHERE nom LIKE '%" + paraula + "%' ORDER BY nom;";
            Connection con = conexio();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);

            while (rs.next()) {
                String id = rs.getString("idProducte");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");
                String registre = MessageFormat.format("Producte id: {0}, nom: {1}, preu: {2} euros.", id, nom, preu);
                System.out.println(registre);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mostrarProductes() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("ID del producto que quieres mostrar:");
            String idProduct = sc.nextLine();
            String sentenciaSQL = "SELECT * from productes WHERE idProducte = " + idProduct;
            Connection con = conexio();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sentenciaSQL);

            while (rs.next()) {
                String id = rs.getString("idProducte");
                String nom = rs.getString("nom");
                String preu = rs.getString("preu");
                String registre = MessageFormat.format("Producte id: {0}, nom: {1}, preu: {2} euros.", id, nom, preu);
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
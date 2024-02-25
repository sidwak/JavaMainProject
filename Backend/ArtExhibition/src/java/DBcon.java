/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Vaishanvi
 */
import java.io.*;
import java.sql.*;
import java.util.*;

public class DBcon {
    private static final String DB_URL = "jdbc:mysql://localhost/artex";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    public static Connection conn = null;
    static {       
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection to the database
            //conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                       
        } 
//        catch (SQLException e) {
//            System.out.println("Exception");
//        }
        catch (ClassNotFoundException e){
            
        }
    }
    
    public static void insertSignData(String name, String email, String password){
        String query = " insert into artist(name, email, password, country, prof)"
            + " values (?, ?, ?, ?, ?)";
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, "not set");
            stmt.setString(5, "not set");
            stmt.execute();      
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Exception");
        }
    }
    
    public static boolean checkLogin(String email, String password){  
        boolean ret = false;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "select password from artist where email=?";
            PreparedStatement stmt = conn.prepareStatement(query);   
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                String chkpass = rs.getString(1);
                if (chkpass.equals(password)){
                    ret = true;
                }
            }
            else {
                
            }
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Exception");
        }
        return ret;
    }
    
    public static void updateUserData(String name, String password, String country, String prof){
        try {
            String email = CredentialsManager.credentials.get("email");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            if (name.equals("")==false){
                String query = "update artist set name=? where email=?";
                PreparedStatement stmt = conn.prepareStatement(query);   
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.executeUpdate();
            }
            if (password.equals("")==false){
                String query = "update artist set password=? where email=?";
                PreparedStatement stmt = conn.prepareStatement(query);   
                stmt.setString(1, password);
                stmt.setString(2, email);
                stmt.executeUpdate();
            }
            if (country.equals("")==false){
                String query = "update artist set country=? where email=?";
                PreparedStatement stmt = conn.prepareStatement(query);   
                stmt.setString(1, country);
                stmt.setString(2, email);
                stmt.executeUpdate();
            }
            if (prof.equals("")==false){
                String query = "update artist set prof=? where email=?";
                PreparedStatement stmt = conn.prepareStatement(query);   
                stmt.setString(1, prof);
                stmt.setString(2, email);
                stmt.executeUpdate();
            }
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Exception"+e.getMessage());
        }
    }

    public static void insertExhibit(String exname, String exdesp, String sale, String price, String artId, String artEmail){
        String query = " insert into exhibits(exname, exdesp, sale, price, artid, artemail)"
            + " values (?, ?, ?, ?, ?, ?)";
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, exname);
            stmt.setString(2, exdesp);
            stmt.setString(3, sale);
            stmt.setString(4, price);
            stmt.setString(5, artId);
            stmt.setString(6, artEmail);
            stmt.execute();      
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Exception insertexhibit"+e.getMessage());
        }
    }
    
    public static List<Map<String, String>> gridData(){
        List<Map<String, String>> toRet = new ArrayList<Map<String, String>>();
        String query = " select exname,sale,price,artid from exhibits";
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                Map<String, String> toadd = new HashMap<>();
                toadd.put("exname",rs.getString(1));
                toadd.put("sale",rs.getString(2));
                toadd.put("price",rs.getString(3));
                toadd.put("artid",rs.getString(4));
                toRet.add(toadd);
            }     
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Exception insertexhibit"+e.getMessage());
        }
        return toRet;
    }public static List<Map<String, String>> artistGridData(){
        List<Map<String, String>> toRet = new ArrayList<Map<String, String>>();
        String query = " select name,email,prof,country from artist";
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                Map<String, String> toadd = new HashMap<>();
                toadd.put("name",rs.getString(1));
                toadd.put("email",rs.getString(2));
                toadd.put("prof",rs.getString(3));
                toadd.put("country",rs.getString(4));
                toRet.add(toadd);
            }     
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Exception insertexhibit"+e.getMessage());
        }
        return toRet;
    }
    
    public static List<Map<String, String>> artistExhibitData(){
        List<Map<String, String>> toRet = new ArrayList<Map<String, String>>();
        String email = CredentialsManager.getEmail();
        System.out.println("email"+email);
        String query = " select exname,sale,price,artid from exhibits where artemail='"+email+"'";
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query);
            //stmt.setString(1, email);
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                Map<String, String> toadd = new HashMap<>();
                toadd.put("exname",rs.getString(1));
                toadd.put("sale",rs.getString(2));
                toadd.put("price",rs.getString(3));
                toadd.put("artid",rs.getString(4));
                toRet.add(toadd);
            }     
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Exception inasdsadertexhibit"+e.getMessage());
        }
        return toRet;
    }
    
    public static void deleteExhibit(String toexname){
        try {
            String email = CredentialsManager.credentials.get("email");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "delete from exhibits where exname='"+toexname+"'";
            PreparedStatement stmt = conn.prepareStatement(query);
            int result = stmt.executeUpdate();
            if (result > 0){
                System.out.println("removed from table");
            }
        }
        catch (SQLException e){
            System.out.println("Exception"+e.getMessage());
        }
    }
    
    public static void addNewBlog(String title, String content){
        String query = " insert into blogs(artid, artemail, title, content)"
            + " values (?, ?, ?, ?)";
        String artId = CredentialsManager.getId();
        String artEmail = CredentialsManager.getEmail();
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, artId);
            stmt.setString(2, artEmail);
            stmt.setString(3, title);
            stmt.setString(4, content);
            stmt.execute();      
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Exception insertexhibit"+e.getMessage());
        }
    }public static List<Map<String, String>> getBlogs(){
        List<Map<String, String>> toRet = new ArrayList<Map<String, String>>();
        String query = " select artid, title, content from blogs";
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                Map<String, String> toadd = new HashMap<>();
                toadd.put("artid",rs.getString(1));
                toadd.put("title",rs.getString(2));
                toadd.put("content",rs.getString(3));
                toRet.add(toadd);
            }     
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Exception insertexhibit"+e.getMessage());
        }
        return toRet;
    }
    
    public static void insertBuyRequest(String cname, String cemail, String cprice, String cnotes, String exname){
        String query = " insert into buyorder(cname, cemail, cprice, cnotes, exname, artid)"
            + " values (?, ?, ?, ?, ?, ?)";
        try {          
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String artid = "";
            String qq2 = "select artid from exhibits where exname='"+exname+"'";
            PreparedStatement st2= conn.prepareStatement(qq2);
            ResultSet rs = st2.executeQuery();
            if (rs.next()){
                artid = rs.getString(1);
            }     
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, cname);
            stmt.setString(2, cemail);
            stmt.setString(3, cprice);
            stmt.setString(4, cnotes);
            stmt.setString(5, exname);
            stmt.setString(6, artid);
            stmt.execute();      
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Exception insertexhibit"+e.getMessage());
        }
    }

    public static List<Map<String, String>> getBuyTable(){
        List<Map<String, String>> toRet = new ArrayList<Map<String, String>>();
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String artid = CredentialsManager.getId();
            String query = " select * from buyorder where artid='"+artid+"'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                Map<String, String> toadd = new HashMap<>();
                toadd.put("cname",rs.getString(1));
                toadd.put("cemail",rs.getString(2));
                toadd.put("cprice",rs.getString(3));
                toadd.put("cnotes",rs.getString(4));
                toadd.put("exname",rs.getString(5));
                toRet.add(toadd);
            }     
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Exception insertexhibit"+e.getMessage());
        }
        return toRet;
    }
}

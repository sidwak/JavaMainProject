
import java.util.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Vaishanvi
 */
public class CredentialsManager {
    public static Map<String, String> credentials = new HashMap<>();

    static {
        // Initialize some default credentials (you can load them from a database or configuration file)
        credentials.put("email", "null");
        credentials.put("password", "null");
        // Add more credentials as needed
    }
    
    public static void setLoginData(String email, String password){
        System.out.println("logged set "+email+" "+password);
        credentials.put("email", email);
        credentials.put("password", password);
    }        
      
    public static void printData(){
        System.out.println("Email: "+credentials.get("email"));
        System.out.println("Password: "+credentials.get("password"));
    }
    
    public static void logout(){
        System.out.println("logged out");
        credentials.put("email", "null");
        credentials.put("password", "null");
    }
    
    public static String getId(){
        String email = credentials.get("email");
        int atIndex = email.indexOf('@');
        String username = email.substring(0, atIndex);
        return username;
    }
    
    public static String getEmail(){
        return credentials.get("email");
    }
    
    public static String getFormatedId(String email){
        int atIndex = email.indexOf('@');
        String username = email.substring(0, atIndex);
        return username;
    }
}

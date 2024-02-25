/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.nio.file.Files;

/**
 *
 * @author Vaishanvi
 */
public class LoginServlet extends HttpServlet {
    String globName = "nn";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            
//        }
        
        
        if (request.getHeader("isimage") != null){
            addimage(request, response);
        }
        else {
            String type = request.getParameter("type");
            if (type.equals("login")){
                loguser(request, response);
            }
            else if(type.equals("logout")){
                logout(request, response);
            }
            else if(type.equals("signup")){
                globName = request.getParameter("name");
                signuser(request, response);
            }
            else if(type.equals("setup")){
                updateAct(request, response);
            }
        }
    }
    
    private void loguser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (DBcon.checkLogin(email, password)) {
            // Credentials are valid
            response.getWriter().write("valid");
        } else {
            // Credentials are invalid
            response.getWriter().write("invalid");
        }
        CredentialsManager.setLoginData(email, password);
        //CredentialsManager.printData();
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CredentialsManager.logout();
        CredentialsManager.printData();
    }
    
    private void signuser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println(email);   
        CredentialsManager.setLoginData(email, password);
        DBcon.insertSignData(name, email, password);
    }
    
    private void updateAct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String country = request.getParameter("country");
        String prof = request.getParameter("prof");
        DBcon.updateUserData(name, password, country, prof);
    }
    
    
    private void addimage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get profile picture
        Part filePart = request.getPart("profile");
        String fileName = CredentialsManager.getId();
        String directory = "ProfileImages";
        String appPath = request.getServletContext().getRealPath("");
        String savePath = "C:\\Users\\Vaishanvi\\Desktop\\Siddhesh\\SEM IV\\Projects\\JavaMainProject\\JavaMainProject\\Backend\\ArtExhibition\\web\\ProfileImages";
        System.out.println("path: "+savePath);
        String fileName2 = filePart.getSubmittedFileName();
        String extension = fileName2.substring(fileName2.lastIndexOf("."));
        try {
            File file = new File(savePath, fileName+extension);

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath());
            }
        }
        catch (Exception ee){
        }
        response.getWriter().write("valid");  
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

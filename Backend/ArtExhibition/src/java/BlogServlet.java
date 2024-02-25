/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.*;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Vaishanvi
 */
public class BlogServlet extends HttpServlet {

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
        if (request.getHeader("blogs") != null){
            getBlogs(request, response);
        }
        else {
            String type = request.getParameter("type");
            if (type.equals("add")){
                insertBlog(request, response);
            }
        }
    }
    
    private void insertBlog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        DBcon.addNewBlog(title, content);
        response.getWriter().write("valid");
    }
    
    private void getBlogs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Map<String, String>> gridData = DBcon.getBlogs();
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < gridData.size(); i++){
            String artId = gridData.get(i).get("artid");
            String title = gridData.get(i).get("title");
            String content = gridData.get(i).get("content");  
            String imgName = artId + ".jpg";        
            sbf.append("<div class=\"d-flex align-items-center\" style=\"height:225px;\">\n" +
        "          <img class=\"rounded-circle\" src=\"ProfileImages/"+imgName+"\" alt=\"...\" style=\"height:150px;width:150px;object-fit: cover;\">\n" +
        "          <div class=\"px-4 mx-4 border border-1 w-100 border-dark rounded-3\">\n" +
        "            <h2 >"+title+"</h2>\n" +
        "            <p>"+content+
        "             </p>\n" +
        "            <h6>by @"+artId+"</h6>\n" +
        "          </div>\n" +
        "        </div>");
        }
        response.getWriter().write(sbf.toString());
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

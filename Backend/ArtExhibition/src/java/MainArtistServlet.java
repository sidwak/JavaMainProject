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
public class MainArtistServlet extends HttpServlet {

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
        if (request.getHeader("isimage") != null){
            //addimage(request, response);
        }
        else if(request.getHeader("fetchgrid") != null){
            fetchGrid(request, response);
        }
        else if(request.getHeader("artistexhibitgrid") != null){
            fetchExhibitsGrid(request, response);
        }
        else if(request.getHeader("deleteexhibit") != null){
            deleteExhibit(request, response);
        }
        else if(request.getHeader("addbuy") != null){
            insertBuyRequest(request, response);
        } 
        else if(request.getHeader("buytable") != null){
            getBuyTable(request, response);
        }
        else {
//            String type = request.getParameter("type");
//            if (type.equals("delete")){
//                deleteExhibit(request, response);
//            }
        }
    }

    private void deleteExhibit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String exname = request.getHeader("exname");
        DBcon.deleteExhibit(exname);
        String fileName = CredentialsManager.getId() + exname + ".jpg";
        System.out.println("filename: "+fileName);
        try {
            Files.deleteIfExists(Paths.get("C:\\Users\\Vaishanvi\\Desktop\\Siddhesh\\SEM IV\\Projects\\Jav"
                    + "aMainProject\\JavaMainProject\\Backend\\ArtExhibition\\web\\ExhibitImages\\"+fileName));
            System.out.println("Deletion successful.");
        } catch (Exception e) {
            System.out.println("No such file/directory exists");
        }
        response.getWriter().write("valid");
    }
    
    private void fetchGrid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Map<String, String>> gridData = DBcon.artistGridData();
        StringBuffer sbf = new StringBuffer();
        sbf.append("<div class=\"row\">");
        for (int i = 0; i < gridData.size(); i++){
            String name = gridData.get(i).get("name");
            String email = gridData.get(i).get("email");
            String prof = gridData.get(i).get("prof");  
            String country = gridData.get(i).get("country");
            String artId = CredentialsManager.getFormatedId(email);
            String imgName = artId + ".jpg";        
            sbf.append("<div class=\"col-xl-3 col-md-6 mb-4\">\n" +
"              <div class=\"card border-0 shadow\">\n" +
"                <img src=\"ProfileImages/"+imgName+"\" class=\"card-img-top\" alt=\"...\">\n" +
"                <div class=\"card-body text-center\">\n" +
"                  <h5 class=\"card-title mb-0\">"+name+"</h5>\n" +
"                  <div class=\"card-text text-black-50\">"+prof+"</div>\n" +
"                  <div class=\"card-text\">"+country+"</div>\n" +
"                </div>\n" +
"              </div>\n" +
"            </div>");
        }
        sbf.append("</div>");
        response.getWriter().write(sbf.toString());
    }
    
    private void fetchExhibitsGrid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Map<String, String>> gridData = DBcon.artistExhibitData();
        StringBuffer sbf = new StringBuffer();
        sbf.append("<div class=\"row g-3\" data-masonry='{\"percentPosition\":true}' id=\"buybutton\">");
        for (int i = 0; i < gridData.size(); i++){
            String exname = gridData.get(i).get("exname");
            String price = gridData.get(i).get("price");
            String artid = gridData.get(i).get("artid");
            String sale = gridData.get(i).get("sale");
            String imgName = artid + exname + ".jpg";        
            if (sale.equals("sale")){
                sbf.append("<div class=\"col-sm-6 col-md-3\">\n" +
        "            <div class=\"card shadow efcard rounded-4 arcard overflow-hidden\">\n" +
        "              <img class=\"card-img rounded-4\" src=\"ExhibitImages/"+imgName+"\">\n" +
        "              <div class=\"arcardover\">\n" +
        "                <div class=\"card-img-overlay rounded-4 bg-dark text-white bg-opacity-50 p-3 d-flex flex-column\">\n" +
        "                  <h3 class=\"card-title text-nowrap overflow-hidden mt-auto\">"+exname+"</h3>\n" +
        "                  <div class=\"d-flex justify-content-between align-items-center \">\n" +
        "                    <h5 class=\"m-0\">$"+price+"</h5>\n" +
        "                    <button type=\"button\" class=\"btn btn-danger p-1 px-3\" id=\""+exname+"\">Delete</button>\n" +
        "                  </div>\n" +
        "                </div>\n" +
        "              </div>\n" +
        "            </div>\n" +
        "          </div>");
            }
            else {
                 sbf.append("<div class=\"col-sm-6 col-md-3\">\n" +
        "            <div class=\"card shadow efcard rounded-4 arcard overflow-hidden\">\n" +
        "              <img class=\"card-img rounded-4\" src=\"ExhibitImages/"+imgName+"\">\n" +
        "              <div class=\"arcardover\">\n" +
        "                <div class=\"card-img-overlay rounded-4 bg-dark text-white bg-opacity-50 p-3 d-flex flex-column\">\n" +
        "                  <h3 class=\"card-title text-nowrap overflow-hidden mt-auto\">"+exname+"</h3>\n" +
        "                  <div class=\"d-flex justify-content-end \">\n" +
        "                    <button type=\"button\" class=\"btn btn-danger p-1 px-3\" id=\""+exname+"\">Delete</button>\n" +
        "                  </div>\n" +
        "                </div>\n" +
        "              </div>\n" +
        "            </div>\n" +
        "          </div>");
            }
        }
        sbf.append("</div>");
        response.getWriter().write(sbf.toString());
    }
    
    private void insertBuyRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cname = request.getParameter("cname");
        String cemail = request.getParameter("cemail");
        String cprice = request.getParameter("cprice");
        String cnotes = request.getParameter("cnotes");
        String exname = request.getParameter("exname");
        DBcon.insertBuyRequest(cname, cemail, cprice, cnotes, exname);
        response.getWriter().write("valid");
    }
    
    private void getBuyTable (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        List<Map<String, String>> gridData = DBcon.getBuyTable();
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < gridData.size(); i++){
            String cname = gridData.get(i).get("cname");
            String cemail = gridData.get(i).get("cemail");
            String cprice = gridData.get(i).get("cprice");  
            String cnotes = gridData.get(i).get("cnotes"); 
            String exname = gridData.get(i).get("exname"); 
            sbf.append("<tr>" +
                            "<td>" + cname + "</td>" +
                            "<td>" + cemail + "</td>" +
                            "<td>" + cprice + "</td>" +
                            "<td>" + cnotes + "</td>" +
                            "<td>" + exname + "</td>" +
                          "</tr>");
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

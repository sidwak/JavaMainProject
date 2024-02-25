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

/**
 *
 * @author Vaishanvi
 */
public class AddExhibitServlet extends HttpServlet {
    String globExname = "";
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
            addimage(request, response);
        }
        else if(request.getHeader("fetchgrid") != null){
            fetchGrid(request, response);
        }
        else {
            String type = request.getParameter("type");
            if (type.equals("add")){
                addExhibit(request, response);
            }
        }
    }
    
    private void addExhibit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String exname = request.getParameter("exname");
        String exdesp = request.getParameter("exdesp");
        String sale = request.getParameter("sale");
        String price = request.getParameter("price");
        String artId = CredentialsManager.getId();
        String artEmail = CredentialsManager.getEmail();
        globExname = exname;
        DBcon.insertExhibit(exname, exdesp, sale, price, artId, artEmail);
    }
    
    private void addimage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //globExname
        Part filePart = request.getPart("expic");
        String artId = CredentialsManager.getId();
        String fileName = artId+globExname;
        String directory = "ProfileImages";
        String appPath = request.getServletContext().getRealPath("");
        String savePath = "C:\\Users\\Vaishanvi\\Desktop\\Siddhesh\\SEM IV\\Projects\\JavaMainProject\\JavaMainProject\\Backend\\ArtExhibition\\web\\ExhibitImages";
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

    private void fetchGrid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Map<String, String>> gridData = DBcon.gridData();
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
    "              <div class=\"card shadow efcard rounded-4 arcard overflow-hidden\">\n" +
    "                <img class=\"card-img rounded-4\" src=\"ExhibitImages/"+imgName+"\">\n" +
    "                <div class=\"arcardover\">\n" +
    "                  <div class=\"card-img-overlay rounded-4 bg-dark text-white bg-opacity-50 p-3 d-flex flex-column\">\n" +
    "                    <h3 class=\"card-title text-nowrap mt-auto\">"+exname+"</h3>\n" +
    "                    <p class=\"card-text text-nowrap overflow-hidden\">From @"+artid+"</p>\n" +
    "                    <div class=\"d-flex justify-content-between align-items-center \">\n" +
    "                      <h5 class=\"m-0\">$"+price+"</h5>\n" +
    "                      <button type=\"button\" class=\"btn btn-primary p-1 px-3\" id=\""+exname+"\">Contact Artist</button>\n" +
    "                    </div>\n" +
    "                  </div>\n" +
    "                </div>\n" +
    "              </div>\n" +
    "            </div>");
            }
            else {
                 sbf.append("<div class=\"col-sm-6 col-md-3\">\n" +
    "              <div class=\"card shadow efcard rounded-4 arcard overflow-hidden\">\n" +
    "                <img class=\"card-img rounded-4\" src=\"ExhibitImages/"+imgName+"\">\n" +
    "                <div class=\"arcardover\">\n" +
    "                  <div class=\"card-img-overlay rounded-4 bg-dark text-white bg-opacity-50 p-3 d-flex flex-column\">\n" +
    "                    <h3 class=\"card-title text-nowrap overflow-hidden mt-auto\">"+exname+"</h3>\n" +
    "                    <p class=\"card-text text-nowrap overflow-hidden\">From @"+artid+"</p>\n" +
    "                  </div>\n" +
    "                </div>\n" +
    "              </div>\n" +
    "            </div>");
            }
        }
        sbf.append("</div>");
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

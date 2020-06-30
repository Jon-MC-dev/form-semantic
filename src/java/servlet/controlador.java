/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import clases.Persona;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Jonathan Morales
 */
@WebServlet(name = "controlador", urlPatterns = {"/controlador"})
public class controlador extends HttpServlet {

    private Persona pe;
    Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        pe = new Persona();
        try (PrintWriter out = response.getWriter()) {
            try {
                if (request.getParameter("getAllPersonas") != null) {
                    out.print(pe.getAllPersonas());
                } else if (request.getParameter("newPersona") != null) {
                    System.out.println(request.getParameter("datos"));
                    establecerValores(pe, request);
                    out.print(pe.newPersona());
                } else if (request.getParameter("actualizarPersona") != null) {
                    establecerValores(pe, request);
                    out.println(pe.updatePersona());
                } else if (request.getParameter("eliminarPersona") != null) {
                    procesarFormData(request);
                    pe.setIdPersona(id);
                    out.print(pe.deletePersona());
                }
            } catch (java.lang.NullPointerException ex) {
                ex.printStackTrace();
            }

        }
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

    public String datoss = "";
    public int id = 0;

    private void establecerValores(Persona objPersona, HttpServletRequest datos) {
        procesarFormData(datos);
        JsonObject jsonObject = new JsonParser().parse(datoss).getAsJsonObject();
        JsonArray habilidades = jsonObject.get("multipleHabilidades").getAsJsonArray();
        System.out.println(jsonObject.get("nombre").getAsString());
        System.out.println(habilidades.get(0));
        System.out.println(habilidades.get(0).getAsString());
        String Shabilidades = "[" + habilidades.get(0).getAsString() + "," + habilidades.get(1).getAsString() + "," + habilidades.get(2).getAsString() + "]";
        objPersona.setNombre(jsonObject.get("nombre").getAsString());
        objPersona.setApellidoP(jsonObject.get("apellidP").getAsString());
        objPersona.setApellidoM(jsonObject.get("apellidoM").getAsString());
        objPersona.setHabilidades(Shabilidades);
        objPersona.setAlgoMas(jsonObject.get("algoMas").getAsString());
        objPersona.setIdPersona(id);
    }

    private void procesarFormData(HttpServletRequest request) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        List items;
        try {
            items = upload.parseRequest(request);

            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (item.isFormField()) {
                    if (item.getFieldName().equals("datos")) {
                        datoss = item.getString();
                    }
                    if (item.getFieldName().equals("id")) {
                        id = Integer.parseInt(item.getString());
                    }
                }
            }
        } catch (FileUploadException ex) {
            ex.printStackTrace();
        }
    }
}

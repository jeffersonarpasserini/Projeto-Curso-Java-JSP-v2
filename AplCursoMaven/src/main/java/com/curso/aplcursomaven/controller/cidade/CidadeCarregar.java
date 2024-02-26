/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curso.aplcursomaven.controller.cidade;

import com.curso.aplcursomaven.dao.CidadeDAO;
import com.curso.aplcursomaven.dao.EstadoDAO;
import com.curso.aplcursomaven.model.Cidade;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author jeffe
 */
@WebServlet(name = "CidadeCarregar", urlPatterns = {"/CidadeCarregar"})
public class CidadeCarregar extends HttpServlet {

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
        response.setContentType("text/html;charset=iso-8859-1");
        try {
            int idCidade = Integer.parseInt(request.getParameter("idCidade"));
            //Cria objeto de CidadeDAO - camada dao
            CidadeDAO oCidadeDAO = new CidadeDAO();
            //cria variavel no servidor para armazenar objeto de Cidade com os dados carregados
            request.setAttribute("cidade", oCidadeDAO.carregar(idCidade));
            //busca uma lista de estados para alimentar a caixa de seleção na view (jsp)
            EstadoDAO oEstadoDAO = new EstadoDAO();
            request.setAttribute("estados", oEstadoDAO.listar());
            //dispacha os objetos de cidade e a lista de estados para a pagina jsp
            request.getRequestDispatcher("/cadastros/cidade/cidadeCadastrar.jsp").forward(request, response);
        } catch (Exception ex){
            System.out.println("Erro carregar cidade "+ex.getMessage());
            ex.printStackTrace();
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

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.curso.controller.estado;

import br.com.curso.dao.EstadoDAO;
import br.com.curso.dao.GenericDAO;
import br.com.curso.model.Estado;
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
@WebServlet(name = "EstadoCadastrar", urlPatterns = {"/EstadoCadastrar"})
public class EstadoCadastrar extends HttpServlet {

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
        int idEstado = Integer.parseInt(request.getParameter("idestado"));
        String nomeEstado = request.getParameter("nomeestado");
        String siglaEstado = request.getParameter("siglaestado");
        String mensagem = null;

        Estado oEstado = new Estado();
        oEstado.setIdEstado(idEstado);
        oEstado.setNomeEstado(nomeEstado);
        oEstado.setSiglaEstado(siglaEstado);
        try{
            GenericDAO dao = new EstadoDAO();
            if (dao.cadastrar(oEstado)){
                mensagem = "Estado cadastrado com sucesso!";                
            } else {
                mensagem = "Problemas ao cadastrar Estado.Verifique os dados informados e tente novamente!";
            }
            request.setAttribute("mensagem", mensagem);
            response.sendRedirect("EstadoListar");
        } catch (Exception ex){
             System.out.println("Problemas no Servlet ao cadastrar Estado! Erro: " + ex.getMessage());
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

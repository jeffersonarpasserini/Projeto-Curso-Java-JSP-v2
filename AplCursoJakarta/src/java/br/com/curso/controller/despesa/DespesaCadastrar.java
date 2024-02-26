/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.curso.controller.despesa;

import br.com.curso.dao.DespesaDAO;
import br.com.curso.model.Despesa;
import br.com.curso.utils.Conversao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author jeffe
 */
@WebServlet(name = "DespesaCadastrar", urlPatterns = {"/DespesaCadastrar"})
public class DespesaCadastrar extends HttpServlet {

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
        String mensagem = null;
        try{           
            Despesa oDespesa = new Despesa();
            oDespesa.setIdDespesa(Integer.parseInt(request.getParameter("iddespesa")));
            oDespesa.setDescricao(request.getParameter("descricao"));
            oDespesa.setValorDespesa(Conversao.valorDinheiro(request.getParameter("valordespesa")));
            oDespesa.setValorPago(Conversao.valorDinheiro(request.getParameter("valorpago")));
            oDespesa.setDataDocumento(Date.valueOf(request.getParameter("datadocumento")));
            oDespesa.setImagemDocumento(request.getParameter("imagemdocumento"));
            DespesaDAO dao = new DespesaDAO();

            if(dao.cadastrar(oDespesa)){
                //mensagem = "Cadastrado com Sucesso!";
                response.getWriter().write("1");
            }else{
                //mensagem = "Problemas ao cadastrar Despesa!";
                response.getWriter().write("0");
            }
        } catch (Exception e) {
            System.out.println("Problemas no servelet Cadastrar Despesa!Erro: " + e.getMessage());
            e.printStackTrace();
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

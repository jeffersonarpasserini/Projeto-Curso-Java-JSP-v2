/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.curso.controller.pessoa;

import br.com.curso.dao.AdministradorDAO;
import br.com.curso.dao.PessoaDAO;
import br.com.curso.model.Administrador;
import br.com.curso.model.Pessoa;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jeffe
 */
@WebServlet(name = "PessoaBuscarCpfCnpj", urlPatterns = {"/PessoaBuscarCpfCnpj"})
public class PessoaBuscarCpfCnpj extends HttpServlet {

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
        try{
            String cpfCnpj = request.getParameter("cpfcnpjpessoa");
            String tipoPessoa = request.getParameter("tipopessoa");
            int id = 0;
            String jsonRetorno="";
            if (tipoPessoa.equals("administrador")){
                AdministradorDAO oAdmDAO = new AdministradorDAO();
                //busca Adm por cpf.
                Administrador oAdm = (Administrador) oAdmDAO.carregar(oAdmDAO.verificarCpf(cpfCnpj));
                //gera retorno
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                jsonRetorno = gson.toJson(oAdm);
            } else if (tipoPessoa.equals("cliente")){
                //implementar
                
            } else if (tipoPessoa.equals("fornecedor")){
                //implementar
                
            } else {
                //não tem ADM/Cliente ou Forn. -- então verifica Pessoa por CPF
                PessoaDAO oPessoaDAO = new PessoaDAO();
                Pessoa oPessoa = oPessoaDAO.carregarCpf(cpfCnpj);

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                jsonRetorno = gson.toJson(oPessoa);
            }
         
            response.setCharacterEncoding("iso-8859-1");
            response.getWriter().write(jsonRetorno);
            
        } catch (Exception ex) {
            System.out.println("Problemas ao carregar pessoa por CPF/CNPJ"
                    + " Erro: " + ex.getMessage());
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

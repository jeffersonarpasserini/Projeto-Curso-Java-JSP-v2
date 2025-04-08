/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package br.com.aplcurso.controller.usuario;

import br.com.aplcurso.dao.GenericDAO;
import br.com.aplcurso.dao.UsuarioDAO;
import br.com.aplcurso.model.Usuario;
import br.com.aplcurso.utils.DocumentoValidador;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jeffe
 */
@WebServlet(name = "UsuarioCadastrar", urlPatterns = {"/UsuarioCadastrar"})
public class UsuarioCadastrar extends HttpServlet {

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
            
            UsuarioDAO dao = new UsuarioDAO();
            
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            Date dataNascimento = java.sql.Date.valueOf(request.getParameter("datanascimento"));
            String cpf = request.getParameter("cpf");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            //tratamento para o campo salario R$1.000,00
            String salarioStr = request.getParameter("salario");
            // Remove "R$", espaços e pontos de milhar, e troca a vírgula por ponto
            salarioStr = salarioStr.replace("R$", "")     // remove "R$"
                           .replace(".", "")       // remove ponto de milhar
                           .replace(",", ".")      // troca vírgula decimal por ponto
                           .trim();                // remove espaços extras

            double salario = Double.parseDouble(salarioStr);
            
            //Tratamento para o CPF
            if (!DocumentoValidador.isDocumentoValido(cpf)){
                //verifica se cpf é valido
                response.getWriter().write("3");
            } else if (dao.cpfExiste(cpf)){
                //verifica se cpf já esta cadastrado
                response.getWriter().write("4");
            } else if (nome.isEmpty() || nome.isBlank() || salario <= 0 || 
                       email.isBlank() || email.isEmpty() || senha.isBlank() ||
                       senha.isEmpty()) {
                //verifica inconsistencias em outros atributos do cadastro
                response.getWriter().write("5");
            } else {
                //passou nas validacoes - grava dados
                Usuario oUsuario = new Usuario();
                oUsuario.setId(id);
                oUsuario.setNome(nome);
                oUsuario.setCpf(cpf);
                oUsuario.setDataNascimento(dataNascimento);
                oUsuario.setEmail(email);
                oUsuario.setSenha(senha);
                oUsuario.setSalario(salario);

                if (dao.cadastrar(oUsuario)){
                    response.getWriter().write("1");               
                } else {
                    response.getWriter().write("0");
                }
            }
        } catch (Exception ex){
             System.out.println("Problemas no Servlet ao cadastrar Usuario! Erro: " + ex.getMessage());
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

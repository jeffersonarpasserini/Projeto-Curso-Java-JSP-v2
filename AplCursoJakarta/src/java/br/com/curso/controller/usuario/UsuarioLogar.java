/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.curso.controller.usuario;

import br.com.curso.dao.UsuarioDAO;
import br.com.curso.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author jeffe
 */
@WebServlet(name = "UsuarioLogar", urlPatterns = {"/UsuarioLogar"})
public class UsuarioLogar extends HttpServlet {

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
            String loginUsuario = request.getParameter("login");
            String senhaUsuario = request.getParameter("senha");
            String tipoUsuario = request.getParameter("tipo");
            String usuarioLogado = "";
            
            UsuarioDAO oUsuarioDAO = new UsuarioDAO();
            Usuario oUsuario = oUsuarioDAO.logar(loginUsuario, senhaUsuario, tipoUsuario);

            if (oUsuario != null) {
                //cria a sessão no contexto do sistema
                HttpSession sessao = request.getSession();
                sessao.setAttribute("idusuario", oUsuario.getId());
                sessao.setAttribute("nomeusuario", oUsuario.getNome());
                sessao.setAttribute("tipousuario", oUsuario.getTipo());                
                usuarioLogado = "ok";
            } else {
                //usuario recusado
                System.out.println("Usuário ou senha invalida, verificar dados");
                usuarioLogado = "";
            }
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(usuarioLogado);
            
        } catch (Exception ex) {
            System.out.println("Problemas ao logar Usuário! Erro: "+ ex.getMessage());
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

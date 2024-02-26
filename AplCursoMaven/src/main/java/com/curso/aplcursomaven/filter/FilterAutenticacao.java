package com.curso.aplcursomaven.filter;

import com.curso.aplcursomaven.model.Usuario;
import com.curso.aplcursomaven.utils.SingleConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns={"/*"})
public class FilterAutenticacao implements Filter {

    private static Connection conexao;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        conexao = SingleConnection.getConnection();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
        throws IOException, ServletException {
         try{
            HttpServletRequest req = (HttpServletRequest) request;
            //Pega a sessão vigente no contexto do servidor
            HttpSession sessao = req.getSession(false);
            String urlParaAutenticar = req.getServletPath(); //Url que está sendo acessada
            
            if ((sessao != null && sessao.getAttributeNames().hasMoreElements()) ||
                    (urlParaAutenticar.equalsIgnoreCase("/index.jsp") ||
                     urlParaAutenticar.equalsIgnoreCase("/home.jsp") ||
                     urlParaAutenticar.equalsIgnoreCase("/login.jsp") ||
                     urlParaAutenticar.equalsIgnoreCase("/UsuarioBuscarPorLogin") ||
                     urlParaAutenticar.equalsIgnoreCase("/UsuarioLogar") ||
                     urlParaAutenticar.equalsIgnoreCase("/js/jquery-3.3.1.min.js") ||
                     urlParaAutenticar.equalsIgnoreCase("/js/jquery.mask.min.js") ||
                     urlParaAutenticar.equalsIgnoreCase("/js/jquery.maskMoney.min.js") ||
                     urlParaAutenticar.equalsIgnoreCase("/js/app.js"))) {
                
                    //valida controle de usuário
                    if (Usuario.verificaUsuario(urlParaAutenticar, sessao)){
                        //passou pela validação de segurança encaminha para a execução 
                        chain.doFilter(request, response);    
                    } else{
                        //se a sessão for nula volta para tela sem logar
                        request.getRequestDispatcher("/cadastros/homeLogado.jsp").forward(request, response);
                        return; //Para a execução e redireciona para o index.jsp
                    }
                        
            } else {
                //se a sessão for nula volta para tela sem logar
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                return; //Para a execução e redireciona para o index.jsp
            }
        } catch(Exception e){
            System.out.println("Erro: "+e.getMessage());
            e.printStackTrace();
        } 
    }

    @Override
    public void destroy() {
        try {
            if (conexao.isValid(0))
                conexao.close();
        } catch (SQLException ex) {
            System.out.println("Erro :" +ex.getMessage());
            ex.printStackTrace();
        }
    }  
}

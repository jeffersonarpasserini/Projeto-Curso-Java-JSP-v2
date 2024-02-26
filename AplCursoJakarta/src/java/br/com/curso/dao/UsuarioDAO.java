
package br.com.curso.dao;

import br.com.curso.model.Usuario;
import br.com.curso.utils.SingleConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection conexao;
    
    public UsuarioDAO() throws Exception{
        conexao = SingleConnection.getConnection();
    }
    
    public Usuario logar(String login, String senha, String tipo)
    {         
       PreparedStatement stmt = null;
       ResultSet rs = null;
       Usuario oUsuario = null;
       String sql = "select * from usuario u where u.login=? and u.senha=? and u.tipo=?";        
       try{
           stmt=conexao.prepareStatement(sql);
           stmt.setString(1, login);
           stmt.setString(2, senha);
           stmt.setString(3, tipo);
           rs=stmt.executeQuery();
      
           while (rs.next()){
                oUsuario = new Usuario(rs.getInt("idpessoa"),
                                rs.getString("nome"),
                                rs.getString("cpfcnpj"),
                                rs.getString("login"),
                                rs.getString("senha"),
                                rs.getString("tipo"),
                                rs.getInt("id"));
           }
           return oUsuario;
       }
       catch(SQLException ex){
           System.out.println("Problemas ao carregar usuario!"
                   + "Erro:"+ex.getMessage());
           ex.printStackTrace();
           return null;
       }
    }
    
    public List<Usuario> listar(String loginUsuario){
        List<Usuario> lstUsuario = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select * from usuario u where u.login = ?;";
        try {
          stmt = conexao.prepareStatement(sql);
          stmt.setString(1, loginUsuario);
          rs = stmt.executeQuery();
          while (rs.next()){
            Usuario oUsuario = new Usuario(rs.getInt("idpessoa"),
                                    rs.getString("nome"),
                                    rs.getString("cpfcnpj"),
                                    rs.getString("login"),
                                    rs.getString("senha"),
                                    rs.getString("tipo"),
                                    rs.getInt("id"));
             lstUsuario.add(oUsuario);
          }
        }catch (SQLException ex){
            System.out.println("Problemas ao listar Usuario! Erro:"
                    + ex.getMessage());
        } 
      return lstUsuario;
    }
}

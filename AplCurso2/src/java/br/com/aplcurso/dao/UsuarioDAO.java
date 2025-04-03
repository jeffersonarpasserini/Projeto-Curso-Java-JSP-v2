
package br.com.aplcurso.dao;

import br.com.aplcurso.model.Usuario;
import br.com.aplcurso.utils.SingleConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UsuarioDAO implements GenericDAO {

    private Connection conexao;
    
    public UsuarioDAO() throws Exception{
        conexao = SingleConnection.getConnection();
    }
    
    @Override
    public Boolean cadastrar(Object objeto) {
        Usuario oUsuario = (Usuario) objeto;
        Boolean retorno=false;
        if (oUsuario.getId()== 0) {
            retorno = this.inserir(oUsuario);
        }else{
            retorno = this.alterar(oUsuario);
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object objeto) {
        Usuario oUsuario = (Usuario) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into usuario (nome,datanascimento, cpf, email, senha, salario) "
                + "values (?,?,?,?,?,?)";  
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oUsuario.getNome());        
            stmt.setDate(2,new java.sql.Date(oUsuario.getDataNascimento().getTime()));
            stmt.setString(3, oUsuario.getCpf());
            stmt.setString(4, oUsuario.getEmail());
            stmt.setString(5, oUsuario.getSenha());
            stmt.setDouble(6, oUsuario.getSalario());
            stmt.execute();
            conexao.commit();
            return true;
        } catch (Exception ex) {
            try {
                System.out.println("Problemas ao cadastrar a Usuário! Erro: "+ex.getMessage());
                ex.printStackTrace();
                conexao.rollback();
            } catch (SQLException e) {
                System.out.println("Erro:"+e.getMessage());
                e.printStackTrace();
            }
            return false;
        } 
    }

    @Override
    public Boolean alterar(Object objeto) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Boolean excluir(int numero) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Object carregar(int numero) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<Object> listar() {
        List<Object> resultado = new ArrayList<>();
        String sql = "Select * from usuario order by id";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) 
        {

            while (rs.next()) {                
                Usuario oUsuario = new Usuario();
                oUsuario.setId(rs.getInt("id"));
                oUsuario.setNome(rs.getString("nome"));
                oUsuario.setCpf(rs.getString("cpf"));
                oUsuario.setEmail(rs.getString("email"));
                oUsuario.setSalario(rs.getDouble("salario"));
                oUsuario.setDataNascimento(rs.getDate("datanascimento"));
                resultado.add(oUsuario);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar usuários: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        return resultado;
    }

}

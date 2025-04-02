
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
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Boolean inserir(Object objeto) {
        throw new UnsupportedOperationException("Not supported yet."); 
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

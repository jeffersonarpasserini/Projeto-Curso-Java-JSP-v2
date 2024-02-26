package br.com.curso.dao;

import br.com.curso.model.Cidade;
import br.com.curso.model.Pessoa;
import br.com.curso.utils.Conversao;
import br.com.curso.utils.SingleConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class PessoaDAO {
    
    private Connection conexao;
    
    public PessoaDAO() throws Exception{
        conexao = SingleConnection.getConnection();
    }
        
    public int cadastrar(Object objeto) throws ParseException {
        Pessoa oPessoa = (Pessoa) objeto;
        int retorno = 0;
        if (oPessoa.getIdPessoa()==0)
        {
            Pessoa objPessoa = this.carregarCpf(oPessoa.getCpfCnpj());
            if (objPessoa.getIdPessoa()==0)
                retorno = this.inserir(oPessoa);
            else
                retorno = objPessoa.getIdPessoa();
        }
        else {
            retorno = this.alterar(oPessoa);
        }
        return retorno;
    }
    
    public int inserir(Object objeto) {
        Pessoa oPessoa = (Pessoa) objeto;
        PreparedStatement stmt = null;
        ResultSet rs=null;
        Integer idPessoa=null;
        String sql = "insert into pessoa (cpfcnpj, nome, datanascimento, idcidade, login,"
                + "senha, foto) values (?, ?, ?, ?, ?, ?, ?) returning idPessoa;";
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oPessoa.getCpfCnpj());
            stmt.setString(2, oPessoa.getNome());
            stmt.setDate(3, new java.sql.Date(oPessoa.getDataNascimento().getTime()));
            stmt.setInt(4, oPessoa.getCidade().getIdCidade());
            stmt.setString(5, oPessoa.getLogin());
            stmt.setString(6, oPessoa.getSenha());
            stmt.setString(7, oPessoa.getFoto());
            rs=stmt.executeQuery();
            conexao.commit();
            
            while (rs.next()){
                idPessoa = rs.getInt("idPessoa");
            }
        } catch (SQLException e){
            try {
                System.out.println("Problemas ao cadastrar Pessoa!Erro: " + e.getMessage());
                e.printStackTrace();
                conexao.rollback();  
            } catch (SQLException ex) {
                System.out.println("Problemas ao executar rollback" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return idPessoa;
    }
    
    public int alterar(Object objeto) {
        Pessoa oPessoa = (Pessoa) objeto;
        PreparedStatement stmt = null;
        Integer idPessoa=oPessoa.getIdPessoa();
        String sql = "update pessoa set nome=?, datanascimento=?, idcidade=?,"
                + " login=?, senha=?, foto=? "
                + "where idpessoa=?;";
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oPessoa.getNome());
            stmt.setDate(2, new java.sql.Date(oPessoa.getDataNascimento().getTime()));
            stmt.setInt(3, oPessoa.getCidade().getIdCidade());
            stmt.setString(4, oPessoa.getLogin());
            stmt.setString(5, oPessoa.getSenha());
            stmt.setString(6, oPessoa.getFoto());
            stmt.setInt(7, oPessoa.getIdPessoa());
            stmt.execute();
            conexao.commit();
        } catch (SQLException e){
            try {
                System.out.println("Problemas ao alterar Pessoa!Erro: " + e.getMessage());
                e.printStackTrace();
                conexao.rollback();  
            } catch (SQLException ex) {
                System.out.println("Problemas ao executar rollback" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return idPessoa;
    }
    
    public Pessoa carregar(int id){
        int idPessoa = id;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pessoa oPessoa = null;
        String sql = "Select * from pessoa where idpessoa=?";
        
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setInt(1, idPessoa);
            rs=stmt.executeQuery();            

            while(rs.next()){
                
                Cidade oCidade = null;
                try{
                   CidadeDAO oCidadeDAO = new CidadeDAO();
                   int idCidade = rs.getInt("idcidade");
                   oCidade = (Cidade) oCidadeDAO.carregar(idCidade);
                }catch(Exception ex){
                   System.out.println("Problemas ao carregar usuario! Erro:"+ex.getMessage());
                }

                oPessoa = new Pessoa(rs.getInt("idpessoa"),
                                     rs.getString("cpfcnpj"),
                                     rs.getString("nome"),
                                     rs.getDate("datanascimento"),
                                     oCidade,
                                     rs.getString("login"),
                                     rs.getString("senha"),
                                     rs.getString("foto"));
            }
            return oPessoa;
        }catch(SQLException ex){
            System.out.println("Problemas ao carregar pessoa! Erro "+ex.getMessage());
            return null;
        }   
    }
    
    public Pessoa carregarCpf(String cpf) throws ParseException {
       PreparedStatement stmt = null;
       ResultSet rs = null;
       Pessoa oPessoa = null;
       String sql = "Select * from pessoa where cpfcnpj=?;";

       try{
            stmt=conexao.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs=stmt.executeQuery();
            while (rs.next()){
               oPessoa = this.carregar(rs.getInt("idpessoa"));
            }
            if (oPessoa == null)
            {
                Date novaData = Conversao.dataAtual();
                Cidade oCidade = new Cidade();
                oPessoa = new Pessoa(0,"","",novaData,oCidade,"","",null);
            }
       }
       catch(SQLException ex){
           System.out.println("Problemas ao carregar pessoa! Erro:"+ex.getMessage());
       }
       return oPessoa;
    }
}

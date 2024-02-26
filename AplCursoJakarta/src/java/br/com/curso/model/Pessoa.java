package br.com.curso.model;

import java.util.Date;

public class Pessoa {
  
    private int idPessoa;
    private String cpfCnpj;
    private String nome;
    private Date dataNascimento;
    private Cidade cidade;
    private String login;
    private String senha;
    private String foto;

    public Pessoa(int idPessoa, String cpfCnpj, String nome, Date dataNascimento, Cidade cidade, 
            String login, String senha, String foto) {
        this.idPessoa = idPessoa;
        this.cpfCnpj = cpfCnpj;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cidade = cidade;
        this.login = login;
        this.senha = senha;
        this.foto = foto;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

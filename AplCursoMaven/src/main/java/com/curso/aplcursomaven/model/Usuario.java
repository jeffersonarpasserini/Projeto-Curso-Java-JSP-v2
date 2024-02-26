package com.curso.aplcursomaven.model;

import jakarta.servlet.http.HttpSession;

public class Usuario {
    
    private int idPessoa;
    private String nome;
    private String cpfcnpj;
    private String login;
    private String senha;
    private String tipo;
    private int id;

    public Usuario() {
        this.idPessoa = 0;
        this.id = 0;
        this.tipo = "";
    }

    public Usuario(int idPessoa, String nome, String cpfcnpj, String login, String senha, String tipo, int id) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.cpfcnpj = cpfcnpj;
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
        this.id = id;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public static boolean verificaUsuario(String recurso, HttpSession sessao){
        boolean status=false;
        
        try{
            //se for um acesso liberado permiter passagem
            if ( recurso.equalsIgnoreCase("/index.jsp") ||
                     recurso.equalsIgnoreCase("/home.jsp") ||
                     recurso.equalsIgnoreCase("/login.jsp") ||
                     recurso.equalsIgnoreCase("/UsuarioBuscarPorLogin") ||
                     recurso.equalsIgnoreCase("/UsuarioLogar") ||
                     recurso.equalsIgnoreCase("/js/jquery-3.3.1.min.js") ||
                     recurso.equalsIgnoreCase("/js/jquery.mask.min.js") ||
                     recurso.equalsIgnoreCase("/js/jquery.maskMoney.min.js") ||
                     recurso.equalsIgnoreCase("/js/app.js")) {
                status = true;
            }
            
            if(sessao != null && sessao.getAttributeNames().hasMoreElements()){
                //pega dados do usuário
                int idUsuario = Integer.parseInt(sessao.getAttribute("idusuario").toString());
                String tipoUsuario = sessao.getAttribute("tipousuario").toString();
                
                //verifica permissões
                //se for administrador libera todos os recursos
                if (tipoUsuario.equalsIgnoreCase("administrador")){
                    status=true;
                } else {
                    if (tipoUsuario.equalsIgnoreCase("Cliente"))
                    {
                        if (tipoUsuario.equalsIgnoreCase("/CidadeCarregar") ||
                            recurso.equalsIgnoreCase("/CidadeCarregar") ||
                            recurso.equalsIgnoreCase("/CidadeAlterar") ||
                            recurso.equalsIgnoreCase("/CidadeListar") ||
                            recurso.equalsIgnoreCase("/CidadeNovo") || 
                            recurso.equalsIgnoreCase("/EstadoCadastrar") ||
                            recurso.equalsIgnoreCase("/EstadoCarregar") ||
                            recurso.equalsIgnoreCase("/EstadoAlterar") ||
                            recurso.equalsIgnoreCase("/EstadoListar") ||
                            recurso.equalsIgnoreCase("/EstadoNovo") ||
                            recurso.equalsIgnoreCase("/UsuarioDeslogar") ||    
                            recurso.equalsIgnoreCase("/cadastros/homeLogado.jsp")){
                            status=true;//permite acesso ao usuario tipo cliente
                        }
                    }
                    
                    if (tipoUsuario.equalsIgnoreCase("Fornecedor"))
                    {
                        if (recurso.equalsIgnoreCase("/EstadoCadastrar") ||
                            recurso.equalsIgnoreCase("/EstadoCarregar") ||
                            recurso.equalsIgnoreCase("/EstadoAlterar") ||
                            recurso.equalsIgnoreCase("/EstadoListar") ||
                            recurso.equalsIgnoreCase("/EstadoNovo") ||
                            recurso.equalsIgnoreCase("/UsuarioDeslogar") ||    
                            recurso.equalsIgnoreCase("/cadastros/homeLogado.jsp")){
                            //permite acesso ao usuario tipo cliente
                            status=true;
                        }
                    } 
                }
            }
        } catch (Exception ex){
             System.out.println("Erro: " + ex.getMessage());
             ex.printStackTrace();
        }        
        return status;
    }
}

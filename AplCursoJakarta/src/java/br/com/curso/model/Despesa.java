/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.curso.model;

import br.com.curso.utils.Conversao;
import java.util.Date;

public class Despesa {
    private int idDespesa;
    private String descricao;
    private Date dataDocumento;
    private double valorDespesa;
    private double valorPago;
    private String imagemDocumento;

    public Despesa() {
        idDespesa = 0;
        descricao = "";
        valorDespesa = 0;
        valorPago = 0;
        dataDocumento = Conversao.dataAtual();
    }

    public Despesa(int idDespesa, String descricao, Date dataDocumento, double valorDespesa, 
            double valorPago, String imagemDocumento) {
        this.idDespesa = idDespesa;
        this.descricao = descricao;
        this.dataDocumento = dataDocumento;
        this.valorDespesa = valorDespesa;
        this.valorPago = valorPago;
        this.imagemDocumento = imagemDocumento;
    }

    public int getIdDespesa() {
        return idDespesa;
    }

    public void setIdDespesa(int idDespesa) {
        this.idDespesa = idDespesa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(Date dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public double getValorDespesa() {
        return valorDespesa;
    }

    public void setValorDespesa(double valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public String getImagemDocumento() {
        return imagemDocumento;
    }

    public void setImagemDocumento(String imagemDocumento) {
        this.imagemDocumento = imagemDocumento;
    }
}

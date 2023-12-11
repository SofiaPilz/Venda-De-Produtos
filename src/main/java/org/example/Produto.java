package org.example;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class Produto implements Comparable<Produto> {
    static Locale locale = new Locale("pt", "BR");
    static NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance();

    String descricao;
    Double valor;

    // construtores

    public Produto() {
    }

    public Produto(String descricao, Double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }


    // getter setter

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    // precisa tem uma forma string pra caso eu queira chamar  só String
    public String getValorFormatado() {
        return formatoMoeda.format(valor);
    }


    // forma q formata o valor
    public void setValor(Double valor) {
        this.valor = formatarValorProduto(valor);
    }


    // toString

    @Override
    public String toString() {
        return "Produto{" +
                "descricao='" + descricao + '\'' +
                ", valor=" + getValorFormatado() +
                '}';
    }

    // equals e hashcode
    @Override
    public boolean equals(Object o) {
        //se nao forem objetos da mesma classe sao objetos diferente
        if (this == o) return true;
        //se forem o mesmo objeto, retorna true
        if (!(o instanceof Produto produto)) return false;
        // compara com base nos produtos relevantes
        return Objects.equals(getDescricao(), produto.getDescricao()) && Objects.equals(getValor(), produto.getValor());
    }

    @Override
    public int hashCode() {
        //método hashCode gera um código hash com base nos atributos descricao e valor.
        //Isso garante que objetos iguais (conforme definido pelo método equals) tenham o mesmo código hash
        return Objects.hash(getDescricao(), getValor());
    }

    // metodos
    public int compareTo(Produto outroProduto) {
return this.descricao.compareTo(outroProduto.descricao);
    }


    public Double formatarValorProduto(double naoFormatado) {
        String valorFormatado = formatoMoeda.format(naoFormatado);
        return Double.parseDouble(valorFormatado);

    }



} // PRODUTO


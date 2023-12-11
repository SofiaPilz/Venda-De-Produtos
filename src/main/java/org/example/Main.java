package org.example;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

//Construa uma aplicação que permita a venda de produtos. Ao iniciar as vendas, o usuário tem a
//opção de registrar, onde é exibido a lista de produtos com a sua descrição e respectivo valor (pode ser fixado
//valores no programa), devendo selecionar um destes e informar uma quantidade vendida. O usuário deve ser
//questionado se deseja adicionar outro até dizer que finalizou e então definir a forma de pagamento escolhida
//para a venda (Dinheiro, Débito, Crédito ou Pix). A outra opção disponível é finalizar vendas, onde então é
//exibido um resumo informando a data e hora de início e fim das vendas, quantidade vendida de cada produto
//e valor total (do maior para o menor), quantidade de venda por forma de pagamento e valor total (do menor
//para o maior) e total de vendas registradas. Os valores precisam estar formatados em moeda, ex: R$ 150,00.


public class Main {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        Controller controller = new Controller();
        controller.addProdutos();

        while (true) {
            System.out.println("................................");
            System.out.println("      1) REGISTRAR VENDA        ");
            System.out.println("       2) ENCERRAR CAIXA        ");
            System.out.println("................................");

            while (true) {
                try {
                    int acao = in.nextInt();
                    in.nextLine();

                    switch (acao) {
                        case 1:
                            controller.registrarVenda();
                            break;
                        case 2:
                            controller.finalizarVenda();
                            break;
                        default:
                            System.out.println("Erro");
                            break;
                    }
                    break;

                } catch (InputMismatchException e) {
                    System.out.println("Erro, código inválido. Tente novamente: ");
                    in.nextLine();
                }
            }
        }
    }
} // MAIN
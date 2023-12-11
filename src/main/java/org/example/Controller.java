package org.example;

//import java.security.PublicKey;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;

public class Controller {
    static ArrayList<Produto> listaProdutos = new ArrayList<>();
    static int[] contarPagamentos = new int[3];
    static Scanner in = new Scanner(System.in);
    static Locale locale = new Locale("pt", "BR");

    // para pegar o idioma e o país do ambiente q o código está sendo executado
    // Locale locale = Locale.getDefault();
    static NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(locale);
    static StringBuilder nota = new StringBuilder();

    // HASHMAP
    static TreeMap<Produto, Double> totaisProdutosVendidos = new TreeMap<>();
    static TreeMap<Produto, Integer> quantidadeProdutosVendidos = new TreeMap<>();
    static TreeMap<Integer, Integer> salvarPagamento = new TreeMap<>();
    static TreeMap<Integer, Double> salvarTotalPagamento = new TreeMap<>();


    // REGISTRAR-----------------------------------------------------------------------------------------------------------
    public void registrarVenda() {
        iniciarVendas();
        cadastrarCompras();
    }


// FINALIZAR-----------------------------------------------------------------------------------------------------------

    public void finalizarVenda() {
        finalizarVendas();
        System.out.println("Relatório do dia: ");
        System.out.println("Abertura do caixa: " + inicio);
        System.out.println("Fechamento de caixa: " + finalizar);

        System.out.println("――――――――――――――――――――");
        System.out.println("| QUANTIDADE DE PRODUTOS VENDIDOS |");
        imprimirVendas();

        System.out.println("―――――――――――――――――――");
        System.out.println("| FORMAS DE PAGAMENTO UTILIZADAS |");
        imprimirPagamentos();
        zerarTudo();

    }

    // HORARIOS ------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
    public LocalDateTime inicio;
    public LocalDateTime finalizar;

    public void iniciarVendas() {
        this.inicio = LocalDateTime.now();
    }

    public void finalizarVendas() {
        this.finalizar = LocalDateTime.now();
    }

    // METODOS----------------------------------------------------------------------------------------------------------
// ADICIONAR PRODUTOS---------------------------------------------------------------------------------------------------
    public void addProdutos() {
        Produto p1 = new Produto("Água", 3.5);
        listaProdutos.add(p1);

        Produto p2 = new Produto("Suco", 4.0);
        listaProdutos.add(p2);

        Produto p3 = new Produto("Salgadinho", 6.0);
        listaProdutos.add(p3);

        Produto p4 = new Produto("Uva Verde", 14.5);
        listaProdutos.add(p4);

        Produto p5 = new Produto("Detergente", 7.5);
        listaProdutos.add(p5);

        Produto p6 = new Produto("Sorvete 1L", 46.5);
        listaProdutos.add(p6);

    }

//FORMATAR O VALOR
//-----------------------------------------------------------------------------------------------------------------------
//CHAMAR LISTA D PRODUTOS
//----------------------------------------------------------------------------------------------------------------------
    // quando chama dnv na hr d add mais intens no carrinha ela add os msm produtos na lista dnv

    //    public double formatarValor(double naoFormatado) {
//        String valorFormatado = formatoMoeda.format(naoFormatado);
//        return Double.parseDouble(valorFormatado);
//    }
    public static StringBuilder chamaListaProdutos() {
        StringBuilder lista = new StringBuilder();
        int i = 0;
        for (Produto produto : listaProdutos) {
            i++;
            lista.append(i).append(") ").append(produto.getValorFormatado()).append("  |  ").append(produto.getDescricao()).append("\n").append("-------------------------").append("\n");
        }
        return lista;
    }

// COMPRAS DE PRODUTOS
//----------------------------------------------------------------------------------------------------------------------

    public void cadastrarCompras() {
        StringBuilder mercadorias = chamaListaProdutos();
        pagamento(escolherProdutos(mercadorias)); // pagamento ja recolhe o valor total para o contador
    }


    // ESCOLHER PRODUTO
//--------------------------------------------------------------------------------------------------------------
    public double escolherProdutos(StringBuilder mercadorias) {
        double totalCompra = 0.0;
        int j = 0;

        while (true) {
            try {
                System.out.println(mercadorias);
                System.out.print("Indique o indice do produto que deseja adicionar ao carrinho: ");

                int indiceProduto = (in.nextInt() - 1);
                in.nextLine();
                if (indiceProduto >= 0 && indiceProduto < listaProdutos.size()) {
                    Produto produtoSelecionado = listaProdutos.get(indiceProduto);
                    double valorProduto = produtoSelecionado.getValor();

                    // METODO quantidadeProduto
                    int quantidade = quantidadeProduto();
                    // hashmap dentro de contagem
                    contagem(quantidade, produtoSelecionado);

                    double totalProduto = (valorProduto) * quantidade;

                    totalCompra += totalProduto;
                    String totalCompraFormatado = formatoMoeda.format(totalCompra);

                    System.out.println("-------- CARRINHO ---------");
                    System.out.print(nota);
                    for (int contador = quantidade; contador > 0; contador--) {
                        j++;
                        System.out.println(j + ") " + produtoSelecionado.getValorFormatado() + "  |  " + produtoSelecionado.getDescricao());
                        System.out.println("----------------------------");
                        nota.append(j).append(") ").append(produtoSelecionado.getValorFormatado()).append("  |  ").append(produtoSelecionado.getDescricao()).append("\n").append("----------------------------").append("\n");
                    }
                    System.out.println("Valor Total: " + totalCompraFormatado + " ------");

                    System.out.println("Deseja adicionar mais produtos? ");
                    System.out.println("       0- SIM      1- NÃO       ");
                    while (true) {
                        try {
                            int resposta = in.nextInt();
                            in.nextLine();
                            if (resposta == 0) {
                                break;
                            } else if (resposta == 1) {
                                nota.append("Valor Total: ").append(totalCompraFormatado).append(" ------").append("\n");
                                System.out.println(nota);
                                return totalCompra;

                            } else {
                                System.out.print("Indice inválido, digite um existente. Tente novamente: ");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Erro, tente novamente: ");
                            in.nextLine();
                        }
                    }
                } else {
                    System.out.println("Indice inválido, digite um existente. Tente novamente: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Erro, digite um indice válido. Tente novamente: ");
                in.nextLine();
            }
        }
    }


    // PERGUNTA A QUANTIDADE DO PRODUTO
//----------------------------------------------------------------------------------------------------------------------
    public static int quantidadeProduto() {
        System.out.print("Digite a quantidade desejada: ");
        while (true) {
            try {
                int quantidade = in.nextInt();
                in.nextLine();
                return quantidade;

            } catch (InputMismatchException e) {
                System.out.print("Erro, digite um valor exixtente. Tente novamente: ");
                in.nextLine();
            }

        }
    }

// FORMA DE PAGAMENTO
//----------------------------------------------------------------------------------------------------------------------

    public void pagamento(double totalCompra) {
        System.out.println("Indique a forma de pagamento desejada: ");
        System.out.println("     [ 1 ] - PIX     ");
        System.out.println("     [ 2 ] - CRÉDITO     ");
        System.out.println("     [ 3 ] - DÉBITO     ");
        System.out.println("     [ 4 ] - DINHEIRO     ");
        while (true) {
            try {
                int formaPagamento = in.nextInt();
                in.nextLine();
                if (formaPagamento == 1) {
                    contagemPagamentos(formaPagamento, totalCompra);
                    nota.append("Forma de pagamento selecionada: PIX").append("\n");
                    System.out.println(nota);
                    System.out.println("Chave do pix: 039.524.860-43");
                    System.out.println("Pagamento finalizado!");
                    nota.setLength(0);
                    break;

                } else if (formaPagamento == 2) {
                    contagemPagamentos(formaPagamento, totalCompra);
                    nota.append("Forma de pagamento selecionada: CRÉDITO").append("\n");
                    System.out.println(nota);
                    System.out.println("Insira o cartão.");
                    System.out.println("Pagamento finalizado!");
                    nota.setLength(0);
                    break;

                } else if (formaPagamento == 3) {
                    contagemPagamentos(formaPagamento, totalCompra);
                    nota.append("Forma de pagamento selecionada: DÉBITO").append("\n");
                    System.out.println(nota);
                    System.out.println("Insira o cartão.");
                    System.out.println("Pagamento finalizado!");
                    nota.setLength(0);
                    break;

                    // ESTOURA UMA EXCESSAO QUANDO SELECIONA DINHEIRO = ArrayIndexOutOfBoundsExceptio

                } else if (formaPagamento == 4) {
                    contagemPagamentos(formaPagamento, totalCompra);
                    nota.append("Forma de pagamento selecionada: DINHEIRO").append("\n");
                    System.out.println(nota);
                    System.out.println("Pagamento finalizado!");
                    nota.setLength(0);
                    break;

                } else {
                    System.out.println("Código inválido, tente novamente: ");
                }


            } catch (InputMismatchException e) {
                System.out.println("Erro, digite um código válido. Tente novamente: ");
                in.nextLine();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Erro, digite um código válido. Tente novamente: ");
                in.nextLine();
            }
        }
    }


    // CONTAGEM E SALVAR VENDAS
//----------------------------------------------------------------------------------------------------------------------
    int somaA = 0;
    double totalA = 0.0;

    int somaB = 0;
    double totalB = 0.0;

    int somaC = 0;
    double totalC = 0.0;

    int somaD = 0;
    double totalD = 0.0;

    int somaE = 0;
    double totalE = 0.0;

    int somaF = 0;
    double totalF = 0.0;


    public void contagem(int quantidade, Produto produto) {
        if (produto.equals(listaProdutos.get(0))) {
            somaA += quantidade;
            totalA += (quantidade * produto.valor);
            totaisProdutosVendidos.put(produto, totalA);
            quantidadeProdutosVendidos.put(produto, quantidadeProdutosVendidos.getOrDefault(produto, 0) + somaA);


        } else if (produto.equals(listaProdutos.get(1))) {
            somaB += quantidade;
            totalB += (quantidade * produto.valor);
            totaisProdutosVendidos.put(produto, totalB);
            quantidadeProdutosVendidos.put(produto, quantidadeProdutosVendidos.getOrDefault(produto, 0) + somaB);


        } else if (produto.equals(listaProdutos.get(2))) {
            somaC += quantidade;
            totalC += (quantidade * produto.valor);
            totaisProdutosVendidos.put(produto, totalC);
            quantidadeProdutosVendidos.put(produto, quantidadeProdutosVendidos.getOrDefault(produto, 0) + somaC);


        } else if (produto.equals(listaProdutos.get(3))) {
            somaD += quantidade;
            totalD += (quantidade * produto.valor);
            totaisProdutosVendidos.put(produto, totalD);
            quantidadeProdutosVendidos.put(produto, quantidadeProdutosVendidos.getOrDefault(produto, 0) + somaD);


        } else if (produto.equals(listaProdutos.get(4))) {
            somaE += quantidade;
            totalE += (quantidade * produto.valor);
            totaisProdutosVendidos.put(produto, totalE);
            quantidadeProdutosVendidos.put(produto, quantidadeProdutosVendidos.getOrDefault(produto, 0) + somaE);


        } else if (produto.equals(listaProdutos.get(5))) {
            somaF += quantidade;
            totalF += (quantidade * produto.valor);
            totaisProdutosVendidos.put(produto, totalF);
            quantidadeProdutosVendidos.put(produto, quantidadeProdutosVendidos.getOrDefault(produto, 0) + somaF);

        }

    }


// IMPRIMIR VENDAS
//----------------------------------------------------------------------------------------------------------------------

    // NAO TA IMPRIMINDO NA ORDEM CERTA

    public static void imprimirVendas() {
        // Convertendo TreeMap para LinkedHashMap para inverter a ordem (DECRESCENTE), da treemap com a informação q precisa estar na ordem (totais)
        Map<Produto, Double> imprimirTotaisDecrescente = new LinkedHashMap<>(totaisProdutosVendidos);

        for (Map.Entry<Produto, Double> totais : imprimirTotaisDecrescente.entrySet()) {
            Produto produto = totais.getKey();
            double valorTotal = totais.getValue();
            String valorTotalFormatado = formatoMoeda.format(valorTotal);

            // trazendo info de outra treemap que tem a mesma chave (produto)
            //int quantidade = quantidadeProdutosVendidos.get(produto);
            double quantDouble = (valorTotal / produto.getValor());
            int quantidade = (int) quantDouble;

            System.out.println("――――――――――――――――――――");
            System.out.println("   "+ produto.getDescricao() + ":  " + quantidade + "  ⎸  " + valorTotalFormatado);
        }
        System.out.println("――――――――――――――――――――");
    }

    // CONTAGEM POR FORMA DE PAGAMENTO E TOTAIS
//----------------------------------------------------------------------------------------------------------------------
    int pix = 0;
    double totalPix = 0.0;
    int credito = 0;
    double totalCred = 0.0;
    int debito = 0;
    double totalDeb = 0.0;
    int dinheiro = 0;
    double totalDin = 0.0;

    // TROCQUE A CHAVE PARA UMA STRING EM VEZ D UM NUMERO

    public void contagemPagamentos(int formaPagamento, double valorTotal) {
        if (formaPagamento == 1) {
            pix += 1;
            contarPagamentos[0] = pix;
            totalPix += valorTotal;
            //salvarPagamento.put(1, contarPagamentos[0]);
            salvarTotalPagamento.put(1, salvarTotalPagamento.getOrDefault(1, 0.0) + totalPix);

        } else if (formaPagamento == 2) {
            credito += 1;
            contarPagamentos[1] = credito;
            totalCred += valorTotal;
            //salvarPagamento.put(2, contarPagamentos[1]);
            salvarTotalPagamento.put(2, salvarTotalPagamento.getOrDefault(2, 0.0) + totalCred);

        } else if (formaPagamento == 3) {
            debito += 1;
            contarPagamentos[2] = debito;
            totalDeb += valorTotal;
            //salvarPagamento.put(3, contarPagamentos[2]);
            salvarTotalPagamento.put(3, salvarTotalPagamento.getOrDefault(3, 0.0) + totalDeb);

        } else if (formaPagamento == 4) {
            dinheiro += 1;
            contarPagamentos[3] = dinheiro;
            totalDin += valorTotal;
            //salvarPagamento.put(4, contarPagamentos[3]);
            salvarTotalPagamento.put(4, salvarTotalPagamento.getOrDefault(4, 0.0) + totalDin);
        }

    }

    // IMPRIMIR CONTAGEM PAGAMENTOS
//---------------------------------------------------------------------------------------------------------------------

    // NAO TA IMPRIMINDO NA ORDEM CERTA
    // ou nem imprime, tasomando errado os valores totais tb

    public void imprimirPagamentos() {
        // Convertendo TreeMap para LinkedHashMap para inverter a ordem (CRESCENTE), da treemap com a informação q precisa estar na ordem (totais)
        // descending.map = CRESCENTE
        Map<Integer, Double> imprimirTotaisCrescente = new LinkedHashMap<>(salvarTotalPagamento.descendingMap());

        for (Map.Entry<Integer, Double> totais : imprimirTotaisCrescente.entrySet()) {
            int chave = totais.getKey();
            double valorTotal = totais.getValue();
           // String valorTotalFormatado = formatoMoeda.format(valorTotal);

            // trazendo info de outra treemap que tem a mesma chave (produto)

            // N TA DANDO CERTOOOOOOOOOOOOOOOOOOOOOOO
            //int quantidade = salvarPagamento.get(chave);

            //valorTotalFormatado
            //System.out.println("―――――――――――――――――――");
            //                System.out.println("   " + chave + ":  " + quantidade + "    |  " + formatoMoeda.format(totalPix));

            int quantidade = 0;
            String pagamento = "a";
            if (chave == 1) {
                quantidade = pix;
                pagamento = "pix";

            } else if (chave == 2) {
               quantidade = credito;
               pagamento = "crédito";

            } else if (chave == 3) {
                quantidade = debito;
                pagamento = "débito";

            } else if (chave == 4) {
                quantidade = dinheiro;
                pagamento = "dinheiro";
            }
            System.out.println("―――――――――――――――――――");
            System.out.println("   " + pagamento + ":  " + quantidade + "    |  " + formatoMoeda.format(valorTotal));

        }
        System.out.println("―――――――――――――――――――");
    }


    // ZERAR TUDO
//----------------------------------------------------------------------------------------------------------------------
    public void zerarTudo() {
        somaA = 0;
        somaB = 0;
        somaC = 0;
        somaD = 0;
        somaE = 0;
        somaF = 0;
        pix = 0;
        credito = 0;
        debito = 0;
        dinheiro = 0;

    }

} // CONTROLLER

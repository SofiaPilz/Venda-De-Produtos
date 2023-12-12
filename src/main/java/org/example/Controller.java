package org.example;

//import java.security.PublicKey;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    static TreeMap<Produto, Integer> quantidadeProdutosVendidos = new TreeMap<>();
    static TreeMap<Produto, Double> totaisProdutosVendidos = new TreeMap<>();
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
    int somaB = 0;
    int somaC = 0;
    int somaD = 0;
    int somaE = 0;
    int somaF = 0;

    public Controller() {
    }

    public int getSomaA() {
        return somaA;
    }

    public void setSomaA(int somaA) {
        this.somaA = somaA;
    }

    public int getSomaB() {
        return somaB;
    }

    public void setSomaB(int somaB) {
        this.somaB = somaB;
    }

    public int getSomaC() {
        return somaC;
    }

    public void setSomaC(int somaC) {
        this.somaC = somaC;
    }

    public int getSomaD() {
        return somaD;
    }

    public void setSomaD(int somaD) {
        this.somaD = somaD;
    }

    public int getSomaE() {
        return somaE;
    }

    public void setSomaE(int somaE) {
        this.somaE = somaE;
    }

    public int getSomaF() {
        return somaF;
    }

    public void setSomaF(int somaF) {
        this.somaF = somaF;
    }

    public void contagem(int quantidade, Produto produto) {
        if (produto.equals(listaProdutos.get(0))) {
            setSomaA(getSomaA() + quantidade);
            quantidadeProdutosVendidos.put(produto, getSomaA());

        } else if (produto.equals(listaProdutos.get(1))) {
            setSomaB(getSomaB() + quantidade);
            quantidadeProdutosVendidos.put(produto, getSomaB());

        } else if (produto.equals(listaProdutos.get(2))) {
            setSomaC(getSomaC() + quantidade);
            quantidadeProdutosVendidos.put(produto, getSomaC());

        } else if (produto.equals(listaProdutos.get(3))) {
            setSomaD(getSomaD() + quantidade);
            quantidadeProdutosVendidos.put(produto, getSomaD());

        } else if (produto.equals(listaProdutos.get(4))) {
            setSomaE(getSomaE() + quantidade);
            quantidadeProdutosVendidos.put(produto, getSomaE());

        } else if (produto.equals(listaProdutos.get(5))) {
            setSomaF(getSomaF() + quantidade);
            quantidadeProdutosVendidos.put(produto, getSomaF());
        }

        for (Map.Entry<Produto, Integer> tipo : quantidadeProdutosVendidos.entrySet()) {
            Produto chave = tipo.getKey();
            int quant = tipo.getValue();
            // calcula e ja salva
            totaisProdutosVendidos.put(chave, calcularTotal(chave, quant));
        }
    }


// IMPRIMIR VENDAS
//----------------------------------------------------------------------------------------------------------------------

    public static void imprimirVendas() {
        //Criar uma lista Map.Entry ordenada pelos valores totais, parte q realmente ordena é do .sorted ate p reversed()
        // o reversed inverte a ordem para ficar na ordem DECRESCENTE
        List<Map.Entry<Produto, Double>> vendasOrdenadas = totaisProdutosVendidos.entrySet().stream()
                .sorted(Map.Entry.<Produto, Double>comparingByValue().reversed())
                .toList();

        // Imprimir as informações ordenadas
        for (Map.Entry<Produto, Double> venda : vendasOrdenadas) {
            Produto produto = venda.getKey();
            double valorTotal = venda.getValue();
            double calcularQuant = (valorTotal / produto.getValor());
            int quantidade = (int) calcularQuant;

            System.out.println("――――――――――――――――――――");
            System.out.println(produto.getDescricao() + ":  " + quantidade + "  |  " + formatoMoeda.format(valorTotal));
        }

        System.out.println("――――――――――――――――――――");
        quantidadeProdutosVendidos.clear();
    }


    public static double calcularTotal(Produto produto, int quantidade) {
        return produto.getValor() * quantidade;
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

    public int getPix() {
        return pix;
    }

    public void setPix(int pix) {
        this.pix = pix;
    }

    public double getTotalPix() {
        return totalPix;
    }

    public void setTotalPix(double totalPix) {
        this.totalPix = totalPix;
    }

    public int getCredito() {
        return credito;
    }

    public void setCredito(int credito) {
        this.credito = credito;
    }

    public double getTotalCred() {
        return totalCred;
    }

    public void setTotalCred(double totalCred) {
        this.totalCred = totalCred;
    }

    public int getDebito() {
        return debito;
    }

    public void setDebito(int debito) {
        this.debito = debito;
    }

    public double getTotalDeb() {
        return totalDeb;
    }

    public void setTotalDeb(double totalDeb) {
        this.totalDeb = totalDeb;
    }

    public int getDinheiro() {
        return dinheiro;
    }

    public void setDinheiro(int dinheiro) {
        this.dinheiro = dinheiro;
    }

    public double getTotalDin() {
        return totalDin;
    }

    public void setTotalDin(double totalDin) {
        this.totalDin = totalDin;
    }


    public void contagemPagamentos(int formaPagamento, double valorTotal) {
        if (formaPagamento == 1) {
            setPix(getPix() + 1);;
            setTotalPix(getTotalPix() + valorTotal);
            salvarPagamento.put(1, getPix());
            salvarTotalPagamento.put(1, getTotalPix());

        } else if (formaPagamento == 2) {
            setCredito(getCredito() + 1);
            setTotalCred(getTotalCred() + valorTotal);
            salvarPagamento.put(2, getCredito());
            salvarTotalPagamento.put(2, getTotalCred());

        } else if (formaPagamento == 3) {
            setDebito(getDebito() + 1);
            setTotalDeb(getTotalDeb() + valorTotal);
            salvarPagamento.put(3, getDebito());
            salvarTotalPagamento.put(3, getTotalDeb());

        } else if (formaPagamento == 4) {
            setDinheiro(getDinheiro() + 1);
            setTotalDin(getTotalDin() + valorTotal);
            salvarPagamento.put(4, getDinheiro());
            salvarTotalPagamento.put(4, getTotalDin());
        }

    }

    // IMPRIMIR CONTAGEM PAGAMENTOS
//---------------------------------------------------------------------------------------------------------------------

    // NAO TA IMPRIMINDO NA ORDEM CERTA
    // ou nem imprime, tasomando errado os valores totais tb
    public void imprimirPagamentos() {
       // lista map entry ordenada pelos valores totais q acompanham as chaves
        List<Map.Entry<Integer, Double>> pagamentosOrdenados = salvarTotalPagamento.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue()).toList();

        for (Map.Entry<Integer, Double> totais : pagamentosOrdenados) {
            int chave = totais.getKey();
            double valorTotal = totais.getValue();
           int quantidade = salvarPagamento.get(chave);

            String pagamento = "a";
            switch (chave) {
                case 1:
                    pagamento = "pix";
                    break;

                case 2:
                    pagamento = "crédito";
                    break;

                case 3:
                    pagamento = "débito";
                    break;

                case 4:
                    pagamento = "dinheiro";
                    break;
            }
            System.out.println("―――――――――――――――――――");
            System.out.println(pagamento + ":  " + quantidade + "    |  " + formatoMoeda.format(valorTotal));

        }
        System.out.println("―――――――――――――――――――");
        salvarTotalPagamento.clear();
        salvarPagamento.clear();
        pix = 0;
        totalPix = 0.0;
        credito = 0;
        totalCred = 0.0;
        debito = 0;
        totalDeb = 0.0;
        dinheiro = 0;
        totalDin = 0.0;
    }


    // ZERAR TUDO
//----------------------------------------------------------------------------------------------------------------------


} // CONTROLLER

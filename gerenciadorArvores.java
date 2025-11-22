import java.io.*;
import java.util.Scanner;

public class gerenciadorArvores {
    
    private Funcionario[] funcionarios = new Funcionario[1000];
    

    private ArvoreAvl AxionAvl = new ArvoreAvl();
    private ArvoreAvl TitaniumAvl = new ArvoreAvl();
    private ArvoreAvl QueenBeeAvl = new ArvoreAvl();
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        gerenciadorArvores gerenciador = new gerenciadorArvores();
        int opcao = 0;
        int nomesLidosAxion = 0;
        int nomesLidosTitanium = 0;

        do {
            System.out.println("---------Menu-------");
            System.out.println("1. Inserir dados de funcionários na empresa Axion");
            System.out.println("2. Exibir lista de dados dos funcionários de Axion ordenados pelo Nome Completo");
            System.out.println("3. Inserir dados de funcionários na empresa Titanium");
            System.out.println("4. Exibir lista de dados dos funcionários de Titanium ordenados pelo NomeCompleto ");
            System.out.println("5. Unificar dados de Axion e Titanium na empresa QueenBee");
            System.out.println("6. Inserir dados de novo funcionário em QueenBee");
            System.out.println("7. Exibir lista de dados dos funcionários de QueenBee ordenados pelo Nome Completo ");
            System.out.println("8. Exibir a lista de funcionários de QueenBee cujos nomes completos começam pela letra LETRA, onde LETRA é dada pelo usuário do programa. ");
            System.out.println("9. Buscar os dados de um funcionário a partir de seu Nome Completo");
            System.out.println("10. Exibir a relação dos funcionários com os n salários mais altos em QueenBee em ordem decrescente de salário, com n digitado pelo usuário. ");
            System.out.println("11. Sair ");

            System.out.print("\nEscolha a opcão que deseja: ");
            if (sc.hasNextInt()) {
                opcao = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                sc.next();
                continue;
            }

            switch (opcao) {
                case 1:
                    nomesLidosAxion = gerenciador.processarArquivo("AxionAvl.txt");
                    gerenciador.inserirNaArvore(nomesLidosAxion, gerenciador.AxionAvl);
                    System.out.println(nomesLidosAxion);
                    break;
                case 2:
                    gerenciador.AxionAvl.imprimirEmOrdem();
                    break;
                case 3:
                    nomesLidosTitanium = gerenciador.processarArquivo("TitaniumAvl.txt");
                    gerenciador.inserirNaArvore(nomesLidosTitanium, gerenciador.TitaniumAvl);
                    System.out.println(nomesLidosTitanium);
                    break;
                case 4:
                    gerenciador.TitaniumAvl.imprimirEmOrdem();
                    break;
                case 5:
                    gerenciador.unirDuasArvores(gerenciador.AxionAvl, gerenciador.TitaniumAvl, nomesLidosAxion, nomesLidosTitanium);
                    break;
                case 6:
                    System.out.print("Informe o nome do funcionario: ");
                    String nome = sc.nextLine();
                    System.out.print("Informe a data de nascimento do funcionario: ");
                    String dataNascimento = sc.nextLine();
                    System.out.print("Informe a data de ontratacao do funcionario: ");
                    String dataContratacao = sc.nextLine();
                    System.out.print("Informe o setor do funcionario: ");
                    String setor = sc.nextLine();
                    System.out.print("Informe a funcao do funcionario: ");
                    String funcao = sc.nextLine();
                    System.out.print("Informe o salario do funcionario: ");
                    Double salario = Double.parseDouble(sc.nextLine().trim());
                    Funcionario novoFuncionario = new Funcionario (nome, dataNascimento, dataContratacao, setor, funcao, salario);
                    gerenciador.QueenBeeAvl.inserir(novoFuncionario);
                    System.out.println(novoFuncionario);
                    break;
                case 7:
                    gerenciador.QueenBeeAvl.imprimirEmOrdem();
                    break;
                case 8:
                    //exibir os usuários com uma determinada letra
                    break;
                case 9:
                    //buscar usuario pelo nome Completo
                    break;
                case 10:
                    //exibir n funcionarios com maiores salarios
                    break;
                case 11:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 11);
        
        sc.close();
    }

    private int processarArquivo(String nomeArquivo) {
        try (BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            int contador = 0;
            while ((linha = leitor.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;
                try {
                    int inicio = linha.indexOf('(');
                    int fim = linha.lastIndexOf(')');
                    
                    String[] tokens = new String[0];

                    if (inicio != -1 && fim != -1 && fim > inicio) {

                        String contentInsideParenthesis = linha.substring(inicio + 1, fim); 
                        

                        tokens = contentInsideParenthesis.split(";");
                        
                        for (int i = 0; i < tokens.length; i++) {
                            tokens[i] = tokens[i].trim();
                        }
                    } else {
                        System.err.println("Aviso: Linha com formato inválido (sem parênteses) e será ignorada: " + linha);
                        continue;
                    }
                    if (tokens.length == 0 || tokens[0] == null || tokens[0].isEmpty()) {
                        System.err.println("Aviso: Dados de funcionário incompletos (Nome ausente ou vazio) e serão ignorados: " + linha);
                        continue;
                    }
                    this.funcionarios[contador] = new Funcionario(tokens.length, tokens);
                    contador++;
                } catch (RuntimeException e) {
                    System.out.println("Erro ao processar linha: " + e.getMessage());
                }
            }
            return contador;
        } catch (IOException e) {
            System.err.println("Erro ao abrir arquivo " + nomeArquivo + ": " + e.getMessage());
            return 0;
        }
    }
     
    private void unirDuasArvores(ArvoreAvl arvore1, ArvoreAvl arvore2, int contadorAxion, int contadorTitaniun){
        int total = QueenBeeAvl.juntarArvores(arvore1, arvore2);
        System.out.println("Total de inserção: " + total);
        if(total != contadorTitaniun + contadorAxion){
            throw new IllegalArgumentException("Total de inserções errado, diferente do total de funcionarios a serem inseridos.");
        }
        
    }   
    private void inserirNaArvore(int tamanho, ArvoreAvl arvore) {
        for (int i = 0; i < tamanho; i++) {
            arvore.inserir(this.funcionarios[i]);
        }
        this.funcionarios = new Funcionario[1000];
    }
}

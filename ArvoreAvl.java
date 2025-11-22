public class ArvoreAvl {
    private Funcionario raiz;
    int tamanho;
    
    int obterAltura(Funcionario no) {
        if (no == null)
            return 0;
        return no.altura;
    }

    int maximo(int a, int b) {
        return (a > b) ? a : b;
    }

    public Funcionario rotacaoDireita(Funcionario novo) {
        Funcionario x = novo.esquerda;
        
        if (x == null) {
            return novo;
        }
        
        Funcionario y = x.direita;

        x.direita = novo;
        novo.esquerda = y;

        novo.altura = maximo(obterAltura(novo.esquerda), obterAltura(novo.direita)) + 1;
        x.altura = maximo(obterAltura(x.esquerda), obterAltura(x.direita)) + 1;

        return x;
    }

    public Funcionario rotacaoEsquerda(Funcionario novo) {
        Funcionario y = novo.direita;
        Funcionario x = y.esquerda;

        y.esquerda = novo;
        novo.direita = x;

        novo.altura = maximo(obterAltura(novo.esquerda), obterAltura(novo.direita)) + 1;
        y.altura = maximo(obterAltura(y.esquerda), obterAltura(y.direita)) + 1;

        return y;
    }

    public int obterFatorBalanceamento(Funcionario no) {
        if (no == null)
            return 0;
        return obterAltura(no.esquerda) - obterAltura(no.direita);
    }
    
    private Funcionario inserir(Funcionario no, Funcionario novoFuncionario) {
        if (no == null) {
            novoFuncionario.id = this.tamanho + 1;
            this.tamanho++;
            return novoFuncionario;
        }

        String nomeNovo = novoFuncionario.nome;
        int comparacao = nomeNovo.compareTo(no.nome);

        if (comparacao < 0) {
            no.esquerda = inserir(no.esquerda, novoFuncionario);
        }
        else { 
            no.direita = inserir(no.direita, novoFuncionario);
        }
        
        no.altura = 1 + maximo(obterAltura(no.esquerda), obterAltura(no.direita));
        int balanceamento = obterFatorBalanceamento(no);
        
        if (balanceamento > 1) {
            if (obterFatorBalanceamento(no.esquerda) < 0) {
                no.esquerda = rotacaoEsquerda(no.esquerda);
            }
            return rotacaoDireita(no);
        }

        if (balanceamento < -1) {
            if (obterFatorBalanceamento(no.direita) > 0) {
                no.direita = rotacaoDireita(no.direita);
            }
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public void inserir(Funcionario novoFuncionario) {
        raiz = inserir(raiz, novoFuncionario);
    }

    private void imprimirEmOrdem(Funcionario no) {
        if (no != null) {
            imprimirEmOrdem(no.esquerda);
            System.out.println(no.nome); 
            imprimirEmOrdem(no.direita);
        }
    }
    
    public void imprimirEmOrdem() {
        imprimirEmOrdem(raiz);
        System.out.println();
    }
    
    public Funcionario getRaiz() {
        return raiz;
    }

    private void juntar(Funcionario atual){
        if( atual != null){
            Funcionario copia = new Funcionario(
                atual.nome,
                atual.dataNascimento,
                atual.dataContratacao,
                atual.departamento,
                atual.cargo,
                atual.salario
            );
            
            inserir(copia); 
            
            juntar(atual.esquerda);
            juntar(atual.direita);
        }
    }

    public int juntarArvores (ArvoreAvl AxionAvl, ArvoreAvl TitaniumAvl){
        Funcionario raizAxion = AxionAvl.getRaiz();
        Funcionario raizTitanium = TitaniumAvl.getRaiz();

        if(raizTitanium == null && raizAxion == null){
            throw new IllegalArgumentException("Arvores vazias.");
        }
        if(raizAxion == null){
            throw new IllegalArgumentException("Arvore Axion vazia.");
        }
        if(raizTitanium == null){
            throw new IllegalArgumentException("Arvore Titanium vazia.");
        }

        juntar(raizAxion);
        juntar(raizTitanium);
        
        return this.tamanho;
    }
    
    public Funcionario buscar(String elemento) {
 
        Funcionario atual = this.raiz;
       
        while(atual != null) {
            
            int comparacao = elemento.compareTo(atual.nome);
            

            if (comparacao < 0) {
                atual = atual.esquerda;
            }
            else if(comparacao > 0){
                atual = atual.direita;
            }
            else { 
                return atual;
            }
        }
        return null;
    }
    
    public void buscarLetra(char letra){
        if (raiz != null) {
            buscarLetra(letra, raiz);
        }
    }
    
    private void buscarLetra(char letra, Funcionario atual){
        if(atual == null){
            return;
        }
        char primeiraLetra = atual.nome.charAt(0);
        if (primeiraLetra == letra) {
            System.out.println(atual.nome);
        }
        buscarLetra(letra, atual.esquerda);
        buscarLetra(letra, atual.direita);
    }


    private void inserirEOrdenar(Funcionario novo, Funcionario[] maiores, int n) {
        if (n <= 0) return;
        for (int i = 0; i < n; i++) {
            if (maiores[i] == null) {
                maiores[i] = novo;
                
                for (int j = i; j > 0; j--) {
                    if (maiores[j].salario < maiores[j-1].salario) {
                        Funcionario temp = maiores[j];
                        maiores[j] = maiores[j-1];
                        maiores[j-1] = temp;
                    } else {
                        break;
                    }
                }
                return;
            }
        }

        if (novo.salario > maiores[0].salario) {
            maiores[0] = novo; 
            for (int j = 0; j < n - 1; j++) {
                if (maiores[j].salario > maiores[j+1].salario) {

                    Funcionario temp = maiores[j];
                    maiores[j] = maiores[j+1];
                    maiores[j+1] = temp;
                } else {
                    break;
                }
            }
        }
    }

    private void buscarMaioresRecursivo(Funcionario atual, int n, Funcionario[] maiores) {
        if (atual == null) {
            return;
        }
        buscarMaioresRecursivo(atual.esquerda, n, maiores);
                inserirEOrdenar(atual, maiores, n);
        
        buscarMaioresRecursivo(atual.direita, n, maiores);
    }
    
    public void buscarMaiores(int n) {
        if (raiz == null || n <= 0) {
            System.out.println("A árvore está vazia ou o número N é inválido.");
            return;
        }
        
        Funcionario[] maiores = new Funcionario[n];

        buscarMaioresRecursivo(raiz, n, maiores);

        System.out.println("\nOs " + n + " Funcionários com Maiores Salários: ");

        for (int i = n - 1; i >= 0; i--) {
            Funcionario f = maiores[i];
            if (f != null) {
                System.out.println("Salário: R$" + String.format("%.2f", f.salario) + 
                                   ", Nome: " + f.nome + 
                                   " (ID: " + f.id + ")");
            }
        }
    }
}
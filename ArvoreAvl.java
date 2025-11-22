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
            this.tamanho++;
            return novoFuncionario;
        }

        String nomeNovo = novoFuncionario.nome;
        
        int comparacao = nomeNovo.compareTo(no.nome);

        if (comparacao < 0) {
            no.esquerda = inserir(no.esquerda, novoFuncionario);
        }
        
        else if (comparacao > 0) {
            no.direita = inserir(no.direita, novoFuncionario);
        }
        
        else {
            return no; 
        }
        
        no.altura = 1 + maximo(obterAltura(no.esquerda), obterAltura(no.direita));
        int balanceamento = obterFatorBalanceamento(no);
      
        if (balanceamento > 1 && nomeNovo.compareTo(no.esquerda.nome) < 0) {
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && nomeNovo.compareTo(no.direita.nome) > 0) {
            return rotacaoEsquerda(no);
        }

        if (balanceamento > 1 && nomeNovo.compareTo(no.esquerda.nome) > 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && nomeNovo.compareTo(no.direita.nome) < 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public void inserir(Funcionario novoFuncionario) {
        raiz = inserir(raiz, novoFuncionario);
        tamanho++;
    }

    private void imprimirEmOrdem(Funcionario no) {
        if (no != null) {
            imprimirEmOrdem(no.esquerda);
            System.out.print(no.nome + " "); 
            imprimirEmOrdem(no.direita);
        }
    }
    
    public void imprimirEmOrdem() {
        imprimirEmOrdem(raiz);
        System.out.println();
    }
    
    // Adicione getters se necessário para testar a raiz
    public Funcionario getRaiz() {
        return raiz;
    }
}

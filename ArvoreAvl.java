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
        System.out.println(tamanho);
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
}
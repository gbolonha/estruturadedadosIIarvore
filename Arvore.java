public class Arvore {
    private No raiz;

    public void construir(String[] tokens) {
        int[] indice = new int[]{0};
        raiz = construirArvore(tokens, indice);
    }

    private No construirArvore(String[] tokens, int[] indice) {
        if (indice[0] >= tokens.length) return null;

        String t = tokens[indice[0]];

        if (t.equals("*")) {
            indice[0]++;
            return null;
        }

        if (t.equals("(")) {
            indice[0]++;
            if (indice[0] >= tokens.length)
                throw new RuntimeException("Valor esperado após '('");

            String valor = tokens[indice[0]];
            if (valor.equals("(") || valor.equals(")"))
                throw new RuntimeException("Valor inválido após '('");
            indice[0]++;

            No no = new No(valor);
            no.esquerda = construirArvore(tokens, indice);
            no.direita = construirArvore(tokens, indice);

            if (indice[0] >= tokens.length || !tokens[indice[0]].equals(")"))
                throw new RuntimeException("Parêntese de fechamento esperado");

            indice[0]++;
            return no;
        }

        throw new RuntimeException("Elemento inválido: " + t);
    }

    public void exibirPreOrdemrecursivo() {
        exibirPreOrdem(raiz, 0);
    }

    private void exibirPreOrdem(No no, int nivel) {
        if (no == null) return;

        // direita primeiro
        exibirPreOrdem(no.direita, nivel + 1);

        // nó atual
        for (int i = 0; i < nivel; i++) System.out.print("- ");
        System.out.println(no.valor);

        // esquerda depois
        exibirPreOrdem(no.esquerda, nivel + 1);
    }
}

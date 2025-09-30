import java.io.*;
import java.util.Scanner;

public class VerificadorExpressoes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (BufferedReader leitor = new BufferedReader(new FileReader("entrada.txt"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;

                System.out.println("\n=== Processando expressão: " + linha + " ===");
                try {
                    String[] tokens = tokenizar(linha);
                    Arvore arvore = new Arvore();
                    arvore.construir(tokens);

                    arvore.exibirPreOrdemrecursivo();

                } catch (RuntimeException e) {
                    System.out.println("Erro: " + e.getMessage());
                }

                System.out.print("\nDeseja processar a próxima expressão? (S/N): ");
                String resp = sc.nextLine().trim().toUpperCase();
                if (!resp.equals("S")) break;
            }
        } catch (IOException e) {
            System.err.println("Erro ao abrir arquivo entrada.txt: " + e.getMessage());
        }

        sc.close();
    }

    private static String[] tokenizar(String expressao) {
        String[] temp = new String[expressao.length() * 2];
        int count = 0;
        String token = "";

        for (int i = 0; i < expressao.length(); i++) {
            char c = expressao.charAt(i);
            if (c == '(' || c == ')') {
                if (token.length() > 0) {
                    temp[count++] = token;
                    token = "";
                }
                temp[count++] = "" + c;
            } else if (c == ' ') {
                if (token.length() > 0) {
                    temp[count++] = token;
                    token = "";
                }
            } else {
                token += c;
            }
        }

        if (token.length() > 0) {
            temp[count++] = token;
        }

        String[] tokens = new String[count];
        for (int i = 0; i < count; i++) tokens[i] = temp[i];
        return tokens;
    }
}

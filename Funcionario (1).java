public class Funcionario {
    int id;
    String nome;
    String dataNascimento;
    String dataContratacao;
    String departamento;
    String cargo;
    Double salario;
    int altura;
    Funcionario esquerda;
    Funcionario direita;
    
    public Funcionario(String nome, String dataNascimento, String dataContratacao, String departamento, String cargo, Double salario) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.dataContratacao = dataContratacao;
        this.departamento = departamento;
        this.cargo = cargo;
        this.salario = salario;
        this.altura = 1;
        this.esquerda = null;
        this.direita = null;
    }
    
    public Funcionario(int tamanho, String tokens[]) {
        if(tamanho < 6){
            throw new IllegalArgumentException("Dados de funcionário incompletos.");
        }
        if(tamanho == 7){
            this.nome = tokens[0];
            this.dataNascimento = tokens[2];
            this.dataContratacao = tokens[3];
            this.departamento = tokens[4];
            this.cargo = tokens[5];
            
            try {
                this.salario = Double.parseDouble(tokens[6].trim()); 
            } catch (NumberFormatException e) {
                System.err.println("Aviso: Salário inválido para funcionário: " + this.nome);
            }
        }
        else{
            this.nome = tokens[0];
            this.dataNascimento = tokens[1];
            this.dataContratacao = tokens[2];
            this.departamento = tokens[3];
            this.cargo = tokens[4];
            
            try {
                this.salario = Double.parseDouble(tokens[5].trim()); 
            } catch (NumberFormatException e) {
                System.err.println("Aviso: Salário inválido para funcionário: " + this.nome);
            }
        }
        
        this.altura = 1;
        this.esquerda = null;
        this.direita = null;
    }
    
    public Funcionario(){
        this.nome = null;
        this.dataNascimento = null;
        this.dataContratacao = null;
        this.departamento = null;
        this.cargo = null;
        this.salario = 0.0;
        this.altura = 1;
        this.esquerda = null;
        this.direita = null;
    }
}

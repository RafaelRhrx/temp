

public class Account {
    private static int nextAccountNumber = 1;

    public String name;
    public String cpf;
    public String agency;
    public int accountNumber;
    public double balance;

    public Account() {
        this.name = name;
        this.cpf = cpf;
        this.agency = agency;
        this.accountNumber = nextAccountNumber++;
        this.balance = balance;
    }
}

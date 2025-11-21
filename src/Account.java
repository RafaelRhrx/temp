import java.util.ArrayList;

public class Account {

    public String name;
    public String cpf;
    public String agency;
    public String accountNumber;
    public double balance;

    public ArrayList<String> transactions;

    public Account(String name, String cpf, String agency, String accountNumber, double balance) {
        this.name = name;
        this.cpf = cpf;
        this.agency = agency;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = new ArrayList<>();

        transactions.add("Conta criada com saldo inicial de R$ " + balance);
    }

}

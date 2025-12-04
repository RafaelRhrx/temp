import java.time.ZoneId;
import java.util.ArrayList;

public class Account {

    public String name;
    public String cpf;
    public String agency;
    public String accountNumber;
    public double balance;
    public String state;

    public ArrayList<Transaction> transactions;

    public Account(String name, String cpf, String agency, String accountNumber, double balance, String state) {
        this.name = name;
        this.cpf = cpf;
        this.agency = agency;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.state = state != null ? state.toUpperCase() : null;
        this.transactions = new ArrayList<>();

    }

    public ZoneId getZoneId() {
        return BrazilStateZone.zoneForUf(this.state);
    }

    public void addTransaction(Transaction t) {
        if (t != null) {
            this.transactions.add(t);
        }
    }

    @Override
    public String toString() {
        return String.format("Nome: %s | CPF: %s | Agência: %s | Conta: %s | Estado: %s | Saldo: R$ %.2f",
                name, cpf, agency, accountNumber, state, balance);
    }

}

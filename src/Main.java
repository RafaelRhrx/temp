import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Account findAccount(ArrayList<Account> accountsList, String agency, String accountNumber) {
        for (Account c : accountsList) {
            if (c.agency.equals(agency) && c.accountNumber.equals(accountNumber)) {
                return c;
            }
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println("Aplicação Juniores iniciada com sucesso\n");

        ArrayList<Account> accountsList = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        String menu = """
                \nBem vindo, escolha uma das opções:
                
                1. Criar conta
                2. Visualizar todas as contas
                3. Excluir uma conta
                4. Fazer uma transferência
                5. Mostrar extrato
                0. Sair.
                """;


        int opcao = 1;

        while (opcao != 0) {
            System.out.println(menu);
            System.out.println("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> {

                    System.out.println("Digite seu nome: ");
                    String name = scanner.nextLine();

                    System.out.println("Digite seu cpf (somente números): ");
                    String cpfTyped = scanner.nextLine();

                    while (!cpfTyped.matches("^\\d{11}$")) {
                        System.out.println("CPF inválido!  Digite exatamente 11 números (sem pontos ou traços).");
                        System.out.println("Digite novamente: ");
                        cpfTyped = scanner.nextLine();
                    }

                    System.out.println("Digite a agencia: ");
                    String agencyTyped = scanner.nextLine();

                    while (!agencyTyped.matches("^\\d{3}$")) {
                        System.out.println("Agência inválida! O número da agencia deve conter 3 números");
                        System.out.println("Digite novamente: ");
                        agencyTyped = scanner.nextLine();
                    }

                    System.out.println("Digite o número da conta");
                    String accountNumber = scanner.nextLine();

                    while (!accountNumber.matches("^\\d{4}$")) {
                        System.out.println("Número da conta inválido! O número da conta deve conter 4 números (sem pontos ou traços).");
                        System.out.println("Digite novamente: ");
                        accountNumber = scanner.nextLine();
                    }


                    System.out.println("Digite o saldo da conta");
                    double initialBalance = scanner.nextDouble();

                    while (initialBalance <= 0) {
                        System.out.println("O saldo inicial deve ser maior que 0");
                        initialBalance = scanner.nextDouble();
                        scanner.nextLine();
                    }

                    Account account = new Account(name, cpfTyped, agencyTyped, accountNumber, initialBalance);

                    accountsList.add(account);

                    System.out.println("Conta criada com sucesso!\n");
                }


                case 2 -> {

                    if (accountsList.isEmpty()) {
                        System.out.println("Nenhuma conta cadastrada.\n");
                    } else {
                        System.out.println("\n---Contas Cadastradas---");
                        for (int i = 0; i < accountsList.size(); i++) {
                            Account c = accountsList.get(i);
                            System.out.printf(
                                    "%d. Nome: %s | CPF: %s | Agência: %s | Número da conta: %s | Saldo: R$ %.2f%n", i + 1, c.name, c.cpf, c.agency, c.accountNumber, c.balance);

                        }
                        System.out.println("---------------\n");
                    }


                }

                case 3 -> {

                    if (accountsList.isEmpty()) {
                        System.out.println("Erro! Nenhuma conta para exlcuir\n");
                        break;
                    }

                    System.out.println("Digite o CPF da conta que deseja excluir");
                    String cpfTyped = scanner.nextLine();

                    Account accountToRemove = null;

                    for (Account account : accountsList) {
                        if (account.cpf.equals(cpfTyped)) {
                            accountToRemove = account;
                            break;
                        }
                    }

                    if (accountToRemove != null) {
                        accountsList.remove(accountToRemove);
                        System.out.println("Conta removida com sucesso!\n");
                    } else {
                        System.out.println("Nenhuma conta encontrada com o CPF informado.\n");
                    }
                }

                case 4 -> {
                    //Transferencia

                    if (accountsList.isEmpty()) {
                        System.out.println("Erro! Ainda não possui nenhuma conta criada\n");
                        break;
                    }


                    System.out.println("Digite a agencia e número da conta de débito (ex: 123 4567):");
                    String inputDebit = scanner.nextLine();
                    String[] debitParts = inputDebit.split(" ");
                    String debitAgency = debitParts[0];
                    String debitAccount = debitParts[1];

                    Account accountToDebit = findAccount(accountsList, debitAgency, debitAccount);

                    if (accountToDebit == null) {
                        System.out.println("Conta de débito não encontrada");
                        break;
                    }

                    System.out.println("Digite a agencia e número da conta de crédito (ex: 123 4567):");
                    String inputCredit = scanner.nextLine();
                    String[] creditParts = inputCredit.split(" ");
                    String creditAgency = creditParts[0];
                    String creditAccount = creditParts[1];

                    Account accountToCredit = findAccount(accountsList, creditAgency, creditAccount);

                    if (accountToCredit == null) {
                        System.out.println("Conta de crédito não encontrada");
                        break;
                    }

                    System.out.println("Digite o valor da transferência: ");
                    double transferValue = scanner.nextDouble();
                    scanner.nextLine();

                    String valueFormat = String.format("%.2f", transferValue);


                    if (transferValue <= 0) {
                        System.out.println("Saldo insuficiente\n");
                    } else if (transferValue > accountToDebit.balance) {
                        System.out.println("O valor digitado é maior que o saldo da conta\n");
                    } else {
                        accountToDebit.balance = accountToDebit.balance - transferValue;
                        accountToDebit.transactions.add(
                                "Transferência enviada de R$ " + valueFormat + " para Agência " + creditAgency + " Conta " +creditAccount);
                        accountToCredit.balance = accountToCredit.balance + transferValue;
                        accountToCredit.transactions.add(
                                "Transferência recebida: R$ " + valueFormat + " de Agência " + debitAgency + " Conta " +debitAccount);
                        System.out.println("Transferencia realizada com sucesso!");
                    }

                }

                case 5 -> {
                    //Extrato
                    System.out.println("Digite o número de Agência e conta que deseja visualizar o Extrato ex: (123 1234):");
                    String bankStatement = scanner.nextLine();
                    String[] bankStatementParts = bankStatement.split(" ");
                    String agency = bankStatementParts[0];
                    String accountN = bankStatementParts[1];

                    Account account = findAccount(accountsList, agency, accountN);

                    if (account == null) {
                        System.out.println("Conta não encontrada");
                    } else {
                        System.out.printf(
                                "Nome: %s | CPF: %s | Agência: %s | Número da conta: %s | Saldo: R$ %.2f%n\n", account.name, account.cpf, account.agency, account.accountNumber, account.balance);
                        for (String t : account.transactions) {
                            System.out.println(t);
                        }
                    }

                }

                case 0 -> {
                    System.out.println("Encerrando aplicação");
                }

                default -> {
                    System.out.println("Opção inválida! Tente novamente.\n");
                }

            }

        }
        scanner.close();
    }
}
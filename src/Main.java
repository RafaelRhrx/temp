import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static Account findAccount(ArrayList<Account> accountsList, String agency, String accountNumber) {
        for (Account c : accountsList) {
            if (c.agency.equals(agency) && c.accountNumber.equals(accountNumber)) {
                return c;
            }
        }
        return null;
    }

    private static boolean askRetryOrMenu() {
        System.out.println("\nDigite");
        System.out.println("1. Tentar novamente");
        System.out.println("0. Voltar ao menu principal");
        System.out.println("Opção: ");

        String op = scanner.nextLine();
        return op.equals("1");
    }

    private static String readValidated(String message, String regex, String errorMessage) {
        while (true) {
            System.out.println(message);
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("0")) {
                return null;
            }

            if (input.matches(regex)) {
                return input;
            }

            System.out.println(errorMessage);
            if (!askRetryOrMenu()) {
                return null;
            }
        }
    }

    private static double readValidDouble(String message) {
        while (true) {
            try {
                System.out.println(message);
                String input = scanner.nextLine();

                double value;
                try {
                    value = Double.parseDouble(input.replace(",", "."));
                } catch (Exception e) {
                    if (input.equals("0")) {
                        System.out.println("O valor deve ser maior que 0.");
                        if (!askRetryOrMenu()) {
                            return -1;
                        }
                        continue;
                    }
                    System.out.println("Valor inválido!");
                    if (!askRetryOrMenu()) {
                        return -1;
                    }
                    continue;
                }

                if (value > 0) {
                    return value;
                }
                System.out.println("O valor deve ser maior que 0.");
                if (!askRetryOrMenu()) {
                    return -1;
                }
            } catch (Exception e) {
                System.out.println("Valor inválido");
                if (!askRetryOrMenu()) {
                    return -1;
                }
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("Aplicação Juniores iniciada com sucesso\n");

        ArrayList<Account> accountsList = new ArrayList<>();

        int opcao = 1;

        while (opcao != 0) {

            System.out.println("""
                \n====MENU PRINCIPAL====:

                1. Criar conta
                2. Visualizar todas as contas
                3. Excluir uma conta
                4. Fazer uma transferência
                5. Mostrar extrato
                0. Sair.
                """);

            System.out.println("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Opção Inválida!\n");
                continue;
            }

            switch (opcao) {
                case 1 -> {
                    while (true) {

                        String name = readValidated(
                                "Digite seu nome completo (Ex: Rafael Borges) ou 0 para voltar ao menu principal",
                                "^[A-Z][a-z]+ [A-Z][a-z]+$",
                                "Nome deve conter Nome e Sobrenome, iniciando com letras maiúsculas."
                        );
                        if (name == null) break;

                        String cpf = readValidated(
                                "Digite seu CPF (somente números) ou 0 para voltar ao menu principal",
                                "^\\d{11}$",
                                "CPF inválido! Deve conter exatamente 11 dígitos numéricos."
                        );
                        if (cpf == null) break;

                        boolean cpfExists = false;
                        for (Account account : accountsList) {
                            if (account.cpf.equals(cpf)) {
                                cpfExists = true;
                                break;
                            }
                        }
                        if (cpfExists) {
                            if (!askRetryOrMenu()) break;
                            else continue;
                        }

                        String state = readValidated(
                                "Digite a sigla do seu estado (ex: SP) ou 0 para voltar ao menu principal",
                                "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$",
                                "Sigla de estado inválida! Use siglas válidas do Brasil (ex: SP, RJ, MG)."
                        );
                        if (state == null) break;

                        String agency = readValidated(
                                "Digite o número da agência (ex: 123) ou 0 para voltar ao menu principal",
                                "^\\d{3}$",
                                "Número de agência inválido! Deve conter exatamente 3 dígitos numéricos."
                        );
                        if (agency == null) break;

                        String accNumber = readValidated(
                                "Digite o número da conta (ex: 1234) ou 0 para voltar ao menu principal",
                                "^\\d{4}$",
                                "Número de conta inválido! Deve conter exatamente 4 dígitos numéricos."
                        );
                        if (accNumber == null) break;

                        Account existingAccount = findAccount(accountsList, agency, accNumber);
                        if (existingAccount != null) {
                            System.out.println("Erro! Já existe uma conta com essa agência e número de conta.");
                            if (!askRetryOrMenu()) break;
                            else continue;
                        }

                        double balance = readValidDouble(
                                "Digite o saldo inicial (maior que 0,00) ou 0 para voltar ao menu principal"
                        );
                        if (balance <= 0) continue;

                        Account account = new Account(name, cpf, agency, accNumber, balance, state);

                        accountsList.add(account);

                        System.out.println("Conta criada com sucesso!\n");
                        break;
                    }
                }

                case 2 -> {
                    if (accountsList.isEmpty()) {
                        System.out.println("Nenhuma conta cadastrada.\n");
                    } else {
                        System.out.println("\n---Contas Cadastradas---");
                        for (Account c : accountsList) {
                            System.out.printf(
                                    " Nome: %s | CPF: %s | Agência: %s | Número da conta: %s | Saldo: R$ %.2f%n",
                                    c.name, c.cpf, c.agency, c.accountNumber, c.balance);
                        }
                        System.out.println("---------------\n");
                    }
                }

                case 3 -> {
                    if (accountsList.isEmpty()) {
                        System.out.println("Erro! Nenhuma conta para excluir\n");
                        break;
                    }

                    String inputRemove = readValidated(
                            "Digite o número da agência e conta que deseja remover (somente números) ou 0 para voltar ao menu principal",
                            "^\\d{3} \\d{4}$",
                            "Formato inválido! Deve ser no formato: 123 4567"
                    );
                    if (inputRemove == null) continue;

                    String[] removeParts = inputRemove.split(" ");
                    Account accRemove = findAccount(accountsList, removeParts[0], removeParts[1]);

                    if (accRemove == null) {
                        System.out.println("Conta não encontrada.\n");
                    } else {
                        accountsList.remove(accRemove);
                        System.out.println("Conta removida com sucesso!\n");
                    }
                }

                case 4 -> {
                    if (accountsList.isEmpty()) {
                        System.out.println("Erro! Ainda não possui nenhuma conta criada\n");
                        break;
                    }

                    String inputDebit = readValidated(
                            "Digite o número da agência e conta de débito (somente números) ou 0 para voltar ao menu principal",
                            "^\\d{3} \\d{4}$",
                            "Formato inválido! Deve ser no formato: 123 4567"
                    );
                    if (inputDebit == null) continue;

                    String[] debitParts = inputDebit.split(" ");
                    Account accountToDebit = findAccount(accountsList, debitParts[0], debitParts[1]);

                    if (accountToDebit == null) {
                        System.out.println("Conta de débito não encontrada");
                        continue;
                    }

                    String inputCredit = readValidated(
                            "Digite o número da agência e conta de crédito (somente números) ou 0 para voltar ao menu principal",
                            "^\\d{3} \\d{4}$",
                            "Formato inválido! Deve ser no formato: 123 4567"
                    );
                    if (inputCredit == null) continue;

                    String[] creditParts = inputCredit.split(" ");
                    Account accountToCredit = findAccount(accountsList, creditParts[0], creditParts[1]);

                    if (accountToCredit == null) {
                        System.out.println("Conta de crédito não encontrada");
                        break;
                    }

                    if (accountToDebit.agency.equals(accountToCredit.agency) &&
                            accountToDebit.accountNumber.equals(accountToCredit.accountNumber)) {
                        System.out.println("Erro! Não é possível transferir para a mesma conta.\n");
                        continue;
                    }

                    double transferValue = readValidDouble(
                            "Digite o valor da transferência (maior que 0,00)"
                    );
                    if (transferValue <= 0) continue;

                    if (transferValue > accountToDebit.balance) {
                        System.out.println("Saldo insuficiente para realizar a transferência.\n");
                        continue;
                    }

                    accountToDebit.balance -= transferValue;
                    accountToCredit.balance += transferValue;

                    String descOut = "Transferência enviada de R$ " + String.format("%.2f", transferValue) +
                            " para Agência " + accountToCredit.agency + " Conta " + accountToCredit.accountNumber;
                    String descIn = "Transferência recebida: R$ " + String.format("%.2f", transferValue) +
                            " de Agência " + accountToDebit.agency + " Conta " + accountToDebit.accountNumber;

                    Transaction out = new Transaction(
                            "TRANSFER_SENT",
                            transferValue,
                            null,
                            descOut,
                            accountToCredit.agency,
                            accountToCredit.accountNumber,
                            accountToDebit.getZoneId()
                    );
                    Transaction in = new Transaction(
                            "TRANSFER_RECEIVED",
                            transferValue,
                            null,
                            descIn,
                            accountToDebit.agency,
                            accountToDebit.accountNumber,
                            accountToCredit.getZoneId()
                    );

                    accountToDebit.addTransaction(out);
                    accountToCredit.addTransaction(in);

                    System.out.println("Transferencia realizada com sucesso!");
                }

                case 5 -> {
                    if (accountsList.isEmpty()) {
                        System.out.println("Erro! Ainda não possui nenhuma conta criada\n");
                        break;
                    }

                    String bankStatementInput = readValidated(
                            "Digite o número da agência e conta para ver o extrato (somente números) ou 0 para voltar ao menu principal",
                            "^\\d{3} \\d{4}$",
                            "Formato inválido! Deve ser no formato: 123 4567"
                    );
                    if (bankStatementInput == null) continue;

                    String [] bankstatementParts = bankStatementInput.split(" ");
                    Account account = findAccount(accountsList, bankstatementParts[0], bankstatementParts[1]);

                    if (account == null) {
                        System.out.println("Conta não encontrada");
                    } else {
                        System.out.printf(
                                "Nome: %s | CPF: %s | Agência: %s | Número da conta: %s | Saldo: R$ %.2f%n\n",
                                account.name, account.cpf, account.agency, account.accountNumber, account.balance);
                        for (Transaction t : account.transactions) {
                            System.out.println(t);
                        }
                    }
                }

                case 0 -> System.out.println("Encerrando aplicação");

                default -> System.out.println("Opção inválida! Tente novamente.\n");
            }
        }

        scanner.close();
    }
}

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Aplicação Juniores iniciada com sucesso");

        ArrayList<Account> accountsList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        String menu = """
                1. Criar Conta
                2. Visualizar Contas
                3. Excluir uma conta
                4. Fechar Menu
                """;


        int opcao = 0;

        while (opcao != 4) {
            System.out.println(menu);
            System.out.println("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> {
                    Account account = new Account();

                    System.out.println("Digite seu nome: ");
                    account.name = scanner.nextLine();

                    System.out.println("Digite seu cpf (somente números): ");
                    String cpf = scanner.nextLine();

                    while (!cpf.matches("^\\d{11}$")) {
                        System.out.println("CPF inválido!  Digite exatamente 11 números (sem pontos ou traços).");
                        System.out.println("Digite novamente: ");
                        cpf = scanner.nextLine();
                    }
                    account.cpf = cpf;

                    System.out.println("Digite a agencia: ");
                    account.agency = scanner.nextLine();


                    System.out.println("Digite o saldo da conta");
                    account.balance = scanner.nextDouble();

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
                    System.out.println("Digite o número da conta que deseja excluir");
                    int i = scanner.nextInt();
                    accountsList.remove(i - 1);
                }

                case 4 -> {
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
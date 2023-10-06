package BANCOOO.BANCO.Service;

public class CpfValidator {

    public static boolean validateCPF(String CPF) {
        // Remove caracteres especiais do CPF (apenas os dígitos serão considerados)
        CPF = NumberCleaner.cleanNumber(CPF);

        // Verifica se o CPF possui 11 dígitos
        if (CPF.length() != 11) {
            return false;
        }

        // Verifica se o CPF não possui todos os dígitos iguais
        if (CPF.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (CPF.charAt(i) - '0') * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) {
            firstDigit = 0;
        }

        // Verifica o primeiro dígito verificador
        if (CPF.charAt(9) - '0' != firstDigit) {
            return false;
        }

        // Calcula o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (CPF.charAt(i) - '0') * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) {
            secondDigit = 0;
        }

        // Verifica o segundo dígito verificador
        return CPF.charAt(10) - '0' == secondDigit;
    }
}

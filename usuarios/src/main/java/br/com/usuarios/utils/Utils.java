package br.com.usuarios.utils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    Utils(){}


    public static void logJsonEntradaSaida(Object object, String requestType, String className, String endpoint, String entradaSaida) {
        Logger LOGGER = LoggerFactory.getLogger(className);
        Gson gson = new Gson();

        String jsonString = gson.toJson(object);

        if(entradaSaida.equalsIgnoreCase("entrada")) {
            LOGGER.info("Executando metodo: {} | Endpoint: \"{}\"", requestType, endpoint);
            LOGGER.info("Body input  " + endpoint + ": " + jsonString);
        }else{
            LOGGER.info("Body output " + endpoint + ": " + jsonString);
        }
    }

    public static boolean isValidCPF(String cpf) {
        if (cpf.length() != 11) return false;

        // Calcula os primeiros 9 dígitos do CPF
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }

        int checkDigit1 = 11 - (sum % 11);
        if (checkDigit1 == 10 || checkDigit1 == 11) checkDigit1 = 0;

        if (checkDigit1 != Character.getNumericValue(cpf.charAt(9))) return false;

        // Calcula os primeiros 10 dígitos do CPF
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }

        int checkDigit2 = 11 - (sum % 11);
        if (checkDigit2 == 10 || checkDigit2 == 11) checkDigit2 = 0;

        return checkDigit2 == Character.getNumericValue(cpf.charAt(10));
    }

    public static boolean isValidCNPJ(String cnpj) {
        if (cnpj.length() != 14) return false;

        // Calcula os primeiros 12 dígitos do CNPJ
        int sum = 0;
        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights1[i];
        }

        int checkDigit1 = 11 - (sum % 11);
        if (checkDigit1 == 10 || checkDigit1 == 11) checkDigit1 = 0;

        if (checkDigit1 != Character.getNumericValue(cnpj.charAt(12))) return false;

        // Calcula os primeiros 13 dígitos do CNPJ
        sum = 0;
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 13; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights2[i];
        }

        int checkDigit2 = 11 - (sum % 11);
        if (checkDigit2 == 10 || checkDigit2 == 11) checkDigit2 = 0;

        return checkDigit2 == Character.getNumericValue(cnpj.charAt(13));
    }

}

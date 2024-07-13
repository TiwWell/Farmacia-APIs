package br.com.farmacia.farmacia.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

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

}

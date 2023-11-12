package ar.edu.unlu.teocomp1.grupo3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConsolaMain {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = null;
        inputStream = new FileInputStream(new File("original_prueba.txt"));
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        Lexico lexico = new Lexico(inputReader);
        /*
         * lexico.next_token();
         * for (Resultado resultado : lexico.getResultados()) {
         *     System.out.println(resultado.getLexema());
         * }
         */
        Sintactico parser = new Sintactico(lexico);
        try {
            parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Todo OK");
    }
}

package ar.edu.unlu.teocomp1.grupo3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		InputStream inputStream = null;
		inputStream = new FileInputStream(new File("input.txt"));
		InputStreamReader inputReader = new InputStreamReader(inputStream);
		new Lexico(inputReader).next_token();
		System.out.println("Todo OK");
	}
}

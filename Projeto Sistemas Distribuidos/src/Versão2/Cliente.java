package Versão2;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Cliente {

	public static void main(String[] arg) {

		Socket s = null;
		try {
			System.out.println("Conectando...");
			s = new Socket("25.2.118.222", 9000);
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			System.out.println("Conectado");

			Scanner input = new Scanner(System.in);
			out.writeInt(1);

			System.out.println("\n\n-------Plataforma para calculo de juros compostos-------\n\n");

			int continuar = 1;

			while (continuar == 1) {

				System.out.print("\n\n\nInforme o valor da primeira parcela: ");
				double valor = input.nextDouble();
				out.writeDouble(valor);

				System.out.print("\nInforme a quantidade de parcelas: ");
				int quantidade = input.nextInt();
				out.writeInt(quantidade);

				System.out.print("\nInforme a porcentagem dos juros: ");
				double porcentagem = input.nextDouble();
				out.writeDouble(porcentagem);

				for (int x = 1; x <= quantidade; x++) {

					DecimalFormat formatter = new DecimalFormat("#000.00");

					System.out.println("Parcela numero " + x + " = " + formatter.format(in.readDouble()));
				}

				System.out.println("Deseja continuar? 1-sim / 0-nÃ£o");
				continuar = input.nextInt();
				out.writeInt(continuar);
			}
			out.flush();
			out.writeInt(0);
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		} finally {
			try {
				if (s != null)
					s.close();
			} catch (Exception e2) {
			}
		}
		System.out.println("Conexao encerrada");
		try {
			System.in.read();
		} catch (Exception e3) {
		}
		System.out.println("teste");
	}
}

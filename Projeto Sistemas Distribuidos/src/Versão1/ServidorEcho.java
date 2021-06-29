package Versão1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ServidorEcho {

	public static void main(String[] args) {

		/* Limita a quantidade de comandos e passa como paramentro para um vetor de string */
		int ncomandos = 4;
		String comandos[] = new String[ncomandos];
		comandos[0] = "date";
		comandos[1] = "so";
		comandos[2] = "process";
		comandos[3] = "comandos";

		/* ESTABELECENDO CONEXÃO */
		try {
			ServerSocket s = new ServerSocket(9000);
			Socket incoming = s.accept();

			try {
				InputStream inStream = incoming.getInputStream();
				OutputStream outStream = incoming.getOutputStream();

				/*"in" é a variavel que recebe dados do cliente */
				Scanner in = new Scanner(inStream);
				
				/*"out" é a variavel que passa dados para o cliente */
				PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);
				
				
				out.println("Ola! Digite BYE para sair.\n");
				out.println("Lista de comandos disponiveis:\n");

				
				/* Escreve os comandos na tela */
				for (int x = 0; x < comandos.length; x++) {
					out.println("* - " + comandos[x]);
				}
				out.println("------------------------------\n");

				
// CONECTADO COM O CLIENTE
				boolean done = false;
				while (!done && in.hasNextLine()) {

					/* "Line" é a linha digitada pelo cliente */
					String line = in.nextLine();

					/* "line.contains" verifica se tem o paramentro dentro da string*/
					if (line.contains("date")) {

						/* Passa a formatação que será aplicada */
						DateFormat formatar = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						
						/* Cria objeto date */
						Date data = new Date();

						out.print("Exibindo data e hora do servidor: ");
						
						/*Escreve no cliente com a formatação */
						out.println(formatar.format(data));

					} else if (line.contains("so")) {

						// Variável estática que irá conter o nome do SO em minúsculo
						String so = System.getProperty("os.name").toLowerCase();

						// Variável estática que irá conter a versão do SO em minúsculo
						String vr = System.getProperty("os.version").toLowerCase();

						// Variável estática que irá conter a arquitetura do SO em minúsculo
						String ar = System.getProperty("os.arch").toLowerCase();

						out.println("\nSistema: " + so);
						out.println("Versao: " + vr);
						out.println("Arquitetura: " + ar + "\n\n");

					}else if (line.contains("process")) {

						Runtime runtime = Runtime.getRuntime();

						String cmds[] = { "cmd", "/c", "tasklist" };

						Process proc = runtime.exec(cmds);

						InputStream inputstream = proc.getInputStream();

						InputStreamReader inputstreamreader = new InputStreamReader(inputstream);

						BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

						String linha;

						while ((linha = bufferedreader.readLine()) != null) {

							out.println(linha);

						}

						out.println("\n-------------\n");

					}
					if (line.contains("comandos")) {
						out.println("Lista de comandos disponiveis:\n");

						for (int x = 0; x < comandos.length; x++) {
							out.println("* - " + comandos[x]);
						}
						out.println("------------------------------\n");
					}

					if (line.contains("BYE"))
						done = true;
				}
			} finally {
				incoming.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package Versão3;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ThreadedEchoServer {

	public static List<Socket> clientes = new ArrayList<Socket>();

	public static void main(String[] args) {
		int i = 1;

		try {
			ServerSocket s = new ServerSocket(8196);

			for (;;) {
				Socket incoming = s.accept();
				System.out.println("Spawning " + i);
				new ThreadedEchoHandler(incoming, i).start();
				i++;
				setclientes(incoming, i);

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	static public void setclientes(Socket novo, int i) {

		clientes.add(novo);
		Socket p = clientes.get(i - 2);
		System.out.println(p);
	}

}

class ThreadedEchoHandler extends Thread {

	public ThreadedEchoHandler(Socket i, int c) {
		incoming = i;
		counter = c;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
			PrintWriter out = new PrintWriter(incoming.getOutputStream(), true /* autoFlush */);

			out.println("Hello! Enter BYE to exit.");
			out.println("\n");
			out.println("----Bem vindo ao CHAT de sistemas distribuidos----");
			out.println("Sua identificacao: Cliente "+counter+"");
			out.println("----------------------------------------------------");

			boolean done = false;
			while (!done) {
				String str = in.readLine();
				System.out.println(" - " + str);
				if (str == null)
					done = true;
				else {

					Disparador(incoming, str);

					

					if (str.trim().equals("BYE"))
						done = true;
				}
			}
			incoming.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void Disparador(Socket mensageiro, String str) {

		int contador = 0;

		ThreadedEchoServer servidor = new ThreadedEchoServer();
		int tamanho = servidor.clientes.size();
			
		while (contador < tamanho) {

			Socket clienteAtual = servidor.clientes.get(contador);
			System.out.println(clienteAtual);

			try {

				if (clienteAtual != mensageiro) {
					System.out.println("Mensageiro diferente");
					BufferedReader in = new BufferedReader(new InputStreamReader(clienteAtual.getInputStream()));
					PrintWriter out = new PrintWriter(clienteAtual.getOutputStream(), true /* autoFlush */);

					out.println("cliente "+counter+ " = "+str);
					System.out.println("Testando se entra");
				}

				

			} catch (Exception e) {
				System.out.println("deu erro");
			}

			contador++;
		}

	}

	private Socket incoming;
	private int counter;
}

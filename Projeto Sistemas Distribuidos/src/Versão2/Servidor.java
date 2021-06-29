package Versão2;

import java.io.*;
import java.net.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;


public class Servidor {
	public static void main(String[] args) {
		ServerSocket s;
		try {
			s = new ServerSocket(9000);
			System.out.println("Servidor iniciado na porta 9000");
			while (true) {
				Socket cliente = s.accept();
				System.out.println("Conexão estabelecida " + "(" + cliente + ")");
				DataInputStream in = new DataInputStream(cliente.getInputStream());
				DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
			
				int continuar = in.readInt();
				
				while(continuar == 1) {
					
					double valor = in.readDouble();
					System.out.println(valor);
					
					int parcelas = in.readInt();
					System.out.println(parcelas);
					
					double porcentagem = in.readDouble();
					System.out.println(porcentagem);
					
					for(int i= 0; i < parcelas; i++){
						double valorjuros = (valor * porcentagem) / 100;
						valor = valor + valorjuros;
						out.writeDouble(valor);
						
					}
					
					NumberFormat formatter = new DecimalFormat("#0,00");
					System.out.println(formatter.format(2.0));
					
					continuar = in.readInt();
				}
				
				
				
				cliente.close();
				System.out.println("Conexão encerrada.");
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}

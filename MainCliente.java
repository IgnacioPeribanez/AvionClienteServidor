package practicaAvionBien;

import java.io.IOException;
import java.util.Scanner;

//Clase principal que hará uso del cliente
public class MainCliente {
	public static void main(String[] args) throws IOException {
		Scanner entrada = new Scanner(System.in);
		Thread clientes[] = new Thread[10];
		while (true) {
			System.out.println("Que accion quieres realizar?");
			System.out.println(
					"------------------------------------------------------------------------------------------------------");
			System.out.println("1.- Lanzar cliente");
			System.out.println("2.- Clientes Automaticos");
			System.out.print("\n¿Que opcion deseas ejecutar?: ");
			int numero = entrada.nextInt();
			System.out.println("");
			if (numero > 2 || numero < 0) { // Mostramos el error en caso de introducir una opcion inexistente
				System.out.println("Error, elija una de las opciones validas");
			} else if (numero == 1) {
				Cliente cli = new Cliente(); // Se crea el cliente
				cli.startClient(); // Se inicia el cliente
				break;
			} else if (numero == 2) {
				// Se crean 10 threads de clientes
				for (int i = 0; i < clientes.length; i++) {
					clientes[i] = new Thread(new Cliente());
				}
				for (int i = 0; i < clientes.length; i++) {
					clientes[i].start();
				}
				for (int i = 0; i < clientes.length; i++) {
					try {
						clientes[i].join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			}
		}	
	}
}
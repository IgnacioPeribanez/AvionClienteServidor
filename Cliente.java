package practicaAvionBien;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

// Clase que actua como el constructor de un cliente y controla sus acciones.
public class Cliente extends Conexion implements Runnable { // Se hereda de conexión para hacer uso de los sockets y demás
	public Cliente() throws IOException {
		super("cliente");
	} 

	/*
	 * Pre: --
	 * Post: Este metodo gestiona las acciones de un cliente
	 */
	public void startClient() {
		Scanner entrada = new Scanner(System.in);
		try {
			// Canal para recibir mensajes (entrada)
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			// Canal para enviar mensajes (salida)
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			// Contador de plazas reservadas con exito
			int plazasReservadas = 0;
			System.out.print("INICIO COMPRA: ");
			String nombre = entrada.nextLine();
			String mensaje = "INICIO COMPRA: " + nombre;
			// Enviamos el mensaje con el nombre del cliente al servidor
			out.writeUTF(mensaje);
			mensaje = in.readUTF();
			System.out.println(mensaje);
			// Bucle que gestiona las reservas del cliente
			while (true) {
				System.out.print("Que asiento desea reservar: ");
				String asiento = entrada.nextLine();
				mensaje = "RESERVAR: " + asiento;
				// Enviamos el mensaje con la peticion de reserva
				out.writeUTF(mensaje);
				// Recibimos la respuesta del servidor, esta respuesta puede ser "RESERVADA",
				// el estado del avion, o que el avión se encuentra completo
				mensaje = in.readUTF();
				System.out.println(mensaje);
				// Condicion (Si se ha reservado, aumenta el contador, si esta el vuelo completo
				// se muestran las plazas y se termina el programa)
				if (mensaje.substring(0, 9).equalsIgnoreCase("RESERVADA")) {
					plazasReservadas++;
				}else if (mensaje.equalsIgnoreCase("VUELO COMPLETO")) {
					System.out.println("Has reservado " + plazasReservadas + " plazas");
					// Fin de la conexión
					clientSocket.close();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Pre: --
	 * Post: Este metodo gestiona las acciones de un cliente solo si se genera como un thread, en este caso se 
	 * 		 ejecuta cuando seleccionemos el modo automatico.
	 */
	@Override
	public void run() {
		try {
			// Canal para recibir mensajes (entrada)
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			// Canal para enviar mensajes (salida)
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			// Contador de plazas reservadas con exito
			int plazasReservadas = 0;
			int id = (int) (Math.floor(Math.random() * 500) + 1);
			System.out.println("INICIO COMPRA: " + id);
			String mensaje = "INICIO COMPRA: " + id;
			// Enviamos el mensaje con el nombre del cliente al servidor
			out.writeUTF(mensaje);
			mensaje = in.readUTF();
			System.out.println(mensaje);
			// Bucle que gestiona las reservas del cliente
			while (true) {
				String[] asientos = {"A", "B", "C", "D"};
				String asiento = asientos[(int) (Math.floor(Math.random() * asientos.length) + 1) - 1];
				int fila = (int) (Math.floor(Math.random() * 4) + 1);
				System.out.println("- RESERVAR: " + fila + "" + asiento + " por el cliente " + id);
				mensaje = "RESERVAR: " + fila + "" + asiento;
				// Enviamos el mensaje con la peticion de reserva
				out.writeUTF(mensaje);
				// Recibimos la respuesta del servidor, esta respuesta puede ser "RESERVADA",
				// el estado del avion, o que el avión se encuentra completo
				mensaje = in.readUTF();
				System.out.println(mensaje);
				// Condicion (Si se ha reservado, aumenta el contador, si esta el vuelo completo
				// se muestran las plazas y se termina el programa)
				if (mensaje.substring(0, 9).equalsIgnoreCase("RESERVADA")) {
					plazasReservadas++;
				} else if (mensaje.equalsIgnoreCase("VUELO COMPLETO")) {
					System.out.println("El cliente " + id + " ha reservado " + plazasReservadas + " plazas");
					// Fin de la conexión
					clientSocket.close();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
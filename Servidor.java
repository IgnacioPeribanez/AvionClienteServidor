package practicaAvionBien;

import java.io.IOException;

// Clase que actua como el constructor de un servidor y controla sus conexiones.
public class Servidor extends Conexion { // Se hereda de conexión para hacer uso de los sockets y demás
	public Servidor() throws IOException {
		super("servidor"); 
	} 

	/*
	 * Pre: --
	 * Post: Este metodo lanza un servidor y gestiona las conexiones recibidas
	 */
	public void startServer() {
		// Se crea un avion, el cual sera gestionado por el servidor
		Avion avion = new Avion();
		avion.rellenar();
		try {
			while (true) {
				System.out.println("Esperando..."); 
				clientSocket = serverSocket.accept();
				System.out.println("Cliente en línea");
				// Se crea un hilo por cada conexion
                ClientHandler clientSock = new ClientHandler(clientSocket, avion);
                new Thread(clientSock).start();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Pre: --
	 * Post: Este metodo gestiona la reserva sincrona de los clientes
	 */
	public synchronized static String reserva(Avion avion, int fila, int asiento, String peticion, String inicio)  {
		// Almacenamos el estado actual del avion
		String[][] asientos = avion.getAsientos();
		String estado = "";
		for (int i = 0; i < asientos.length; i++) {
			for (int j = 0; j < asientos[i].length; j++) {
				estado = estado + "" + asientos[i][j];
			}
			if (i < asientos.length - 1) {
				estado = estado + "-";
			}
		}
		// Comprobamos si el asiento esta libre y lo reservamos
		if (asientos[fila][asiento].equals("L")) {
			asientos[fila][asiento] = "X";
			System.out.println("RESERVADA: " + peticion.substring(10, 12) + " por " + inicio.substring(15, inicio.length()));
			return ("RESERVADA: " + peticion.substring(10, 12) + " por " + inicio.substring(15, inicio.length()));
		} else {
			return ("PLAZA OCUPADA: " + estado);
		}
	}
}
package practicaAvionBien;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

// Clase que actua como controlador de un cliente
public class ClientHandler implements Runnable {
	private Socket clientSocket;
	private Avion avion;

	public ClientHandler(Socket socket, Avion avion) {
		this.clientSocket = socket;
		this.avion = avion;
	}

	@Override
	public void run() {
		try {
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			out.writeUTF("BIENVENIDO AL SERVICIO");
			// Recibimos el mensaje con el nombre del cliente
			String inicio = in.readUTF();
			System.out.println(inicio);
			// Recibimos la peticion de reserva del cliente
			while (true) {
				String peticion = in.readUTF();
				// Boolean que gestiona si el avion esta lleno
				Boolean completo = true;
				// Almacenamos la fila seleccionada
				int fila = Integer.parseInt(peticion.substring(10, 11)) - 1;
				// Condicion (Si la fila y la letra es valida)
				if (fila < 4 && fila >= 0 && peticion.substring(11, 12).equalsIgnoreCase("A")
						|| peticion.substring(11, 12).equalsIgnoreCase("B")
						|| peticion.substring(11, 12).equalsIgnoreCase("C")
						|| peticion.substring(11, 12).equalsIgnoreCase("D")) {
					int asiento = 0;
					System.out.println("Intentando reservar el asiento " + peticion.substring(10, 12) + " por el cliente " + inicio.substring(15, inicio.length()));
					// Almacenamos el asiento traduciendolo a numero
					if (peticion.substring(11, 12).equalsIgnoreCase("A")) {
						asiento = 0;
					} else if (peticion.substring(11, 12).equalsIgnoreCase("B")) {
						asiento = 1;
					} else if (peticion.substring(11, 12).equalsIgnoreCase("C")) {
						asiento = 2;
					} else if (peticion.substring(11, 12).equalsIgnoreCase("D")) {
						asiento = 3;
					}
					
					// Almacenamos el estado actual del avion
					String[][] asientos = avion.getAsientos();
					String estado = "";
					for (int i = 0; i < asientos.length; i++) {
						for (int j = 0; j < asientos[i].length; j++) {
							estado = estado + "" + asientos[i][j];
							if (asientos[i][j].equalsIgnoreCase("L")) {
								completo = false;
							}
						}
						if (i < asientos.length - 1) {
							estado = estado + "-";
						}
					}
					
					// Condicion (Si el vuelo esta completo se lo notifica al cliente y cierra la
					// conexion,si no es asi, intenta reservarle el asiento)
					if (completo == true) {
						out.writeUTF("VUELO COMPLETO");
						System.out.println("VUELO COMPLETO");
						// Se finaliza la conexión con el cliente
						clientSocket.close();
						break;
					} else {
						// Llamamos al metodo de reserva y comprobamos si podemos o no reservar el asiento
						out.writeUTF(Servidor.reserva(avion, fila, asiento, peticion, estado));
					}
				} else {
					out.writeUTF("Porfavor introduzca una asiento valido");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

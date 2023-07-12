package practicaAvionBien;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// Clase que actua como conexion en una relacion cliente-servidor
public class Conexion {
    private final int PUERTO = 1234; //Puerto para la conexión
    private final String HOST = "localhost"; //Host para la conexión
    protected ServerSocket serverSocket; //Socket del servidor
    protected Socket clientSocket; //Socket del cliente
    
    public Conexion(String tipo) throws IOException {//Constructor
        if(tipo.equalsIgnoreCase("servidor")) {
            serverSocket = new ServerSocket(PUERTO);//Se crea el socket para el servidor en puerto 1234
        } else {
            clientSocket = new Socket(HOST, PUERTO); //Socket para el cliente en localhost en puerto 1234
        }
    }
}
package com.cibertec.socket.Cibertec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class ServerSocketEnviaArchivo {


    private String RUTA_ARCHIVO = "C:/Users/DisneyMedina/Documents/Utilidades-DM-GIT/EnviarArchivo";
    private final int PUERTO = 8095;

    public ServerSocketEnviaArchivo() {
        System.out.println("Server: Esperando Cliente");
        System.out.println("_______________________________________");

        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO);
            while (true) {
                Socket socketCliente = serverSocket.accept(); // Espera a que un cliente se conecte
                System.out.println("Cliente conectado." + "IP " + socketCliente.getInetAddress());

                ObjectInputStream nombreArchivoInput = new ObjectInputStream(socketCliente.getInputStream());
                String nombreArchivo = (String) nombreArchivoInput.readObject();

                File archivo = new File(RUTA_ARCHIVO, nombreArchivo);
                long fileSize = archivo.length();

                ObjectOutputStream tamañoArchivoOutput = new ObjectOutputStream(socketCliente.getOutputStream());
                tamañoArchivoOutput.writeLong(fileSize);
                tamañoArchivoOutput.flush();

                FileInputStream fis = new FileInputStream(archivo);
                OutputStream outputStream = socketCliente.getOutputStream();

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                fis.close();
                outputStream.close();
                nombreArchivoInput.close();
                tamañoArchivoOutput.close();
                socketCliente.close();
                System.out.println("Archivo enviado al cliente: " + nombreArchivo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ServerSocketEnviaArchivo();
    }
}

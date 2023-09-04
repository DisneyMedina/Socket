package com.cibertec.socket.Cibertec;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@SpringBootApplication
public class ClientSocketRecibeArchivo {

    private String RUTA_DESTINO = "C:/Users/DisneyMedina/Documents/Utilidades-DM-GIT/ArchivoRecibido";
    private String fileName = "Archivo.pdf";
    private final String SERVIDOR = "192.168.1.13";
    private final int PUERTO = 8095;
    public ClientSocketRecibeArchivo() {


        try {
            Socket socketCliente = new Socket(SERVIDOR, PUERTO);

            ObjectOutputStream nombreArchivoOutput = new ObjectOutputStream(socketCliente.getOutputStream());
            nombreArchivoOutput.writeObject(fileName);

            InputStream inputStream = socketCliente.getInputStream();
            FileOutputStream fos = new FileOutputStream(RUTA_DESTINO + File.separator + fileName);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            fos.close();
            inputStream.close();
            nombreArchivoOutput.close();
            socketCliente.close();
            System.out.println("Archivo recibido del servidor [" + SERVIDOR + "] y guardado en " + RUTA_DESTINO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ClientSocketRecibeArchivo();
    }
}

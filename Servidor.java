import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



public class Servidor {
    private HashMap<HashMap<InetAddress,Integer>,ArrayList<String>> listaUsuarioArchivo;

    public Servidor() {
        listaUsuarioArchivo = new HashMap<>();
    }
    public HashMap<HashMap<InetAddress,Integer>,ArrayList<String>> getListaUsuarioArchivo() {
        return listaUsuarioArchivo;
    }
    public void setListaUsuarioArchivo(HashMap<HashMap<InetAddress,Integer>,ArrayList<String>> listaUsuarioArchivo) {
        this.listaUsuarioArchivo = listaUsuarioArchivo;
    }
    public static void main(String args[]) throws Exception {
        Servidor servidor = new Servidor();
        DatagramSocket sock = new DatagramSocket(8888);
        while (true) {
            byte[] buffer = new byte[1000];
            DatagramPacket paquete = new DatagramPacket(buffer,1000);
            sock.receive(paquete);
            String mensaje = new String(paquete.getData(),StandardCharsets.US_ASCII);
            if (mensaje.charAt(0) == '0') {
                HashMap<InetAddress,Integer> usuario = new HashMap<>();
                usuario.put(paquete.getAddress(), paquete.getPort());
                servidor.getListaUsuarioArchivo().put(usuario, new ArrayList<>());
                mensaje = new String(paquete.getData(),1,mensaje.length(),StandardCharsets.US_ASCII);
                for (String archivo : mensaje.split("\n")) {
                    if (archivo.length() > 0) {
                            servidor.getListaUsuarioArchivo().get(usuario).add(archivo);
                        }
                    }
                }
            else {
                // llamo al hilo que va a enviar el nombre de un archivo
                HiloServidorPeticion hilo2 = new HiloServidorPeticion(sock,paquete,servidor);
                hilo2.start();
            }

            sock.close();
            System.out.println("Socket closed...");
        }
}
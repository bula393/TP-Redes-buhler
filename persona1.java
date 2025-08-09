import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;



public class persona1 {

    public void enviar_lista(String ruta_archivo) throws Exception {
        File carpeta = new File(ruta_archivo);
        String lista_archivos;
        DatagramSocket sock = new DatagramSocket();
        File[] archivos = carpeta.listFiles();
        if (archivos != null) {
            System.out.println("Archivos y directorios en la carpeta:");
            for (File archivo : archivos) {
                lista_archivos += archivo.getName() + '\n';
            }
        }
        byte ip[] = {(byte) 127,0,0, (byte) 1};
        InetAddress server = InetAddress.getByAddress(ip);
        byte[] outBuf = lista_archivos.getBytes("US-ASCII");
        DatagramPacket outPkt = new DatagramPacket(outBuf, outBuf.length,server, 8888);
        sock.send(outPkt);
        sock.close();
    }
}


    public static void main(String args[]) throws Exception {
        enviar_lista();
        hiloCliente hilo = new hiloCliente();
        hilo.start();
        DatagramSocket sock = new DatagramSocket();
        Scanner s1 = new Scanner(System.in);
        String ruta_archivo =  s1.nextLine();
        byte ip[] = {(byte) 127,0,0, (byte) 1};
        InetAddress server = InetAddress.getByAddress(ip);
        byte[] outBuf = ruta_archivo.getBytes("US-ASCII");
        DatagramPacket outPkt = new DatagramPacket(outBuf, outBuf.length,server, 8888);
        sock.send(outPkt);
        byte[] inBuf = new byte[1000];
        DatagramPacket inPkt = new DatagramPacket(inBuf, inBuf.length);
        sock.receive(inPkt);
        String destino = new String(inBuf, 0, 31, "US-ASCII");
        int puerto = new Integer(inBuf, 32, 49);
        InetAddress ipdestino = InetAddress.getByAddress(destino);
        DatagramPacket peticion = new DatagramPacket(outBuf, outBuf.length, ipdestino, puerto );
        hiloCliente hilo = new hiloClienteReceptor();
        hilo.start();
        sock.close();
        System.out.println("Socket closed...");
}

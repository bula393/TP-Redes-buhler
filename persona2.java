import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;



public class persona2 {
    
    public static void enviar_lista(DatagramSocket sock,String ruta_archivo) throws Exception {
        File carpeta = new File(ruta_archivo);
        String lista_archivos = "0";
        File[] archivos = carpeta.listFiles();
        if (archivos != null) {
            System.out.println("Archivos y directorios en la carpeta:");
            for (File archivo : archivos) {
                lista_archivos += archivo.getName() + '\n';
            }
        }
        lista_archivos += sock.getLocalPort();
        byte ip[] = {(byte) 127,0,0, (byte) 1};
        InetAddress server = InetAddress.getByAddress(ip);
        byte[] outBuf = lista_archivos.getBytes("US-ASCII");
        DatagramPacket outPkt = new DatagramPacket(outBuf, outBuf.length,server, 8888);
        sock.send(outPkt);
        sock.close();
    }
}


    public static void main(String args[]) throws Exception {
        DatagramSocket sock = new DatagramSocket();
        try {
            persona2.enviar_lista(sock,"carpetap2");
        } catch (Exception e) {
            e.printStackTrace();
        }        
        // hilo para esperar si le llega un peticion para enviar un archivo :)
        hiloClienteEnvio hilo = new hiloClienteEnvio(sock);
        hilo.start();
        // pedirle un archivo para pedir al server al usuario
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
        // armaar el paquete para pedir al usuario que tenga el archivo que me lo pase
        if (inPkt.getData() != null) {
            String destino = new String(inPkt.getData(), 0, 31, "US-ASCII");
            String puertoString = new String(inPkt.getData(), 32, 49, "US-ASCII");
            int puerto =    new Integer(puertoString);
            InetAddress ipdestino = InetAddress.getByName(destino);
            DatagramPacket peticion = new DatagramPacket(outBuf, outBuf.length, ipdestino, puerto );
            sock.send(peticion);
            hiloClienteReceptor hiloR = new hiloClienteReceptor(sock);
            hiloR.start();
            sock.close();
            }
            else {
                System.out.println("El archivo no esta");
            }
        System.out.println("Socket closed...");
}

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class HiloServidorPeticion extends Thread {
    private DatagramSocket socket;
    private DatagramPacket paquete;
    private Servidor servidor;

    public HiloServidorPeticion(DatagramSocket socket, DatagramPacket paquete,Servidor servidor) {
        this.socket = socket;
        this.paquete = paquete;
        this.servidor = servidor;
    }

    public void run() {
        String mensaje = new String(paquete.getData(),StandardCharsets.US_ASCII);
        DatagramPacket paqueteRespuesta;
        boolean esta = false;
        HashMap<InetAddress,Integer> usuario;
        for (Map.Entry<HashMap<InetAddress,Integer>,ArrayList<String>> listaArchivos : servidor.getListaUsuarioArchivo().entrySet()) {
            for(String archivo : listaArchivos.getValue()) {
                if (archivo.equals(mensaje)) {
                    usuario = listaArchivos.getKey();
                    esta = true;
                }
        }
        for (Map.Entry<InetAddress,Integer> entry : usuario.entrySet()) {
                        String respuesta = entry.getKey().toString() + ":" + entry.getValue();
                        byte[] bufferRespuesta = respuesta.getBytes(StandardCharsets.US_ASCII);
                        paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length, paquete.getAddress(), paquete.getPort());
                        try {
                            socket.send(paqueteRespuesta);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(!esta){
                        byte[] bufferRespuesta = null;
                        paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length, paquete.getAddress(), paquete.getPort());
                        try {
                            socket.send(paqueteRespuesta);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } 
                    }
    }

}

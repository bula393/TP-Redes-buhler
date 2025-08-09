public class hiloClienteEnvio extends Thread {
    public void run() {
        try {
            DatagramSocket sock = new DatagramSocket();
            byte[] inBuf = new byte[10000];
            DatagramPacket inPkt = new DatagramPacket(inBuf, inBuf.length);
            sock.receive(inPkt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Enviando archivo...");
        enviar_archivo(inPkt.getAddress(), inPkt.getPort(),inPkt.getData().toString());
        System.out.println("Archivo enviado.");
        sock.close();
        this.interrupt();
    }
}
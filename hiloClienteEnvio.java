public class hiloClienteReceptor extends Thread {
    
    public void enviar_archivo(InetAddress receptor,port,String ruta_archivo) throws Exception {
        File archivo = new File(ruta_archivo);
        Path archivo_salida = Paths.get(ruta_archivo);
        byte ip[] = {(byte) 127,0,0, (byte) 1};
        InetAddress server = InetAddress.getByAddress(ip);
        byte[] outBuf = archivo_salida.toString().getBytes("US-ASCII");
        DatagramPacket outPkt = new DatagramPacket(outBuf, outBuf.length, server, 8888);
        sock.send(outPkt);
    }
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
        hiloCliente hilo = new hiloCliente();
        hilo.start();
        enviar_archivo(inPkt.getAddress(), inPkt.getPort(),inPkt.getData().toString());
        System.out.println("Archivo enviado.");
        sock.close();
        this.interrupt();
    }
}
import java.io.InputStreamReader;
import java.net.ServerSocket;


public class WebServerMain {
    public static void main(String args[]) {
        if(args.length == 2){
            int port = Integer.parseInt(args[1]);
            String dir = args[0];
            
            Server s = new Server(port, dir);
            // Client c = new Client(dir, port);
        }
        else{
            System.out.println("Usage: java WebServerMain <document_root> <port>");
        }
     }
}
//from Basic Client Server Example on StudRes
//used code from https://codereview.stackexchange.com/questions/84800/webserver-for-handling-get-and-head-requests
import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.text.DefaultStyledDocument.ElementSpec;
import javax.xml.ws.http.HTTPBinding;

public class ConnectionHandler {

	private Socket conn;       // socket representing TCP/IP connection to Client
	private InputStream is=null;    // get data from client on this input stream	
	private DataOutputStream out=null;   // can send data back to the client on this output stream
	BufferedReader br;         // use buffered reader to read client data
	String httpMethod = "";
	String contentType = null;
	String contentLength = null;
    String httpVersion = "";
    String fileName = "";
    String status = "";
	String END = "\r\n";
	File file = null;
	FileInputStream fis=null;
	
	boolean badRequest = true;
	String path = "";

	public ConnectionHandler(Socket conn, String dir) {
		this.conn = conn;
		this.path = dir;
		try{
			is = conn.getInputStream();     // get data from client on this input stream
			out = new DataOutputStream(conn.getOutputStream());  // to send data back to the client on this stream
			br = new BufferedReader(new InputStreamReader(is)); // use buffered reader to read client data
		} catch (IOException ioe){
			System.out.println("ConnectionHandler: " + ioe.getMessage());
		}
	}

	public void handleClientRequest() { 
		System.out.println("new ConnectionHandler constucted .... ");
		try {
			processData();
		} catch  (Exception e){ // exit cleanly for any Exception (including IOException, ClientDisconnectedException)
			System.out.println("ConnectionHandler.handleClientRequest: " + e.getMessage());
			cleanup();     // cleanup and exit
		}
	}


	private void processData() throws DisconnectedException, IOException {
			String line = br.readLine();
			StringTokenizer st = new StringTokenizer(line);
			
			if(line == null || line.equals("null") || line.equals("exit") ){
				throw new DisconnectedException(" ... client has closed the connection ... ");
			} 
			if(line.startsWith("GET") || line.startsWith("HEAD") && line.endsWith("HTTP/1.0") || line.endsWith("HTTP/1.1")){
				badRequest = false;
				
				if(st.countTokens() < 3){
					System.out.println("badRequest is true");
					badRequest = true;
				}

				
			}
			

			if(!badRequest){
				httpMethod = st.nextToken();
				System.out.println("httpMethod: " + httpMethod);
				fileName = st.nextToken();
				
				// fileName = fileName.substring(1);
				System.out.println("filename: " + fileName);
				httpVersion = st.nextToken();
				System.out.println("httpVersion: " + httpVersion);
			}
			

			System.out.println("ConnectionHandler: " + line); // assuming no exception, print out line received from client
			
			sendResponse(httpMethod, fileName, conn, badRequest);
		
		
	}

	public void sendResponse(String httpMethod, String fileName, Socket conn, boolean badRequest) throws DisconnectedException, IOException{
		if(badRequest){
			System.out.println("\n400 Bad Request\n");
            status = "HTTP/1.1 400 Bad Request" + END;
		}

		try{
            this.out = new DataOutputStream(conn.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		String pathfilename = path+fileName;
		System.out.println("pathfilename: " + pathfilename);

		File file = new File(path+fileName);
		if(!file.exists()){
			System.out.println("\n404 Not Found: \n" + fileName.substring(1) + " was not found on this server...");
            status = "HTTP/1.1 404 Not Found" + END;
		}
		else if(file.exists() && !badRequest){
			status = "HTTP/1.1 200 OK" + END;
		}
 
		file = new File(path+fileName);

		try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        contentType = "Content-type: " + getContentType(fileName) + END;
		contentLength = "Content-Length: " + file.length() + END;
		
		out.writeBytes(status);
        out.writeBytes(contentType);
        out.writeBytes(contentLength);
        out.writeBytes(END);    


        if(httpMethod.equalsIgnoreCase("GET")) {
            sendBytes(fis, out);
		}
		else if(httpMethod.equalsIgnoreCase("HEAD")){
			System.out.println("ees a head");
		}

		out.close();

        cleanup();



	}

	private void sendBytes(FileInputStream in, OutputStream out) throws IOException{
        byte[] buffer = new byte[1024];
        int bytes = 0;

        while((bytes = in.read(buffer)) != -1 )
        {
            out.write(buffer, 0, bytes);
        }   
    }

	private String getContentType(String fileName)
    {
        if(fileName.endsWith(".TXT")) {return "text/plain";}
        if(fileName.endsWith(".HTM") || fileName.endsWith(".HTML")) {return "text/html";}
        if(fileName.endsWith(".JPG") || fileName.endsWith(".JPEG")) {return "image/jpeg";}
        if(fileName.endsWith(".PNG")) {return "image/png";}
        if(fileName.endsWith(".GIF")) {return "image/gif";}
        if(fileName.endsWith(".ICO")) {return "image/ico";}
        return "application/octet-stream";
	}


	private void cleanup(){
		System.out.println("ConnectionHandler: ... cleaning up and exiting ... " );
		try{
			br.close();
			is.close();
			conn.close();
		} catch (IOException ioe){
			System.out.println("ConnectionHandler:cleanup " + ioe.getMessage());
		}
	}



}

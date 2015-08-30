
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


// This class implements the client actions
public class Client implements Runnable{
	
	private UserInterface ui;
	private Socket socket;
	private BufferedWriter writer;
	private BufferedReader reader;
	private boolean connected;
	private String channel, nick;
	
	
	public Client(){
		ui = new UserInterface(this);		
		connected = false;
	}

	
	@Override
	public void run() {
		while(true){
			if(connected){
				read();
			}
		}
	}

	// Connect with the data provided in the GUI
	public void connect(String nick, String server, String port, String channel){
		try{
			this.channel = channel;
			this.nick = nick;
			socket = new Socket(server, Integer.parseInt(port));
        	writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        	reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	writer.write("NICK " + nick + "\r\n");
        	writer.write("USER " + nick + " 8 * : rofle\r\n");
        	writer.write("JOIN " + channel + "\r\n");
            writer.flush();
            connected = true;
            new Thread(this).start();	
		}catch(Exception ex){
			ui.setText(ex.getMessage() + "\n");
			ui.setText("Failed to connect" + "\n");
		}		
	}
	
	
	// Read lines sent by the server which the user is connected to
	// Its necessary to respond with to a "PING" with a "PONG" to remain connected.
	public void read(){
		 String line = null;
	        try {
				while ((line = reader.readLine()) != null) {		         
			            if (line.startsWith("PING")) {
			                writer.write("PONG " + line.substring(5) + "\r\n");
			                writer.flush();
			            }
			            else {
			                ui.setText(line + "\r\n");
			            }		        
				}
			} catch (IOException ex) {
				ui.setText(ex.getMessage() + "\r\n");
				connected = false;
			}
	}
	
	// Send a message to the server
	public void send(String msg){
		try {
			String text = ":" + nick + "!~lolz@king.eu PRIVMSG " + channel + " :" + msg + "\r\n";
			writer.write(text);
			writer.flush();
			ui.setText(text);
		} catch (IOException ex) {
			ui.setText(ex.getMessage() + "\r\n");
		}
	}
	
	
	public static void main(String[] args) {	
		new Client();
	}


}
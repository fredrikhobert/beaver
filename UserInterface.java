
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class sets up the GUI and listens to various actions by the user
public class UserInterface extends Frame implements ActionListener {


	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField nick, server, port, channel, msg;
	private JTextArea textArea;
	private JButton send, connect; 
	private JLabel label;
	private JScrollPane scrollPane;
	private Client client;

	
	// Initialize userinterface components
	public UserInterface(Client client) {
		this.client = client;
		frame = new JFrame("Best IRC client");
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 900, 460);
        
        label = new JLabel("Nickname: ");		
		frame.getContentPane().add(label);
        
        nick = new JTextField();
        nick.setColumns(10);
        frame.getContentPane().add(nick);
        
        label = new JLabel("Server: ");		
		frame.getContentPane().add(label);
        
        server = new JTextField("sinisalo.freenode.net");
        server.setColumns(15);
        frame.getContentPane().add(server);
        
        label = new JLabel("Port: ");		
		frame.getContentPane().add(label);
        
        port = new JTextField("6667");
        port.setColumns(5);
        frame.getContentPane().add(port);
        
        label = new JLabel("Channel: ");		
		frame.getContentPane().add(label);
        
        channel = new JTextField("#ubuntu");
        channel.setColumns(10);
        frame.getContentPane().add(channel);
        
        connect = new JButton("Connect");
        frame.getContentPane().add(connect);
        connect.addActionListener(this);
        		
        textArea = new JTextArea(20, 75);
        scrollPane = new JScrollPane(textArea); 
        textArea.setEditable(false);       
        frame.getContentPane().add(scrollPane);
        
        label = new JLabel("Message: ");		
		frame.getContentPane().add(label);
        
        msg = new JTextField();
        msg.setColumns(40);
        frame.getContentPane().add(msg);
        
        send = new JButton("Send");
        frame.getContentPane().add(send);
        send.addActionListener(this);
        
        frame.setVisible(true);
	}

	
	public void setText(String text){
		textArea.append(text);
	}
	
	
	// Listens to various actions
	@Override
	public void actionPerformed(ActionEvent arg) {
		JButton button = (JButton)arg.getSource();
        if(button.getText() == "Connect"){
        	client.connect(nick.getText().trim(), server.getText().trim(), port.getText().trim(), channel.getText().trim());
        } else if(button.getText() == "Send"){
        	client.send(msg.getText());
        	msg.setText("");
        } else {
        	System.out.println("fml");
        }
		
	}
	

}

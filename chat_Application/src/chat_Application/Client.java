package chat_Application;


/**
 *Todos los imports necesarios que el programa ocupa para que logre funcionar
 *@version 4/3/2020 
 */


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



/**
 * 
 * @author Jose
 *Aqui se inicializa la clase cliente con el uso de Swing que se importó anteriormente, creando todo lo necesario  para
 *lo que se va a ver como interfaz
 *Uso de Swing
 *
 */
public class Client {
	
	JFrame window_chat=null;
	JButton btn_send=null;
	JTextField txt_msg=null;
	JTextArea area_chat=null;
	JPanel container_areachat=null;
	JPanel container_btntxt=null;
	JScrollPane scroll=null;
	Socket socket =null;
	BufferedReader reader=null;
	PrintWriter writer=null;
	
	
/**
 * 	Aqui se crea la interfaz que ocupa el programa y tambien se le instancias los tamaños requeridos a cada cosa
 * Tambien se crea el constructor o metodo para la creacion del menu
 */
	
	public Client() {
		makeInterfaxe();
		
	}
	
	
	public void makeInterfaxe() {
		window_chat=new JFrame("Client");
		btn_send=new JButton("Send");
		txt_msg=new JTextField(4);
		area_chat=new JTextArea(10,12);
		scroll=new JScrollPane(area_chat);
		container_areachat=new JPanel();
		container_areachat.setLayout(new GridLayout(1,1));
		container_areachat.add(scroll);
		container_btntxt=new JPanel();
		container_btntxt.setLayout(new GridLayout(1,2));
		container_btntxt.add(txt_msg);
		container_btntxt.add(btn_send);
		window_chat.setLayout(new BorderLayout());
		window_chat.add(container_areachat,BorderLayout.NORTH);
		window_chat.add(container_btntxt,BorderLayout.SOUTH);
		window_chat.setSize(300,220);
		window_chat.setVisible(true);
		window_chat.setResizable(false);
		window_chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/**
		 * Aqui viene la creacion del hilo principal del programa Cliente, se activa y se crea el primer socket
		 * @
		 */
		
		Thread main =new Thread(new Runnable() {
			public void run() {
				try {                              //Este try y catch fueron referenciados gracias a las tutorias
				socket=new Socket("localhost",9000);// está conectado a la misma pc
				read();
				write();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
				
			}//cierre del meotodo
		});
		main.start();
		
	}//cierre de la clase

	/**
	 * Creacion del metodo leer
	 * Creacion del hilo del metodo leer
	 */
	public void read() {
		Thread read_thread = new Thread(new Runnable() {
			public void run() {
				try {                                        //Este try y catch fueron referenciados gracias a las tutorias
					 reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					    while(true) {
						    String msg_received= reader.readLine();
						    area_chat.append("Server: "+ msg_received +"\n");
					    }
				}catch(Exception ex) {
					ex.printStackTrace();
					
				}
			}
		});//cierre del hilo
       
		read_thread.start();
}//cierre del metodo	


	
	/**
	 * Creacion del metodo escribir
	 * Creacion del hilo del metodo escribir
	 */
public void write() {
	Thread write_thread = new Thread(new Runnable() { 
		public void run() {
			try {
				writer=new PrintWriter(socket.getOutputStream(),true);
				btn_send.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
				    
					   String send_msg= txt_msg.getText();
					   writer.println(send_msg);
					   txt_msg.setText("");
					}


				});
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	});//cierre del metodo
	write_thread.start();
}//cierre del hilo
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	    new Client();

	}//cierre del metodo del hilo main

}//cierre del hilo principal del programa Client

package chat_Application;


/**
 *Todos los imports necesarios que el programa ocupa para que logre funcionar
 *@version 4/3/2020 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.awt.*;



/**
 * 
 * @author Jose
 *Aqui se inicializa la clase servifor con el uso de Swing que se importó anteriormente, creando todo lo necesario  para
 *lo que se va a ver como interfaz
 *Uso de Swing
 *
 */
public class Server {//constructor
	JFrame window_chat=null;
	JButton btn_send=null;
	JTextField txt_msg=null;
	JTextArea area_chat=null;
	JPanel container_areachat=null;
	JPanel container_btntxt=null;
	JScrollPane scroll=null;
	ServerSocket server=null;
	Socket socket =null;
	BufferedReader reader=null;
	PrintWriter writer=null;
	
	
	
	/**
	 * 	Aqui se crea la interfaz que ocupa el programa y tambien se le instancias los tamaños requeridos a cada cosa
	 * Tambien se crea el constructor o metodo para la creacion del menu
	 */
	
	public Server() {
		makeInterfaxe();
		
	}

	public void makeInterfaxe() {
		window_chat=new JFrame("Server");
		btn_send=new JButton("Send");
		txt_msg=new JTextField(4);
		area_chat=new JTextArea(10,12);
		scroll =new JScrollPane(area_chat);
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
		
		Thread main = new Thread(new Runnable() {
			public void run() {
				try {
			
			    server=new ServerSocket(9000);
			        while(true) {
			        	socket= server.accept();
			        	read();
			        	write();
			        	
			        }
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});//cierre del hilo
		main.start();//Aqui se inicializa el menu del servidor
		
	}//cierre del metodo interfaz	
	
	
	
	/**
	 * Creacion del metodo leer
	 * Creacion del hilo del metodo leer
	 */
	
	public void read() {
		Thread read_thread = new Thread(new Runnable() {
			public void run() {
				try {                                             //Este try y catch fueron referenciados gracias a las tutorias
					 reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					    while(true) {
						    String msg_received= reader.readLine();
						    area_chat.append("Client: "+ msg_received+"\n" );
					    }
				}catch(Exception ex) {
					ex.printStackTrace();
					
				}
			}
		});
       
		read_thread.start();//el hilo del metodo leer esta listo para usarse
}//cierre del metodo leer	

	
	
	
	/**
	 * Creacion del metodo escribir
	 * Creacion del hilo del metodo escribir
	 */
public void write() {
	Thread write_thread = new Thread(new Runnable() { 
		public void run() {
			try {                                            //Este try y catch fueron referenciados gracias a las tutorias
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
	});
	write_thread.start();//hilo escribir listo para usarse
}//cierre del metodo escribir
				
	    public static void main(String[] args) {

		
		new Server();

	}//cierre del metodo del hilo main
	

}//cierre del hilo principal del programa Server

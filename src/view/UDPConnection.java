package view;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPConnection extends Thread {
	
	private DatagramSocket socket;
	IObserver observer;
	
	@Override
	public void run() {
		
		try {
			
			//1.escuchar
			socket = new DatagramSocket(5000);
			
			//2. espera msg
			while(true) {
				
				//3. parametros en constructor
				byte[] buffer =  new byte[100];
		
				DatagramPacket packet = new DatagramPacket (buffer, buffer.length);
				System.out.println("esperando el datagram");
				
				socket.receive(packet);
				String msg = new String(packet.getData()).trim();
				
				observer.notifyMsg(msg);
				
				System.out.println("Datagram recibido" + msg);
				
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void sendMsg(String msg) {
		
		new Thread(
				
				()-> {
					
					try {
						
						//4.parametros, datagram envio
						InetAddress ip;
						
						ip = InetAddress.getByName("10.0.2.2");
						DatagramPacket packet = new DatagramPacket (msg.getBytes(), msg.getBytes().length, ip, 9000);
					
						socket.send(packet);
						
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}).start();
		
	}

	public void setObserver(IObserver observer) {
		this.observer = observer;
	
	}
	
	
}

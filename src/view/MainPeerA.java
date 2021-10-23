package view;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.Food;
import processing.core.PApplet;
import processing.core.PImage;

public class MainPeerA extends PApplet implements IObserver{

	UDPConnection udp;
	
	int x, y, num;
	private int pedidos;
	ArrayList <Food> food;
	String fecha;
	Food order;
	
	//imagenes
	PImage burger, fries, hotdog, shake;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main(MainPeerA.class.getName());

	}
	
	public void settings() {
		size(500,800);
	}
	
	public void setup() {
		
		pedidos = 0;
				
		//conexion UDP
		udp = new UDPConnection();
		udp.setObserver(this);
		udp.start();
		
		//arreglo
		food = new ArrayList<>();
	
		//imagenes
		burger = loadImage("./data/burger.png");
		fries = loadImage("./data/fries.png");
		hotdog = loadImage("./data/hotdog.png");
		shake = loadImage("./data/milkshake.png");
		
	}
	
	public void draw() {
		
		background(255);
		
		for(int i=0; i<food.size(); i++) {
			
			order = food.get(i);
			
			
			
			switch (order.getType()) {
			
			case "burger":
				
				image(burger, order.getX()-200, order.getY()-50,160,160);
				
				break;
			
			case "fries":
				
				image(fries, order.getX()-200, order.getY()-50,160,160);
				
				break;
			
			case "hotDog":
				
				image(hotdog, order.getX()-200, order.getY()-50,160,160);
				
				break;

			case "shake":
				
				image(shake, order.getX()-200, order.getY()-50,160,160);
				
				break;
	
		}
			//nombre del pedido y la hora en la que se realizo
			fill(80);
			textAlign(LEFT);
			text(food.get(i).getType(), 240, 60 + (100*i));
			textSize(14);
			text("Hora: " + food.get(i).getTime(), 240, 80 + (100*i));	
		}
		
	}
	
	//al presionar sobre el pedido manda el msg y lo eliminia del la lista
	//lo elimina pero no manda el mensaje
	public void mousePressed() {
		
		for (int i = 0; i < food.size(); i++) {
			
			System.out.println("Pedido listo");
			
			if (mouseX > 40 && mouseX < 160 && mouseY > (70+(100*i)) - 40 && mouseY < (70+(100*i)) + 40) {
				
				udp.sendMsg("Pedido listo");
				food.remove(i);
			}
		}
	}

	
	@Override
	public void notifyMsg(String msg) {
		// TODO Auto-generated method stub
		
		Gson gson = new Gson();
		order = gson.fromJson(msg, Food.class);
		
		//limite para que las ordenes no pasen del tamaño de la pantalla
		if(order.getNum() <= 8) {
			
			food.add(order);
		}
	}
	

}

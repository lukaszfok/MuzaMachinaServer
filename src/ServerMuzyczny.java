import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;



public class ServerMuzyczny {

	
	ArrayList<ObjectOutputStream> strumienieWyjDoKlientow;
	
	public static void main(String[] args) {
		new ServerMuzyczny().doRoboty();
	}
	 class ObslugaKlientow implements Runnable {
		ObjectInputStream wej;
		Socket gniazdoKlienta;
		
		public ObslugaKlientow(Socket socket) {
			try {
			gniazdoKlienta = socket;
			wej = new ObjectInputStream(gniazdoKlienta.getInputStream());
			} catch(Exception ex) {ex.printStackTrace();}
		} // koniec konstruktora
		
		public void run() {
				Object o2 = null;
				Object o1 = null;
				try {
					while ((o1 = wej.readObject()) != null) {
						o2 = wej.readObject();
						System.out.println("odczytano dwa obiekty");
						przekazDoWszystkich(o1, o2);
					} // koniec while
				} catch(Exception ex) {ex.printStackTrace();}
			} // koniec run
		}//koniec klasy wewnetrznej
	
	 public void doRoboty() {
		 strumienieWyjDoKlientow = new ArrayList<ObjectOutputStream>();
		 	try {
		 		ServerSocket gniazdoSerwera = new ServerSocket(4242);
		 		while(true) {
		 			Socket clientSocket = gniazdoSerwera.accept();
		 			ObjectOutputStream wyj = new ObjectOutputStream(clientSocket.getOutputStream());
		 			strumienieWyjDoKlientow.add(wyj);
		 			Thread watek = new Thread(new ObslugaKlientow(clientSocket));
		 			watek.start();
		 			System.out.println("mamy połączenie");
		 		}
		 		} catch(Exception ex) {
		 			ex.printStackTrace ();
		 		}
		 	} // koniec metody
	 
		 public void przekazDoWszystkich(Object obj1, Object obj2) {
		 Iterator iter = strumienieWyjDoKlientow.iterator();
		 			while(iter.hasNext()) {
		 				try {
		 					ObjectOutputStream wyj = (ObjectOutputStream) iter.next();
		 					wyj.writeObject(obj1);
		 					wyj.writeObject(obj2);
		 				} catch(Exception ex) {ex.printStackTrace();}
		 			}
		 		} // koniec metody
	
		
	}



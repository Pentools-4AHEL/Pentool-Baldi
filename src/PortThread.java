import java.net.InetSocketAddress;
import java.net.Socket;

public class PortThread extends Thread {
	private String name;
	private boolean isNew;
	private boolean isOld;
	private String address;
	private int port;
	private int timeoutMil;

	public PortThread(String name, String address, int port, int timeoutMil) {
		this.name = name;
		this.isNew = true;
		this.isOld = false;
		this.address = address;
		this.port = port;
		this.timeoutMil = timeoutMil;
	}

	public void run() {
		isNew = false;

		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(address, port), timeoutMil);
			socket.close();
			System.out.println("Port " + port + " is open");
		} catch (Exception ex) {
		}

		isOld = true;
	}
	
	public boolean isNew() {
		return isNew;
	}
	public boolean isOld() {
		return isOld;
	}
}
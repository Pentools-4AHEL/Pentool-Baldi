import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPGetter {

    private InetAddress[] myHost = null;

    public IPGetter(String address) {
        try {
            myHost = InetAddress.getAllByName(address);
        } catch (UnknownHostException e) {
            System.exit(1);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PortScanner scanner = new PortScanner("192.168.1.1", 1, 65535, 100000, 500);
        //scanner.getOpenPortList();

		/*IPGetter getter = new IPGetter("www.apple.at");
		for(InetAddress add : getter.getHost()) {
			System.out.println(add.getHostAddress());
		}*/
    }

    public InetAddress[] getHost() {
        return myHost;
    }

}

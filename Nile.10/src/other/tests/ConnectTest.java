package other.tests;
import com.controller.fetcher.Connector;

public class ConnectTest {
	
	public static void main(String args[]) {
		Connector connect = new Connector();
		connect.openConnection();
		System.out.println(connect.toString());
		System.out.println(connect.isClosed());
		connect.closeConnection();
		System.out.println(connect.toString());
		System.out.println(connect.isClosed());
	}
}

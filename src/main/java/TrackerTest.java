import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class TrackerTest
{
    public static void main(String[] args) throws IOException{
        DatagramSocket socket;
        socket = new DatagramSocket();

        byte[] sendData;
        sendData = new ConnectClient().getBytes();
        InetAddress IPAddress = InetAddress.getByName("localhost");

        //  Send Connect Message to Tracker
        DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, 6969);
        socket.send(sendPacket);

        //  Receive Connect Response
        byte[] receiveData = new byte[512];
        DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
        socket.receive(receivePacket);
        ByteBuffer receiveDataBB = ByteBuffer.wrap(receivePacket.getData(), 0, receiveData.length);

        int action = receiveDataBB.getInt();
        int transactionid = receiveDataBB.getInt();
        long connectionid = receiveDataBB.getLong();

        //  Send Announce Message
        AnnounceClient a = new AnnounceClient(connectionid, transactionid+1);
        sendData = a.getBytes();
        sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 6969);
        socket.send(sendPacket);

        //  Receive announce response
        receivePacket = new DatagramPacket(receiveData,receiveData.length);
        socket.receive(receivePacket);

        socket.close();
    }
}

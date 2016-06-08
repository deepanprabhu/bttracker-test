import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class TrackerTest
{
    public static void main(String[] args) throws IOException{
        DatagramSocket socket;
        socket = new DatagramSocket();

        byte[] sendData;
        sendData = new ConnectClient().getBytes();
        InetAddress IPAddress = InetAddress.getByName("tracker.coppersurfer.tk");

        //  Send Connect Message to Tracker
        DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, 6969);
        socket.send(sendPacket);

        //  Receive Connect Response
        byte[] receiveData = new byte[1024];
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

        //  Decoding the Announce response
        receiveDataBB = ByteBuffer.wrap(receivePacket.getData(), 0, receiveData.length);

        action = receiveDataBB.getInt();
        transactionid = receiveDataBB.getInt();
        int interval = receiveDataBB.getInt();
        int leechers = receiveDataBB.getInt();
        int seeders = receiveDataBB.getInt();

        int ip = receiveDataBB.getInt();
        int port = (int) receiveDataBB.getShort();

        while( ip != 0 && port != 0){
            System.out.println(InetAddress.getByAddress(ByteBuffer.allocate(4).putInt(ip).array()));
            System.out.println(port);

            ip = receiveDataBB.getInt();
            port = (int) receiveDataBB.getShort();
        }

        //  Send Announce Message
        ScrapeClient c = new ScrapeClient(connectionid, transactionid+2);
        sendData = c.getBytes();
        sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 6969);
        socket.send(sendPacket);

        //  Receive announce response
        receivePacket = new DatagramPacket(receiveData,receiveData.length);
        socket.receive(receivePacket);

        receiveDataBB = ByteBuffer.wrap(receivePacket.getData(), 0, receiveData.length);

        action = receiveDataBB.getInt();
        transactionid = receiveDataBB.getInt();

        int complete = receiveDataBB.getInt();
        int downloaded = receiveDataBB.getInt();
        int incomplete = receiveDataBB.getInt();

        while(complete >= 0 && downloaded >= 0 && incomplete >= 0){
            System.out.println(complete);
            System.out.println(downloaded);
            System.out.println(incomplete);

            complete = receiveDataBB.getInt();
            downloaded = receiveDataBB.getInt();
            incomplete = receiveDataBB.getInt();
        }

        socket.close();
    }
}

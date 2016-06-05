import java.nio.ByteBuffer;
import java.util.Random;

public class AnnounceClient {
    long connectionId;
    int action = 1 ;
    int transactionId;
    byte[] infoHash = new byte[20];
    byte[] peerId = new byte[20];
    long downloaded = 0l;
    long left = 1000l;
    long uploaded = 2l;
    int event = 2;
    int ip = 0;
    int key;
    int numWant = -1;
    short port;
    short extensions;
    public AnnounceClient(long connectionId, int transactionId){
        this.connectionId = connectionId;
        this.transactionId = transactionId;
        new Random().nextBytes(infoHash);
        new Random().nextBytes(peerId);
        key = new Random().nextInt();
        port = 6000;
    }
    public byte[] getBytes(){
        ByteBuffer bb = ByteBuffer.allocate(100);
        bb.putLong(connectionId);
        bb.putInt(action);
        bb.putInt(transactionId);
        bb.put(infoHash).put(peerId).putLong(downloaded).putLong(left).putLong(uploaded);
        bb.putInt(event).putInt(ip).putInt(key).putInt(numWant).putShort(port).putShort(extensions);
        return bb.array();
    }
}

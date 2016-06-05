import java.io.*;
import java.nio.ByteBuffer;

public class ConnectClient implements Serializable{
    public final long connectionId = Long.decode("0x41727101980");
    public final int action = 0;
    public final int transactionId = 1000;

    public byte[] getBytes() throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(connectionId).putInt(action).putInt(transactionId);
        return buffer.array();
    }
}

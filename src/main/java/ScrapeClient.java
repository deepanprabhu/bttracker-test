import java.nio.ByteBuffer;
import java.util.Random;

public class ScrapeClient {
    long connectionId;
    int action = 2;
    int transactionId;
    byte[] infoHash = hexStringToByteArray("E80521C29A26EC53358F4462C72D99175FE53985");
    byte[] infoHash1 = hexStringToByteArray("50280CE45E08CD43D7B03728F25EEE2493DB4FFF");

    public byte[] getBytes(){
        ByteBuffer bb = ByteBuffer.allocate(56);
        bb.putLong(connectionId);
        bb.putInt(action);
        bb.putInt(transactionId);
        bb.put(infoHash);
        bb.put(infoHash1);
        return bb.array();
    }

    public ScrapeClient(long connectionId, int transactionId){
        this.connectionId = connectionId;
        this.transactionId = transactionId;
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}

import jcifs.smb.NtlmPasswordAuthentication;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Scanner;

public  class CryptoAlgoritm {
    private final static char[] HEX_ARRAY = "0123456789ABCDEFZ".toCharArray();
    private final static char[] ALPHAVIT = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();


    public static void main(String... args){
        String hash= "7A21990FCD3D759941E45C490F143D5F";
        Thread a = new Thread(()-> {
            StringBuilder pass = new StringBuilder("0");
            while (true) {
                String currentHash = hashingText(pass);
                if (currentHash.equals(hash)) {
                    break;
                }
                pass = nextString(pass);

                Thread.yield();
            }
            System.out.println(pass + "1");
        });
        Thread b = new Thread(()-> {
            StringBuilder pass = new StringBuilder("0000");
            while (true) {
                String currentHash = hashingText(pass);
                if (currentHash.equals(hash)) {
                    break;
                }
                pass = nextString(pass);
                Thread.yield();
            }
            System.out.println(pass + "2");
            if(a.getState().equals(Thread.State.TERMINATED)){
                return;
            }

        });
        a.start();
        b.start();




    }
    private static StringBuilder nextString(StringBuilder current){
        for(int i=current.length()-1;i>=0;i--){
            if(current.charAt(i)!=ALPHAVIT[ALPHAVIT.length-1]){

                current.replace(i,i+1, String.valueOf(ALPHAVIT[ArrayUtils.indexOf(ALPHAVIT,current.charAt(i))+1]));
                for(int j=i+1;j<current.length();j++){
                    current.replace(j,j+1, String.valueOf(ALPHAVIT[0]));
                }
                return current;
            }
        }
        StringBuilder result = new StringBuilder();
        for(int i = 0; i<current.length()+1;i++){
            result.append(ALPHAVIT[0]);
        }
        return result;
    }

    private static String hashingText(StringBuilder text){
        String s = (text != null) ? text.toString() : "";
        byte[] hash = NtlmPasswordAuthentication.nTOWFv1(s);
        return bytesToHex(hash).toUpperCase();
    }
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}

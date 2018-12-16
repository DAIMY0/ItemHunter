import javax.swing.JFrame;
import java.io.*;
 
public class ClientRunner {
 
    public static void main(String args[]) throws IOException {
 
        JFrame frame = new JFrame("Client");
 
        Client sc = new Client();//Client is the screen class
        frame.add(sc);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
 
        sc.poll();
    }
}
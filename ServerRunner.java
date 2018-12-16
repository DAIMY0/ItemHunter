import javax.swing.JFrame;
import java.io.*;
 
public class ServerRunner {
 
    public static void main(String args[]) throws IOException {
 
        JFrame frame = new JFrame("Server");
 
        Server sc = new Server();//Server is the screen class
        frame.add(sc);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
 
        sc.poll();
    }
}
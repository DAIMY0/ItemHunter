import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Font;
import java.util.Map;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.event.*;
import java.util.Iterator;
import java.util.Stack;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Client extends JPanel implements KeyListener {
	private int gamewins = 0;
	private int gamelosses = 0;
	private int gameties = 0;
	private ObjectOutputStream outObj;
	private ObjectInputStream inObj;
	private Font standardFont;
	private Stack<Integer> healthBar = new Stack<Integer>();
	private int fontSize = 16;
	private int x = 100;
    private int y = 100;
	private String hostName = "localhost"; 
    private int portNumber =  444;
	private int score = 0;
	private HashMap<Location, Item> gameBoard;
	private Location myLoc = new Location(100, 100);
	private Location serverLoc = new Location(100, 100);
	private int gamewon = 0;
	
	public Client() {
		for(int i = 0; i < 4; i++) {
			healthBar.push(i);
		}
		this.setLayout(null);
		this.setFocusable(true);
		addKeyListener(this);
		standardFont = new Font("Arial", Font.PLAIN, 136);
		try {
			Socket serverSocket = new Socket(hostName, portNumber);
			outObj = new ObjectOutputStream(serverSocket.getOutputStream());
			inObj = new ObjectInputStream(serverSocket.getInputStream());
		} catch (Exception i) {
			i.printStackTrace();
			System.exit(0);
		}
	}
	
    public Dimension getPreferredSize() {
        return new Dimension(800,600);
    }
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Color Brown = new Color(139, 69, 19);
		Color serverColor = new Color(32, 54, 150);
		Color Brown1 = new Color(59, 69, 59);
		Color obstacleColor = new Color(255, 255, 255);
		g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		g.drawString("Server wins: " + gamewins, 550, 10);
		g.drawString("Client wins: " + gamelosses, 450, 10);
		if(gamewon > 0) {
			try {
                Thread.sleep(5000);
				g.drawString("YOU WON!", 450, 10);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
		}
		g.drawString("Score: " + score, 450, 50);
		g.setFont(standardFont);	
		for(int x = 0; x < 9; x++) {
			for(int y = 0; y < 9; y++) {
				g.setColor(Color.WHITE);
				g.fillRect(x * 50, y, 5, 400);//draws x of grid
				g.fillRect(x, y *50, 400, 5);//draws y of grid
				Location temp = new Location(x, y);
				if(gameBoard != null && gameBoard.containsKey(temp) && gameBoard.get(temp).getType() == 3){
					g.setColor(Brown1);
					g.fillRect(temp.getX() * 50, temp.getY() * 50, 50, 50);
				}
				if(gameBoard != null && gameBoard.containsKey(temp) && gameBoard.get(temp).getType() == 4){ 
					g.setColor(obstacleColor);
					g.fillRect(temp.getX() * 50, temp.getY() * 50, 50, 50);//draws obstacles
				}
			}
		}
		if(!healthBar.empty()) {
			for(int i = 0; i < healthBar.size(); i++) {
			g.setColor(Color.RED);
			g.fillRect(500 + i*20, 50, 10, 10);
			}
		}
		g.setColor(Brown);
        g.fillRect(x*50, y*50, 10, 50);
		g.fillOval(x*50, y*50, 25, 25);//client icon
		g.setColor(serverColor);
		g.fillRect(serverLoc.getX()*50, serverLoc.getY()*50, 10, 50);
		g.fillOval(serverLoc.getX()*50, serverLoc.getY()*50, 25, 25);
    }
	
	@SuppressWarnings("unchecked")
    public void poll() throws IOException {
        try {
			while (true) {
				Object temp = inObj.readObject();
				if(temp instanceof Map) {
					this.gameBoard = (HashMap<Location, Item>)temp;
				}
				if(temp instanceof Location) {
					serverLoc = (Location)temp;
				}
				if(temp instanceof String) {
					if(temp.equals("restart")) {
						myLoc = new Location(100, 100);
						serverLoc = new Location(100, 100);
					}
					if(temp.equals("win")) {
						gamewon++;
					}
				}
				repaint();
			}
        } catch (ClassNotFoundException e) {
            System.err.println("Class does not exist" + e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(1);
        }
    }
	
	public void reset() {
		for(int x = 0; x < 9; x++) {
			for(int y = 0; y < 9; y++) {
				Location temp = new Location(x, y);
				if(gameBoard.containsKey(temp)){ 
					gameBoard.remove(temp);
				}
			}
		}
		x = 100;
		y = 100;
		myLoc = new Location(x/50, y/50);
		serverLoc = new Location(x/50, y/50);
		for(int i = 0; i < 11; i++) {
			int rand = (int)(Math.random()* 8);
			int rand2 = (int)(Math.random()* 8);
			while(gameBoard.containsKey(new Location(rand, rand2))) {
				rand = (int)(Math.random()* 8);
				rand2 = (int)(Math.random()* 8);
			}
			gameBoard.put(new Location(rand, rand2), new Item(3));
		}
		for(int i = 0; i < 10; i++) {
			int rand = (int)(Math.random()* 8);
			int rand2 = (int)(Math.random()* 8);
			
			while(gameBoard.containsKey(new Location(rand, rand2))) {
				rand = (int)(Math.random()* 8);
				rand2 = (int)(Math.random()* 8);
			}
			gameBoard.put(new Location(rand, rand2), new Item(4));
		}
		if(healthBar.size() < 4) {
			for(int i = 0; i < 4; i++) {
				healthBar.push(i);
			}
		}
		repaint();
	}	
	
	public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 40) {
            y += 50;
        }
        if (e.getKeyCode() == 38) {
            y -= 50;
        }
        if (e.getKeyCode() == 37) {
            x -= 50;
        }
        if (e.getKeyCode() == 39) {
            x += 50;
        }
		
		try {
			myLoc = new Location(x/50, y/50);
			if(gameBoard.containsKey(myLoc) && gameBoard.get(myLoc).getType() == 3) {
				xSound();
				gameBoard.remove(myLoc);
				outObj.reset();
				outObj.writeObject(gameBoard);
				score++;
				if(score%6 == 0) {
					gamewins++;
					reset();
					outObj.reset();
					outObj.writeObject(gameBoard);
					outObj.writeObject("restart");
					outObj.writeObject("win");
				}
			}
			if(gameBoard.containsKey(myLoc) && gameBoard.get(myLoc).getType() == 4) {
				loseSound();
				x = 100;
				y = 100;
				myLoc = new Location(x/50, y/50);
				if(healthBar.size() < 0) {
					gamelosses++;
					gameBoard.remove(myLoc);
					gameBoard.put(new Location(x/50, y/50), new Item(2));
					reset();	
					outObj.reset();
					outObj.writeObject(gameBoard);
					return;
				} else {
					healthBar.pop();
				}
				//also lose 1 health here
			}
			outObj.writeObject(myLoc);
		}  catch (IOException f) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(1);
        }
        repaint();
    }
	
	public void loseSound() {
 
        try {
            URL url = this.getClass().getClassLoader().getResource("sound/lose.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
	
	public void xSound() {
 
        try {
            URL url = this.getClass().getClassLoader().getResource("sound/xplace.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
	
	public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}
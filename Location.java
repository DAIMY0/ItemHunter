import java.io.Serializable;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Location implements Serializable{
	private final String BLANK = " ";
    private int x;
    private int y;
	public static final int width = 0;
	
	
    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }
    

	public void reset() {
		
	}
	
	public int hashCode() {
		return 31*x+y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean equals(Object o) {
		Location temp = (Location) o;
		if(temp.getX() == x && temp.getY() == y) {
			return true;
		}
		return false;
	}
	
    public void printLocation(){
        System.out.println("I am located at " + x + ", " + y);
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
	
	public void winSound() {
 
        try {
            URL url = this.getClass().getClassLoader().getResource("sound/win.wav");
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
	
	public void oSound() {
 
        try {
            URL url = this.getClass().getClassLoader().getResource("sound/oplace.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
	
	public boolean checkTie() {
		boolean tf = false;
		int bnum = 0;
		
		System.out.println(bnum);
		return tf;
	}
	
	public String toString() {
		return "" + x + " " + y + "\n";
	}
}
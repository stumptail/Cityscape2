import javax.swing.JFrame;
import java.awt.*;
/**
 * Write a description of class MouseTrack here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MouseTrack
{
    public static void main(String[] args) throws InterruptedException
    {
        // create and configure the frame (window) for the program
        JFrame frame = new JFrame();
        
        frame.setSize(800 /* x */, 600 /* y */);
        frame.setTitle("Cityscape");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // a frame contains a single component; create the Cityscape component and add it to the frame
        MouseTrackComp component = new MouseTrackComp();
        //frame.add(component);
        
        // make the frame visible which will result in the paintComponent method being invoked on the
        //  component.
        frame.setVisible(true);
        
        // animate the cityscape
        for( int seconds = 0; seconds < 10000; seconds++ )
        {

            
            Point location = MouseInfo.getPointerInfo().getLocation();
            component.nextFrame(location);
            Thread.sleep(32); // 1 second pause
            
        }
        
    }
    public static void draw(){
        
    }
}

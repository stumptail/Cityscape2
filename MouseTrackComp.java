import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Write a description of class MouseTrackComp here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MouseTrackComp extends JComponent
{
    double currentAngle;
    int x;
    int y;
    int v;
    /**
     * Constructor for objects of class MouseTrackComp
     */
    public MouseTrackComp()
    {
        x = 100;
        y = 100;
        v = 5;
        // initialise instance variables
        
    }
    @Override
    public void paintComponent(Graphics g){
    }
    public void nextFrame(Point location){
        
        repaint();
    }
    
    
    public static BufferedImage rotate(BufferedImage bimg, Double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
               cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = bimg.getWidth();
        int h = bimg.getHeight();
        int neww = (int) Math.floor(w*cos + h*sin),
            newh = (int) Math.floor(h*cos + w*sin);
        BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((neww-w)/2, (newh-h)/2);
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        graphic.drawRenderedImage(bimg, null);
        graphic.dispose();
        return rotated;
    }
}

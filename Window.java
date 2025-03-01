import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Write a description of class Window here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Window extends Quadrilateral 
{
    // instance variables - replace the example below with your own
    Color onColor;
    Color offColor;

    /**
     * Constructor for objects of class Window
     */
    public Window(double[] newCoord1, double[] newCoord2, double[] newCoord3, double[] newCoord4)
    {
        super(newCoord1, newCoord2, newCoord3, newCoord4);
        // initialise instance variables
        onColor = Color.YELLOW;
        offColor = Color.BLACK;
    }
    public void draw(Graphics2D g){
        super.draw(g, onColor);
    }
}

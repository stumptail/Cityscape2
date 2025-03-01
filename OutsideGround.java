import java.awt.*;

/**
 * Creates the outside part of the ground
 *
 * @author Willson Zhu
 * @version 10/20/24
 */
public class OutsideGround
{
    Building outsideGround;

    /**
     * Constructor for objects of class OutsideGround
     *
     * @param baseLocation the location of the ground
     */
    public OutsideGround(double[][] baseLocation, int r, int g, int b){
        Color groundColor = new Color(r,g,b);
        outsideGround = new Building(baseLocation,200, groundColor);
    }

    /**
     * updates the location of the ground
     *
     * @param baseLocation the new location
     */
    public void updateLocation(double[][]baseLocation){
        outsideGround.updateLocation(baseLocation);
    }

    /**
     * draws the outsideGround
     *
     * @param g the drawer
     */
    public void draw(Graphics2D g, double[][] overall){
        outsideGround.draw(g, overall);
    }

}

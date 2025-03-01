import java.awt.*;

/**
 * Creates the outside part of the ground
 *
 * @author Willson Zhu
 * @version 10/20/24
 */
public class OutsideGround extends Building
{
    public OutsideGround(double[][] baseLocation, double height, Color normColor, Color groundColor){
        
        super(baseLocation, -height, normColor);
        //this.updateHeight = -heigh
        super.updateRoofColor(groundColor);
        
    }
}

import java.awt.*;

/**
 * Top part of ground (repersents roads)
 *
 * @author Willson Zhu
 * @version 10/20/24
 */
public class Ground {
    
    Quadrilateral ground;

    /**
     * Creates a new ground, which functions off the qudrilateral class
     *
     * @param newCoord1 1 vertex
     * @param newCoord2 2 vertex
     * @param newCoord3 3 vertex
     * @param newCoord4 4 vertex
     */
    public Ground(double[] newCoord1, double[] newCoord2, double[] newCoord3, double[] newCoord4) {
        this.ground = new Quadrilateral(newCoord1, newCoord2, newCoord3, newCoord4);
    }

    /**
     * Updates location
     *
     * @param newCoord1 Vertex 1
     * @param newCoord2 Vertex 2
     * @param newCoord3 Vertex 3
     * @param newCoord4 Vertex 4
     * @param offsetX the amount off from 0,0 it should be in X
     * @param offsetY the amount off from 0,0 it should be in Y
     */
    public void updateGround(double[] newCoord1, double[] newCoord2, double[] newCoord3, double[] newCoord4, double offsetX, double offsetY) {
        
        double[] finalCoord1 = {newCoord1[0] + offsetX, newCoord1[1] + offsetY};
        double[] finalCoord2 = {newCoord2[0] + offsetX, newCoord2[1] + offsetY};
        double[] finalCoord3 = {newCoord3[0] + offsetX, newCoord3[1] + offsetY};
        double[] finalCoord4 = {newCoord4[0] + offsetX, newCoord4[1] + offsetY};
        
        this.ground.updateCoords(finalCoord1, finalCoord2, finalCoord3, finalCoord4);
    }

    /**
     * get the location of the ground
     * @return an array of coordinates that repersent location
     */
    public double[][] getLocation(){
        return this.ground.getLocation();
    }

    /**
     * Draws the ground
     *
     * @param g object that draws
     */
    public void draw (Graphics2D g, Color color){
        this.ground.draw(g, color);
    }
}

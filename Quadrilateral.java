import java.awt.geom.Path2D;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.Arrays;

/**
 * Creates a Quadrilateral
 *
 * @author Willson Zhu
 * @version 10/20/24 (is older, but I forgot to write it down)
 */
public class Quadrilateral
{
    
    private Path2D.Double quadrilateral;
    private double[] coord1 = new double[2];
    private double[] coord2 = new double[2];
    private double[] coord3 = new double[2];
    private double[] coord4 = new double[2];
    /**
     * Constructor for objects of class Quadrilateral
     * 
     * @param newCoord1 the first vertex on the Quadrilateral
     * @param newCoord2 the second vertex on the Quadrilateral
     * @param newCoord3 the third vertex on the Quadrilateral
     * @param newCoord4 the fourth vertex on the Quadrilateral
     */
    
    
    public Quadrilateral(double[] newCoord1, double[] newCoord2, double[] newCoord3, double[] newCoord4){
        //System.out.println("Don't");

        this.coord1 = Arrays.copyOf(newCoord1,2);
        this.coord2 = Arrays.copyOf(newCoord2,2);
        this.coord3 = Arrays.copyOf(newCoord3,2);
        this.coord4 = Arrays.copyOf(newCoord4,2);
    }

    /**
     * to String method for Quadrilateral
     * @return vertexs of the quadrilateral
     */
    public String toString(){
        return   "Coord1: " + Arrays.toString(this.coord1)
               + "Coord2: " + Arrays.toString(this.coord2)
               + "Coord3: " + Arrays.toString(this.coord3)
               + "Coord4: " + Arrays.toString(this.coord4);
    }
    /**
     * Draws the quadrilateral with a given Graphics2D object
     * 
     * @param g the given Graphics2D object used to draw the quadrilateral 
     */
    public void draw(Graphics2D g, Color color){
        this.quadrilateral = new Path2D.Double();
        
        this.quadrilateral.moveTo(this.coord1[0],this.coord1[1]);
        this.quadrilateral.lineTo(this.coord2[0],this.coord2[1]);
        this.quadrilateral.lineTo(this.coord3[0],this.coord3[1]);
        this.quadrilateral.lineTo(this.coord4[0],this.coord4[1]);
        this.quadrilateral.closePath();
        
        g.setColor(color);
        g.fill(this.quadrilateral);
        
    }

    /**
     * updates the location of the Quadilateral
     *
     * @param newCoord1 Vertex 1
     * @param newCoord2 Vertex 2
     * @param newCoord3 Vertex 3
     * @param newCoord4 Vertex 4
     */
    public void updateCoords(double[] newCoord1, double[] newCoord2, double[] newCoord3, double[] newCoord4){
        this.coord1 = Arrays.copyOf(newCoord1,2);
        this.coord2 = Arrays.copyOf(newCoord2,2);
        this.coord3 = Arrays.copyOf(newCoord3,2);
        this.coord4 = Arrays.copyOf(newCoord4,2);
    }

    /**
     * gets the coordinates of the vertices of the quadirlateral
     *
     * @return an array of coordinates that repersent the qudrilaterals location
     */
    public double[][] getLocation(){
        double[][] loc = {this.coord1,this.coord2,this.coord3,this.coord4};
        return loc;
    }

}

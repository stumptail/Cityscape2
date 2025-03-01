import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.Color;
import java.util.Random;

/**
 * Class that creates instances of the classes that comprise the cityscape and delegates drawing the
 *  cityscape to these object.
 * 
 * @author Willson Zhu
 * @version 8 October 2024
 */
public class CityscapeComponent extends JComponent
{
    // declare the objects in your Cityscape as instance variables
    // ...
   
    
    private final double MAX_X = 3;
    private final double MAX_Y = 3;
    
    private Building[][] buildingList;
    private Building[][] buildingShadow;
    private double[][][][] buildingListCoords;
    private double[][][][] buildingShadowCoords;
    private Background background;
    private Ground ground;
    private OutsideGround outside;
    // the above list repersents the simple x,y coords
    //private Building building1;
    
    // define the CityscapeComponent constructor and intiailize all instance variables
    // ...
    public CityscapeComponent(){
        //double[][] building1coords = {{500,199},{600,200},{300,300},{200,299}};
        //building1 = new Building(building1coords,-100,Color.GRAY);

        this.setDoubleBuffered(true);
        this.autoGenerateBuildings(1234);
        
        double[][] boundingBox = {{0, 0}, {2000, 0}, {2000, 2000}, {0, 2000}};
        
        this.background = new Background();
        this.outside    = new OutsideGround(boundingBox,140,97,46); // placholder vals here
        this.ground     = new Ground(boundingBox[0],boundingBox[1],boundingBox[2],boundingBox[3]);
    }

    /**
    *
    * Generates a random set of buildings 
    *
    * @param setSeed the seed used for random
    *
    */
    public void autoGenerateBuildings(long setSeed){
        Random randy = new Random(setSeed);
        this.buildingList = new Building[(int)(MAX_X*2)][(int)(MAX_Y*2)];
        this.buildingListCoords = new double[(int)(MAX_X*2)][(int)(MAX_Y*2)][4][2];
        buildingShadow = new Building[(int)(MAX_X*2)][(int)(MAX_Y*2)];
        this.buildingShadowCoords = new double[(int)(MAX_X*2)][(int)(MAX_Y*2)][4][2];
        for (int i = -(int)MAX_X; i<(MAX_X); i++){
            for (int j = -(int)MAX_Y; j<(MAX_Y); j++){
                //System.out.println(i + " " + j + " " + (int)(MAX_Y*2));

                double randHeight = randy.nextDouble()*randy.nextDouble()*400+25;
                this.buildingListCoords[i+(int)MAX_X][j+(int)MAX_Y] =
                        new double[][]{{i+0.1,j+0.1},{i+0.9,j+0.1},{i+0.9,j+0.9},{i+0.1,j+0.9}};
                this.buildingShadowCoords[i+(int)MAX_X][j+(int)MAX_Y] =
                        new double[][]{{i+0.1,j+0.1},{i+0.9,j+0.1},{i+0.9,j+0.9},{i+0.1,j+0.9}};
                this.buildingList[i+(int)MAX_X][j+(int)MAX_Y] =
                        new Building(buildingListCoords[i+(int)MAX_X][j+(int)MAX_Y], -randHeight, Color.GRAY);
                this.buildingShadow[i+(int)MAX_X][j+(int)MAX_Y] =
                        new Building(buildingListCoords[i+(int)MAX_X][j+(int)MAX_Y], -randHeight,
                                Color.DARK_GRAY);
            }
        }
    }
    
    /**
     * This method is invoked by the Java Run-Time whenever the component needs to be redrawn.
     * It does not need to be invoked explicitly.
     * 
     * @param g a reference to the Graphics object used for all drawing operations
     *
     */
    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //building1.draw(g2);
        this.background.draw(g2);
        this.outside.draw(g2, ground.getLocation());
        this.ground.draw(g2, Color.BLACK);
        Building[] drawShadow = CityscapeCalculator.sortBuildingByYCoord(buildingShadow);



        Building[] drawList = CityscapeCalculator.sortBuildingByYCoord(buildingList);

        float opacity = this.background.getOpacity();
        float red = Color.GRAY.getRed();
        float green = Color.GRAY.getBlue();
        float blue = Color.GRAY.getGreen();
        Color drawColor = new Color(red/255,green/255,blue/255,opacity);

        final boolean ghostMode = false;

        for (int i =0; i<drawList.length; i++){
            drawShadow[i].draw(g2, ground.getLocation());
            drawList[i].updateColor(drawColor);
            drawList[i].draw(g2, ground.getLocation());
        }
        if (ghostMode) {

            for (Building building : drawShadow) {
                building.draw(g2, ground.getLocation());
            }



            for (Building building : drawList) {
                building.updateColor(drawColor);
                building.draw(g2, ground.getLocation());
            }

        }
        
    }
    
    /**
     * Animate the cityscape by updating the objects such that they appear to be animated when
     *      they are next drawn.
     *
     */
    public void nextFrame(int time)
    {
        // update the objects in the cityscape so they are animated
        // ...
        final double  C1 = 90;
        final double C2 = 180;
        final double C3 = 270;
        final double C4 = 0;
        final double MAJOR_AXIS = 180;
        final double MINOR_AXIS = 30;
        final double ROT_SPEED = 1;
        final double X_OFFSET = 400;
        final double Y_OFFSET = 400;

        double[] newC1 = CityscapeCalculator.calcNewCoords(C1,time,MAJOR_AXIS,MINOR_AXIS,ROT_SPEED);
        double[] newC2 = CityscapeCalculator.calcNewCoords(C2,time,MAJOR_AXIS,MINOR_AXIS,ROT_SPEED);
        double[] newC3 = CityscapeCalculator.calcNewCoords(C3,time,MAJOR_AXIS,MINOR_AXIS,ROT_SPEED);
        double[] newC4 = CityscapeCalculator.calcNewCoords(C4,time,MAJOR_AXIS,MINOR_AXIS,ROT_SPEED);
        
        double[][] multBy = CityscapeCalculator.equivalentVectors (this.MAX_X,this.MAX_Y,newC1,newC2);
        //double[][] testDouble = CityscapeCalculator.finalCoordArray(buildingListCoords[1][1],X_OFFSET,Y_OFFSET,multBy);
        /*
        System.out.println(multBy[0][0] + " " + multBy[0][1]);
        System.out.println(multBy[1][0] + " " + multBy[1][1]);
        System.out.println("time: " + time);
        System.out.println(testDouble[0][0] + " " + testDouble[0][1]);
        System.out.println(testDouble[1][0] + " " + testDouble[1][1]);
        System.out.println(testDouble[2][0] + " " + testDouble[2][1]);
        System.out.println(testDouble[3][0] + " " + testDouble[3][1]);
        */
        for (int i = 0; i<(this.MAX_X*2); i++){
            for (int j = 0; j<(this.MAX_Y*2); j++){
                this.buildingList[i][j].updateLocation(
                        CityscapeCalculator.finalCoordArray(
                                buildingListCoords[i][j],X_OFFSET,Y_OFFSET,multBy));
                this.buildingShadow[i][j].updateLocation(
                        CityscapeCalculator.finalCoordArray(
                                buildingShadowCoords[i][j],X_OFFSET,Y_OFFSET,multBy));
                //System.out.println(buildingList[i][j]);
            }
        }
        ground.updateGround(newC1,newC2,newC3,newC4, X_OFFSET,Y_OFFSET);
        outside.updateLocation(ground.getLocation());
        background.setCycle(time);
        // request that the Java Runtime repaints this component by invoking its paintComponent method
        //  do not explicitly invoke the paintComponent method
        repaint();
    }

}

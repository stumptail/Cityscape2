import java.awt.Graphics2D;
import java.awt.Color;


/**
 * Creates a building
 *
 * @author Willson Zhu
 * @version 14 October 2024
 */
public class Building {
    // the x-y cordinate needed to draw
    private double[][] baseCoords;
    private double[][] drawCoords;

    private double buildingHeight;

    // the four parrallegrams that each buildings is made of
    private Quadrilateral roof;
    private Quadrilateral face1;
    private Quadrilateral face2;

    
    private Color drawColorRoof; // color of buildings
    private Color drawColorSideLight;
    private Color drawColorSideDark;
    
    
    private boolean face = true; // tells which side iss suppposed to be dark
    private boolean[] delay = {true,true,true}; // used to make sure the epislon doesn't proc multiple times
    
    /**
     * Constructor for objects of class Building, sets the location and height
     * 
     * @param baseLocation a list of cordinates for each corner
     * @param height the height of the building
     */
    public Building(double[][] baseLocation, double height, Color buildingColor){
        //System.out.println("flag");
        
        this.drawColorRoof= buildingColor;
        this.drawColorSideLight = buildingColor.brighter();
        this.drawColorSideDark = buildingColor.darker();
        
        this.baseCoords = baseLocation;
        this.buildingHeight = height; 
        this.drawCoords = CityscapeCalculator.findCoordsOfSides(baseCoords);
        //for (int i=0; i< drawCoords.length; i++){
        //     System.out.println(drawCoords[i][0] + " " +drawCoords[i][1]);
        //}
        //updateLocation(baseLocation) ;
        double[][] roofCoords = new double[4][2];
        for (int i =0; i<4; i++){
            roofCoords[i][0] = this.baseCoords[i][0];
            roofCoords[i][1] = this.baseCoords[i][1]+this.buildingHeight;
        }
        this.roof = new Quadrilateral (roofCoords[0],roofCoords[1],roofCoords[2],roofCoords[3]);
        
        double[][] elvatedDrawCoords = new double[3][2];
        for (int i =0; i<3; i++){
            elvatedDrawCoords[i][0] = this.drawCoords[i][0];
            elvatedDrawCoords[i][1] = this.drawCoords[i][1]+this.buildingHeight;
        }
        
        this.face1 = new Quadrilateral (this.drawCoords[0],this.drawCoords[1],elvatedDrawCoords[1],elvatedDrawCoords[0]);
        this.face2 = new Quadrilateral (this.drawCoords[1],this.drawCoords[2],elvatedDrawCoords[2],elvatedDrawCoords[1]);
        
    }

    /**
     * toString method for Building
     *
     * @return prints out data of roof and faces
     */
    public String toString(){

        return "Roof: " + this.roof + "Side1: " + this.face1 + "Side2: " +this.face2;
    }
    /**
     * Draws the building
     * 
     * @param g used to draw the building
     */
    public void draw(Graphics2D g, double[][] overall){
        /*
        double[] drawColorRGB = new double[]{drawColor.getRed(),drawColor.getBlue(),drawColor.getRed()};
        double[][] twoPointsR= new double[][]{light,roof.getLocation()[3]};
        double[][] twoPoints1= new double[][]{light,face2.getLocation()[3]};
        double[][] twoPoints2= new double[][]{light,face1.getLocation()[3]};
        Color baseColor = CityscapeCalculator.tuneRGB(drawColorRGB,twoPointsR, 600,2);
        roof.draw(g, CityscapeCalculator.tuneRGB(drawColorRGB,twoPointsR, 600,2));
        face2.draw(g, CityscapeCalculator.tuneRGB(drawColorRGB,twoPoints1, 600,1.5));
        face1.draw(g, CityscapeCalculator.tuneRGB(drawColorRGB,twoPoints2, 600,1));
        */
        
       //face = 
        boolean x1 = (Math.abs(overall[0][1]-overall[1][1])<0.4);
        boolean x2 = (Math.abs(overall[2][1]-overall[3][1])<0.4);
        boolean x3 = (overall[2][1]-overall[3][1])<=0;
        boolean y1 = (Math.abs(overall[1][1]-overall[2][1])<0.4);
        boolean y2 = (Math.abs(overall[3][1]-overall[0][1])<0.4);
        boolean y3 = (overall[1][1]-overall[2][1])<=0;
        //System.out.println(y1 + " " + y2 + " " + y3);

        if (((x1&&x2&&x3)||(y1&&y2&&y3)) && 
             (this.delay[0] && this.delay[1] && this.delay[2])){
            this.face = !this.face;
            this.delay[0] = false;
        }
        else{
            this.delay[2] = this.delay[1];
            this.delay[1] = this.delay[0];
            this.delay[0] = true;
        }
        if (face){
            this.roof.draw(g, this.drawColorRoof);
            this.face1.draw(g, this.drawColorSideLight);
            this.face2.draw(g, this.drawColorSideDark);
        }
        else{
            this.roof.draw(g, this.drawColorRoof);
            this.face2.draw(g, this.drawColorSideLight);
            this.face1.draw(g, this.drawColorSideDark);
        }
        
    }
    /**
     * Updates the location of the corners of the building
     * 
     * @param baseLocation new location of the base
     */
    public void updateLocation(double[][] baseLocation){
        this.baseCoords = baseLocation;
        this.drawCoords = CityscapeCalculator.findCoordsOfSides(baseCoords);
        //for (int i=0; i< drawCoords.length; i++){
        //     System.out.println(drawCoords[i][0] + " " +drawCoords[i][1]);
        //}
       
        double[][]roofCoords = new double[4][2];
        for (int i =0; i<4; i++){
            roofCoords[i][0] = this.baseCoords[i][0];
            roofCoords[i][1] = this.baseCoords[i][1]+this.buildingHeight;
        }
        this.roof.updateCoords(roofCoords[0],roofCoords[1],roofCoords[2],roofCoords[3]);

        double[][]elvatedDrawCoords = new double[3][2];
        for (int i =0; i<3; i++){
            elvatedDrawCoords[i][0] = this.drawCoords[i][0];
            elvatedDrawCoords[i][1] = this.drawCoords[i][1]+this.buildingHeight;
        }
        
        this.face1.updateCoords(this.drawCoords[0],this.drawCoords[1],elvatedDrawCoords[1],elvatedDrawCoords[0]);
        this.face2.updateCoords(this.drawCoords[1],this.drawCoords[2],elvatedDrawCoords[2],elvatedDrawCoords[1]);
        //System.out.println(face1);
    }
    /**
     * Updates the height of the building
     * 
     * @param height the new height
     */
    public void updateHeight(double height){
        this.buildingHeight = height; 
    }
    /**
     * Changes the draw color
     *
     * @param newColor the new draw color
     */
    public void updateColor(Color newColor){
        this.drawColorRoof= newColor;
        this.drawColorSideLight = newColor.brighter();
        this.drawColorSideDark = newColor.darker();
    }
    /**
     * Changes roof color
     * 
     * @param newColor the new roof color
     */
    public void updateRoofColor(Color newColor){
        this.drawColorRoof = newColor;
    }
    /**
    * Returns the x cordinates of the building
    *
    * @return a list of x cordiates for the building
    */
    public double[] getX(){
        return new double[]{this.drawCoords[0][0],this.drawCoords[1][0],this.drawCoords[2][0],this.drawCoords[3][0]};
    }
    /**
    * Returns the y cordinates of the building
    *
    * @return a list of y cordiates for the building
    */
    public double[] getY(){
        return new double[]{this.baseCoords[0][1],this.baseCoords[1][1],this.baseCoords[2][1],this.baseCoords[3][1]};
    }
    /**
    * Returns the x,y cordinates of the building
    *
    * @return a list of x,y cordiates for the building
    */
    public double[][] getXY(){
        return new double[][]{this.baseCoords[0],this.baseCoords[1],this.baseCoords[2],this.baseCoords[3]};
    }
}


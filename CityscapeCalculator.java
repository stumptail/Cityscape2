import java.util.Arrays;
import java.util.Comparator;
import java.awt.Color;

/**
 * Does all mathematical operations for CityScape, e.g. coordinates adn shading
 *
 * @author Willson Zhu
 * @version 9 October 2024
 */
public class CityscapeCalculator{
    /*
    /**
     * Calculates the distance between two points
     * 
     * @param point1 1st point
     * @param point2 2nd point
     * @return the distance between the two points
     */
    /*
    private static double findDistance(double[] point1, double[] point2){
        double distance = Math.sqrt((point1[0]-point2[0])*(point1[0]-point2[0])+(point1[1]-point2[1])*(point1[1]-point2[1])); 
        System.out.println(distance);
        return distance;
    }
    */
    public static Color tuneRGB(double[] rgb, double scalar){
        
        double[] multiplierList;
        double multiplier = 0.1;
        Color adjusted = new Color((float)(rgb[0]*multiplier/255),(float)(rgb[1]*multiplier/255),(float)(rgb[2]*multiplier/255));
        return adjusted;
    }
    
    /**
    * Calculates what the i and j vectors in terms of x and y (assumes a four quadrant plane)
    * Note: You will still have to translate stuff to the origin, this only rescales.
    *
    * @param x the maximum value of x on the plane
    * @param y the maximum value of y on the plane (should be the same)
    * @param cornerQuad1 the x,y screen coords of the corner of the plane in quadrant 1
    * @param cornerQuad2 the x,y screen coords of the
    */
    public static double[][] equivalentVectors (double x, double y, double cornerQuad1[], double cornerQuad2[]){ //note to self: issue occurs due to how this is coded. To fix, change all references of origin to 0,0
        // vectorI is the x-Coord equivalent
        // vectorJ is the y-Coord equivalent
        double vectorIx  = (cornerQuad1[0]-cornerQuad2[0])/(2*x);
        double vectorIy  = (cornerQuad1[1]-cornerQuad2[1])/(2*y);
        double vectorJx  = (cornerQuad1[0]-x*vectorIx)/(x); 
        double vectorJy  = (cornerQuad1[1]-y*vectorIy)/(y);
        
        double[] vectorI = {vectorIx,vectorIy};
        double[] vectorJ = {vectorJx,vectorJy};
        
        double[][] vectorPair = {vectorI,vectorJ};
        
        return vectorPair;
    }
    /**
    * Calculates the final coordinates for an array of coordinates
    *
    * @param original the original coordinates
    * @param offsetX the x offset of the origin
    * @param offsetY the y offset of the origin
    * @param scalar the scaling need for the coords. 
    *
    */
    public static double[][] finalCoordArray(double[][] original, double offsetX, double offsetY, double[][] scalar){
        double[][] movedCoords = new double[original.length][2];
        for (int i=0; i<original.length; i++){
            movedCoords[i] = finalCoord(original[i], offsetX, offsetY, scalar);
        }
        
        return movedCoords; 
    }
    /**
    * Caculates the final coordinates for the base of the building 
    *
    * @param original the original coordinates
    * @param offsetX the x offset of the origin
    * @param offsetY the y offset of the origin
    * @param scalar the scaling need for the coords. 
    *
    */
    public static double[] finalCoord(double[] original, double offsetX, double offsetY, double[][] scalar){
        double[] movedCoord = new double[2];
        double[] scaled = scaleBy(original,scalar);
        
        movedCoord[0] = scaled[0]+offsetX;
        movedCoord[1] = scaled[1]+offsetY;
        
        return movedCoord;
    }
    /**
    * Changes regular xy coordinates into the ij vectors
    *
    * @param original the original xy coordinates
    * @param scalar the scale of ij coordinates
    *
    * @return returns the final scaled coordinate point for the screen
    */
    private static double[] scaleBy (double[] original, double[][] scalar){
        double[] scaled = new double[2];
        
        scaled[0] = original[0]*scalar[0][0]+original[1]*scalar[1][0]; //think over this later, brain too fried. 
        scaled[1] = original[0]*scalar[0][1]+original[1]*scalar[1][1];
        
        return scaled;
    }
    /**
    * Uses a paremtric equation to describe a circular movment on an ellipse 
    * 
    * @param theta original starting angle  
    * @param time the current amount of units of time that have passed
    * @param majorAxis controls the length of the major axis of the ellipse
    * @param minorAxis controls the length of the major axis of the ellipse
    * @param rotSpeed controls the speed of the rotation 
    * @return integer coordinates of the current location
    */
    public static double[] calcNewCoords(double theta, double time, double majorAxis, double minorAxis, double rotSpeed){

        double currentRad = Math.toRadians(theta)+Math.toRadians(time)*rotSpeed;
        
        double x=majorAxis*Math.cos(currentRad);
        double y=majorAxis*Math.sin(currentRad);
        
        double newY = toEllipse(x,y,majorAxis, minorAxis);   
        
        double[] coords = {x,newY};
        
        return coords;
    }
    /**
    * compresses the circular movment into an ellipse to simulate 3D movment 
    * 
    * @param x the current x position on the circle
    * @param y the current y position on the circle
    * @param majorAxis the length of the major axis of the ellipse
    * @param minorAxis the length of the minor axis of the ellipse
    * @return the y position of the point after being projected onto an ellipse
    */
    private static double toEllipse(double x, double y, double majorAxis, double minorAxis){ //default is 60 for minor 300 for major
        
        double newY = Math.sqrt(Math.abs(minorAxis*minorAxis*(1-x*x/(majorAxis*majorAxis))));
        
        if (y<0){
            newY = 0-newY;
        }
        return newY; 
    }
    /**
     * Takes in four point, and locates which three are closest to camera, and how they are paired
     * 
     * @param coordList the four coordinates that need to be parsed
     * @return paired coordinates
     */
    public static double[][] findCoordsOfSides(double[][] coordList){

        int lowestYIndex = 0;
        double[][] coordPairsToSort = new double[3][2];
        
        for (int i=1; i<4; i++){
            if (coordList[i][1] < coordList[lowestYIndex][1]){
                lowestYIndex = i;
            }
            else if ((coordList[i][1] == coordList[lowestYIndex][1]) && (coordList[i][1] < coordList[lowestYIndex][1])){
                lowestYIndex = i;
            }
        }
        
        int currentIndex=0;
        for (int i=0; i<4; i++){
            if (i != lowestYIndex){
                coordPairsToSort[currentIndex] = coordList[i];
                currentIndex++;
            }
        }
       // double[][] test = sortByXCoord(coordPairsToSort);
        /*
        for (int i=0; i< coordPairsToSort.length; i++){
            System.out.println(coordPairsToSort[i][0] + " " +coordPairsToSort[i][1]);
        }
        */

        // left to right
        return sortByXCoord(coordPairsToSort);
    }
    public static boolean sideHandling(double overall[][], boolean delay[], boolean face1){
        boolean face = face1;
        
        boolean x1 = (Math.abs(overall[0][1]-overall[1][1])<0.4);
        boolean x2 = (Math.abs(overall[2][1]-overall[3][1])<0.4);
        boolean x3 = (overall[2][1]-overall[3][1])<=0;
        boolean y1 = (Math.abs(overall[1][1]-overall[2][1])<0.4);
        boolean y2 = (Math.abs(overall[3][1]-overall[0][1])<0.4);
        boolean y3 = (overall[1][1]-overall[2][1])<=0;
        //System.out.println(y1 + " " + y2 + " " + y3);

        if (((x1&&x2&&x3)||(y1&&y2&&y3)) && 
             (delay[0] && delay[1] && delay[2])){
            face = !face;
            delay[0] = false;
        }
        else{
            delay[2] = delay[1];
            delay[1] = delay[0];
            delay[0] = true;
        }
        return face;
    }
    /**
    * Sorts the coordinates by their x value least to most
    * 
    * @param coordsToSort the coordinates that need to be sorted 
    *
    */
    private static double[][] sortByXCoord(double[][] coordsToSort){
        Arrays.sort(coordsToSort, new Comparator<double[]>(){
            /*
             * Idea from stack overflow
             */
            @Override  
            public int compare(double[] coord1, double[] coord2){  
                 return (int)((coord1[0]-coord2[0])*100); //*100 is to help avoid lossy conversions in comparision
            }  
        }); 
        return coordsToSort;
    }
    /**
    * Sorts buildings by their Y coord
    *
    * @param buildingList the list of buildings
    */
    public static Building[] sortBuildingByYCoord(Building[][] buildingList){
        Building[] finalBuildingList = new Building[buildingList.length*buildingList[0].length]; // assumes rectangle grid
        for (int i=0; i<buildingList.length; i++){
            for (int j=0; j<buildingList[i].length; j++){
                finalBuildingList[i*buildingList[i].length+j] = buildingList[i][j];
            }
        }
        sortBuildings(finalBuildingList);
        return finalBuildingList;
    }
    /**
     * Sorts buildings by their Y coord
     *
     * @param buildingList the list of buildings
     */
    private static void sortBuildings(Building[] buildingList){
        Arrays.sort(buildingList, new Comparator<Building>(){
            /*
             * Idea from stack overflow
             */
            @Override  
            public int compare(Building building1, Building building2){
                 double[] first = building1.getY();
                 double[] second = building2.getY();
                 Arrays.sort(first);
                 Arrays.sort(second);
                 return (int)((first[0]-second[0])*100); //*100 is to help avoid lossy conversions in comparision
            }  
        }); 
    }
}


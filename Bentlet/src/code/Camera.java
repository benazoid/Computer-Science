package code;

import code.GameObj.GameObject;
import java.awt.Point;

public class Camera {
    
    private static int size = 32;
    
    private static Point loc = new Point();
    
    //Moves the camera based on the player
    public static void moveCamera(GameObject go){
        loc = go.getLoc();
    }
    
    public static Point getLoc(){
        return loc;
    }
    
    public static int getSize(){
        return size;
    }
    
}

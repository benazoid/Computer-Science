package code;

import code.GameObj.GameObject;
import java.awt.Point;

public class Camera {
    
    private static Point loc;
    
    public static void moveCamera(GameObject go){
        loc = go.getLoc();
    }
    
    public static Point getLoc(){
        return loc;
    };
}

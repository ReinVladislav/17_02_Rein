
package Coordination;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;


public class Rotation {
    private int _Rot;
    private Rotation(int Rot)
    {
        _Rot = Rot;
    }
    
    public static Rotation Left()
    {
        return new Rotation(-1);
    }
    
    public static Rotation Right()
    {
        return new Rotation(1);
    }
    
    public int DirRotate()
    {
        return _Rot;
    }
    
    
}

public class ThreeDPosition extends Position {
    private int z;

    
    
    public ThreeDPosition(int x, int y, int z){
        super(x,y);
        this.z=z;

    }
    
    
    public int getZ(){
        return z;
    }

    public String toString(){
        return "("+getX()+","+getY()+","+z+")";
    }

    

}
// IDEA . Have a powerups interface that the player class implements.


public class Item extends Entity{
    
    private String effectID; 

    public Item(Position position, String effectID){
        super(position);
        this.effectID = effectID;
    }

    public Item(int x, int y, String itemID){
        super(x,y);
        this.effectID = itemID;
    }

    public void render(){
        System.out.print(effectID);
    }

    public String getID(){
        return effectID;
    }

}

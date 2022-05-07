public class Item extends Entity{
    
    private String itemID; 

    public Item(Position position, String itemID){
        super(position);
        this.itemID = itemID;
    }

    public Item(int x, int y, String itemID){
        super(x,y);
        this.itemID = itemID;
    }

    public void render(){
        System.out.print(itemID);
    }

}

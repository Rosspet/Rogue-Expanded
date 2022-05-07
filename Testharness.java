
import java.util.ArrayList; // import the ArrayList class
import java.util.Iterator;

public class Testharness {
    
    
    

    public static void main(String[] args) {
        
        //int numPositions = 0;
        //ArrayList<Position> myPositions = new ArrayList<Position>();

        Position position = new Position();
        System.out.println(position);
        /*
        for (int i=0; i<2; i++){
            Position pos = new Position(i,3);
            myPositions.add(pos);
            numPositions+=1;
        }
        
        Position pos1 = new Position(0,0);
        myPositions.add(pos1);

        Position pos2 = new Position(1,1);
        myPositions.add(pos2);

        ThreeDPosition pos3 = new ThreeDPosition(3,3,3);
        myPositions.add(pos3);

        printList(myPositions);

        //Object pos3_b = (Object) pos3;
        Position position = myPositions.get(2); // will stilll remove dis.
        if (position instanceof ThreeDPosition){
            System.out.println("YES"); // yay this works. can use this to check if player or monster or item in list of entities :) - SAVED we can (1) can as in store in one big asf array so as to keep track of what was added first
        }
        */
        
        
        //System.out.println(position);
        //printList(myPositions);

        // aiming to store ALL entities in an array.
        // maybe have an array for creatures and another for items. this way can go through the creaatures using remove if .health()<0
        // (confirm this) bit prrety sure monsters are rendered over items anyway.
        // No, Monsters can move into tiles with items on them. The entity shown on the map will be the entity added to the world the earliest. (and nothing said about what is added first)
        // maybe have an array list for rendering entities and another for ... -- wait!! can't have an array list for each TILE!!  ... yes we can now, see (1) we can as in we can store everything in one big asf array.
        // this is because we need to keep track of entities that are added into the world first.
        // dont be keeping everything in an arrayList for each tile. Need one big array for all entities. maybe dont even have 2D array of tiles anymore, maybe good to still have to traversible or not actually.
        // print positions in the arrayList of pos'

        //Iterator<Position> iter = myPositions.iterator();
        //while(iter.hasNext()){
        //    System.out.println(iter.next());
        //}

        // so now 2 positions in the arrayList. we want to remove the ones who have a x position of less than 
        // or equal to 0. ((like how we will remove creatures (mainly mosnters) who have health <= 0))
        //myPositions.removeIf(pos -> pos.getY()==3);
        //printList(myPositions);


    }

    public static void printList(ArrayList<Position> positionList){
        // print positions in the arrayList of pos'
        Iterator<Position> iter = positionList.iterator();
        while(iter.hasNext()){
            //System.out.println(iter.next());
            System.out.println(iter.next().getClass());
        }
    }



}

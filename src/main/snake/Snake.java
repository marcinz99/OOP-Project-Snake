package snake;
import java.util.LinkedList;

public class Snake {
    private LinkedList<Vector2d> positions;
    private Map map;
    private Direction dir;
    private Vector2d v_0_0 = new Vector2d(0,0);

    public Snake(Map map, Vector2d central, Direction dir){
        this.map = map;
        this.dir = dir;
        this.positions = new LinkedList<>();

        positions.addLast(central);
        map.occupyLocation(central);
        Vector2d back_1 = central.subtract(dir.toUnitVector());
        positions.addLast(back_1);
        map.occupyLocation(back_1);
    }

    public LinkedList<Vector2d> getPositions(){
        return positions;
    }

    public Vector2d getNextHeadPosition(){
        Vector2d head = positions.getFirst();
        return head.add(dir.toUnitVector());
    }

    public Vector2d performMove(Vector2d newHeadPosition){
        positions.addFirst(newHeadPosition);
        map.occupyLocation(newHeadPosition);
        Vector2d formerTail = positions.pollLast();
        map.freeLocation(formerTail);
        return formerTail;
    }

    public void performMoveAndLengthen(Vector2d newHeadPosition){
        positions.addFirst(newHeadPosition);
        map.occupyLocation(newHeadPosition);
    }

    public void changeDirection(Direction newDir){
        if(!newDir.toUnitVector().add(dir.toUnitVector()).equals(v_0_0)){
            dir = newDir;
        }
    }
}

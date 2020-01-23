package snake;
import java.util.Random;

public enum Direction {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    static Random generator = new Random();

    public String toString(){
        switch(this){
            case NORTH: return "Północ";
            case SOUTH: return "Południe";
            case WEST:  return "Zachód";
            case EAST:  return "Wschód";
            default:    return "¯\\_(ツ)_/¯";
        }
    }
    public Direction next(){
        switch(this){
            case NORTH: return EAST;
            case SOUTH: return WEST;
            case WEST:  return NORTH;
            case EAST:  return SOUTH;
            default:    return NORTH;
        }
    }
    public Direction previous(){
        switch(this){
            case NORTH: return WEST;
            case SOUTH: return EAST;
            case WEST:  return SOUTH;
            case EAST:  return NORTH;
            default:    return NORTH;
        }
    }
    public Vector2d toUnitVector(){
        switch(this){
            case NORTH: return new Vector2d(0, 1);
            case SOUTH: return new Vector2d(0, -1);
            case WEST:  return new Vector2d(-1, 0);
            case EAST:  return new Vector2d(1, 0);
            default:    return new Vector2d(0, 0);
        }
    }
    public static Direction randomDirection(){
        int r = generator.nextInt(4);
        switch(r){
            case 0:  return NORTH;
            case 1:  return EAST;
            case 2:  return SOUTH;
            case 3:  return WEST;
            default: return NORTH;
        }
    }
}

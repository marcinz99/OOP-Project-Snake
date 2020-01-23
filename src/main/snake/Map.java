package snake;
import java.util.ArrayList;
import java.util.LinkedList;

public class Map {
    private Game game;
    private int boardWidth;
    private int boardHeight;
    private Snake snake;
    private boolean mapFolding;
    private FreeSpaces free;
    private Vector2d vBottomLeft;
    private Vector2d vUpperRight;
    private Vector2d applePosition;

    private Vector2d v_0_1 = new Vector2d(0,1);
    private Vector2d v_0n1 = new Vector2d(0,-1);
    private Vector2d v_1_0 = new Vector2d(1,0);
    private Vector2d vn1_0 = new Vector2d(-1,0);

    public Map(Game game, int width, int height, int mapId, boolean mapFolding){
        this.game = game;
        this.boardWidth = width;
        this.boardHeight = height;
        this.mapFolding = mapFolding;

        vBottomLeft = new Vector2d(0,0);
        vUpperRight = new Vector2d(width-1,height-1);

        this.free = new FreeSpaces(height, 0);
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                free.add(new Vector2d(i, j));
            }
        }

        if(mapId == 0) prepareMapId0();
        if(mapId == 1) prepareMapId1();
        if(mapId == 2) prepareMapId2();

        spawnApple();
        spawnSnake();
    }

    private void prepareMapId0(){

    }

    private void prepareMapId1(){
        ArrayList<String> map1 = FileHandler.readMap(1);
        if(map1 == null) return;

        for(int i=0; i<30; i++){
            String line = map1.get(i);
            for(int j=0; j<30; j++){
                if(line.charAt(j) == '1'){
                    Vector2d vec = new Vector2d(i, j);
                    game.wallHere(vec);
                    free.delete(vec);
                }
            }
        }
    }

    private void prepareMapId2(){
        ArrayList<String> map1 = FileHandler.readMap(2);
        if(map1 == null) return;

        for(int i=0; i<30; i++){
            String line = map1.get(i);
            for(int j=0; j<30; j++){
                if(line.charAt(j) == '1'){
                    Vector2d vec = new Vector2d(i, j);
                    game.wallHere(vec);
                    free.delete(vec);
                }
            }
        }
    }

    private void spawnSnake(){
        Vector2d central;
        Direction dir;
        Vector2d unit;

        while(true){
            central = free.getRandomPosition();
            dir = Direction.randomDirection();
            unit = dir.toUnitVector();
            Vector2d back_1 = central.subtract(unit);
            Vector2d forward_1 = central.add(unit);
            Vector2d forward_2 = forward_1.add(unit);
            if(back_1.follows(vBottomLeft) && back_1.precedes(vUpperRight) && free.isFree(back_1)) {
                if (forward_1.follows(vBottomLeft) && forward_1.precedes(vUpperRight) && free.isFree(forward_1)) {
                    if (forward_2.follows(vBottomLeft) && forward_2.precedes(vUpperRight) && free.isFree(forward_2)) {
                        break;
                    }
                }
            }
        }
        snake = new Snake(this, central, dir);

        drawSnake();
    }

    private void drawSnake(){
        LinkedList<Vector2d> positions = snake.getPositions();
        Vector2d[] pos = new Vector2d[positions.size()];
        int[] img = new int[positions.size()];
        int len = positions.size();

        int j = 0;
        for(Vector2d vec : positions){
            pos[j++] = vec;
        }

        Vector2d head = pos[0].subtract(pos[1]);
        head = prepareVector(head);
        if(head.x == 0 && head.y == -1) img[0] = 10;
        else if(head.x == 1 && head.y == 0) img[0] = 11;
        else if(head.x == 0 && head.y == 1) img[0] = 12;
        else img[0] = 13;

        Vector2d tail = pos[len-2].subtract(pos[len-1]);
        tail = prepareVector(tail);
        if(tail.x == 0 && tail.y == -1) img[len-1] = 40;
        else if(tail.x == 1 && tail.y == 0) img[len-1] = 41;
        else if(tail.x == 0 && tail.y == 1) img[len-1] = 42;
        else img[len-1] = 43;

        for(int i=1; i<len-1; i++){
            Vector2d front = pos[i-1].subtract(pos[i]);
            front = prepareVector(front);
            Vector2d back = pos[i+1].subtract(pos[i]);
            back = prepareVector(back);

            if(front.equals(v_0n1) && back.equals(v_0_1)) img[i] = 20;
            else if(front.equals(v_1_0) && back.equals(vn1_0)) img[i] = 21;
            else if(front.equals(v_0_1) && back.equals(v_0n1)) img[i] = 22;
            else if(front.equals(vn1_0) && back.equals(v_1_0)) img[i] = 23;
            else if(front.equals(v_0n1) && back.equals(v_1_0)) img[i] = 30;
            else if(front.equals(v_1_0) && back.equals(v_0n1)) img[i] = 30;
            else if(front.equals(v_1_0) && back.equals(v_0_1)) img[i] = 31;
            else if(front.equals(v_0_1) && back.equals(v_1_0)) img[i] = 31;
            else if(front.equals(vn1_0) && back.equals(v_0_1)) img[i] = 32;
            else if(front.equals(v_0_1) && back.equals(vn1_0)) img[i] = 32;
            else img[i] = 33;
        }

        game.drawSnake(pos, img);
    }

    private Vector2d prepareVector(Vector2d vec){
        if(vec.x > 1) vec = new Vector2d(vec.x-boardWidth, vec.y);
        if(vec.x < -1) vec = new Vector2d(vec.x+boardWidth, vec.y);
        if(vec.y > 1) vec = new Vector2d(vec.x, vec.y-boardHeight);
        if(vec.y < -1) vec = new Vector2d(vec.x, vec.y+boardHeight);
        return vec;
    }

    private void spawnApple(){
        Vector2d spawnPoint = free.getRandomPosition();
        free.delete(spawnPoint);
        game.appleSpawned(spawnPoint);
        applePosition = spawnPoint;
    }

    private void spawnObstacle(){
        Vector2d spawnPoint = free.getRandomPosition();
        free.delete(spawnPoint);
        game.obstacleSpawned(spawnPoint);
    }

    public void occupyLocation(Vector2d position){
        free.delete(position);
    }

    public void freeLocation(Vector2d position){
        free.add(position);
    }

    public MoveResult performMove(){
        Vector2d moveTo = snake.getNextHeadPosition();

        if(!(moveTo.follows(vBottomLeft) && moveTo.precedes(vUpperRight))){
            if(mapFolding) moveTo = moveTo.normalizePosition(boardWidth, boardHeight);
            else return MoveResult.DIE;
        }
        if(free.isFree(moveTo)){
            game.somethingDisappeared(snake.performMove(moveTo));
            drawSnake();
            return MoveResult.MOVE;
        }
        else if(moveTo.equals(applePosition)){
            snake.performMoveAndLengthen(moveTo);
            drawSnake();
            spawnApple();
            return MoveResult.EAT;
        }
        else{
            return MoveResult.DIE;
        }
    }

    public void changeSnakeMoveDirection(Direction newDir){
        snake.changeDirection(newDir);
    }

    public void spawnObstacles(int numOfObstacles){
        for(int i=0; i<numOfObstacles; i++){
            spawnObstacle();
        }
    }
}

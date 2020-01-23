package snake;

public class Game {
    private int boardWidth;
    private int boardHeight;
    private int mapId;
    private int diffId;
    private int highScore;
    private int score = 0;
    private int frameDelay;
    private boolean isRunning = false;
    private boolean canChangeDir = true;
    private GameWindow gWnd;
    private Map map;

    public Game(GameWindow gWnd, int width, int height, int mapId, int diffId, int highScore, boolean mapFold){
        this.gWnd = gWnd;
        this.boardWidth = width;
        this.boardHeight = height;
        this.mapId = mapId;
        this.diffId = diffId;
        this.highScore = highScore;
        this.map = new Map(this, width, height, mapId, mapFold);

        switch(diffId){
            case 0:
                this.frameDelay = 220;
                break;
            case 1:
                this.frameDelay = 190;
                this.map.spawnObstacles(5);
                break;
            default:
                this.frameDelay = 160;
                this.map.spawnObstacles(10);
                break;
        }
    }

    public void startStopProceeding(){
        isRunning = !isRunning;
        if(isRunning){
            Thread runTheSim = new Thread(this::playSimulation);
            runTheSim.start();
        }
    }

    public void playSimulation(){
        wait(15);
        while(isRunning){
            canChangeDir = true;
            proceedSingleMove();
            wait(frameDelay);
            gWnd.requestFocus();
        }
    }

    private void wait(int miliseconds){
        try{
            Thread.sleep(miliseconds);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void proceedSingleMove(){
        MoveResult result = map.performMove();
        switch(result){
            case EAT:
                score += 10;
                GameWindow.updateScore(gWnd, score);
                break;
            case DIE:
                isRunning = false;
                GameWindow.endTheGame(gWnd, score, highScore);
                break;
            default: break;
        }
    }

    public void appleSpawned(Vector2d position){
        GameWindow.appleSpawned(gWnd, position);
    }

    public void obstacleSpawned(Vector2d position){
        GameWindow.obstacleSpawned(gWnd, position);
    }

    public void drawSnake(Vector2d[] pos, int[] img){
        GameWindow.drawSnake(gWnd, pos, img);
    }

    public void somethingDisappeared(Vector2d position){
        GameWindow.somethingDisappeared(gWnd, position);
    }

    public void wallHere(Vector2d position){
        GameWindow.wallHere(gWnd, position);
    }

    public void changeSnakeMoveDirection(Direction newDir){
        if(canChangeDir){
            map.changeSnakeMoveDirection(newDir);
            canChangeDir = false;
        }
    }
}

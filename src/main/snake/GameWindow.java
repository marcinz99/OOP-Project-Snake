package snake;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame {
    private static Color c_info = new Color(232, 240, 223);
    private static Color c_area = new Color(115, 157, 52);
    private static Color c_info_dark = new Color(198, 232, 183);
    private static ImageIcon i_sand;
    private static ImageIcon i_lava;
    private static ImageIcon i_apple;
    private static ImageIcon i_cactus;
    private static ImageIcon[] i_snake_1;
    private static ImageIcon[] i_snake_2;
    private static ImageIcon[] i_snake_3;
    private static ImageIcon[] i_snake_4;
    private static boolean graphicAlreadyLoaded = false;
    private static SettingsWindow sWnd;
    private int unitDim = 25;
    private int boardWidth;
    private int boardHeight;
    private int windowWidth;
    private int windowHeight;
    private int mapId;
    private int diffId;
    private JButton[] buttons;
    private JLabel[] labels;
    private JButton[][] cells;
    private boolean isOn = false;
    private boolean isOver = false;

    public static void runTheGameWindow(SettingsWindow sWnd, int width, int height,
                                        int mapId, int diffId, int highScore, boolean mapFold){
        GameWindow gWnd = new GameWindow(sWnd, width, height);
        gWnd.mapId = mapId;
        gWnd.diffId = diffId;
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        gWnd.add(panel);
        addComponents(gWnd, panel);
        gWnd.setVisible(true);

        Game game = new Game(gWnd, width, height, mapId, diffId, highScore, mapFold);
        addActionListeners(gWnd, sWnd, game);
        gWnd.requestFocus();
        GameWindow.updateScore(gWnd, 0);
        GameWindow.updateHighScore(gWnd, highScore);
    }

    private GameWindow(SettingsWindow sWnd, int width, int height){
        super("Snake");
        this.boardWidth = width;
        this.boardHeight = height;
        this.windowWidth = Math.max(unitDim * width + 40, 300);
        this.windowHeight = unitDim * height + 200;
        this.sWnd = sWnd;
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        if(!graphicAlreadyLoaded){
            loadGraphics(this);
            graphicAlreadyLoaded = true;
        }

        setSize(this.windowWidth, this.windowHeight);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dimension.width - this.windowWidth)/2, (dimension.height - this.windowHeight)/2);

        Border emptyBorder = BorderFactory.createEmptyBorder();
        Dimension dims = new Dimension(this.unitDim, this.unitDim);

        this.cells = new JButton[this.boardWidth][this.boardHeight];
        for(int i=0; i<this.boardWidth; i++){
            for(int j=0; j<this.boardHeight; j++){
                cells[i][j] = new JButton();
                cells[i][j].setPreferredSize(dims);
                cells[i][j].setBorder(emptyBorder);
                cells[i][j].setIcon(i_sand);
            }
        }

        Border whiteline = BorderFactory.createLineBorder(Color.white);

        buttons = new JButton[2];
        for(int i=0; i<2; i++){
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Consolas", Font.PLAIN, 22));
            buttons[i].setBorder(whiteline);
            buttons[i].setBackground(c_info_dark);
            buttons[i].setHorizontalAlignment(JLabel.CENTER);
            buttons[i].setVerticalAlignment(JLabel.CENTER);
        }
        buttons[0].setPreferredSize(new Dimension(5*this.unitDim, this.unitDim+10));
        buttons[1].setPreferredSize(new Dimension(4*this.unitDim, this.unitDim+10));

        labels = new JLabel[5];
        for(int i=0; i<5; i++) {
            labels[i] = new JLabel();
            labels[i].setFont(new Font("Consolas", Font.PLAIN, 18));
            labels[i].setBorder(whiteline);
            labels[i].setOpaque(true);
            labels[i].setBackground(c_info);
            labels[i].setHorizontalAlignment(JLabel.CENTER);
            labels[i].setVerticalAlignment(JLabel.CENTER);
        }
        labels[2].setFont(new Font("Consolas", Font.PLAIN, 22));
        labels[4].setFont(new Font("Consolas", Font.PLAIN, 22));

        labels[0].setPreferredSize(new Dimension((this.boardWidth-9)*this.unitDim, this.unitDim+10));
        labels[1].setPreferredSize(new Dimension(5*this.unitDim, this.unitDim+10));
        labels[2].setPreferredSize(new Dimension((this.boardWidth-5)*this.unitDim, this.unitDim+10));
        labels[3].setPreferredSize(new Dimension(5*this.unitDim, this.unitDim+10));
        labels[4].setPreferredSize(new Dimension((this.boardWidth-5)*this.unitDim, this.unitDim+10));
    }

    private static void loadGraphics(GameWindow gWnd){
        i_sand = GameWindow.loadImage(gWnd, "sand");
        i_lava = GameWindow.loadImage(gWnd, "bricks");
        i_apple = GameWindow.loadImage(gWnd, "apple");
        i_cactus = GameWindow.loadImage(gWnd, "cactus");

        String name = "snake_1_";
        i_snake_1 = new ImageIcon[4];
        for(int i=0; i<4; i++){
            i_snake_1[i] = GameWindow.loadImage(gWnd, name+String.valueOf(i));
        }

        name = "snake_2_";
        i_snake_2 = new ImageIcon[4];
        for(int i=0; i<4; i++){
            i_snake_2[i] = GameWindow.loadImage(gWnd, name+String.valueOf(i));
        }

        name = "snake_3_";
        i_snake_3 = new ImageIcon[4];
        for(int i=0; i<4; i++){
            i_snake_3[i] = GameWindow.loadImage(gWnd, name+String.valueOf(i));
        }

        name = "snake_4_";
        i_snake_4 = new ImageIcon[4];
        for(int i=0; i<4; i++){
            i_snake_4[i] = GameWindow.loadImage(gWnd, name+String.valueOf(i));
        }
    }

    private static void addComponents(GameWindow gWnd, JPanel panel){
        JPanel controls = new JPanel(new GridBagLayout());
        panel.add(controls);
        JPanel scene = new JPanel(new GridBagLayout());
        panel.add(scene);
        controls.setBackground(c_area);
        scene.setBackground(c_area);

        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;

        gWnd.buttons[0].setText("<html>START</html>");
        gWnd.buttons[1].setText("<html>QUIT</html>");
        gWnd.labels[0].setText("<html></html>");
        gWnd.labels[1].setText("<html>Points:</html>");
        gWnd.labels[2].setText("<html>0</html>");
        gWnd.labels[3].setText("<html>High score:</html>");
        gWnd.labels[4].setText("<html>0</html>");

        layoutConstraints.gridy = 0;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;

        layoutConstraints.gridx = 0;
        controls.add(gWnd.buttons[0], layoutConstraints);

        layoutConstraints.gridx = 1;
        controls.add(gWnd.labels[0], layoutConstraints);

        layoutConstraints.gridx = 2;
        controls.add(gWnd.buttons[1], layoutConstraints);

        for(int i=0; i<2; i++){
            layoutConstraints.gridy = 1+i;

            layoutConstraints.gridx = 0;
            layoutConstraints.gridwidth = 1;
            controls.add(gWnd.labels[2*i+1], layoutConstraints);

            layoutConstraints.gridx = 1;
            layoutConstraints.gridwidth = 2;
            controls.add(gWnd.labels[2*i+2], layoutConstraints);
        }

        layoutConstraints = new GridBagConstraints();

        for(int i=0; i<gWnd.boardWidth; i++){
            for(int j=0; j<gWnd.boardHeight; j++){
                layoutConstraints.gridx = i;
                layoutConstraints.gridy = j;
                layoutConstraints.gridwidth = 1;
                layoutConstraints.gridheight = 1;
                scene.add(gWnd.cells[i][j], layoutConstraints);
            }
        }
    }

    private static void addActionListeners(GameWindow gWnd, SettingsWindow sWnd, Game game) {
        gWnd.buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gWnd.isOver){
                    gWnd.buttons[0].setText("<html></html>");
                    return;
                }
                if(gWnd.isOn){
                    gWnd.buttons[0].setText("<html>RESUME</html>");
                    gWnd.isOn = !gWnd.isOn;
                    game.startStopProceeding();
                }
                else{
                    gWnd.buttons[0].setText("<html>PAUSE</html>");
                    gWnd.isOn = !gWnd.isOn;
                    game.startStopProceeding();
                }
                gWnd.requestFocus();
            }
        });
        gWnd.buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gWnd.isOn){
                    gWnd.buttons[0].setText("<html>RESUME</html>");
                    gWnd.isOn = !gWnd.isOn;
                    game.startStopProceeding();
                }
                sWnd.setVisible(true);
                gWnd.dispose();
            }
        });
        gWnd.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int c = keyEvent.getKeyCode();
                switch(c){
                    case 32:
                        if(gWnd.isOver) break;
                        if(gWnd.isOn){
                            gWnd.buttons[0].setText("<html>RESUME</html>");
                            gWnd.isOn = !gWnd.isOn;
                            game.startStopProceeding();
                        }
                        else{
                            gWnd.buttons[0].setText("<html>PAUSE</html>");
                            gWnd.isOn = !gWnd.isOn;
                            game.startStopProceeding();
                        }
                        gWnd.requestFocus();
                        break;
                    case 37: game.changeSnakeMoveDirection(Direction.WEST); break;
                    case 38: game.changeSnakeMoveDirection(Direction.SOUTH); break;
                    case 39: game.changeSnakeMoveDirection(Direction.EAST); break;
                    case 40: game.changeSnakeMoveDirection(Direction.NORTH); break;
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }

    private static ImageIcon loadImage(GameWindow gWnd, String name){
        StringBuilder str = new StringBuilder();
        str.append("src\\img\\");
        str.append(name);
        str.append(".png");
        ImageIcon img = new ImageIcon(str.toString());
        return new ImageIcon(img.getImage().getScaledInstance(gWnd.unitDim, gWnd.unitDim, Image.SCALE_SMOOTH));
    }

    public static void appleSpawned(GameWindow gWnd, Vector2d position){
        gWnd.cells[position.x][position.y].setIcon(i_apple);
    }

    public static void obstacleSpawned(GameWindow gWnd, Vector2d position){
        gWnd.cells[position.x][position.y].setIcon(i_cactus);
    }

    public static void somethingDisappeared(GameWindow gWnd, Vector2d position){
        gWnd.cells[position.x][position.y].setIcon(i_sand);
    }

    public static void wallHere(GameWindow gWnd, Vector2d position){
        gWnd.cells[position.x][position.y].setIcon(i_lava);
    }

    public static void drawSnake(GameWindow gWnd, Vector2d[] pos, int[] img){
        for(int i=0; i<pos.length; i++){
            int a = img[i]/10;
            int b = img[i]%10;
            if(a == 1) gWnd.cells[pos[i].x][pos[i].y].setIcon(i_snake_1[b]);
            if(a == 2) gWnd.cells[pos[i].x][pos[i].y].setIcon(i_snake_2[b]);
            if(a == 3) gWnd.cells[pos[i].x][pos[i].y].setIcon(i_snake_3[b]);
            if(a == 4) gWnd.cells[pos[i].x][pos[i].y].setIcon(i_snake_4[b]);
        }
    }

    public static void updateScore(GameWindow gWnd, int score){
        gWnd.labels[2].setText(String.format("<html>%d</html>", score));
    }

    public static void updateHighScore(GameWindow gWnd, int highScore){
        gWnd.labels[4].setText(String.format("<html>%d</html>", highScore));
    }

    public static void endTheGame(GameWindow gWnd, int score, int highScore){
        gWnd.isOver = true;
        InfoWindow.showInfoWindow(GameWindow.sWnd, gWnd, score, highScore);
    }

    public static int getMapId(GameWindow gWnd){
        return gWnd.mapId;
    }

    public static int getDiffId(GameWindow gWnd){
        return gWnd.diffId;
    }
}

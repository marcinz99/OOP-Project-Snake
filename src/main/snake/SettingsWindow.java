package snake;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SettingsWindow extends JFrame {
    private static Color c_area = new Color(115, 157, 52);
    private static Color c_info = new Color(232, 240, 223);
    private static Color c_info_light = new Color(238, 246, 232);
    private static Color c_info_dark = new Color(198, 232, 183);
    private static Color c_info_inactive = new Color(193, 203, 191, 255);
    private JLabel[] labels;
    private JButton[] buttons;
    private JTextField[] inputs;
    private int unitWidth = 52;
    private int unitHeight = 40;
    private ArrayList<Integer> scores;

    private int mapId = 0;
    private int diffId = 0;
    private boolean mapFold = false;

    public static void showSettingsWindow() throws FileNotFoundException {
        SettingsWindow sWnd = new SettingsWindow();
        JPanel panel = new JPanel();
        sWnd.add(panel);
        addComponents(sWnd, panel);
        sWnd.setVisible(true);
        addActionListeners(sWnd);
        importHighScores(sWnd);
    }
    private SettingsWindow(){
        super("Snake");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        int width = 660;
        int height = 750;
        setSize(width, height);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dimension.width - width)/2, (dimension.height - height)/2);

        Border whiteline = BorderFactory.createLineBorder(Color.white);

        this.labels = new JLabel[35];
        for(int i=0; i<35; i++) {
            labels[i] = new JLabel();
            labels[i].setFont(new Font("Consolas", Font.PLAIN, 22));
            labels[i].setBorder(whiteline);
            labels[i].setOpaque(true);
            labels[i].setBackground(c_info);
            labels[i].setHorizontalAlignment(JLabel.CENTER);
            labels[i].setVerticalAlignment(JLabel.CENTER);
        }
        for(int i=27; i<32; i++){
            labels[i].setBackground(c_area);
            labels[i].setBorder(null);
        }
        labels[0].setBackground(c_area);
        labels[0].setBorder(null);
        for(int i=33; i<35; i++){
            labels[i].setBackground(c_area);
            labels[i].setBorder(null);
        }

        labels[0].setPreferredSize(new Dimension(3*this.unitWidth, this.unitHeight/2));
        for(int i=1; i<7; i++)
            labels[i].setPreferredSize(new Dimension(3*this.unitWidth, 1*this.unitHeight));
        labels[7].setPreferredSize(new Dimension(7*this.unitWidth, 1*this.unitHeight));
        labels[8].setPreferredSize(new Dimension(7*this.unitWidth, 1*this.unitHeight));
        labels[9].setPreferredSize(new Dimension(9*this.unitWidth, 1*this.unitHeight));
        labels[10].setPreferredSize(new Dimension(12*this.unitWidth, 1*this.unitHeight));
        for(int i=11; i<27; i++)
            labels[i].setPreferredSize(new Dimension(3*this.unitWidth, 1*this.unitHeight));
        labels[27].setPreferredSize(new Dimension(1*this.unitWidth, this.unitHeight/2));
        labels[28].setPreferredSize(new Dimension(2*this.unitWidth, this.unitHeight/2));
        labels[29].setPreferredSize(new Dimension(3*this.unitWidth, this.unitHeight/2));
        labels[30].setPreferredSize(new Dimension(2*this.unitWidth, this.unitHeight/2));
        labels[31].setPreferredSize(new Dimension(1*this.unitWidth, this.unitHeight/2));

        labels[32].setPreferredSize(new Dimension(12*this.unitWidth, 2*this.unitHeight));
        labels[32].setFont(new Font("Consolas", Font.PLAIN, 28));
        labels[9].setFont(new Font("Consolas", Font.PLAIN, 16));

        for(int i=33; i<35; i++)
            labels[i].setPreferredSize(new Dimension(12*this.unitWidth, this.unitHeight/2));

        this.inputs = new JTextField[2];
        inputs[0] = new JTextField(String.valueOf(20));
        inputs[1] = new JTextField(String.valueOf(20));
        for(int i=0; i<2; i++){
            inputs[i].setFont(new Font("Consolas", Font.PLAIN, 22));
            inputs[i].setPreferredSize(new Dimension(1*this.unitWidth, 1*this.unitHeight));
            inputs[i].setHorizontalAlignment(JTextField.CENTER);
            inputs[i].setBorder(whiteline);
            inputs[i].setOpaque(true);
            inputs[i].setBackground(c_info_light);
        }

        this.buttons = new JButton[7];
        for(int i=0; i<7; i++){
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Consolas", Font.PLAIN, 22));
            buttons[i].setBorder(whiteline);
            buttons[i].setBackground(c_info_dark);
            buttons[i].setHorizontalAlignment(JLabel.CENTER);
            buttons[i].setVerticalAlignment(JLabel.CENTER);
        }
        for(int i=0; i<2; i++)
            buttons[i].setPreferredSize(new Dimension(1*this.unitWidth, 1*this.unitHeight));
        buttons[2].setPreferredSize(new Dimension(9*this.unitWidth, 1*this.unitHeight));
        for(int i=3; i<5; i++)
            buttons[i].setPreferredSize(new Dimension(1*this.unitWidth, 1*this.unitHeight));
        buttons[5].setPreferredSize(new Dimension(12*this.unitWidth, 1*this.unitHeight));
        buttons[6].setPreferredSize(new Dimension(12*this.unitWidth, 1*this.unitHeight));
    }

    private static void addComponents(SettingsWindow sWnd, JPanel panel){
        panel.setLayout(new GridBagLayout());
        panel.setBackground(c_area);
        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        layoutConstraints.gridwidth = 6;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[32], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[0], layoutConstraints);

        for(int i=27; i<32; i++){
            layoutConstraints.gridx = i-26;
            panel.add(sWnd.labels[i], layoutConstraints);
        }

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 2;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[1], layoutConstraints);

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 2;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.buttons[0], layoutConstraints);

        layoutConstraints.gridx = 2;
        layoutConstraints.gridy = 2;
        layoutConstraints.gridwidth = 3;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[7], layoutConstraints);

        layoutConstraints.gridx = 5;
        layoutConstraints.gridy = 2;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.buttons[1], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 3;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[2], layoutConstraints);

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 3;
        layoutConstraints.gridwidth = 5;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.inputs[0], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 4;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[3], layoutConstraints);

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 4;
        layoutConstraints.gridwidth = 5;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.inputs[1], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 5;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[4], layoutConstraints);

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 5;
        layoutConstraints.gridwidth = 5;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.buttons[2], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 6;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[5], layoutConstraints);

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 6;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.buttons[3], layoutConstraints);

        layoutConstraints.gridx = 2;
        layoutConstraints.gridy = 6;
        layoutConstraints.gridwidth = 3;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[8], layoutConstraints);

        layoutConstraints.gridx = 5;
        layoutConstraints.gridy = 6;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.buttons[4], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 7;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[6], layoutConstraints);

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 7;
        layoutConstraints.gridwidth = 5;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[9], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 8;
        layoutConstraints.gridwidth = 6;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.buttons[5], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 9;
        layoutConstraints.gridwidth = 6;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[33], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 10;
        layoutConstraints.gridwidth = 6;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[10], layoutConstraints);

        for(int i=0; i<4; i++){
            layoutConstraints.gridx = 0;
            layoutConstraints.gridy = 11+i;
            layoutConstraints.gridwidth = 1;
            layoutConstraints.gridheight = 1;
            panel.add(sWnd.labels[11+4*i], layoutConstraints);

            layoutConstraints.gridx = 1;
            layoutConstraints.gridy = 11+i;
            layoutConstraints.gridwidth = 2;
            layoutConstraints.gridheight = 1;
            panel.add(sWnd.labels[12+4*i], layoutConstraints);

            layoutConstraints.gridx = 3;
            layoutConstraints.gridy = 11+i;
            layoutConstraints.gridwidth = 1;
            layoutConstraints.gridheight = 1;
            panel.add(sWnd.labels[13+4*i], layoutConstraints);

            layoutConstraints.gridx = 4;
            layoutConstraints.gridy = 11+i;
            layoutConstraints.gridwidth = 2;
            layoutConstraints.gridheight = 1;
            panel.add(sWnd.labels[14+4*i], layoutConstraints);
        }

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 15;
        layoutConstraints.gridwidth = 6;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.labels[34], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 16;
        layoutConstraints.gridwidth = 6;
        layoutConstraints.gridheight = 1;
        panel.add(sWnd.buttons[6], layoutConstraints);

        sWnd.labels[0].setText("<html></html>");
        sWnd.labels[1].setText("<html>Map</html>");
        sWnd.labels[2].setText("<html>Width</html>");
        sWnd.labels[3].setText("<html>Height</html>");
        sWnd.labels[4].setText("<html>Map folding</html>");
        sWnd.labels[5].setText("<html>Difficulty</html>");
        sWnd.labels[6].setText("<html>Info</html>");
        sWnd.labels[7].setText("<html>Casual</html>");
        sWnd.labels[8].setText("<html>EASY</html>");
        sWnd.labels[9].setText("<html>No obstacles &amp; regular tempo</html>");
        sWnd.labels[10].setText("<html>HIGH SCORES</html>");
        sWnd.labels[11].setText("<html>Map</html>");
        sWnd.labels[12].setText("<html>Casual</html>");
        sWnd.labels[13].setText("<html>Map 1</html>");
        sWnd.labels[14].setText("<html>Map 2</html>");
        sWnd.labels[15].setText("<html>EASY</html>");
        sWnd.labels[16].setText("<html>0</html>");
        sWnd.labels[17].setText("<html>0</html>");
        sWnd.labels[18].setText("<html>0</html>");
        sWnd.labels[19].setText("<html>MEDIUM</html>");
        sWnd.labels[20].setText("<html>0</html>");
        sWnd.labels[21].setText("<html>0</html>");
        sWnd.labels[22].setText("<html>0</html>");
        sWnd.labels[23].setText("<html>HARD</html>");
        sWnd.labels[24].setText("<html>0</html>");
        sWnd.labels[25].setText("<html>0</html>");
        sWnd.labels[26].setText("<html>0</html>");
        sWnd.labels[27].setText("<html></html>");
        sWnd.labels[28].setText("<html></html>");
        sWnd.labels[29].setText("<html></html>");
        sWnd.labels[30].setText("<html></html>");
        sWnd.labels[31].setText("<html></html>");
        sWnd.labels[32].setText("<html>S N A K E</html>");
        sWnd.labels[33].setText("<html></html>");
        sWnd.labels[34].setText("<html></html>");

        sWnd.buttons[0].setText("<html>&lt;</html>");
        sWnd.buttons[1].setText("<html>&gt;</html>");
        sWnd.buttons[2].setText("<html>Disabled</html>");
        sWnd.buttons[3].setText("<html>&lt;</html>");
        sWnd.buttons[4].setText("<html>&gt;</html>");
        sWnd.buttons[5].setText("<html>PLAY</html>");
        sWnd.buttons[6].setText("<html>EXIT</html>");
    }

    private static void addActionListeners(SettingsWindow sWnd) {
        sWnd.buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sWnd.mapId = (sWnd.mapId + 2) % 3;
                if(sWnd.mapId == 0){
                    sWnd.labels[7].setText("<html>Casual</html>");
                    sWnd.inputs[0].setBackground(c_info_light);
                    sWnd.inputs[0].setEditable(true);
                    sWnd.inputs[1].setBackground(c_info_light);
                    sWnd.inputs[1].setEditable(true);
                }
                if(sWnd.mapId == 1){
                    sWnd.labels[7].setText("<html>Map 1</html>");
                    sWnd.inputs[0].setBackground(c_info_inactive);
                    sWnd.inputs[0].setText("30");
                    sWnd.inputs[0].setEditable(false);
                    sWnd.inputs[1].setBackground(c_info_inactive);
                    sWnd.inputs[1].setText("30");
                    sWnd.inputs[1].setEditable(false);
                }
                if(sWnd.mapId == 2){
                    sWnd.labels[7].setText("<html>Map 2</html>");
                    sWnd.inputs[0].setBackground(c_info_inactive);
                    sWnd.inputs[0].setText("30");
                    sWnd.inputs[0].setEditable(false);
                    sWnd.inputs[1].setBackground(c_info_inactive);
                    sWnd.inputs[1].setText("30");
                    sWnd.inputs[1].setEditable(false);
                }
            }
        });
        sWnd.buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sWnd.mapId = (sWnd.mapId + 1) % 3;
                if(sWnd.mapId == 0){
                    sWnd.labels[7].setText("<html>Casual</html>");
                    sWnd.inputs[0].setBackground(c_info_light);
                    sWnd.inputs[0].setEditable(true);
                    sWnd.inputs[1].setBackground(c_info_light);
                    sWnd.inputs[1].setEditable(true);
                }
                if(sWnd.mapId == 1){
                    sWnd.labels[7].setText("<html>Map 1</html>");
                    sWnd.inputs[0].setBackground(c_info_inactive);
                    sWnd.inputs[0].setText("30");
                    sWnd.inputs[0].setEditable(false);
                    sWnd.inputs[1].setBackground(c_info_inactive);
                    sWnd.inputs[1].setText("30");
                    sWnd.inputs[1].setEditable(false);
                }
                if(sWnd.mapId == 2){
                    sWnd.labels[7].setText("<html>Map 2</html>");
                    sWnd.inputs[0].setBackground(c_info_inactive);
                    sWnd.inputs[0].setText("30");
                    sWnd.inputs[0].setEditable(false);
                    sWnd.inputs[1].setBackground(c_info_inactive);
                    sWnd.inputs[1].setText("30");
                    sWnd.inputs[1].setEditable(false);
                }
            }
        });
        sWnd.buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sWnd.mapFold = !sWnd.mapFold;
                if(sWnd.mapFold) sWnd.buttons[2].setText("<html>Enabled</html>");
                else sWnd.buttons[2].setText("<html>Disabled</html>");
            }
        });
        sWnd.buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sWnd.diffId = (sWnd.diffId + 2) % 3;
                if(sWnd.diffId == 0){
                    sWnd.labels[8].setText("<html>EASY</html>");
                    sWnd.labels[9].setText("<html>No obstacles &amp; slow tempo</html>");
                }
                if(sWnd.diffId == 1){
                    sWnd.labels[8].setText("<html>MEDIUM</html>");
                    sWnd.labels[9].setText("<html>5 randomly placed obstacles &amp; regular tempo</html>");
                }
                if(sWnd.diffId == 2){
                    sWnd.labels[8].setText("<html>HARD</html>");
                    sWnd.labels[9].setText("<html>10 randomly placed obstacles &amp; fast tempo</html>");
                }
            }
        });
        sWnd.buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sWnd.diffId = (sWnd.diffId + 1) % 3;
                if(sWnd.diffId == 0){
                    sWnd.labels[8].setText("<html>EASY</html>");
                    sWnd.labels[9].setText("<html>No obstacles &amp; slow tempo</html>");
                }
                if(sWnd.diffId == 1){
                    sWnd.labels[8].setText("<html>MEDIUM</html>");
                    sWnd.labels[9].setText("<html>5 randomly placed obstacles &amp; regular tempo</html>");
                }
                if(sWnd.diffId == 2){
                    sWnd.labels[8].setText("<html>HARD</html>");
                    sWnd.labels[9].setText("<html>10 randomly placed obstacles &amp; fast tempo</html>");
                }
            }
        });
        sWnd.buttons[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int width = Integer.parseInt(sWnd.inputs[0].getText());
                if(width < 10) width = 10;
                if(width > 30) width = 30;
                int height = Integer.parseInt(sWnd.inputs[1].getText());
                if(height < 10) height = 10;
                if(height > 30) height = 30;
                int highScore = sWnd.scores.get(3*sWnd.mapId + sWnd.diffId);
                GameWindow.runTheGameWindow(sWnd, width, height, sWnd.mapId, sWnd.diffId, highScore, sWnd.mapFold);
                sWnd.setVisible(false);
            }
        });
        sWnd.buttons[6].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sWnd.dispose();
            }
        });
    }

    private static void importHighScores(SettingsWindow sWnd) throws FileNotFoundException {
        sWnd.scores = FileHandler.readHighScore();
        sWnd.labels[16].setText(String.format("<html>%d</html>", sWnd.scores.get(0)));
        sWnd.labels[17].setText(String.format("<html>%d</html>", sWnd.scores.get(3)));
        sWnd.labels[18].setText(String.format("<html>%d</html>", sWnd.scores.get(6)));
        sWnd.labels[20].setText(String.format("<html>%d</html>", sWnd.scores.get(1)));
        sWnd.labels[21].setText(String.format("<html>%d</html>", sWnd.scores.get(4)));
        sWnd.labels[22].setText(String.format("<html>%d</html>", sWnd.scores.get(7)));
        sWnd.labels[24].setText(String.format("<html>%d</html>", sWnd.scores.get(2)));
        sWnd.labels[25].setText(String.format("<html>%d</html>", sWnd.scores.get(5)));
        sWnd.labels[26].setText(String.format("<html>%d</html>", sWnd.scores.get(8)));
    }

    public static void updateHighScores(SettingsWindow sWnd, int map, int diff, int score) {
        sWnd.scores.set(3*map+diff, score);
        sWnd.labels[16+map+4*diff].setText(String.format("<html>%d</html>", score));
        FileHandler.writeHighScore(sWnd.scores);
    }
}

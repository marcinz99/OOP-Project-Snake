package snake;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoWindow extends JFrame {
    private static Color c_area = new Color(115, 157, 52);
    private static Color c_info = new Color(232, 240, 223);
    private static Color c_info_light = new Color(238, 246, 232);
    private static Color c_info_dark = new Color(198, 232, 183);
    private JLabel[] labels;
    private JButton button;
    private int unitWidth = 180;
    private int unitHeight = 50;
    private int score;
    private int highScore;

    public static void showInfoWindow(SettingsWindow sWnd, GameWindow gWnd, int score, int highScore){
        InfoWindow iWnd = new InfoWindow();
        iWnd.score = score;
        iWnd.highScore = highScore;
        JPanel panel = new JPanel();
        iWnd.add(panel);
        addComponents(iWnd, panel, score, highScore);
        iWnd.setVisible(true);
        addActionListeners(iWnd, gWnd, sWnd);
    }
    private InfoWindow(){
        super("You've just committed suicide");
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        int width = 400;
        int height = 270;
        setSize(width, height);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dimension.width - width)/2, (dimension.height - height)/2);

        Border whiteline = BorderFactory.createLineBorder(Color.white);
        this.labels = new JLabel[5];
        for(int i=0; i<5; i++){
            labels[i] = new JLabel();
            labels[i].setBorder(whiteline);
            labels[i].setOpaque(true);
            labels[i].setBackground(c_info);
            labels[i].setFont(new Font("Consolas", Font.PLAIN, 22));
            labels[i].setPreferredSize(new Dimension(1*this.unitWidth, 1*this.unitHeight));
            labels[i].setHorizontalAlignment(JLabel.CENTER);
            labels[i].setVerticalAlignment(JLabel.CENTER);
        }
        labels[4].setFont(new Font("Consolas", Font.PLAIN, 20));
        labels[4].setPreferredSize(new Dimension(2*this.unitWidth, 1*this.unitHeight));

        button = new JButton();
        button.setFont(new Font("Consolas", Font.PLAIN, 22));
        button.setBorder(whiteline);
        button.setBackground(c_info_dark);
        button.setPreferredSize(new Dimension(2*this.unitWidth, 1*this.unitHeight));
        button.setHorizontalAlignment(JLabel.CENTER);
        button.setVerticalAlignment(JLabel.CENTER);
    }
    private static void addComponents(InfoWindow iWnd, JPanel panel, int score, int highScore){
        panel.setLayout(new GridBagLayout());
        panel.setBackground(c_area);
        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;

        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridheight = 1;

        for(int i=0; i<2; i++){
            layoutConstraints.gridy = i;

            layoutConstraints.gridx = 0;
            panel.add(iWnd.labels[2*i], layoutConstraints);

            layoutConstraints.gridx = 1;
            panel.add(iWnd.labels[2*i+1], layoutConstraints);
        }

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 2;
        layoutConstraints.gridwidth = 2;
        layoutConstraints.gridheight = 1;
        panel.add(iWnd.labels[4], layoutConstraints);

        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 3;
        layoutConstraints.gridwidth = 2;
        layoutConstraints.gridheight = 1;
        panel.add(iWnd.button, layoutConstraints);

        iWnd.labels[0].setText("<html>Your score:</html>");
        iWnd.labels[1].setText(String.format("<html>%d</html>", score));
        iWnd.labels[2].setText("<html>High score:</html>");
        iWnd.labels[3].setText(String.format("<html>%d</html>", highScore));
        iWnd.labels[4].setText("<html></html>");
        iWnd.button.setText("<html>OK, that's fine!</html>");
        if(score > highScore) iWnd.labels[4].setText("<html>You've beaten current record!</html>");
    }
    private static void addActionListeners(InfoWindow iWnd, GameWindow gWnd, SettingsWindow sWnd){
        iWnd.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(iWnd.score > iWnd.highScore){
                    int mapId = GameWindow.getMapId(gWnd);
                    int diffId = GameWindow.getDiffId(gWnd);
                    SettingsWindow.updateHighScores(sWnd, mapId, diffId, iWnd.score);
                }
                gWnd.dispose();
                sWnd.setVisible(true);
                iWnd.dispose();
            }
        });
    }
}

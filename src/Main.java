import javax.swing.*;
import java.awt.*;

public class MusicPlayer {

    //Constructor
    public MusicPlayer(){
        //Song Options
        String [] songList = {"GORG.wav"};

        //Frame
        JFrame frame = new JFrame("Music Player");
        frame.setSize(300,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Panel
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(3, 1, 10,10));

        //Buttons
        JPanel buttonPanel = new JPanel();
        //buttonPanel.setLayout(new GridLayout(1,2,10,10));
        JButton playButton = new JButton("Play");
        JButton pauseButton = new JButton("Pause");
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);

        //Labels
        JPanel labelPanel = new JPanel();
        JLabel currentTime = new JLabel("Current Time: 0s");
        JLabel totalTime = new JLabel("Total Time: 0s");
        labelPanel.add(currentTime);
        labelPanel.add(totalTime);

        //Dropdown
        JPanel dropPanel = new JPanel();
        JComboBox<String> songSelector = new JComboBox<>(songList);
        dropPanel.add(songSelector);

        //Set Panel
        panel.add(dropPanel);
        panel.add(buttonPanel);
        panel.add(labelPanel);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new MusicPlayer();
    }
}
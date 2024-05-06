
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    //Class Variables
    private Clip clip;
    private Timer timer;
    private JLabel currentTime;
    private JLabel totalTime;

    private long time = 0;
    private String song;
    //Constructor
    public MusicPlayer(){
        //Song Options
        String [] songList = {"GORG.wav", "Code Kings.wav", "Dumbbells.wav", "Fire in My Belly.wav", "For Loop.wav", "GUI MasterMind.wav" ,
                "Hashin_ in the Code.wav", "Hey Papi.wav", "Mr. Scott.wav", "Programming.wav", "The Boolean Blues.wav", "The Codebreaker_s Fury.wav"};

        //Frame
        JFrame frame = new JFrame("Music Player");
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Panel
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(3, 1, 10,10));

        //Buttons
        JPanel buttonPanel = new JPanel();

        //buttonPanel.setLayout(new GridLayout(1,2,10,10));
        JButton playButton = new JButton("Play");
        JButton pauseButton = new JButton("Pause");
        JButton backButton = new JButton("Backward 15s");
        JButton skipButton = new JButton("Forward 15s");
        buttonPanel.add(backButton);
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(skipButton);

        //Labels
        JPanel labelPanel = new JPanel();
        currentTime = new JLabel("Current Time: 0s");
        totalTime = new JLabel("Total Time: 0s");
        labelPanel.add(currentTime);
        labelPanel.add(totalTime);

        //Dropdown
        JPanel dropPanel = new JPanel();
        JComboBox<String> songSelector = new JComboBox<>(songList);
        dropPanel.add(songSelector);

        //Button Function
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                //tells program what song to play
                if (clip == null) {
                    playMusic(songSelector.getSelectedItem().toString());
                    startTimer();
                    updateLabels();
                    song = songSelector.getSelectedItem().toString();
                } else if (!clip.isRunning()) {
                    if (songSelector.getSelectedItem().toString().equals(song)) {
                        if (clip.getMicrosecondPosition() == clip.getMicrosecondLength()) {
                            playMusic(songSelector.getSelectedItem().toString());
                            clip.setMicrosecondPosition(0);
                            startTimer();
                            updateLabels();
                            song = songSelector.getSelectedItem().toString();
                        } else {
                            playMusic(songSelector.getSelectedItem().toString());
                            startTimer();
                            updateLabels();
                            song = songSelector.getSelectedItem().toString();
                        }
                    } else {
                        playMusic(songSelector.getSelectedItem().toString());
                        clip.setMicrosecondPosition(0);
                        startTimer();
                        updateLabels();
                        song = songSelector.getSelectedItem().toString();
                    }
                } else {
                    clip.stop();
                    playMusic(songSelector.getSelectedItem().toString());
                    clip.setMicrosecondPosition(0);
                    startTimer();
                    updateLabels();
                    song = songSelector.getSelectedItem().toString();
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                pauseMusic();
                stopTimer();
                updateLabels();
            }
        });

        skipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                skipTimer();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                backTimer();
            }
        });

        //Set Panel
        panel.add(dropPanel);
        panel.add(buttonPanel);
        panel.add(labelPanel);
        frame.add(panel);
        frame.setVisible(true);
    }

    public void playMusic(String selectedSong){
        try{
            File file = new File(System.getProperty("user.dir") + "\\src\\Audio\\" + selectedSong);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setMicrosecondPosition(time);
            clip.start();
        } catch(IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void pauseMusic(){
        if(clip != null && clip.isRunning()){
            time = clip.getMicrosecondPosition();
            clip.stop();
        }
    }

    public void startTimer(){
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateLabels();
            }
        });
        timer.start();
        // time = clip.getMicrosecondLength()/1000000;
    }

    public void stopTimer(){
        if(timer != null){
            time =  clip.getMicrosecondPosition();
            timer.stop();
        }
    }
    public void skipTimer(){
        time =  clip.getMicrosecondPosition();
        if(!(clip.getMicrosecondPosition()+ 15000000 > clip.getMicrosecondLength())){
            clip.setMicrosecondPosition(clip.getMicrosecondPosition() + 15000000);
        }
        updateLabels();
    }

    public void backTimer(){
        time = clip.getMicrosecondPosition();
        if(clip.getMicrosecondPosition() - 15000000> 0){

            clip.setMicrosecondPosition(time- 15000000);
            updateLabels();
        } else{
            clip.setMicrosecondPosition(0);
            updateLabels();
        }

        updateLabels();
    }

    public void updateLabels(){
        if(clip != null && clip.isOpen()){
            if(clip.isRunning()){
                currentTime.setText("Current Time: " + clip.getMicrosecondPosition()/1000000 + "s");
                totalTime.setText("Total Time: " + clip.getMicrosecondLength()/1000000 + "s");
                time = clip.getMicrosecondPosition();
            }
        }
    }
    public static void main(String[] args){
        new MusicPlayer();
    }
}
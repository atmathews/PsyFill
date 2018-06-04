import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

import java.awt.Dimension;

public class Main {

        public static AudioFormat audioFormat;
        public static AudioInputStream audioInputStream;
        public static SourceDataLine sourceDataLine;

        public static boolean stopPlayback = false;
        public static File test;

        public static void main(String[] args)
        {
            test = new File("src/Make it happen.wav");

            JFrame frame2 = new JFrame("Instructions");

            //play(test);
            frame2.setSize(500, 500);
            frame2.setLocation(400, 150);
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //SeziurePanel pane = new SeziurePanel();
            BumperPanelEX pane = new BumperPanelEX();
            //frame1.setContentPane(pane);
            //frame1.setVisible(true);

            //play(test);
            pane.requestFocus();
            while(true)
            {
                //JOptionPane
                int number = Integer.parseInt(JOptionPane.showInputDialog(" Type 1 to Play \n Type 2 for Instructions \n Type 3 to Exit"));
                //If the number is 1, then it shows the game
                if(number == 1)
                {
                    play(test);
                    JFrame frame1 = new JFrame("PsyFill: Ball Corral");
                    frame1.setSize(815, 835);
                    Dimension original = new Dimension (frame1.getWidth(), frame1.getHeight());
                    frame1.setLocation(450, 200);
                    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    pane.setPreferredSize(original);
                    frame1.setContentPane(pane);
                    frame1.setVisible(true);
                    break;
                }
                //if the number is 2, then it shows the instructions
                if(number == 2)
                {
                    frame2.setContentPane(new SaneInstructions());
                    frame2.setVisible(true);
                    break;
                }
                //if then number is 3, then you exit
                if(number == 3)
                    System.exit(0);
            }

        }


        public static void play(File soundFile)
        {
            try{
                audioInputStream = AudioSystem.getAudioInputStream(soundFile); //get audio stream
                audioFormat      = audioInputStream.getFormat(); //get format of stream

                //if(ext.equals("wav") && audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) //check that stream is proper format
                //{
                audioInputStream = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED,audioInputStream);
                audioFormat       = audioInputStream.getFormat(); //fix improper wave format
                //}

                DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat); //request a data line (not open)

                sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo); //inform line of data type
                stopPlayback   = false; //allow playback
                new Play_Audio().start(); //play thread
            }
            catch (Exception error)
            {
                JOptionPane.showMessageDialog(null, "The audio file cannot be opened!",
                        "Error!", JOptionPane.INFORMATION_MESSAGE);

            }
        }

        /**
         *Play_Audio Class
         */
        private static class Play_Audio extends Thread
        {
            byte buffer[] = new byte[10000]; //10KB buffer, will not be too large for any system

            /**
             *Thread event which handles audio playback
             */
            public void run()
            {
                try{
                    sourceDataLine.open(audioFormat); //open the line
                    sourceDataLine.start(); //prepare line for data transfer

                    int continue_play;
                    while((continue_play = audioInputStream.read(buffer,0,buffer.length)) != -1 && stopPlayback == false)
                    {
                        if(continue_play > 0) //data still left to write
                        {
                            sourceDataLine.write(buffer, 0, continue_play); //write data to the line
                        }
                    }
                    sourceDataLine.drain(); //clear buffer
                    sourceDataLine.close(); //close line
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, "The audio file cannot be opened!",
                            "Error!", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        }
    }
package agh.cs.lab8;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Visualization {
    JFrame frame;
    Timer timer;
    JTextArea firstTextArea;
    JTextArea secondTextArea;
    JButton startStopButton;
    JButton stopSimulationButton;
    JButton searchButton;
    NeverEndingMap firstNeverEndingMap;
    NeverEndingMap secondNeverEndingMap;
    JTextField jTextField;
    JTextArea firstAnimalDetails;
    JTextArea secondAnimalDetails;

    public Visualization(NeverEndingMap firstNeverEndingMap, NeverEndingMap secondNeverEndingMap){
        this.init();
        this.firstNeverEndingMap = firstNeverEndingMap;
        this.secondNeverEndingMap = secondNeverEndingMap;
    }

    private void init(){
        this.frame = new JFrame("Animals simulation");
        frame.setSize(1400,1200);
        frame.setLayout(null);
        frame.setVisible(true);

        this.firstTextArea=new JTextArea("");
        firstTextArea.setBounds(50,100, 500,500);
        firstTextArea.setEditable(false);

        this.secondTextArea = new JTextArea("");
        secondTextArea.setBounds(600,100, 500,500);
        secondTextArea.setEditable(false);

        this.firstAnimalDetails = new JTextArea("");
        firstAnimalDetails.setBounds(50,650,500,100);
        firstAnimalDetails.setEditable(false);

        this.secondAnimalDetails = new JTextArea("");
        secondAnimalDetails.setBounds(600,650,500,100);
        secondAnimalDetails.setEditable(false);

        this.jTextField = new JTextField("");
        jTextField.setBounds(200, 620, 300, 20);
        jTextField.setEditable(true);

        this.searchButton = new JButton("Search");
        searchButton.setBounds(50,620,100,20);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                boolean flag = true;
                String s=jTextField.getText();
                for(int i=0; i<s.length(); i++){
                    if (s.charAt(i)<48|| s.charAt(i)>57){
                        flag = false;
                        break;
                    }
                }
                if(flag==true) {
                    int id = Integer.parseInt(jTextField.getText());
                    firstAnimalDetails.setText(firstNeverEndingMap.getAnimalStatistic(id));
                    secondAnimalDetails.setText(secondNeverEndingMap.getAnimalStatistic(id));
                }
                else{
                    firstAnimalDetails.setText("Incorrect search. Choose the animal by id. Available numbers: 0-"+firstNeverEndingMap.getTotalNumberOfAnimals());
                    secondAnimalDetails.setText("Incorrect search. Choose the animal by id. Available numbers: 0-"+secondNeverEndingMap.getTotalNumberOfAnimals());
                }
            }
        });


        this.startStopButton = new JButton("");
        startStopButton.setBounds(50,20,100,20);
        frame.add(startStopButton);


        startStopButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (timer == null)
                    startAnimation();
                else
                    stopAnimation();
            }
        });

        this.stopSimulationButton = new JButton("End");
        stopSimulationButton.setBounds(50,60,100,20);
        stopSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String dir = System.getProperty("user.dir");
                PrintWriter zapis = null;
                try {
                    zapis = new PrintWriter(dir+"/results.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                zapis.println(firstNeverEndingMap.getTotalStatistics());
                zapis.println("\n");
                zapis.println(secondNeverEndingMap.getTotalStatistics());
                zapis.close();
                System.exit(0);
            }
        });

        frame.add(startStopButton);
        frame.add(stopSimulationButton);
        frame.add(searchButton);
        searchButton.setVisible(false);


        frame.add(secondAnimalDetails);
        frame.add(firstAnimalDetails);
        frame.add(jTextField);
        secondAnimalDetails.setVisible(false);
        firstAnimalDetails.setVisible(false);
        jTextField.setVisible(false);
    }

    ActionListener timerListener = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if(!firstNeverEndingMap.areAnimalsAlive()){
                firstTextArea.setText(firstNeverEndingMap.toString());
                startStopButton.setVisible(false);
                timer.stop();
            }
            if(!secondNeverEndingMap.areAnimalsAlive()){
                secondTextArea.setText(secondNeverEndingMap.toString());
                startStopButton.setVisible(false);
                timer.stop();
            }
            firstTextArea.setText(firstNeverEndingMap.toString());
            frame.add(firstTextArea);
            secondTextArea.setText(secondNeverEndingMap.toString());
            frame.add(secondTextArea);
            SwingUtilities.updateComponentTreeUI(frame);
            firstNeverEndingMap.run();
            secondNeverEndingMap.run();
        }
    };

    public void startAnimation(){
        if (timer == null) {
            timer = new Timer(100, timerListener);
            timer.start();
            startStopButton.setText("Stop");
            searchButton.setVisible(false);
            secondAnimalDetails.setVisible(false);
            firstAnimalDetails.setVisible(false);
            jTextField.setVisible(false);
        }
    }

    private void stopAnimation() {
        if (timer != null) {
            timer.stop();
            timer = null;
            startStopButton.setText("Start");

            firstTextArea.setText(firstNeverEndingMap.toString());
            frame.add(firstTextArea);
            secondTextArea.setText(secondNeverEndingMap.toString());
            frame.add(secondTextArea);
            SwingUtilities.updateComponentTreeUI(frame);
            searchButton.setVisible(true);
            int firstAnimalNumber=firstNeverEndingMap.getTotalNumberOfAnimals()-1;
            int secondAnimalNumber=secondNeverEndingMap.getTotalNumberOfAnimals()-1;
            secondAnimalDetails.setText("Animal Details. Choose the animal by id. Available numbers: 0-"+secondAnimalNumber);
            firstAnimalDetails.setText("Animal Details. Choose the animal by id. Available numbers: 0-"+firstAnimalNumber);
            secondAnimalDetails.setVisible(true);
            firstAnimalDetails.setVisible(true);
            jTextField.setVisible(true);
        }
    }


}

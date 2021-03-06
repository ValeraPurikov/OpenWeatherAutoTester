package com.autoTesterSystem;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.ArrayList;

public class UI {
    private JPanel pane;
    private JLabel availLabel;
    private JList avaiList;
    private JLabel assignLabel;
    private JList assignList;
    private JButton startBtn;
    private JTextArea console;
    private Thread testThread;
    private static TextAreaOutputStream taOutputStream; //TextAreaOutputStream object, used to make all console outputs to be displayed in console instead

    public JButton getButton() {
        return startBtn;
    }


    public UI() {
        avaiList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Adding a test to assigned list
                super.mouseReleased(e);
                if (avaiList.getSelectedValue() == null)
                    return;
                ArrayList<String> tempList = new ArrayList<String>(); // Create a temporal list tempList
                for (int i = 0; i < assignList.getModel().getSize(); i++) { // Take every test in assigned tests list...
                    tempList.add(assignList.getModel().getElementAt(i).toString()); // ...and add them to tempList
                }
                tempList.add(avaiList.getSelectedValue().toString()); //Then we add to tempList the test we just clicked
                assignList.setListData(tempList.toArray(new String[tempList.size()])); //Set the contents of assigned test list to the contents of tempList
                if (tempList.isEmpty())
                    startBtn.setEnabled(false);
                else
                    startBtn.setEnabled(true);
                ArrayList<String> tempList2 = new ArrayList<String>(); //Create a temporal list tempList2
                for (int i = 0; i < avaiList.getModel().getSize(); i++) { // Take every test in available tests list...
                    tempList2.add(avaiList.getModel().getElementAt(i).toString()); // ...and add them to tempList2
                }
                tempList2.remove(avaiList.getSelectedValue().toString()); //We remove from the list the  test we just clicked
                avaiList.setListData(tempList2.toArray(new String[tempList2.size()])); //Set the contents of available test list to the contents of tempList2
            }
        });
        assignList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Removing the test from assigned list
                super.mouseReleased(e);
                if (assignList.getSelectedValue() == null)
                    return;
                ArrayList<String> tempList = new ArrayList<String>(); // Create a temporal list tempList
                for (int i = 0; i < avaiList.getModel().getSize(); i++) { // Take every test in available tests list...
                    tempList.add(avaiList.getModel().getElementAt(i).toString()); // ...and add them to tempList
                }
                tempList.add(assignList.getSelectedValue().toString()); //Then we add to tempList the test we just clicked
                avaiList.setListData(tempList.toArray(new String[tempList.size()])); //Set the contents of available test list to the contents of tempList

                ArrayList<String> tempList2 = new ArrayList<String>(); //Create a temporal list tempList2
                for (int i = 0; i < assignList.getModel().getSize(); i++) { // Take every test in assigned tests list...
                    tempList2.add(assignList.getModel().getElementAt(i).toString()); // ...And add them to tempList2
                }
                tempList2.remove(assignList.getSelectedValue().toString()); //We remove from the tempList2 the  test we just clicked
                assignList.setListData(tempList2.toArray(new String[tempList2.size()])); //Set the contents of assigned test list to the contents of tempList2
                if (tempList2.isEmpty())
                    startBtn.setEnabled(false);
                else
                    startBtn.setEnabled(true);
            }
        });
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Code that runs after Start/Stop button was clicked
                if (startBtn.getText().equals("Start Tests")) {
                    if (assignList.getModel().getSize() != 0) {
                        startBtn.setText("Stop Tests");
                        testThread = new Thread(() -> {
                            TestHandler th = new TestHandler(assignList, startBtn);
                            th.doAssignedTests();
                        });
                        testThread.start();
                    }
                } else {
                    startBtn.setText("Start Tests");
                    if (testThread != null) {
                        testThread.stop();
                        testThread = null;
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        //Set up the UI
        JFrame frame = new JFrame("UI");
        frame.setContentPane(new UI().pane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    private void createUIComponents() {
        //Set the jTextArea1 as the console
        console = new JTextArea();
        taOutputStream = new TextAreaOutputStream(console);
        System.setOut(new PrintStream(taOutputStream));
        //Make sure that console always shows the bottom line
        DefaultCaret caret = (DefaultCaret) console.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }
}

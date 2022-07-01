package at.zykem;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static java.awt.BorderLayout.PAGE_END;

public class TodoList extends JFrame{

    Frame frame = new JFrame  ("TODO List");

    public TodoList()  {

        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.PAGE_AXIS));

    JTextField textField = new JTextField(20);
    JButton sendButton = new JButton("Add Task");
        sendButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            // styling/init of UI
            JLabel label = new JLabel(textField.getText());
            label.setOpaque(true);
            label.setForeground(Color.WHITE);
            label.setBackground(Color.darkGray);
            JButton done = new JButton("Remove Task (" + label.getText() + ")");
            done.setOpaque(true);
            done.setBackground(Color.WHITE);
            done.setForeground(Color.DARK_GRAY);
            boxPanel.add(done);
            boxPanel.add(label);
            boxPanel.add(Box.createRigidArea(new Dimension(0,5)));
            textField.setText("");
            boxPanel.revalidate();
//              pack();

            // removing tasks
            done.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if(done.getText().contains(textField.getText())) {

                        boxPanel.remove(done);
                        boxPanel.remove(label);
                        boxPanel.revalidate();
                        boxPanel.repaint();

                    }

                }
            });

        }
    });


    JPanel southPanel = new JPanel();
        southPanel.add(textField);
        southPanel.add(sendButton);

    add(new JScrollPane(boxPanel));
    add(southPanel, BorderLayout.PAGE_END);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
    setTitle("TODO-List");
}
    }



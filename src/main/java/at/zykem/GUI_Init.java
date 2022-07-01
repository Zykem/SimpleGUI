package at.zykem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_Init implements ActionListener, Runnable {
    JFrame frame = new JFrame("SimpleGUI");
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SwingUtilities.invokeLater(new GUI_Init());
    }

    @Override
    public void run() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(createMainPanel(), BorderLayout.CENTER);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        panel.setPreferredSize(new Dimension(400, 400));
        JLabel mainTxt = new JLabel(    "Simple Swing GUI App I made to learn more about it :)");
        mainTxt.setForeground(Color.RED);
        JButton discordlookup = new JButton("Open Discord Lookup Tool");
        JButton todolist = new JButton("Open TODO List");
        panel.add(mainTxt);
        panel.add(todolist);
        panel.add(discordlookup);
        todolist.addActionListener(this::actionDone);
        discordlookup.addActionListener(this::actionPerformed);

        return panel;
    }

    public void actionDone(ActionEvent e){

        TodoList itemLoader = new TodoList();
        itemLoader.setVisible(true);
        frame.setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DiscordLookup itemLoader = new DiscordLookup();
        itemLoader.run();
        frame.setVisible(false);
    }
}
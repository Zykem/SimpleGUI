package at.zykem;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DiscordLookup implements Runnable, ActionListener {


    JFrame frame = new JFrame  ("Discord Lookup Tool");


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
        JLabel mainTxt = new JLabel(    "Discord UID Lookup");
        mainTxt.setForeground(Color.RED);
        JButton start = new JButton("Lookup DiscordID");
        JCheckBox checkbox = new JCheckBox();
        checkbox.setText("Send data to webhook");
        JCheckBox checkBox2 = new JCheckBox();
        checkBox2.setText("Copied to clipboard");
        final JLabel msgHandler = new JLabel();

        panel.add(checkbox);
        panel.add(checkBox2);
        panel.add(start);
        panel.add(msgHandler);
        msgHandler.setForeground(Color.RED);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dcId = (String) JOptionPane.showInputDialog(frame, "Enter the DiscordID", "DiscordID Lookup", JOptionPane.PLAIN_MESSAGE, null, null, "");
                try {
                    //getJsonDiscordData(dcId);
                    if(checkBox2.isSelected()) {
                        JFrame msgFrame = new JFrame("Response");
                        Object obj = new JSONParser().parse(getJsonDiscordData(dcId));
                        JSONObject jObj = (JSONObject) obj;
                        JOptionPane.showMessageDialog(msgFrame, "JSON Response has been copied to clipboard!");
                        StringSelection stringSelection = new StringSelection(jObj.toString());
                        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                        cb.setContents(stringSelection, null);
                        //msgHandler.setText(getJsonDiscordData(dcId));
                        System.out.println("Succesfully wrote to data file (or not xd)");
                    }
                    if(checkbox.isSelected()) {

                        // Defining - Parsing JSON  object to normal object
                        Object obj = new JSONParser().parse(getJsonDiscordData(dcId));
                        JSONObject jObj = (JSONObject) obj;


                            String webhookURL = (String) JOptionPane.showInputDialog(frame, "Enter the Discord Webhook", "DiscordID Lookup", JOptionPane.PLAIN_MESSAGE, null, null, "");
                            DiscordWebhook webhook = new DiscordWebhook(webhookURL);
                            webhook.setContent("");
                            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                    .setTitle("Your JSON Discord LOOKUP Response")
                                    .setDescription("```" + jObj.entrySet() + "```")
                                    .setColor(Color.RED)
                                    .setFooter("SimpleGUI", ""));
                            webhook.setUsername("SimpleGUI App");
                            webhook.setTts(false);
                            webhook.execute(); //Handle exception






                    }
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });


        return panel;
    }

    public static String getJsonDiscordData(String discordid) throws IOException, InterruptedException {

        URL discordapi = new URL("https://discord.com/api/v9/users/" + String.valueOf(discordid));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.valueOf(discordapi)))
                .header("Authorization", "Bot bot_token")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GUI_Init main = new GUI_Init();
        main.run();
        frame.setVisible(false);
    }
}

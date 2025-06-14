package GUI;

import Entity.Interest;
import Entity.SimpleInterest;
import File.InterestFileHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;




public class InterestManagementFrame extends JFrame {
    private final ArrayList<Interest> interestRecords;
    private final JTextField principalField, rateField, timeField;
    private final JTextArea recordsArea;

    
    public InterestManagementFrame() {
        interestRecords = InterestFileHandler.loadRecords();

        setTitle("Simple Interest Calculator");

        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icon.png"));
        Image scaledIcon = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        setIconImage(scaledIcon);

        ImageIcon bgIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/background.jpg"));
        BackgroundPanel backgroundPanel = new BackgroundPanel(bgIcon.getImage());
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new BorderLayout());

    
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.WHITE), 
        "Input Details",
        TitledBorder.DEFAULT_JUSTIFICATION, 
        TitledBorder.DEFAULT_POSITION,
        null, 
        Color.WHITE
));
        inputPanel.setOpaque(false);  


        JLabel principalLabel = new JLabel("Principal:");
        principalLabel.setForeground(Color.WHITE);

        JLabel rateLabel = new JLabel("Rate (%):");
        rateLabel.setForeground(Color.WHITE);

        JLabel timeLabel = new JLabel("Time (years):");
        timeLabel.setForeground(Color.WHITE);


        principalField = new JTextField();
        principalField.setOpaque(false);
        principalField.setForeground(Color.WHITE);
        principalField.setCaretColor(Color.WHITE); 
        principalField.setBackground(new Color(0, 0, 0, 100));  

        rateField = new JTextField();
        rateField.setOpaque(false);
        rateField.setForeground(Color.WHITE);
        rateField.setCaretColor(Color.WHITE);
        rateField.setBackground(new Color(0, 0, 0, 100));

        timeField = new JTextField();
        timeField.setOpaque(false);
        timeField.setForeground(Color.WHITE);
        timeField.setCaretColor(Color.WHITE);
        timeField.setBackground(new Color(0, 0, 0, 100));


        principalField.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveToRate");
        principalField.getActionMap().put("moveToRate", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
        rateField.requestFocusInWindow();
    }
});

        rateField.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveToPrincipal");
        rateField.getActionMap().put("moveToPrincipal", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
        principalField.requestFocusInWindow();
    }
});
        rateField.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveToTime");
        rateField.getActionMap().put("moveToTime", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
        timeField.requestFocusInWindow();
    }
});

        timeField.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveToRate");
        timeField.getActionMap().put("moveToRate", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
        rateField.requestFocusInWindow();
    }
});


        inputPanel.add(principalLabel);
        inputPanel.add(principalField);
        inputPanel.add(rateLabel);
        inputPanel.add(rateField);
        inputPanel.add(timeLabel);
        inputPanel.add(timeField);


        JButton calculateButton = new JButton("Calculate");
        JButton saveButton = new JButton("Save Record");
        JButton clearButton = new JButton("Clear");
        JButton deleteAllButton = new JButton("Delete All Records");


        inputPanel.add(calculateButton);
        inputPanel.add(saveButton);
        inputPanel.add(deleteAllButton);

        

        JPanel recordsPanel = new JPanel(new BorderLayout());
        recordsPanel.setBorder(BorderFactory.createTitledBorder("Records"));

        recordsArea = new JTextArea();
        recordsArea.setEditable(false);
        recordsPanel.add(new JScrollPane(recordsArea), BorderLayout.CENTER);
        JPanel bottomButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomButtonsPanel.add(clearButton);
        bottomButtonsPanel.add(deleteAllButton);
        recordsPanel.add(bottomButtonsPanel, BorderLayout.SOUTH);

    
        backgroundPanel.add(inputPanel, BorderLayout.NORTH);
        backgroundPanel.add(recordsPanel, BorderLayout.CENTER);
        



        calculateButton.addActionListener(this::calculateInterest);
        saveButton.addActionListener(this::saveRecord);
        clearButton.addActionListener(e -> recordsArea.setText(""));
        deleteAllButton.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete all records?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
        interestRecords.clear();
        InterestFileHandler.clearSavedFile();
        recordsArea.setText("");
        JOptionPane.showMessageDialog(this, "All records have been deleted.");
    }
});

        displayLoadedRecords();
    }

    private void calculateInterest(ActionEvent e) {
        try {
            double principal = Double.parseDouble(principalField.getText());
            double rate = Double.parseDouble(rateField.getText());
            int time = Integer.parseInt(timeField.getText());

            Interest interest = new SimpleInterest(principal, rate, time);
            double result = interest.calculateInterest();
            recordsArea.append(String.format("Principal: %.2f, Rate: %.2f, Time: %d, Interest: %.2f\n", 
                    principal, rate, time, result));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveRecord(ActionEvent e) {
        try {
            double principal = Double.parseDouble(principalField.getText());
            double rate = Double.parseDouble(rateField.getText());
            int time = Integer.parseInt(timeField.getText());

            Interest interest = new SimpleInterest(principal, rate, time);
            interestRecords.add(interest);
            InterestFileHandler.saveRecords(interestRecords);
            JOptionPane.showMessageDialog(this, "Record saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayLoadedRecords() {
        interestRecords.forEach(record -> recordsArea.append(String.format("Principal: %.2f, Rate: %.2f, Time: %d, Interest: %.2f\n", 
                record.getPrincipal(), record.getRate(), record.getTime(), record.calculateInterest())));
    }
}
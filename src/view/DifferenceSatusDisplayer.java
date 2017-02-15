package view;

import config.TesterConfig;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DifferenceSatusDisplayer extends JFrame {

    private static final int DISPLAYER_HEIGHT = 500;
    private static final int DISPLAYER_WIDTH = 400;
    private static final int HEIGHT_PER_RESULT = 100;
    private static final String TEST = "Teste ";
    private static final String TEST_SUCESS = "PASSOU!";
    private static final String TEST_ERROR = "NÃO PASSOU!!!";
    private static final String TWO_DOTS_CHAR = ":";
    private static final String BTN_DIFF = "ver diferenças";
    private ArrayList<List<String>> differences;

    private int generatePaneHeight(int nItems) {
        if (nItems * HEIGHT_PER_RESULT <= DISPLAYER_HEIGHT) {
            return DISPLAYER_HEIGHT;
        }
        return HEIGHT_PER_RESULT * nItems;
    }

    public void initialize(ArrayList<List<String>> differences, String title) {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().
                getResource(TesterConfig.ICON_PATH));
        this.setIconImage(icon.getImage());
        this.differences = differences;
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(DISPLAYER_WIDTH, DISPLAYER_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle(title);

        JPanel pane = new JPanel();
        Dimension paneDimension = new Dimension(DISPLAYER_WIDTH, generatePaneHeight(differences.size()));
        pane.setPreferredSize(paneDimension);
        pane.setLayout(null);

        for (int currentTeste = 0; currentTeste < differences.size(); ++currentTeste) {
            List<String> difference = differences.get(currentTeste);
            JLabel lbTestNumber = new JLabel(TEST.concat(Integer.toString(currentTeste + 1)).concat(TWO_DOTS_CHAR));
            JLabel lbTestStatus = new JLabel();
            lbTestNumber.setBounds(30, (currentTeste + 1) * 50, 80, 20);
            lbTestStatus.setBounds(90, (currentTeste + 1) * 50, 100, 20);

            if (difference == null) {
                lbTestStatus.setText(TEST_SUCESS);
                lbTestStatus.setForeground(Color.blue);
            } else {
                lbTestStatus.setText(TEST_ERROR);
                lbTestStatus.setForeground(Color.RED);
                JButton btnDisplayFileDiff = new JButton(BTN_DIFF);
                btnDisplayFileDiff.setName(Integer.toString(currentTeste));
                btnDisplayFileDiff.setBounds(200, (currentTeste + 1) * 50 - 5, 150, 30);
                btnDisplayFileDiff.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JButton caller = (JButton) actionEvent.getSource();
                        int itemNumber = Integer.parseInt(caller.getName());

                        List<String> fileVerification = differences.get(itemNumber);

                        new DifferenceDisplayer().
                                displayDifference(fileVerification, TEST.concat(Integer.toString(itemNumber + 1)));
                    }
                });
                pane.add(btnDisplayFileDiff);
            }

            pane.add(lbTestNumber);
            pane.add(lbTestStatus);
        }

        JScrollPane scrollPane = new JScrollPane(pane);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane);
        this.setVisible(true);
    }

}

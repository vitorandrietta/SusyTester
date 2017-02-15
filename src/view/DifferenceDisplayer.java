package view;

import config.TesterConfig;
import view.view_interfaces.IDifferenceDisplayer;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DifferenceDisplayer extends JFrame implements IDifferenceDisplayer {
    
    private static final int DISPLAYER_WIDTH = 650;
    private static final int DISPLAYER_HEIGHT = 500;
    private static final String NEXTLINE_STR = "\n";
    private final JTextArea taDifference = new JTextArea();
    
    @Override
    public void displayDifference(List<String> difference, String title) {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().
                getResource(TesterConfig.ICON_PATH));
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle(title);
        this.setSize(DISPLAYER_WIDTH, DISPLAYER_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new GridLayout(1, 1));
        JScrollPane sp = new JScrollPane(this.taDifference);
        this.add(sp);
        taDifference.setWrapStyleWord(true);
        taDifference.setLineWrap(true);
        taDifference.setEditable(false);
        
        for (String s : difference) {
            this.taDifference.append(s);
            this.taDifference.append(NEXTLINE_STR);
        }
        
        this.setVisible(true);
        
    }
    
}

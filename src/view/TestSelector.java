package view;

import view.view_interfaces.ITestSelectorDisplay;
import config.TesterConfig;
import controller.SusyCrawler;
import controller.SusyTesterManager;
import model.LabTestFile;
import model.MCclass;
import model.MClab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class TestSelector extends JFrame implements ITestSelectorDisplay {

    private static final String FORM_TITLE = "Susy Tester";
    private static final int BASE_WIDTH = 400;
    private static final int BASE_HEIGHT = 430;
    private static final String TEST_TITLE = "Testes abertos do laboratório: ";
    private static final String TAB1 = "Principal";
    private static final String TAB2 = "Sobre";
    private static final String LB1_TXT = "Turma:";
    private static final String LB2_TXT = "Laboratório:";
    private static final String LB3_TXT = "Executável:";
    private static final String ERROR_TITLE = "ERRO";
    private static final String LB_BTN = "Testar";
    private static final String SELECTOR_MESSAGE = "Selecione o Programa";
    private static final String INFO
            = "NÃO SE DEVE ficar trocando rapidamente o valor das opções e clicando indiscriminadamente no botão "
            + "testar, para evitar requisições desnecessárias ao Susy\n\n"
            + "Esse programa tem por objetivo facilitar a verificação dos casos de teste abertos do Susy.\n"
            + "\nO programa utiliza Diff no Linux e FC no Windows, para entender as diferenças geradas:"
            + "\nEntendendo Comando Diff no linux:"
            + "\nhttp://www.computerhope.com/unix/udiff.htm"
            + "\n\nOs arquivos sao baixados na pasta -> TestesSusyApp (durante a execução), onde o programa esta sendo executado"
            + "\n\nQualquer dúvida, sugestão, ou aviso de erro: vitorandrietta@gmail.com";
    private static final String INCOMPLETE_INPUT_MSG = "Preencha todos os campos!";
    private SusyCrawler susyCrawler;
    private ArrayList<MCclass> classes;
    private ArrayList<MClab> labs;
    private ArrayList<LabTestFile> labTests;
    private SusyTesterManager susyManager;
    private JComboBox<String> mcClass, mcWork, cFile;
    private int previousClassIndex = -1;
    private JFileChooser exeSelector;

    private boolean validateFields() {
        if (mcClass.getSelectedItem() == null) {
            return false;
        }

        if (mcWork.getSelectedItem() == null) {
            return false;
        }

        return cFile.getSelectedItem() != null;
    }

    public void initialize() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().
                getResource(TesterConfig.ICON_PATH));
        this.setIconImage(icon.getImage());
        this.susyManager = new SusyTesterManager();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(FORM_TITLE);
        this.setSize(BASE_WIDTH, BASE_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        JTabbedPane tabs = new JTabbedPane();
        JPanel main, about;
        exeSelector = new JFileChooser();
        main = new JPanel();
        main.setLayout(null);
        JLabel lbMcClass, lbMcWork, lbProgram;
        JButton btnTest;
        lbMcClass = new JLabel(LB1_TXT);
        lbMcClass.setBounds(80, 17, 50, 20);
        main.add(lbMcClass);
        this.mcClass = new JComboBox<>();
        this.mcClass.setBounds(130, 10, 200, 30);
        main.add(this.mcClass);
        lbMcWork = new JLabel(LB2_TXT);
        lbMcWork.setBounds(55, 147, 80, 20);
        main.add(lbMcWork);
        this.mcWork = new JComboBox<>();
        this.mcWork.setBounds(130, 140, 200, 30);
        main.add(this.mcWork);
        lbProgram = new JLabel(LB3_TXT);
        lbProgram.setBounds(55, 277, 80, 20);
        main.add(lbProgram);
        this.cFile = new JComboBox<>();
        this.cFile.setBounds(130, 270, 200, 30);
        this.cFile.setEditable(false);
        main.add(this.cFile);
        btnTest = new JButton(LB_BTN);
        btnTest.setBounds(195, 315, 70, 30);
        main.add(btnTest);
        about = new JPanel();
        about.setLayout(new BorderLayout());
        JTextArea taInfo = new JTextArea();
        taInfo.setWrapStyleWord(true);
        taInfo.setLineWrap(true);
        taInfo.setEditable(false);
        taInfo.setText(INFO);
        about.add(taInfo);
        tabs.add(TAB1, main);
        tabs.add(TAB2, about);
        this.add(tabs);

        this.susyCrawler = new SusyCrawler();
        try {
            this.classes = susyCrawler.mcClasses();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        }

        this.mcClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (mcClass.getSelectedIndex() != previousClassIndex && mcClass.getSelectedIndex() != -1) {
                    MCclass mcclassModel = classes.get(mcClass.getSelectedIndex());
                    try {
                        labs = susyCrawler.mcLabs(mcclassModel.getMcClassURL());
                        displayLabs(mcWork, labs);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

                    }
                    previousClassIndex = mcClass.getSelectedIndex();

                }
            }
        });

        this.mcWork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (mcClass.getSelectedIndex() != -1 && mcWork.getSelectedItem() != null) {
                    String mcClassTxt;
                    String mcLabTxt;
                    mcClassTxt = classes.get(mcClass.getSelectedIndex()).getMcClassURL();
                    mcLabTxt = labs.get(mcWork.getSelectedIndex()).getMcLabURL();
                    try {
                        labTests = susyCrawler.labTests(mcClassTxt, mcLabTxt);

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.cFile.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                //not necessary
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                exeSelector.showDialog(null, SELECTOR_MESSAGE);
                if (exeSelector.getSelectedFile() != null) {
                    cFile.removeAllItems();
                    cFile.addItem(exeSelector.getSelectedFile().getAbsolutePath());
                }

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                //not necessary
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                //not necessary
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                //not necessary
            }
        });

        btnTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (validateFields()) {
                    File baseDir = new File(TesterConfig.getTestFileDir());
                    TesterConfig.clearDir(baseDir.listFiles());
                    String programPath = cFile.getSelectedItem().toString();
                    try {
                        ArrayList<List<String>> results = new ArrayList<>();
                        susyManager.downloadAllTestFiles(labTests);
                        ArrayList<LabTestFile> testsOutput = susyManager.generateOutPutForTests(programPath, labTests);
                        int numberOfTests = labTests.size();
                        for (int testNumber = 0; testNumber < numberOfTests; ++testNumber) {
                            LabTestFile testOutput = testsOutput.get(testNumber);
                            LabTestFile expectedOutput = labTests.get(testNumber);
                            List<String> result = susyManager.calculateDifference(expectedOutput, testOutput);
                            results.add(result);
                        }

                        new DifferenceSatusDisplayer().initialize(results, TEST_TITLE.concat(mcWork.getSelectedItem().toString()));

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(null, INCOMPLETE_INPUT_MSG);
                }
            }
        });

        this.displayClasses(this.mcClass, this.classes);
        this.setVisible(true);
    }

    @Override
    public void displayClasses(JComboBox<String> combo, ArrayList<MCclass> classes) {
        combo.removeAllItems();
        for (MCclass mcCurrentClass : classes) {
            combo.addItem(mcCurrentClass.getMcClassName());
        }
    }

    @Override
    public void displayLabs(JComboBox<String> combo, ArrayList<MClab> labs) {
        combo.removeAllItems();
        for (MClab lab : labs) {
            combo.addItem(lab.getMcLabName());
        }
    }

}

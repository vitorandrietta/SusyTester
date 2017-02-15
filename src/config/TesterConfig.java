package config;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class TesterConfig {
    
    public final static String GENERATED_FILE_HEADER = "*****Arquivo Gerado pelo programa*****";
    public final static String RES_FILE_HEADER = "*****Arquivo de saída esperada*****";
    public final static String ICON_PATH = "images/Robot_icon.png"   ;
    public final static String SLASH_SEPARATOR = "/";
    public final static String WINDOWS_NO_DIFFERENCES = "FC: no differences encountered";
    public final static String BACK_SLASH_SEPARATOR = "\\";
    public final static String DOUBLE_BACK_SLASH_SEPARATOR = "\\\\";
    public final static String GENERATED_FILE = "SaidaDoExecutavel";
    public final static String OPERATIONAL_SYSTEM = "os.name";
    public static final String TEST_FILE_INPUT_SUFIX = ".in";
    public static final String TEST_FILE_OUTPUT_SUFIX = ".res";
    private final static String LOOK_AND_FELL = "Nimbus";
    private final static String ERROR_WINDOW = "Erro";
    private final static String TEST_FILES_DIR = "TestesSusyApp";
    private final static String CURRENT_USER_DIR = "user.dir";
    private final static String WINDOWS_OS = "Windows";
    
    
    private static void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (LOOK_AND_FELL.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), ERROR_WINDOW, JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void saveToFile(String info, String filePath) throws IOException {
        File file = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(info);
            fileWriter.flush();
        }
    }

    public static void clearDir(File[] files) {

        for (File f : files) {
            f.delete();
        }
    }

    public static boolean isWindows() {
        String OS = System.getProperty(OPERATIONAL_SYSTEM);
        return OS.startsWith(WINDOWS_OS);
    }

    public static String getTestFileDir() {
        return System.getProperty(CURRENT_USER_DIR).concat(returnSeparator()).concat(TEST_FILES_DIR);
    }

    private static void createTestDir() {
        File f = new File(getTestFileDir());
        if (!f.exists()) {
            f.mkdir();
        } else {
            clearDir(f.listFiles());
        }
    }

    public static String returnSeparator() {
        return isWindows() ? BACK_SLASH_SEPARATOR : SLASH_SEPARATOR;
    }

    public static void initilizeConfigs() {
        createTestDir();
        setLookAndFeel();
    }
    
}

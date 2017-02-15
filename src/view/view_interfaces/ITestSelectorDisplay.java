package view.view_interfaces;

import model.MCclass;
import model.MClab;

import javax.swing.*;
import java.util.ArrayList;

/**
 
 */
public interface ITestSelectorDisplay {

    void displayClasses(JComboBox<String> combo, ArrayList<MCclass> classes);

    void displayLabs(JComboBox<String> combo, ArrayList<MClab> labs);

}

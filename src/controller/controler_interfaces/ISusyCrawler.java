package controller.controler_interfaces;

import model.LabTestFile;
import model.MCclass;
import model.MClab;

import java.io.IOException;
import java.util.ArrayList;

public interface ISusyCrawler {
    ArrayList<MCclass> mcClasses() throws IOException;
    ArrayList<MClab> mcLabs(String mcClass) throws IOException;
    ArrayList<LabTestFile> labTests(String mcClass, String lab) throws IOException;
}

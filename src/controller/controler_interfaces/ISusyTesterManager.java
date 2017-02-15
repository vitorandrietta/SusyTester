package controller.controler_interfaces;

;
import model.LabTestFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ISusyTesterManager {
    ArrayList<LabTestFile> downloadAllTestFiles(ArrayList<LabTestFile> susyTests) throws IOException;
    ArrayList<LabTestFile> generateOutPutForTests(String programPath,ArrayList<LabTestFile> susyTests) throws IOException;
    List<String> calculateDifference(LabTestFile expectedOutput, LabTestFile output) throws Exception;
}

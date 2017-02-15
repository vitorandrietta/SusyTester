package controller;

import config.TesterConfig;
import controller.controler_interfaces.ISusyTesterManager;
import model.LabTestFile;

import java.io.File;
import java.io.IOException;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class SusyTesterManager implements ISusyTesterManager {

    private static final String DOUBLE_ASTERISK = "**";
    
    @Override
    public ArrayList<LabTestFile> downloadAllTestFiles(ArrayList<LabTestFile> susyTests) throws IOException {
        ArrayList<LabTestFile> dirFiles = new ArrayList<>();
        String baseDir = TesterConfig.getTestFileDir();
        for (LabTestFile test : susyTests) {
            String diskPath = baseDir.concat(TesterConfig.returnSeparator()).concat(test.getTestFileName());
            String fileUrl = test.getAbsoluteTestFileURI();
            Downloader.download(fileUrl.concat(TesterConfig.TEST_FILE_INPUT_SUFIX), diskPath.concat(TesterConfig.TEST_FILE_INPUT_SUFIX));
            Downloader.download(fileUrl.concat(TesterConfig.TEST_FILE_OUTPUT_SUFIX), diskPath.concat(TesterConfig.TEST_FILE_OUTPUT_SUFIX));
            LabTestFile lab = new LabTestFile();
            lab.setTestFileName(test.getTestFileName());
            lab.setAbsoluteTestFileURI(diskPath);
            dirFiles.add(lab);
        }

        return dirFiles;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public ArrayList<LabTestFile> generateOutPutForTests(String programPath, ArrayList<LabTestFile> susyTests) throws IOException {
        ArrayList<LabTestFile> outputFiles = new ArrayList<>();
        String baseOutputPath = TesterConfig.getTestFileDir().concat(TesterConfig.returnSeparator()).concat(TesterConfig.GENERATED_FILE);
        for (LabTestFile test : susyTests) {
            try {
                String outputFile = baseOutputPath.concat(test.getTestFileName()).concat(TesterConfig.TEST_FILE_OUTPUT_SUFIX);
                String inputPath = TesterConfig.getTestFileDir().concat(TesterConfig.returnSeparator());
                inputPath = inputPath.concat(test.getTestFileName()).concat(TesterConfig.TEST_FILE_INPUT_SUFIX);
                LabTestFile labGeneratedOut = new LabTestFile();
                labGeneratedOut.setTestFileName(TesterConfig.GENERATED_FILE.concat(test.getTestFileName()).concat(TesterConfig.TEST_FILE_OUTPUT_SUFIX));;
                labGeneratedOut.setAbsoluteTestFileURI(outputFile);
                labGeneratedOut.setAbsoluteTestFileURI(outputFile);
                outputFiles.add(labGeneratedOut);
                String splitSeparator = TesterConfig.isWindows()
                        ? TesterConfig.DOUBLE_BACK_SLASH_SEPARATOR : TesterConfig.SLASH_SEPARATOR;

                String[] programPathVet = programPath.split(splitSeparator);
                ProgramExecutor.executeProgramFromInputSaveInOutput(programPath, inputPath, outputFile, programPathVet[programPathVet.length - 1]);
            } catch (InterruptedException e) {
                throw new InterruptedByTimeoutException();
            }

        }

        return outputFiles;
    }

    private List<String> clearWindowsOutput(List<String> windowsDiff) {
        windowsDiff.remove(0);
        windowsDiff.remove(0);
        windowsDiff.add(0, TesterConfig.GENERATED_FILE_HEADER);
        for(int itemNumber=1;itemNumber<windowsDiff.size();++itemNumber){
            if(windowsDiff.get(itemNumber).contains(DOUBLE_ASTERISK)){
                if(windowsDiff.get(itemNumber).contains(TesterConfig.BACK_SLASH_SEPARATOR)){
                      windowsDiff.remove(itemNumber);
                      windowsDiff.add(itemNumber, TesterConfig.RES_FILE_HEADER);
                }
            }
        }
        return windowsDiff;
    }

    @Override
    public List<String> calculateDifference(LabTestFile expectedOutput, LabTestFile output) throws Exception {
        File expectedOutputFile, outputFile;
        expectedOutputFile = new File(TesterConfig.getTestFileDir().concat(TesterConfig.returnSeparator()).
                concat(expectedOutput.getTestFileName()).concat(TesterConfig.TEST_FILE_OUTPUT_SUFIX));
        outputFile = new File(output.getAbsoluteTestFileURI());
        output.getAbsoluteTestFileURI();
        List<String> out = ProgramExecutor.diffCommand(expectedOutputFile, outputFile);

        if (out.isEmpty()) {
            return null;
        }
        if (TesterConfig.isWindows()) {
            if (out.get(out.size() - 2).contains(TesterConfig.WINDOWS_NO_DIFFERENCES)) {
                return null;
            }
            return clearWindowsOutput(out);
        }

        return out;
    }

}

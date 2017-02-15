package controller;

import config.TesterConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramExecutor {

    private static final String UNIX_EXECUTION = "./%s";
    private static final String LINUX_DIFF = "diff";
    private static final String WINDOWS_DIFF = "FC";
    private static final String LINUX_FIRST_PARAMETER = "/bin/bash";
    private static final String LINUX_SECOND_PARAMETER = "-c";
    private static final String WINDOWS_FIRST_PARAMETER = "CMD";
    private static final String WINDOWS_SECOND_PARAMETER = "/C";

    public static void executeProgramFromInputSaveInOutput(String program, String input, String ouput, String programName) throws IOException, InterruptedException {
        programName = TesterConfig.isWindows() ? programName : String.format(UNIX_EXECUTION, programName);
        ProcessBuilder pb;
        if (TesterConfig.isWindows()) {
            pb = new ProcessBuilder(WINDOWS_FIRST_PARAMETER, WINDOWS_SECOND_PARAMETER, programName);
        } else {
            pb = new ProcessBuilder(LINUX_FIRST_PARAMETER, LINUX_SECOND_PARAMETER, programName);
        }
        String execDirStr = program.substring(0, program.lastIndexOf(TesterConfig.returnSeparator()));

        File execDir = new File(execDirStr);
        File inputFile = new File(input);
        File outpFile = new File(ouput);
        pb.redirectInput(inputFile);
        pb.redirectOutput(outpFile);
        pb.directory(execDir);
        Process process = pb.start();
        if(TesterConfig.isWindows()){
            process.waitFor();
        }
        
    }

    public static List<String> diffCommand(File original, File generated) throws IOException, InterruptedException {
        List<String> out = new ArrayList<>();

        String command;
        if (TesterConfig.isWindows()) {
            command = WINDOWS_DIFF;
        } else {
            command = LINUX_DIFF;
        }
        ProcessBuilder pb;
        if (TesterConfig.isWindows()) {
            pb = new ProcessBuilder(WINDOWS_FIRST_PARAMETER, WINDOWS_SECOND_PARAMETER,
                    command, generated.getAbsolutePath(), original.getAbsolutePath());

        } else {
            pb = new ProcessBuilder(command, generated.getAbsolutePath(), original.getAbsolutePath());
        }

        Process process = pb.start();
        process.waitFor();
        InputStream inputStream = process.getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                out.add(line);
            }
        }
        return out;
    }

}

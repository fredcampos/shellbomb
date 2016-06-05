package com.shellshock;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CommandLine {

    final static String OS = System.getProperty("os.name").toLowerCase();

    private static CommandLine cmdLine;

    private CommandLine(){
    }

    public static CommandLine instance(){
        if (cmdLine == null){
            cmdLine = new CommandLine();
        }
        return cmdLine;
    }

    public ProcessOutput executeCommand(String cmd) {
        String[] command = null;
        List<String> output = new ArrayList<String>();
        List<String> errors = new ArrayList<String>();

        if (OS.startsWith("windows")) {
            command = new String[]{"cmd", "/C", cmd};
        } else {
            command = new String[]{"/bin/sh", "-c", cmd};
        }
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        try {
            String out = null;
            while ((out = stdInput.readLine()) != null) {
                output.add(out);
            }
            String error = null;
            while ((error = stdError.readLine()) != null) {
                errors.add(error);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ProcessOutput(output, errors);
    }
}

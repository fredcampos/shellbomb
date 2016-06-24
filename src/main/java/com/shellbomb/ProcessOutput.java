package com.shellbomb;

import java.util.List;

public class ProcessOutput {
    private List<String> output;
    private List<String> errors;

    public ProcessOutput(List<String> output, List<String> errors){
        this.output = output;
        this.errors = errors;
    }

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(List<String> output) {
        this.output = output;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}

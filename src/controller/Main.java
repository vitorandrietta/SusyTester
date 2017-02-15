package controller;

import config.TesterConfig;

import view.TestSelector;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        TesterConfig.initilizeConfigs();
        new TestSelector().initialize();
    }
}

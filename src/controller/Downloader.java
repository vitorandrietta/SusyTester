package controller;

import config.TesterConfig;
import org.jsoup.Jsoup;
import java.io.IOException;

public class Downloader {

    public static void download(String url, String destination) throws IOException {
        TesterConfig.saveToFile(Jsoup.connect(url).validateTLSCertificates(false).execute().body(), destination);
    }
}

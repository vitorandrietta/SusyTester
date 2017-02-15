package controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebCrawler {

    private Document currentPage;

    public String getUrl() {
        return url;
    }

    private String url;
    private final static String ACCESS_URL_ERROR = " unable to acess url:";

    public void LoadPage(String url) throws IOException {
        try {
            this.currentPage = Jsoup.connect(url).validateTLSCertificates(false).get();
        } catch (IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(e.getMessage());
            stringBuilder.append(ACCESS_URL_ERROR);
            stringBuilder.append(url);
            throw new IOException(stringBuilder.toString());
        }

        this.url = url;

    }

    public Elements selectElements(String selector) {
        return this.currentPage.select(selector);

    }

}

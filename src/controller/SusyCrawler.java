package controller;

import config.TesterConfig;
import controller.controler_interfaces.ISusyCrawler;
import model.LabTestFile;
import model.MCclass;
import model.MClab;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class SusyCrawler implements ISusyCrawler {

    private final WebCrawler webCrawler;

    public static final String BASE_SUSY_URL = "https://susy.ic.unicamp.br:9999";
    public static final String TEST_URL_SUFIX_1 = "dados";
    public static final String TEST_URL_SUFIX_2 = "testes.html";
    private static final String ANCHOR_TAG = "a";
    private static final String CLASS_PREFIX = "mc";
    private static final String HREF_ATTR = "href";
    private static final String TABLE_TAG = "table";
    private static final String BLOCK_QUOTE = "blockquote";
    private static final String BLOCKQUOTE_SECOND_CHILD = "blockquote > blockquote";
    private static final String EXTENSION_DOT = ".";

    public SusyCrawler() {
        webCrawler = new WebCrawler();
    }

    @Override
    public ArrayList<MCclass> mcClasses() throws IOException {
        this.webCrawler.LoadPage(BASE_SUSY_URL);
        Elements elements = webCrawler.selectElements(ANCHOR_TAG);
        ArrayList<MCclass> mcList = new ArrayList<>();
        for (Element e : elements) {
            MCclass mcClass;
            if (e.text().toLowerCase().contains(CLASS_PREFIX)) {
                mcClass = new MCclass();
                mcClass.setMcClassName(e.text());
                mcClass.setMcClassURL(e.attr(HREF_ATTR));
                mcList.add(mcClass);
            }
        }
        return mcList;
    }

    @Override
    public ArrayList<MClab> mcLabs(String mcClass) throws IOException {
        ArrayList<MClab> labs = new ArrayList<>();
        this.webCrawler.LoadPage(BASE_SUSY_URL.concat(TesterConfig.SLASH_SEPARATOR).concat(mcClass));
        Elements elements = this.webCrawler.selectElements(TABLE_TAG).select(ANCHOR_TAG);
        for (Element e : elements) {
            MClab mclab = new MClab();
            mclab.setMcLabName(e.text());
            String[] urlV = e.attr(HREF_ATTR).split(TesterConfig.SLASH_SEPARATOR);
            mclab.setMcLabURL(urlV[urlV.length - 1]);
            labs.add(mclab);
        }
        return labs;
    }

    @Override
    public ArrayList<LabTestFile> labTests(String mcClass, String lab) throws IOException {
        String baseTestsUrl = BASE_SUSY_URL.concat(TesterConfig.SLASH_SEPARATOR).concat(mcClass);
        baseTestsUrl = baseTestsUrl.concat(TesterConfig.SLASH_SEPARATOR).concat(lab).concat(TesterConfig.SLASH_SEPARATOR);
        baseTestsUrl = baseTestsUrl.concat(TEST_URL_SUFIX_1);
        baseTestsUrl = baseTestsUrl.concat(TesterConfig.SLASH_SEPARATOR);
        ArrayList<LabTestFile> labTests = new ArrayList<>();
        this.webCrawler.LoadPage(baseTestsUrl.concat(TEST_URL_SUFIX_2));
        Element element = this.webCrawler.selectElements(BLOCK_QUOTE).get(0);
        element = element.select(BLOCKQUOTE_SECOND_CHILD).get(0);
        Elements elements = element.select(ANCHOR_TAG);

        for (Element e : elements) {
            if (e.text().endsWith(TesterConfig.TEST_FILE_INPUT_SUFIX)) {
                LabTestFile labTest = new LabTestFile();
                String currentTagText = e.text();
                String fileName = currentTagText.substring(0, currentTagText.lastIndexOf(EXTENSION_DOT));
                labTest.setTestFileName(fileName);
                labTest.setAbsoluteTestFileURI(baseTestsUrl.concat(fileName));
                labTests.add(labTest);

            }
        }

        return labTests;

    }

}

package model;

public class LabTestFile {
    public String getTestFileName() {
        return testFileName;
    }

    public void setTestFileName(String testFileName) {
        this.testFileName = testFileName;
    }

    public String getAbsoluteTestFileURI() {
        return absoluteTestFileURI;
    }

    public void setAbsoluteTestFileURI(String absoluteTestFileURI) {
        this.absoluteTestFileURI = absoluteTestFileURI;
    }

    private String testFileName;
    private String absoluteTestFileURI;
}

package com.estyle.httpmock.common;

public class MockEntity {

    private long delayMillis;
    private boolean enable;
    private String fileName;
    private String url;

    public long getDelayMillis() {
        return delayMillis;
    }

    public void setDelayMillis(long delayMillis) {
        this.delayMillis = delayMillis;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MockEntity{" +
                "delayMillis=" + delayMillis +
                ", enable=" + enable +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

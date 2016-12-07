package esir3.vv;

import java.util.ArrayList;
import java.util.List;

public class FuzzingData {

    private String hostname;
    private String contentType;
    private List<UrlInfo> urls;

    private static FuzzingData instance = null;

    static FuzzingData getInstance() {
        if (instance == null) {
            instance = new FuzzingData();
        }
        return instance;
    }

    private FuzzingData() {
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<UrlInfo> getUrlInfos() {
        return urls;
    }

    public void setUrls(List<UrlInfo> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        List<String> result = new ArrayList<>();
        for (UrlInfo path : urls) {
            result.add(path.getLink());
        }
        return result;
    }

}

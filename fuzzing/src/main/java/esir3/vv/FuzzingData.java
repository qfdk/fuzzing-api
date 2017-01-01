package esir3.vv;

import java.util.ArrayList;
import java.util.List;

public class FuzzingData {

    private String hostname;
    private String contentType;
    private List<UrlInfo> urls;

    private static FuzzingData instance = null;

    /**
     * get instance of fuzzing Data.
     * @return instance of fuzzing
     */
    static FuzzingData getInstance() {
        if (instance == null) {
            instance = new FuzzingData();
        }
        return instance;
    }

    private FuzzingData() {
    }

	/**
	 * hostName
	 * @return hostname
	 */
	public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

	/**
	 * ContentType
	 * @return ContentType
	 */
	public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

	/**
	 * Urlinfos
	 * @return list of UrlInfo
	 */
	public List<UrlInfo> getUrlInfos() {
        return urls;
    }

    public void setUrls(List<UrlInfo> urls) {
        this.urls = urls;
    }

}

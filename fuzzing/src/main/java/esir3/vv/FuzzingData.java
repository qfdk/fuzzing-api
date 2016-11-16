package esir3.vv;

import java.util.List;

public class FuzzingData {
	
	private String hostname;
	private String contentType;
	private List<String> paths;
	
	public FuzzingData() {
	}
	
	public FuzzingData(String hostname, String contentType, List<String> paths) {
		this.hostname = hostname;
		this.contentType = contentType;
		this.paths = paths;
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
	public List<String> getPaths() {
		return paths;
	}
	public void setPaths(List<String> paths) {
		this.paths = paths;
	} 
	
}

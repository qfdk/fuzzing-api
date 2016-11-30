package esir3.vv;

import java.util.ArrayList;
import java.util.List;

public class FuzzingData {
	
	private String hostname;
	private String contentType;
	private List<DataPath> paths;

	private static FuzzingData instance=null;

	static FuzzingData getInstance() {
		if (instance == null) {
			instance = new FuzzingData();
		}
		return instance;
	}

	private FuzzingData() {
	}
	
	private FuzzingData(String hostname, String contentType, List<DataPath> paths) {
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
	public List<DataPath> getPaths() {
		return paths;
	}
	public void setPaths(List<DataPath> paths) {
		this.paths = paths;
	}

	public List<String> getLink() {
		List<String> result = new ArrayList<>();
		
		for (DataPath path : paths) {
			result.add(path.getLink());
		}
		return result;
	} 
}

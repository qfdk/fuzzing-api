package esir3.vv;

import java.util.HashSet;
import java.util.Set;


/**
 * @author maiga
 *
 */
public class DataPath {

	private String link;
	private Set<String> code;
	
	public DataPath() {
		code = new HashSet<>();
	}

	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public Set<String> getCode() {
		return code;
	}
	
	public void setCode(Set<String> code) {
		this.code = code;
	}
}


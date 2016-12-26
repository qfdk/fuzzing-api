package esir3.vv;

import java.util.HashSet;
import java.util.Set;


/**
 * @author maiga
 *
 */
public class UrlInfo {

	private String link;
	private Set<String> codes;
	private boolean valided;
	private String reponseCode;
	private String operationType;
	private String postParameters;
	
	
	public UrlInfo() {
		this.codes = new HashSet<>();
		this.setValided(false);
		this.reponseCode="FFF";
	}

	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public Set<String> getCodes() {
		return codes;
	}
	
	public void setCodes(Set<String> code) {
		this.codes = code;
	}



	public void setReponseCode(String reponseCodecode)
	{
        this.reponseCode=reponseCodecode;
		if (codes.contains(reponseCodecode))
		{
			this.setValided(true);
		}
	}

	public String getReponseCode()
    {
        return this.reponseCode;
    }

	public boolean isValided() {
		return valided;
	}

	public void setValided(boolean valided) {
		this.valided = valided;
	}



	public String getPostParameters() {
		return postParameters;
	}

	public void setPostParameters(String postParameters) {
		this.postParameters = postParameters;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
	@Override
	public String toString() {
		
		return "("+operationType+")=>"+link;
	}
}


package esir3.vv;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * @author maiga
 */
public class UrlInfo {

	private String link;
	private Set<String> codes;
	private boolean valided;
	private String reponseCode;
	private String operationType;
	private Map<String, String> params;
	private Map<String, String> paramNameAndType; //  A Map with parameter as key and type as value

	private String reponseBody;

	/**
	 * Info of un url
	 * (codes,valided reponseCode,operationType,params)
	 */
	public UrlInfo() {
		this.codes = new HashSet<>();
		this.setValided(false);
		this.reponseCode = "FFF";
		this.params = new TreeMap<>();
		this.paramNameAndType = new HashMap<>();
	}

	/**
	 * @return Link generated for the url with paramater
	 */
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


	public void setReponseCode(String reponseCodecode) {
		this.reponseCode = reponseCodecode;
		if (codes.contains(reponseCodecode)) {
			this.setValided(true);
		}
	}

	public String getReponseCode() {
		return this.reponseCode;
	}

	/**
	 * @return True if our response is in spec reponse set
	 */
	public boolean isValided() {
		return valided;
	}

	public void setValided(boolean valided) {
		this.valided = valided;
	}


	public Map<String, String> getParameters() {
		return params;
	}

	public void setParameters(Map<String, String> parameters) {

		this.params = parameters;
	}

	/**
	 * @return OperationType : PUT, POST, GET, DELETE
	 */
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {

		this.operationType = operationType;
	}

	@Override
	public String toString() {

		return "(" + operationType + ")=>" + link;
	}

	public String getReponseBody() {
		return reponseBody;
	}

	public void setReponseBody(String reponseBody) {
		this.reponseBody = reponseBody;
	}

	/**
	 * A Map with parameter as key and type as value
	 * @return the paramNameAndType
	 */
	public Map<String, String> getParamNameAndType() {
		return paramNameAndType;
	}

	/**
	 * @param paramNameAndType the paramNameAndType to set
	 */
	public void setParamNameAndType(Map<String, String> paramNameAndType) {
		this.paramNameAndType = paramNameAndType;
	}
}


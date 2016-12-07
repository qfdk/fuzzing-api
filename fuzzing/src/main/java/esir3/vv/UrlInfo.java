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

	public UrlInfo() {
		this.codes = new HashSet<>();
		this.valided=false;
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

	private void setValided(boolean valided)
	{
		this.valided=valided;
	}

	public void setReponseCode(String reponseCodecode)
	{
        this.reponseCode=reponseCodecode;
		if (codes.contains(reponseCodecode))
		{
			this.valided=true;
		}
	}

	public String getReponseCode()
    {
        return this.reponseCode;
    }

	public boolean getValided()
    {
        return this.valided;
    }
}


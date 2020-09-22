package sweet_home.beans;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="Endereco")
@RequestScoped
public class Utils {

	protected static String resp = "";
	
	public String getResp() {
        return resp;
    }
    
    public void setResp(String resp) {
        this.resp = resp;
    }
}

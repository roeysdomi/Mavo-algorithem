import java.util.ArrayList;

public class Var {
    public String var;
    public ArrayList<String>values;
    public ArrayList<String>parents;
    public ArrayList<String>cpt;
    public String notincpt="";
    
    /**
     * this class kinda explain itself .
     * its discribe the detail about any var
     */
    
    public Var()
    {
    	var="";
    	values=new ArrayList<String>();
    	parents=new ArrayList<String>();
    	cpt=new ArrayList<String>();
    }
	public String getVar() {
		return var;
	}


	public void setVar(String var) {
		this.var = var;
	}


	public ArrayList<String> getValues() {
		return values;
	}


	public void setValues(ArrayList<String> values) {
		this.values = values;
	}


	public ArrayList<String> getParents() {
		return parents;
	}


	public void setParents(ArrayList<String> parents) {
		this.parents = parents;
	}


	public ArrayList<String> getCpt() {
		return cpt;
	}


	public void setCpt(ArrayList<String> cpt) {
		this.cpt = cpt;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String f="[B=true]*[E]*[A=false|B=true,E]*[J=true|A=false]*[M=true|A=false]";
        f=f.replaceAll("E", "fdgdfg");
        System.out.println(f);
	}

}

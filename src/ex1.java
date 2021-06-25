import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.script.ScriptException;

public class ex1 {

	public static void main(String[] args) throws IOException, NumberFormatException, ScriptException {
		// TODO Auto-generated method stub
		
		ArrayList<String> results = new ArrayList<String>();

       
			
		        Read r=new Read();
		      
		        String n="input";
		       
				r.file=n;
				r.ReadString(r.Read2string(""));
				Algo1 f=new Algo1(r);
			  
				f.readquer();
				
				for(String w:Algo1.printresult)
			    {
			    	System.out.println(w);
			    }
				f.write2file(r.file);
					Algo1.printresult=new ArrayList<>();
			
		
			

	}

}

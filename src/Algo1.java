import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.omg.Messaging.SyncScopeHelper;

public class Algo1 {
	/**
	 * this class handle all the process of algo1
	 * at first its create the String of the querys
	 * convert them to numbers and sum it all up.
	 * and its also direct the query to run on algo2 and 3 .
	 */
	Read r2;
	String filename;
	//--------------------
	public ArrayList<String>willchange;
	
	
	//----------query--------------
	public ArrayList<String>subquery=new ArrayList<String>();
	 // String fullquery="";
	  String firstpart="";
	  String secondpart="";
	  //---------------------------
	  public ArrayList<String>optionwillchange=new ArrayList<String>();
	  
	//----------open query-------------------
	  public ArrayList<String>first=new ArrayList<String>();
	  //----------final list-------------------
	  public ArrayList<String>ready=new ArrayList<String>();
	  public ArrayList<String>ready2=new ArrayList<String>();
	  public static ArrayList<String>printresult=new ArrayList<String>();
	  
	public static void main(String[] args) throws IOException, NumberFormatException, ScriptException {
		// TODO Auto-generated method stub
		///---readquer-->createwillchange-->createoption-->createbasic-->addfirst2basic-->addoption2basic
		ArrayList<String> results = new ArrayList<String>();


		File[] files = new File(System.getProperty("user.dir")).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 

		for (File file : files) {
		    if (file.isFile()) {
		    	if(file.getName().contains("txt"))
		    	{
		        results.add(file.getName());
		    	}
		    }
		}
		for(String n:results)
		{
			System.out.println(n);
		
	        Read r=new Read();
	       // //System.out.println();
	        n=n.replace(".txt", "");
			r.file=n;
			r.ReadString(r.Read2string(""));
			Algo1 f=new Algo1(r);
		    f.algo1("P(B=true|J=true,M=false)");
			/*
			f.readquer();
			//System.out.println("------------------------");
			for(String w:printresult)
		    {
		    	System.out.println(w);
		    }
			f.write2file(r.file);
				printresult=new ArrayList<>();
				*/
		    break;
		}
			
			
		
		
		
	}
     
	public Algo1()
	{
		
	}
	public Algo1(Read r)
	{
		
		this.r2=r;
		resetall();
	}
	public void  readquer() throws NumberFormatException, ScriptException
	{
		/**
		 * handle all algorithms by type of query
		 */
		for(String s:r2.Queries)
		{   
			if(s.contains(",1"))
			{
			    algo1(s);
			}
			if(s.contains(",2"))
			{
				Algo2.ordertype=2;
				s=s.substring(0, s.length()-2);
				Read r=new Read();
				try {
					r.ReadString(r.Read2string(""));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Algo2 al=new Algo2(r);
				al.algo2(s);
				
			}
			if(s.contains(",3"))
			{
				Algo2.ordertype=3;
				s=s.substring(0, s.length()-2);
				Read r=new Read();
				try {
					r.ReadString(r.Read2string(""));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Algo2 al=new Algo2(r);
				al.algo2(s);
			}
		}
		
	}
	
    //--------create print
	/**
	 * create the string of the query
	 * @param s
	 */
	public void createsubquery(String s)
	{
		/**
		 * handle one query at a time
		 */
		subquery=new ArrayList<String>();
		
		if(s.contains("|"))
		{
			s=s.replace("P", "").replace("(", "").replace(")", "").replaceAll("\\|", "#");
			
			
			String []fo=s.split("#");
			firstpart=fo[0];
			secondpart=fo[1];
			String letter=fo[0].substring(0, 1);
		    for(String d:r2.tree.get(letter).values)
		    {
		    	String w=letter+"="+d+","+fo[1];
		    	subquery.add(w);
		    }
			
			

		}
	}
	public void  createwillchange(String part)
	{
		/**
		 * create list of vars that will change(hidden)
		 */
		willchange=new ArrayList<String>();
		for(String check:r2.Variables)
		{
			
		
		    
			if(!part.contains(check))
			{
				willchange.add(check);
			}
			else
			{
				
			}
			
		}
		createoptions();
	}
	public int lines()
    {
		/**
		 * count lines
		 */
		int f=1;
    	for(String v:willchange)
    	{
    		int d=r2.tree.get(v).values.size();
    	   f=f*d;	
    	}
    	
    	return f;
    }
    public void createbasic(String part)
    {
    	/**
    	 * create the basic lines of the quers before the inset of the will change vars
    	 */
        int line=lines();
    	for(int i=0;i<line;i++) 
    	{
	    	String b="";
	    	for(String z:r2.Variables)
	    	{
	    		
	    		if(r2.tree.get(z).parents.get(0).contains("none"))
	    		{
	    			b=b+"*["+z+"]";
	    		}
	    		else 
	    		{
	    			String e="["+z+"|";
	    			for(String w:r2.tree.get(z).parents)
	    			{
	    				e=e+w+",";
	    			}
	    			e=e+"]";
	    			b=b+"*"+e;
	    			
	    		}
	    		
	    	}
	    	b=b.substring(1,b.length()).replace(",]", "]");
	    	first.add(b);
    	}
    	String current=addfirst2basic(part);
    	first=new ArrayList<String>();
    	for(int i=0;i<line;i++)
    	{
    		first.add(current);
    	}
    	if(willchange.size()==0) {ready=first;}
    	if(willchange.size()!=0) {addoption2basic();}
    	
    }
    public String addfirst2basic(String part)
    {
          /**
           * put the vars with values in the string	
           */
    	String currentbasic=first.get(0);
    	String []split=part.split(",");
    	for(String v:split)
    	{
    		String []spl=v.split("=");
    		currentbasic=currentbasic.replaceAll(spl[0], spl[0]+"="+spl[1]);
    	}
    	return currentbasic;
    }
    public void createoptions()
    {
    	/**
    	 * create all the possible option for each will change
    	 */
    	if(willchange.size()==0) {return;}	
    	optionwillchange=new ArrayList<String>();
    	int line=lines();
    	
    	int counter=0;
    	while (counter<line)
    	{
    		String opti=randomoption();
    		
    		if(!optionwillchange.contains(opti))
    		{
    			optionwillchange.add(opti);
    			counter++;
    		}
    	}
    }
    public String randomoption()
    {
    	/**
    	 * create random option
    	 */
    	String result="";
    	for(String g:willchange)
    	{
    		int e=r2.tree.get(g).values.size();
    		
    		String mako=r2.tree.get(g).values.get(new Random().nextInt(e));
    		result=result+","+g+"="+mako;
    	}
    	////System.out.println(result);
    	result=result.substring(1,result.length());
    	return result;
    }
    public void addoption2basic()
    {
    	String main =first.get(0);
    	String bla="";
    	for(String g:optionwillchange)
    	{
    		String []split=g.split(",");
    		 bla=new String (main);
    		for(String f:split)
    		{
    			String []o=f.split("=");
    			
    		    bla=bla.replaceAll(o[0], o[0]+"="+o[1]);
    			
    		}
    		ready.add(bla);
    		bla="";
    	}
    	
    }
    //--------------------------
    /**
     * transform query to numbers
     */
    public void ready2()
    {
    	/**
    	 * start converting query to number values
    	 */
    	String main ="";
    	for(String g:ready)
    	{
    		//System.out.println(g);
    	    //g.replaceAll("*", "!");
    		//System.out.println(g);
    		String []split=g.split("\\*");
    		for(String b:split)
    		{
    			if(b.contains("|"))
    			{
    				
    				main=main+"*"+yesline(b);
    			}
    			if(!b.contains("|"))
    			{
    				
    				main=main+"*"+noline(b);
    			}
    			
    			
    			
    		}
    		main=main.substring(1,main.length());
    		ready2.add(main);
    		main="";
    		
    	}
    }
    public Double yesline(String s)
    
    {
    	/**
    	 * in case the value is in the line (in some cases its not)
    	 */
    	s=s.replace("]", "").replace("[", "");
    	String []split=s.split("\\|");
    	///-------------------------
    	String letter=split[0].substring(0, 1);
    	String lettervalue=","+split[0].substring(1, split[0].length());
    	//----------------------------------
    	for(String f:r2.tree.get(letter).cpt)
    	{
    		
    		if(f.contains(split[1])&f.contains(lettervalue))
    		{
    			String m=f.substring(f.indexOf(",="),f.length());
    			
    			String []go=m.split(",");
    			for(int i=0;i<go.length;i++)
    			{
    				
    				if(go[i].contains(lettervalue.substring(2,lettervalue.length())))
    				{
    					
    					return Double.valueOf(go[i+1]);
    				}
    				
    			}
    			
    		}
    		if(f.contains(split[1])&!f.contains(lettervalue))
    		{
    			
    			String e="";
    			for(String d:r2.tree.get(letter).values)
    			{
    				e=e+d;
    			}
    			String m=f.substring(f.indexOf(",="),f.length()).replace("=", "");
    			String []go=m.split(",");
    			double num=1;
    			for(String z:go)
    		   
    			{
    				if(!e.contains(z))
    				{
    					
    					double q=Double.valueOf(z);
    					num=num-q;
    				}
    			}
    			
    			return num;
    		}
    		
    		
    		
    		
    	}
    	
    	return 0.9999999;
    }
    public Double noline(String s)
    {
    	/**
    	 * in case the value is not in the line need to calculate the missing value
    	 */
    	
    	s=s.replace("]", "").replace("[", "");
    	
    	String letter=s.substring(0, 1);
    	
    	String value=s.substring(1,s.length());
    	
    	for(String f:r2.tree.get(letter).cpt)
    	{
    		
    		if(f.contains(value))
    		{	
    			
    			String m=f.replace("=", "");
    			String go[]=m.split(",");
    			for(int i=0;i<go.length;i++)
    			{
    				
    				if(go[i].contains(value.replace("=", "")))
    				
    				{
    					
    					return Double.valueOf(go[i+1]);
    				}
    				
    			}
    		}
    		if(!f.contains(value))
    		{
    			
    			String e="";
    			for(String d:r2.tree.get(letter).values)
    			{
    				e=e+d;
    			}
    			///---------------
    			
    			String m=f.replace("=", "");
    			String []go=m.split(",");
    			double num=1;
    			for(String z:go)
    			{
    				if(!e.contains(z))
    				{
    					double q=Double.valueOf(z);
    					num=num-q;
    				}
    				
    			}
    			return num;
    		}
    		
    		
    		
    		
    	}
    	
    	return 0.999999;
    }
    public double resultready2() throws NumberFormatException, ScriptException
    {
    	/**
    	 * create the final number result
    	 */
    	double t=0;
    	
    	ScriptEngineManager m= new ScriptEngineManager();
    	ScriptEngine en=m.getEngineByName("JavaScript");
    	for(String g:ready2)
    	{
    		
    		////System.out.println(g);
    		String[]split=g.split("\\*");
    		double beta=1;
    		/*
    		for(String x:split)
    		{
    			double c =Double.valueOf(x);
    			beta=beta*c;
    		}
    		*/
    		
    		t=t+(double)en.eval(g);
    	}
    	return t;
    }
    ///-------------------------
    
    public void algo1(String s) throws NumberFormatException, ScriptException
    {
    	DecimalFormat df=new DecimalFormat("#.#####");
		df.setRoundingMode(RoundingMode.HALF_EVEN);
    	resetall();
    	s=s.substring(0, s.length()-2);
		createsubquery(s);
		double sum=0;
		double remember=0;
		int line=0;
		for(String g:subquery)
		{
			System.out.println(g);
			createwillchange(g);
			createbasic(g);
			ready2();
			Double po=resultready2();
			
			
			if(g.contains(firstpart)&g.contains(secondpart)) {remember=po;}
			sum=sum+po;
			line=line+lines();
			
			resetall();
		}
		//double dok=remember/sum;
		
		double dok =remember/sum;
		String fiveres=String.valueOf(df.format(dok));
		while(fiveres.length()<7)
    	{
    		fiveres=fiveres+"0";
    	}
		String toprint=fiveres+","+(line-1)+","+(r2.Variables.size()-1)*line;
		printresult.add(toprint);
		////System.out.println("this is result:"+toprint);
		////System.out.println("-----------------------------------");
		
		}
    
    //----------------------------
    public void write2file(String filename) throws FileNotFoundException, UnsupportedEncodingException
    {  
    	 filename=filename.replace("input", "output");
    	PrintWriter writer = new PrintWriter(filename+".txt", "UTF-8");
    	for(String w:printresult)
	    {
    		writer.println(w);
	    }
    	
    	writer.close();
    }
    //--------------------------
    public void resetall()
    {
    	first=new ArrayList<String>();
    	willchange=new ArrayList<String>();
    	ready=new ArrayList<String>();
    	ready2=new ArrayList<String>();
    	optionwillchange=new ArrayList<String>();
    	
    }
}

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Spliterator;

import javax.swing.text.html.HTMLDocument.Iterator;



public class Read {
	/**
	 * this is class thats handle all the basic process of 
	 * reading and setting the vars ,tree(all the letters of the vars),queries.
	 * its prepare everything for the algorhtims to work.
	 */
	public HashMap<String, Var> tree = new HashMap<String, Var>();
	public ArrayList<String>Variables=new ArrayList<String>();;
	public  ArrayList<String>Queries=new ArrayList<String>();
    public static String file="";
	public static void main(String[] args) throws IOException {
		Read r=new Read();
		
		r.ReadString(r.Read2string(""));
		for(Var s:r.tree.values())
		{
			System.out.println(s.cpt.get(0));
		}
		
		
		/*
		for(Var value:r.tree.values())
		{
			
			for(String f:value.getCpt())
			{ System.out.println(f);}
			System.out.println("-------------");
		}
		*/
		
	}
	public Read()
	{
		//Variables=new ArrayList<String>();
		
	}
	
    public String Read2string(String Location) throws IOException
	{
    	/**
    	 * convert al text to string foramt
    	 */
		String everything="";
		BufferedReader br = new BufferedReader(new FileReader(file+".txt"));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		     everything = sb.toString();
		    
		} finally {
		    try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return everything ;
	}
    public  void ReadString(String s)
    {
    	Scanner scanner = new Scanner(s);
    	Var point=new Var();
    	while (scanner.hasNextLine()) {
    	  String line = scanner.nextLine();
    	  
    	  
    	    if(line.contains("Variables"))
    	    {
    	    	ifVariables(line);
    	    }
    	    if(line.contains("Var")&!line.contains("Variables"))
    	    {
    	    	
    	    	point=new Var();
    	    	String letter=ifvar(line);
    	    	point.var=letter;
    	    	tree.put(letter, point);
    	    	
    	    }
    	    
    	    if(line.contains("Values"))
    	    {
    	    	for(String f:ifvalues(line))
    	    	{
    	    		
    	    		point.values.add(f);
    	    	}
    	    }
    	    
    	    if(line.contains("Parents"))
    	    {
    	    	for(String f:ifparents(line))
    	    	{
    	    		point.parents.add(f);
    	    	}
    	    }
    	    
    	   if(line.contains("=")&!line.contains("("))
    	   {
    		   ifcpt(line, point);
    	   }
    	   if(line.contains("P("))
    	   {
    		   Queries.add(line);
    	   }
    	  
    	  // process the line
    	}
    	scanner.close();
    }
    //-----------if--------------
    /**
     * if the line contain one of the vars details you put it in one of the function and you get 
     * only the output you need for the class var.
     * (the same from all the function from here..)
     * @param s
     * @return
     */
    public  String ifvar(String s)
    {
    	String[]split=s.split(" ");
    	return split[1];
    }
    public  String[] ifvalues(String s)
    {   
    	
    	s=s.replaceAll("Values: ", "").replaceAll(",", "");
    	String[]split=s.split(" ");
    	
    	return split;
    }
    public  String[] ifparents(String s)
    {   
    	s=s.replaceAll("Parents: ", "");
    	String[]split=s.split(",");
    	return split;
    }
    public  void ifcpt (String s,Var v)
    {   
    	checkifincpt(s, v);
    	int counter=0;
    	String []split=s.split(",");
    	String result=s.substring(s.indexOf("="), s.length());
    	//System.out.println(result);
    	String main="";
    	//System.out.println(tree.size());
    	//System.out.println(v.parents.size());
    	if(!v.parents.contains("none"))
    	{
    	for(String g:v.parents)
    	{
    		
    		main=main+g+"="+split[counter++]+",";
    	}
    	}
    	if(v.parents.contains("none")) {main=main+",";}
    	main=main+result;
    	v.cpt.add(main);
    }
    public  void ifVariables(String s)
    {
    	
    	s=s.replaceAll("Variables: ", "");
    	String []split =s.split(",");
    	for(String b:split)
    	{
    		//System.out.println("this is value:"+b);
    		 Variables.add(b);
    	}
    } 
    public  void checkifincpt(String s,Var V)
    {
    	for(String c:V.values)
    	{
    		c=",="+c;
    		if(!s.contains(c))
    		{
    			V.notincpt=c.replace(",=", "");
    		}
    	}
    }
    ///---------ready2-----------
    
    
}

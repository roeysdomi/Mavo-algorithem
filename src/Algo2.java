import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Spliterator;

public class Algo2 {
	Read r2;
	/**
	 * handle all the algo2 idea, there is an  explantion in any function and before bunch of functions
	 * this class also use the algo3 since its same the same thing but diffrent order.
	 */
	//--------------------
	public ArrayList<String>otherfactor=new ArrayList<String>();
	public ArrayList<String>hiddenfactor=new ArrayList<String>();
	
    //-------------------
	public HashMap<String, Var> tree2 = new HashMap<String, Var>();
	public ArrayList<String> Variables2=new ArrayList<String>();
	public ArrayList<String> generalVariables2=new ArrayList<String>();
	public String safeletter="";
	public String safequery="";
	public static int sumcounter=0;
	public static int multiplaycounter=0;
	//--------------------
	public static int ordertype=2;
	public static void main(String[] args) throws IOException  {
		Read r=new Read();
	      
			r.file="input6";
			//r.file="INPUT";
		    // r.file="INPUT2";
			r.ReadString(r.Read2string(""));
			Algo2 al=new Algo2(r);
			
			////System.out.println(al.Variables2.toString());
			
			
			//al.algo2("P(B=true|J=true,M=true)");
		al.algo2("P(D=true|A=true)");
		  // al.algo2("P(A=true|B=true)");
			
		
			
			
			
	}
   
	/**
	 * handle the cpts,reset before new action, and fix cpts after reading querys
	 * @param r
	 */
	public Algo2(Read r)
	{  
		this.r2=r;
		
		
	}
	public void resetanddupli()
	{
		/**
		 * reset all the objects for new use
		 */
		resetall();
		this.tree2=r2.tree;
		this.Variables2=r2.Variables;
		this.safeletter="";
		safequery="";
		sumcounter=0;
		multiplaycounter=0;
		
	}
	public void  resetall()
	{
		/**
		 * reset all the objects for new use
		 */
		tree2 = new HashMap<String, Var>();
		Variables2=new ArrayList<String>();
		otherfactor=new ArrayList<String>();
		hiddenfactor=new ArrayList<>();
		
	}
	public ArrayList<String> ordervarli(ArrayList<String> cpt)
	{    
		/**
		 * ordering all the letters by the ABC..
		 */
		char[] charArray = cpt.toString().toCharArray();
		Arrays.sort(charArray);
		String sortedString = new String(charArray);
		 
		sortedString=sortedString.replace(",", "").replace("[", "").replace("]", "").replace(" ", "");
		String split []=sortedString.split("|");
		
		
		cpt=new ArrayList<>();
		for(String g:split)
		{
			cpt.add(g);
		}
		return cpt;
		
	}
    public void orgnizedallcpt()
			
	{
    	/**
    	 * orgnize cpt of every vars in lines
    	 */
		for(String g:generalVariables2)
		{
			orgnizedcpt(g);
		}
		
	}
    public void orgnizedcpt(String letter)
	{
    	/**
    	 * orgnize cpt of every vars in lines
    	 */
		ArrayList<String> tempcpt=new ArrayList<String>();
		

		
		
		for(String g:tree2.get(letter).getCpt())
		{
			
			for(String vals:tree2.get(letter).getValues())
			{
				 vals=",="+vals;
				 
				 
				 
				 if(g.contains(vals))
				 {
					 String m=g.substring(g.indexOf(",=")+1,g.length());
					 String z=g.substring(0,g.indexOf(",="));
					
					 String split []=m.split(",");
					 
					 for(int i=0;i<split.length;i++ )
					 {
						 //--build the string for cpt
						
						 if(vals.contains(split[i]))
						 {
							String cpt=z+","+letter+split[i]+","+split[i+1];
							 if(cpt.substring(0,1).equals(",")) {cpt=cpt.substring(1,cpt.length());}
							tempcpt.add(cpt);
							
							i=split.length;
						 }
						 
					 }
					 
					 
				 }
				 if(!g.contains(vals))
				 {
					
					 String m=g.substring(g.indexOf(",=")+1,g.length());
					 String z=g.substring(0,g.indexOf(",="));
					
					 String split []=m.split(",");
					 double num=1;
					 for(int i=0;i<split.length;i++ )
					 {
						 
						 
						 if(!tree2.get(letter).getValues().contains(split[i].replace("=", "")))
						 {
							double current=Double.valueOf(split[i]);
							num=num-current;
						 }
						 
					 }
					 
					 vals=vals.replace(",=", "");
					 String cpt=z+","+letter+"="+vals+","+num;
					 if(cpt.substring(0,1).equals(",")) {cpt=cpt.substring(1,cpt.length());}
					 tempcpt.add(cpt);
					 
					 
				 }
				
				
				
			}
			
		}
		
		
		tree2.get(letter).setCpt(tempcpt);
		
	}
	public void fixcptquery(String letter)
	{
		/**
    	 * orgnize cpt of every vars in lines
    	 */
		for(String l:generalVariables2)
		{
			
			
				for(int i=0;i<tree2.get(l).cpt.size();i++)
				{
					    
					if(!tree2.get(l).cpt.get(i).contains(letter)&tree2.get(l).cpt.get(i).contains(letter.substring(0, 1)))
                      {
						
						tree2.get(l).cpt.remove(i);i=-1;
                      }
					
				}
				
		}
		
	}
    ///---------------------
	/**
	 * handle all the procces of removing unessery vars
	 * @param main
	 * @return
	 */
	public ArrayList<String> haskids(String main)
	{
		/**
		 * check if he has kids and if his  a leaf
		 */
		ArrayList<String> sons=new ArrayList<String>();
		for(String v: Variables2)
		{
			if(!v.equals(main))
			{
					for(String g:r2.tree.get(v).parents)
					{
						if(g.equals(main)) {sons.add(v);}
					}
			}
		}
		
		return sons;
		
	}
    public boolean isleaf(String main)
    {
    	/**
		 * check if he has kids and if his  a leaf
		 */
    	if(haskids(main).size()>0) {return false;}
    	else 
    		return true;
    }
    public void  removevars()
    {
    	/**
    	 * remove uneccesey vars
    	 */
    	for(int i=0;i<Variables2.size();i++)
    	{
    		
    		if(isleaf(Variables2.get(i))&!safequery.contains(Variables2.get(i)))
    		{
    			
    			Variables2.remove(i);i=-1;}
    	}
    	
    	
    }
    public ArrayList<ArrayList<String>> orderbylistsize(ArrayList<ArrayList<String>> m)
    {
    	/**
    	 * order all the vars by cpt size before te duplicate 
    	 */
    	for(int i=0;i<m.size();i++)
    	{
    		ArrayList<String> first=m.get(i);
    		int firstsize=first.size();
    		for(int p=i+1;p<m.size();p++)
    		{ 
    			ArrayList<String> second=m.get(p);
    			int secondsize=second.size();
    			if(firstsize>secondsize)
    			{
    				
    				m.set(p, first);
    				m.set(i,second);
    				firstsize=0;
    				firstsize=secondsize;
    				first=second;
    			}
    			
    		}
    		
    	}
    	return m;
    }
    //-----------------------
    
    //----------------------
    /**
     * all the procces of join and sum out
     * @param var1
     * @param var2
     * @return
     */
    public ArrayList<String> mutualvars(ArrayList<String> var1,ArrayList<String> var2)
    {  
    	/**
    	 * check whats the mutal vars in order to duplicate as we do in join 
    	 */
    	ArrayList<String> mu=new ArrayList<String>();
        
         String split[]=var1.get(0).split(",");
         for(int i=0;i<split.length-1;i++)
         {
        	 String g=split[i].substring(0, 1);
        	 if(var2.get(0).contains(g))
        	 {
        		
        		 mu.add(g);
        	 }
         }
  
    	return mu;
    }
    public ArrayList<String> cptline2list(String cpt,ArrayList<String> list)
    {
    	/**
    	 * take cpt line and create list from it
    	 */
    	ArrayList<String> temp=new	ArrayList<String>();
    	for(String g:list)
    	{
    	
    		String val=cpt.substring(cpt.indexOf(g), cpt.indexOf(",", cpt.indexOf(g)+1));
    		temp.add(val);
    	}
    	return temp;
    }
    public boolean linesequals(ArrayList<String> list,String cpt)
    {
    	/**
    	 * check if that line fit to duplicate
    	 */
    	for(String g: list)
    	{
    		if(!cpt.contains(g))
    		{return false;}
    	}
    	return true;
    }
    public double  getcptnum(String cpt)
    {
    	/**
    	 * get the num of cpt line
    	 */
    	String []split=cpt.split(",");
    	
    	return Double.valueOf(split[split.length-1]);
    }
    public String newjoincpt(String cpt1,String cpt2)
    {
    	/**
    	 * create new join cpt
    	 */
    	String l="";
    	String []split1=cpt1.split(",");
    	for(int i=0;i<split1.length-1;i++)
    	{
    		if(!l.contains(split1[i]))
    		{
    		l=l+","+split1[i];
    		}
    	}
    	String []split2=cpt2.split(",");
    	for(int i=0;i<split2.length-1;i++)
    	{
    		if(!l.contains(split2[i]))
    		{
    		l=l+","+split2[i];
    		}
    	}
    	
    	l=l.substring(1,l.length());
    	
    	return l;
    }
    public ArrayList<String> join_vars(ArrayList<String> var1,ArrayList<String> var2)
    {     
    	/**
    	 * join vars with mutual factors
    	 */
    	ArrayList<String> joinlist=new ArrayList<String>();
    	//----------------------------
    	ArrayList<String> mut=new ArrayList<String>();
    	
    	mut=mutualvars(var1, var2);
    	for(String g:var1)
    	{
    		ArrayList<String> current=new ArrayList<String>();
        	current=cptline2list(g, mut);
        	for(String z:var2)
        	{
        		
        		if(linesequals(current, z))
        		{
        			double num=getcptnum(g)*getcptnum(z);
        			multiplaycounter++;
        			String b=newjoincpt(g, z)+","+num;
        			joinlist.add(b);
        		}
        		
        	}
    		
    	    		
    	}
    	return joinlist;
    	
    	
    	
    }
    public ArrayList<String> join_vars_no_mut(ArrayList<String> var1,ArrayList<String> var2)
    {
    	/**
    	 * join vars without mutuals factors
    	 */
    	ArrayList<String> joinlist=new ArrayList<String>();
    	for(int i=0;i<var1.size();i++)
    	{
    		
    		String firstline=var1.get(i).substring(0, var1.get(i).lastIndexOf(","));
    		double firstnum=Double.valueOf(var1.get(i).substring(var1.get(i).lastIndexOf(",")+1,var1.get(i).length() ));
    		 
    		for(int z=0;z<var2.size();z++)
    		{
    			
    			double secondnum=Double.valueOf(var2.get(z).substring(var2.get(z).lastIndexOf(",")+1, var2.get(z).length()));
    			double num=firstnum*secondnum;
    			multiplaycounter++;
    			String secondline=var2.get(z).substring(0, var2.get(z).lastIndexOf(","));
    			String we=firstline+","+secondline+","+num;
    			joinlist.add(we);   			
    			
    			
    		}
    		
    	}
    	return joinlist;
    	
    	
    }
    public ArrayList<String> shrinkbyletter(ArrayList<String> Flist,String letter) 
    {
    	/**
    	 * the eliminate procces by letter 
    	 */
    	
    	ArrayList<String> templist=new ArrayList<String>();
          String split[]=Flist.get(0).split(",");
          for(int i=0;i<split.length-1;i++)
          {
        	  String t=split[i].substring(0, 1);
        	  if(!t.contains(letter))
        	  {
        		 // //System.out.println("this is t:"+t);
        		  templist.add(t);
        	  }
          }
          if(templist.size()==0) {return Flist;}
          ///------------------------------------
          
          ArrayList<String> mainlist=new ArrayList<String>();
          for(int i=0;i<Flist.size();i++)
          {
        	  ArrayList<String> currentline=new ArrayList<String>();
        	  if(!Flist.get(i).equals("shit"))
        	  {
	        	  
	        	  currentline=cptline2list(Flist.get(i), templist);
	        	
	        	  
	        	  double num=getcptnum(Flist.get(i));
	        	  for(int z=i+1;z<Flist.size();z++)
	        	  {
	        		  if(linesequals(currentline, Flist.get(z)))
	        		  {
	        			 
	        			sumcounter++;
	        			num=num+getcptnum(Flist.get(z));  
	        			Flist.set(z, "shit");
	        			
	        		  }
	        	  }
        	  
        	  String kim="";
        	  for(String n:currentline)
        	  {
        		  
        		  kim=kim+","+n;
        		  
        	  }
        	  kim=kim+","+num;
        	  kim=kim.substring(1, kim.length());
        	  String rom=kim.substring(0,kim.lastIndexOf(","));
        	 
        	  //if()
        	  if(!mainlist.toString().contains(rom))
        	  {
        	    mainlist.add(kim);
        	  }
        	} 
        	  
          }
         // //System.out.println("after shrink:"+letter);
          for(String m:mainlist)
      	{
      		////System.out.println(m);
      	}
          ////System.out.println("-------------------");
          return mainlist;
    	
    	
    }
 
   
    public ArrayList<String> joinbyletter(ArrayList<String> f,String letter)
    {
    	 /**
    	  * search all the vars the contain the letter and join them(by order of size ofcourse)
    	  */
    	ArrayList<ArrayList<String>> main=new ArrayList<ArrayList<String>>();
    	////System.out.println("words that contains var :"+letter);
    	for(int i=0;i<generalVariables2.size();i++)
    	{
    		ArrayList<String> z=new ArrayList<>();
    		z=tree2.get(generalVariables2.get(i)).cpt;
    		
    		
    		
    		
    		if(i!=-1)
    		{
	    		if(mutualvars(f, z).contains(letter))
	    		{
	    			
	    			//System.out.print(generalVariables2.get(i)+",");
	    			main.add(z);
	    			
	    			if(otherfactor.contains(generalVariables2.get(i)))
	    			{
	    				otherfactor.remove(generalVariables2.get(i));
	    			}
	    			generalVariables2.remove(i);
	    			i=-1;
	    			
	    			
	    			
	    		}
    		}
    	}
    	//System.out.println();
    	
    	
    	while(main.size()>=2)
    	{
    		ArrayList<String> q=new ArrayList<>();
    		
    		orderbylistsize(main);
    		q=join_vars(main.get(0), main.get(1));
    		main.remove(0);
    		main.remove(0);
    		main.add(q);
    		
    	}
    	////System.out.println("before we elimnate :"+letter);
    	for(String h:main.get(0))
    	{
    		////System.out.println(h);
    	}
    	////System.out.println("----");
    	return main.get(0);
    	
    }
   //------------------------
    /**
     * prepare all procees before running fixing cpts,pull un needed vars,ordering vars 
     * @param s
     */
    public void prepare (String s)
    {
    	/**
         * prepare all procees before running fixing cpts,pull un needed vars,ordering vars 
         * @param s
         */
    	resetanddupli();	   	
    	Variables2=ordervarli(Variables2);
    	
    	generalVariables2=new ArrayList<>(Variables2);
		orgnizedallcpt();   
    	if(s.contains("|"))
		{
			s=s.replace("P", "").replace("(", "").replace(")", "").replaceAll("\\|", "#");
			
			
			String []fo=s.split("#");
			safeletter=fo[0];
			safequery=s;
			////System.out.println("this is other factor:"+otherfactor.toString());
			/////System.out.println("this is safequery:"+s);
			String depends[]=fo[1].split(",");
			
		    for(String d:depends)
		    {   
		    	////System.out.println("this is depend:"+d);
		    	fixcptquery(d);
		    }
		    

		}
    	///--------------------
    	removevars();
    	generalVariables2=new ArrayList<>(Variables2);
		for( String p:Variables2)
		{
			
			if(!safequery.contains(p))
			{
				
				if(!p.equals(" "))
				hiddenfactor.add(p);
			}
			else
			{
				//if(!p.equals(" "))
			    otherfactor.add(p);	
			}
		}
		
		if(hiddenfactor.size()!=0)
		{
		hiddenfactor=ordervarli(hiddenfactor);
		}
		if(otherfactor.size()!=0)
		{
		otherfactor=ordervarli(otherfactor);
		}
		if(ordertype==3)
		{
		   Algo3 al=new Algo3(tree2, generalVariables2, hiddenfactor);
		  hiddenfactor=al.neworder();
		}
		///--------------------
    	
    	
    }
    public void algo2(String query)
    {   
    	/**
    	 * do all the procces using the function above and add to the print list which at the end written to afile.
    	 */
    	////System.out.println(query);
    	prepare(query);
    	
    	
        int counter=0;
       
        
    	 ArrayList<String> fl=new ArrayList<>();
    	  for(String p:hiddenfactor)
    	{
    		  /*
    		  //System.out.println("this is vars now:"+generalVariables2.toString());
    		  //System.out.println("this is foucs letter now:"+p);
    		  */
    		  ArrayList<String> currect=new ArrayList<>();
    		  currect=tree2.get(p).cpt;
    		  //System.out.println("letter :"+p);
    		  String name=p+counter++;
    		  for(String j:currect)
    		  {
    			  //System.out.println(j);
    		  }
    		  //System.out.println("------now after join------");
    		  
    		  currect=joinbyletter(currect, p);
    		  for(String j:currect)
    		  {
    			  //System.out.println(j);
    		  }
    		  if(!safeletter.contains(p))
    		  {
    		     currect=shrinkbyletter(currect, p);
    		  }
    		  //System.out.println("-----now after shrink");
    		  for(String j:currect)
    		  {
    			  //System.out.println(j);
    		  }
    		  fl=currect;
    		  Var vari=new Var();
    	      vari.cpt=currect;
    	      tree2.put(name, vari);
    	      generalVariables2.add(name);
    	     
    	    
    	      
    	}
    	 
    	  
    	for(int i=0;i<generalVariables2.size();i++)
    	{
    		
    		if(!otherfactor.contains(generalVariables2.get(i)))
    		{
    			otherfactor.add(generalVariables2.get(i));
    		}
    		
    		
    	}
    		 
      		 
    	while(otherfactor.size()>1)
    	{
    		// //System.out.println(otherfactor.toString());
    		 ArrayList<String> a=new ArrayList<>();
    		 ArrayList<String> b=new ArrayList<>();
    		 ArrayList<String> c=new ArrayList<>();
    		 a=tree2.get(otherfactor.get(0)).cpt;
    		 b=tree2.get(otherfactor.get(1)).cpt;
    		 if(mutualvars(a, b).size()!=0)
    		 {
    			 c=join_vars(a, b);
    			 String name="c"+counter++;
    			 Var v=new Var();
    			 v.cpt=c;
    			 tree2.put(name, v);
    			 otherfactor.remove(0);
    			 otherfactor.remove(0);
    			 otherfactor.add(0, name);
    		 }
    		 else
    		 {
    			// //System.out.println("foucs on:"+otherfactor.get(0)+","+otherfactor.get(1));
    			 c=join_vars_no_mut(a, b);
    			 String name="c"+counter++;
    			 Var v=new Var();
    			 v.cpt=c;
    			 tree2.put(name, v);
    			 otherfactor.remove(0);
    			 otherfactor.remove(0);
    			 otherfactor.add(0, name);
    			 
    		 }
    		
    		 
    		
    		
    	}
    	
    	
    	   
    	
    	/*
      		//System.out.println("final other factor:"+otherfactor.toString());
      		*/
      		 fl=tree2.get(otherfactor.get(0)).cpt;
      		
      		////System.out.println("-------------");
      	
      	
    	 
    	  
    	  
      	
       double num=0;
       double basic=0;
       sumcounter=sumcounter+fl.size()-1;
    	for(String j:fl)
    	{
    		////System.out.println(j);
    		String[]split=j.split(",");
    		if(j.contains(safeletter)) {basic=Double.valueOf(split[split.length-1]);}
    		
    		num=num+(Double.valueOf(split[split.length-1]));
    		
    	}
    	
    	
    	////System.out.println(num);
    	double result=basic*(1/num);
    	DecimalFormat df=new DecimalFormat("#.#####");
		df.setRoundingMode(RoundingMode.HALF_EVEN);
		String fiveres=String.valueOf(df.format(result));
    	while(fiveres.length()<7)
    	{
    		fiveres=fiveres+"0";
    	}
    	String fr=fiveres+","+sumcounter+","+multiplaycounter;
        Algo1.printresult.add(fr);
        //System.out.println(fr);
    	
     
    }
}

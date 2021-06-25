import java.util.ArrayList;
import java.util.HashMap;

public class Algo3 {
	/**
	 * the idea of this algo3 
	 * is to check with every hidden letter which one will create the
	 * smallest size after the elimination 
	 * For every current stage of the vars.
	 * the result is always the most efficent order :) more explanation on the doc.
	 */
	public HashMap<String, Var> tree3 = new HashMap<String, Var>();
	public ArrayList<String> Variables3=new ArrayList<String>();
	//----------------------------------------
	public ArrayList<String> hidden=new ArrayList<String>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public Algo3()
	{
		
	}
	public Algo3(HashMap<String, Var> tree,ArrayList<String> vars,ArrayList<String> hidden)
	{
	   this.tree3=tree;
	  this.Variables3=vars;
	   this.hidden=hidden;
	}
	
	public ArrayList<String> neworder()
	{
		int counter=0;
		ArrayList<String> templist=new ArrayList<String>(Variables3);
		ArrayList<String> temphidden=new ArrayList<String>(hidden);
		ArrayList<String> finalreturn=new ArrayList<String>();
		ArrayList<String>currect2=new ArrayList<String>();
		//--------------------------------------------
		while(temphidden.size()!=0)
		{
			  String max_letter="";
			  int max_num=999999;
			  
			  for(String m:temphidden)
			  {
				  //System.out.println("this is varlist:"+templist.toString());
				  //System.out.println("this is hidden"+temphidden);
				  //System.out.println("this is letter:"+m);
				  ArrayList<String>currect=new ArrayList<String>();
				  
				  currect=joinbyletter(templist, tree3.get(m).cpt, m);
				  int valnum=tree3.get(m).values.size();
				  int currentnum=currect.size()/valnum;
				  //System.out.println("this is maxnum:"+max_num+"and this is currentnum:"+currentnum);
				  if(max_num>currentnum)
				  {
					  //System.out.println("chenged happend");
					  max_letter=m;
					  max_num=currentnum;
					  currect2=currect;
				  }
			  }
			  
			  temphidden.remove(max_letter);
			  finalreturn.add(max_letter);
			  max_num=99999999;
			  
			  Var vi=new Var();
			 
			  currect2=shrinkbyletter(currect2, max_letter);
			  //System.out.println(currect2.size()+","+max_letter);
			  templist=listafter_removemut(templist, max_letter);
			  max_letter=max_letter+counter++;
			  vi.cpt=currect2;
			  tree3.put(max_letter, vi);
			  templist.add(max_letter);
			  //System.out.println("end");
			 
			
			
			
		}
		//System.out.println("this is final:"+finalreturn.toString());
		return finalreturn;
		
		
	}
	//--------------------------------------
	/**
	 * this function are the same from algo2 nly work with new object like tree3 and variiable3
	 * in order not to create new bugs.
	 * @param vars
	 * @param lettercpt
	 * @param letter
	 * @return
	 */
	public ArrayList<String>  joinbyletter(ArrayList<String> vars,ArrayList<String> lettercpt,String letter)
	    { 
		     //System.out.println("joinbyletter:"+letter);
		ArrayList<String> vars2=new ArrayList<String>(vars);
	    	 
	    	ArrayList<ArrayList<String>> main=new ArrayList<ArrayList<String>>();
	    	////System.out.println("words that contains var :"+letter);
	    	//System.out.println("vars:"+vars2.toString());
	    	for(int i=0;i<vars2.size();i++)
	    	{
	    		ArrayList<String> z=new ArrayList<>();
	    		z=tree3.get(vars2.get(i)).cpt;
	    		String zletter=vars2.get(i);
	    		
	    		
	    		
	    		if(i!=-1)
	    		{
		    		if(mutualvars(lettercpt, z).contains(letter))
		    		{
		    			
		    			
		    			//System.out.print(zletter+",");
		    			main.add(z);
		    			vars2.remove(zletter);
		    			
		    			
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
	public ArrayList<String> mutualvars(ArrayList<String> var1,ArrayList<String> var2)
    {   ArrayList<String> mu=new ArrayList<String>();
        
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
	public ArrayList<ArrayList<String>> orderbylistsize(ArrayList<ArrayList<String>> m)
    {
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
	public ArrayList<String> join_vars(ArrayList<String> var1,ArrayList<String> var2)
    {     
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
        			
        			String b=newjoincpt(g, z)+","+num;
        			joinlist.add(b);
        		}
        		
        	}
    		
    	    		
    	}
    	return joinlist;
    	
    	
    	
    }
	public double  getcptnum(String cpt)
	    {
	    	String []split=cpt.split(",");
	    	
	    	return Double.valueOf(split[split.length-1]);
	    }
	public String newjoincpt(String cpt1,String cpt2)
	    {
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
	public ArrayList<String> cptline2list(String cpt,ArrayList<String> list)
	    {
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
	    	for(String g: list)
	    	{
	    		if(!cpt.contains(g))
	    		{return false;}
	    	}
	    	return true;
	    }
	 public ArrayList<String> shrinkbyletter(ArrayList<String> Flist,String letter) 
	    {
	    	////System.out.println("this is letter:"+letter);
	    	
	    	
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
    public ArrayList<String> listafter_removemut(ArrayList<String> list ,String letter)
    {   ArrayList<String> retu=new ArrayList<>();
    
          for(int i=0;i<list.size();i++)
          {
        	 
        	  ArrayList<String> z=new ArrayList<>();
	    		z=tree3.get(list.get(i)).cpt;
	    		if(mutualvars(tree3.get(letter).cpt, z).contains(letter))
	    		{
	    			list.remove(i);
	    			i=-1;
	    		}
	    		
          }
    	
    	retu=list;
    	return retu;
    }

}

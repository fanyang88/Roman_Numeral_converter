package org.test;

public class RNcalculator {
	
	public boolean checkRN(String rn)
	{
		int count=0;   //check only contain c,d,L,x,m,i,v
		for(int i=0;i<rn.length();i++)
		{
			  if(rn.charAt(i)=='C'||rn.charAt(i)=='D'||rn.charAt(i)=='L'||rn.charAt(i)=='X'
					  ||rn.charAt(i)=='V'||rn.charAt(i)=='I'||rn.charAt(i)=='M')
				count++;
			}
		if(count!=rn.length())
			return false;
	    return true;	
	}
	
	public boolean checkNumber(String number)
	{
		for(int i=0;i<number.length();i++)
			{
			  if(number.charAt(i)>'9' || number.charAt(i)<'0' )
				return false;
			}
		
		return true;	
	}
	
	public boolean checkFraction(String number)
	{
		
		int count=0;   
		for(int i=0;i<number.length();i++)
		{
			  if((number.charAt(i)>='0' && number.charAt(i)<='9')||number.charAt(i)=='.')
				count++;
			}
		if(count!=number.length())
			return false;
		if(number.indexOf(".")<0)
			return false;
	    return true;	
	}
	
	public String subconvertRNtoDecimal(String rn)
	{
		int number=0;
		String newrn=transferRN(rn);
		for(int i=0;i<newrn.length();i++)
		{
			switch(newrn.charAt(i))
			{
			case ('I'): number=number+1; break;
			case ('V'): number=number+5; break;
			case ('X'): number=number+10; break;
			case ('L'): number=number+50; break;
			case ('C'): number=number+100; break;
			case ('D'): number=number+500; break;
			case ('M'): number=number+1000; break;
			default: break;
			}
		}		
		return ""+number;
	}
	
	public String transferRN(String rn)
	{
		rn=rn.toUpperCase();
		if(!checkRN(rn))
                return "This is not a Roman Numeral";
		
		String[] rncombine={"IV","IX","XL","XC","CD","CM"};
		String[] mrncombine={"IIII","VIIII","XXXX","LXXXX","CCCC","DCCCC"};
		for(int i=0;i<rncombine.length;i++)
		{
			int split=rn.indexOf(rncombine[i]);
			if(split>=0)
				rn=rn.substring(0,split)+mrncombine[i]+rn.substring(split+2,rn.length());	
		}
		return rn;
	}
	
	
	public String convertoRN(String number)
	{
		String res="";
		if(number==null)
			return "no input ";
		if(checkNumber(number))
		{
			int temp=Integer.parseInt(number);
			 res=convertDecimaltoRN(""+temp);
		}
		
		else if(checkFraction(number))
			 res=ConvertFractiontoRN(number);
	
		else  res="not a number";
		return res;	
	}
	
	
	public String convertDecimaltoRN(String number)
	{
		String res="";
		String cmatrix[][]={{"CD","CM","C","D"},
				            {"XL","XC","X","L"},
				            {"IV","IX","I","V"}};
		
		if(number.length()<=4)
		{
			
			if(number.length()<4)
			{
				while(4-number.length()>0)
					number="0"+number;
			}
			if(Integer.parseInt(""+number.charAt(0))<5)
		  {
			int d4=Integer.parseInt(""+number.charAt(0));
			while(d4>0)
				{res=res+"M"; d4--;}
			
			for(int i=1;i<4;i++)
			{
				int k= Integer.parseInt(""+number.charAt(i));
				if(k==4) res=res+cmatrix[i-1][0];
				else if(k==9) res=res+cmatrix[i-1][1];
				
				else
				{
					int count=0;
					if(0<=k && k<4) 
					count=k;
					else
					{	count=k-5;  
					    res=res+cmatrix[i-1][3];}
					while(count>0)
					{res=res+cmatrix[i-1][2]; count--;}	
				}		
			}
			return res;
		}
		else
			return "exceed limit";
		}
		else
			return "exceed limit";	
	}
	
	public String ConvertFractiontoRN(String input)
	{
		String[] RNdecimals={"",".","..","...","....",".....","S","S.","S..","S...","S....","S....."};
		if(input.indexOf(".")==0)
			input="0"+input;
		double div=Double.parseDouble(input);
		int temp=Integer.parseInt(input.substring(0,input.indexOf(".")));
		
		String intpart= convertDecimaltoRN(""+temp);
		div=div-temp;
		double min=100.0,  pos=1.0;
		for(double i=0.0;i<12.0;i++)
		{
			double value=abs(div-(i/12));
			if(min>value)
			{ min=value; pos=i; }
		}	
		return intpart+" "+RNdecimals[(int)(pos)];	
	}
	
	public String convertRNtoDecimal(String input)
	{
		if(input==null)
			return "no input ";
		input=input.toUpperCase();
		int median= Integer.parseInt(subconvertRNtoDecimal(input));
		String newres=convertDecimaltoRN((""+median).trim());
		if(newres.equals(input))
		  return ""+median;
		else
			return "not exist";		
	}
	
	public String operations(String s1, String s2, int flag)
	{
		s1=s1.toUpperCase();
		s2=s2.toUpperCase();
		s1=convertRNtoDecimal(s1);
		s2=convertRNtoDecimal(s2);
		if(s1.equals("not exist"))
			return "not a roman numeral";
		if(s2.equals("not exist"))
			return "not a roman numeral";
		
		String res="";
		switch(flag)
		{
		case 1:    //+
			int sum=Integer.parseInt(s1)+Integer.parseInt(s2);
			res=convertDecimaltoRN(""+sum);
			break;
		case 2:   //-
			int subtract=Integer.parseInt(s1)-Integer.parseInt(s2);
			res=convertDecimaltoRN(""+subtract);
			break;
		case 3:   //*
			int multiply=Integer.parseInt(s1)*Integer.parseInt(s2);
			res=convertDecimaltoRN(""+multiply);
			break;
		case 4:  //div
			int div=Integer.parseInt(s1)/Integer.parseInt(s2);
			int remain=Integer.parseInt(s1)-(div*Integer.parseInt(s2));
			res="Q:"+convertDecimaltoRN(""+div)+"   R:"+convertDecimaltoRN(""+remain);
			break;
		default:
			break;
		}
		return res;	
	}
	
	
	public String CompoundInterest(String pc,String ain, String tpy, String yg)
	{
		if(pc==null)
			return "principal?";
		if(ain==null)
			return "interest rate?";
		if(tpy==null)
			return "times per year?";
		if(yg==null)
			return "years to grow?";
		
		String principal=convertRNtoDecimal(pc);
		String annualinterest=convertRNtoDecimal(ain);
		String timesperyear=convertRNtoDecimal(tpy);
		String yearstogrow=convertRNtoDecimal(yg);
		double currentprincipal=Double.parseDouble(principal);
		double rate=Double.parseDouble(annualinterest)*0.01/Integer.parseInt(timesperyear);
	    int times=Integer.parseInt(timesperyear)*Integer.parseInt(yearstogrow);
	    for(int i=0;i<times;i++)
	    	currentprincipal=currentprincipal+currentprincipal*rate;
	    String res=(""+currentprincipal).substring(0,(""+currentprincipal).indexOf(".")+6);
	   return res;
	}
	
	public double abs(double n)
	{
		if(n<0)
			n=n*(-1);
		return n;
	}
}

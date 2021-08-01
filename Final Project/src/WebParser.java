

	import java.io.BufferedReader;
import java.io.File;
	import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
	public class WebParser {
		
	    public static void main(String args[]) throws IOException{
	    	String path="C:\\Users\\qadri\\eclipse-workspace\\Final Project\\";
	   In in = new In("C:\\Users\\qadri\\eclipse-workspace\\Final Project\\websites.txt");
	   HashMap<String,String> b = new HashMap<String,String>();
	   String s=null;
	   int i=0,k=0,l=0;
	  HashMap<Integer,String> a=new HashMap<Integer,String>();
	   //String[] a=null;
	   while(!in.isEmpty()) {
		   try {
		   	s= in.readLine();
		   	i++;
	   		org.jsoup.nodes.Document doc = Jsoup.connect(s).get();
	   		String text = doc.text();
	   		PrintWriter out = new PrintWriter("Doc "+i+".txt");
	   		System.out.println("Parsed File doc"+i+".txt");
	   		
			out.println(text);
			out.close();
			a.put(k,("Doc "+i+".txt").toString());
			b.put(a.get(k),s);
			//System.out.println(k+"  "+ a.get(k));
			k++;
			
		   }
		   catch(UnsupportedMimeTypeException e) {}
		   catch(MalformedURLException e) {}
		   catch(HttpStatusException e) {}
		   
	   }
	   System.out.println("All Files parsed and read");
	   Index index = new Index();
	   int size=a.size();
	   String[] str=new String[size];
	   Iterator<Entry<Integer,String>> it = a.entrySet().iterator();
	   while(it.hasNext())
	   {
		   Map.Entry<Integer, String> set = (Map.Entry<Integer, String>) it.next();
		   str[l]=set.getValue();
		   l++;
	   }
	   
		  
       //index.buildIndex(new String[]{"Doc 6.txt"});
	   System.out.println(".........................................................................................");
	   System.out.println("Creating Inverted Index...");
	   System.out.println(".........................................................................................");
       index.buildIndex(str);
       System.out.println("Inverted Index Created...");
       System.out.println(".........................................................................................");
       HashMap[] freq=index.FreqIndex(str);
       System.out.println("Created Word Frequency Index...");
       System.out.println(".........................................................................................");
       while(true) {
       System.out.println("Please print a phrase to search: ");
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       String phrase = br.readLine();

       index.find(phrase,a,b,freq);}
	    
	    }
	}

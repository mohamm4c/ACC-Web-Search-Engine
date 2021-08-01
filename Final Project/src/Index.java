
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

class Index {
    Map<Integer,String> sources;
    HashMap<String, HashSet<Integer>> index;
   
    //HashMap freqindex;

    Index(){
        sources = new HashMap<Integer,String>();
        index = new HashMap<String, HashSet<Integer>>();
       
    }

    public void buildIndex(String[] files){
        int i = 0;
       
        for(String fileName:files){

            
            try(BufferedReader file = new BufferedReader(new FileReader(fileName)))
            {
                sources.put(i,fileName);
                String ln;
                while( (ln = file.readLine()) !=null) {
                    String[] words = ln.split("\\W+");
                    for(String word:words){
                        word = word.toLowerCase();
                        if (!index.containsKey(word))
                            index.put(word, new HashSet<Integer>());
                        index.get(word).add(i);
                    }   
                }
            } catch (IOException e){
                //System.out.println("File "+fileName+" not found. Skip it");
            }
            i++;
        }
        
    }
    public HashMap[] FreqIndex(String[] files){
        int i = 0;
        int size=files.length;
        //System.out.println(size);
        HashMap[] freqindex=new HashMap[size];
        for(int j=0;j<size;j++)
        freqindex[j] = new HashMap<String,Integer>();
        for(String fileName:files){

            
            try(BufferedReader file = new BufferedReader(new FileReader(fileName)))
            {
                sources.put(i,fileName);
                String ln;
                while( (ln = file.readLine()) !=null) {
                    String[] words = ln.split("\\W+");
                    for(String word:words){
                        word = word.toLowerCase();
                        if (!freqindex[i].containsKey(word))
                            freqindex[i].put(word,0);
                        int currentcount=(int) freqindex[i].get(word);
                        //System.out.println(currentcount);
                        currentcount++;
                        freqindex[i].put(word,currentcount);
                        
                    }
                }
            } catch (IOException e){
                //System.out.println("File "+fileName+" not found. Skip it");
            }
            i++;
        }
       return freqindex ;
    }

    public void find(String phrase,HashMap a, HashMap h,HashMap[]freqindex) throws NullPointerException{
        String[] words = phrase.split("\\W+");
        int size=words.length;
        int num2=0;
        try {
        HashSet<Integer> res = new HashSet<Integer>(index.get(words[0].toLowerCase()));
        HashMap<String, Integer> temp = new HashMap<String, Integer>();
        HashMap<String, Integer> temp2 = new HashMap<String, Integer>();
        for(String word: words){
            res.retainAll(index.get(word));
        }

        if(res.size()==0) {
            System.out.println("Not found");
            return;
        }
        for(int num : res){
        	for(String word: words){
        	temp.put(sources.get(num),(Integer) (freqindex[num].get(word)));
        	temp2=sortByValue(temp);
        }}
        System.out.println("Found in: ");
        Iterator<Entry<String,Integer>> it = temp2.entrySet().iterator();
 	   while(it.hasNext())
 	   {
 		   Map.Entry<String,Integer> set = (Map.Entry<String,Integer>) it.next();
 		  Iterator<Entry<Integer,String>> it2 = a.entrySet().iterator();
 		  while(it2.hasNext()) {
 		  Map.Entry<Integer,String> set1 = (Map.Entry<Integer,String>) it2.next();
 		  if(set.getKey()==set1.getValue())
 		  {
 			 num2=set1.getKey();
 		  }}
 		  String docnme = set.getKey();
 		  System.out.println(docnme+"\t");
 		 //System.out.print("Occurances of word: ");
 		for (String word: words)
			System.out.println("Occurances of word:  "+word+"  "+(freqindex[num2].get(word)));
		System.out.println(h.get(sources.get(num2)));
 		// for(int i=0;i<size;i++)
 		// {
 		///	System.out.print(""+words[i]+" - - "+set.getValue());
 		// }
 		// System.out.println(h.get(set.getKey()));
 		System.out.println(".........................................................................................");
 	   }
        /*for(int num : res){
            System.out.println(sources.get(num)
            +"\t");
            
    		for (String word: words)
    			System.out.println("Occurances of word:  "+word+"  "+(freqindex[num].get(word)));
    		System.out.println(h.get(sources.get(num)));
    		
    		System.out.println(".........................................................................................");
        }
    */}
    catch(NullPointerException e) {
    	System.out.println("Not found");
    	System.out.println(".........................................................................................");
    }
    }
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
 
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
         
        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

	public static void main(String args[]) throws IOException{
        Index index = new Index();
        index.buildIndex(new String[]{"Doc 6.txt"});

        System.out.println("Please print a phrase to search: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String phrase = in.readLine();

        //index.find(phrase);
	}
}

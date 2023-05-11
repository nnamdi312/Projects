import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



public class Readinglevelanalyzer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length==0) {
			
			System.out.print("need to pass in a filename in the command line argument");
		
		return;
		}
		
		int count=0;
		
		int wordcount=0;
		
		 int charactercount=0;
		
		int longestword=0;
		 int longestsentence=0;
		 
		 HashMap<String,Integer> diction=Getdictionary();
		String filename=args[0];
		

		
		String filecontent=Getfilecontent(filename);
	filecontent=filecontent.replace('-', ' ');

	String[]words=filecontent.split("\\s+");


	 ArrayList<Sentence> Sentences=new ArrayList<Sentence>();

	 Sentence Sentence=new Sentence();
	 for(int i=0;i<words.length;i++) {
		String word=words[i];

	if(word.endsWith("!")||word.endsWith(".")||word.endsWith("?")) {
		// if(word variable ends with periods,question marks,and exclamation point)
	word = word.substring(0, word.length() - 1);
	if(isNumeric(word)==false) {
		if(diction.containsKey(word.toUpperCase())) {
			
			count =count+diction.get(word.toUpperCase());
			
		
		}
		
		else
		
		count=count+1;
			
			if(word.length()>longestword) {
				
				longestword=word.length();
			}
			Sentence.addword(word);

	wordcount=wordcount+1;

	charactercount=charactercount+word.length();
	}
	if(Sentence.getwords().size()>longestsentence) {
		
		longestsentence=Sentence.getwords().size();
		
		
	}
	 Sentences.add(Sentence);
		Sentence=new Sentence();

	}
	else
		if(isNumeric(word)==false) {
			
	if(word.length()>longestword) {
				
				longestword=word.length();
			}
			
			Sentence.addword(word);
		
		
			if(diction.containsKey(word.toUpperCase())) {
				
				count =count+diction.get(word.toUpperCase());
				
			
			}
			
			else
			
			count=count+1;
			
			
			wordcount=wordcount+1;

			charactercount=charactercount+word.length();
		}
		
	 
	 }
	System.out.println("Character count: " + charactercount  );

	System.out.println("Syllable count: " + count);

	System.out.println("word count: " + wordcount);

	System.out.println("Sentence count: " + Sentences.size());

	System.out.println("Characters per word : " + charactercount*1.0/wordcount);

	System.out.println("Syllables per word : " +count*1.0/wordcount);

	System.out.println("Words per sentence: " + wordcount*1.0/Sentences.size());

	System.out.println("Longest word: " +longestword);

	System.out.println("Longest sentence: " +longestsentence);


	double readingEase = 206.835 - 1.015*(wordcount*1.0/Sentences.size()) - 84.6*(count*1.0/wordcount);
	System.out.println("Flesch-Kincaid Reading Ease: " + readingEase);

	double gradeLevel = 0.39*(wordcount*1.0/Sentences.size()) + 11.8*(count*1.0/wordcount) - 15.59;
	System.out.println("Flesch-Kincaid Grade Level: " +  gradeLevel);


	}

	private static String Getfilecontent(String filePath)
	{

	    // Declaring object of StringBuilder class
	    StringBuilder builder = new StringBuilder();

	    // try block to check for exceptions where
	    // object of BufferedReader class us created
	    // to read filepath
	    try (BufferedReader buffer = new BufferedReader(
	             new FileReader(filePath))) {

	        String str;

	        // Condition check via buffer.readLine() method
	        // holding true upto that the while loop runs
	        while ((str = buffer.readLine()) != null) {

	            builder.append(str).append(" ");
	        }
	    }

	    // Catch block to handle the exceptions
	    catch (IOException e) {

	        // Print the line number here exception occurred
	        // using printStackTrace() method
	        e.printStackTrace();
	    }

	    // Returning a string
	    return builder.toString();
	}


	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

	private static HashMap<String,Integer> Getdictionary()
	{
		HashMap<String,Integer>diction=new HashMap<String,Integer>();
	    // try block to check for exceptions where
	    // object of BufferedReader class us created
	    // to read filepath
	    try (BufferedReader buffer = new BufferedReader(
	             new FileReader("cmudict.txt"))) {

	        
	    	String str;
	        // Condition check via buffer.readLine() method
	        // holding true upto that the while loop runs
	        while (( str = buffer.readLine()) != null) {
	       if(str.startsWith(";;;")==false) {
	    	   String[]des=str.split("\\s+");

	       
	      int count=0;
	for(int i=1;i<des.length;i++) {
	    	   
	    	  String possiblesyllable=des[i];
	    	   
	    	   if(possiblesyllable.endsWith("0")||possiblesyllable.endsWith("1")||possiblesyllable.endsWith("2")) {
	    		   
	    		   count=count+1;
	    	   
	    	   }
	    	   
	       }
	diction.put(des[0],count);
	       
	       }
	        }
	    }

	    // Catch block to handle the exceptions
	    catch (IOException e) {

	        // Print the line number here exception occurred
	        // using printStackTrace() method
	        e.printStackTrace();
	    }

	    // Returning a string
	    return diction;
	}



	

	}



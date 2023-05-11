import java.util.ArrayList;

public class Sentence {

private ArrayList<String> words=new ArrayList<String>();

public ArrayList<String> getwords(){
	
	return words;
}

 public void addword(String word){
	
	this.words.add(word);
	
}


}

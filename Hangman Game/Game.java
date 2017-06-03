/*Janill Lema
 * jl4827
 * This program allows the user to play evil hangman  
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap; 

public class Game {
	
	//current dictionary list 
	private ArrayList<String> currentList;
	//contains array all guessed letters 
	private ArrayList<String> guessedLetters;
    private Scanner input;
    private HashMap<ArrayList<Integer>, ArrayList<String>> wordFamily;
    //stores the user specified word length 
    private int desiredLength; 
    //stores the blanked version of the word 
    private ArrayList<String> blankWord; 
    //corresponds to the value with most words 
    private ArrayList<String> longestValue;
    //corresponding key to the value with most words 
    private ArrayList<Integer>longestKey; 
    //keeps track of the number of guesses
    private int numberGuesses; 
    //continues the game
    private boolean play; 
 
	public Game( ArrayList<String> word ){
		
		currentList=word;
		desiredLength=0; 
		numberGuesses=0; 
		play=false; 
		guessedLetters= new ArrayList<String>(); 
		input= new Scanner(System.in); 
		wordFamily= new HashMap<ArrayList<Integer>, ArrayList<String>>();
		blankWord= new ArrayList<String>(); 
		longestValue= new ArrayList<String>(); 
		longestKey= new ArrayList<Integer>();
		
		}
	
	public void play(){ 
		
		int runningTotal=0;  
		ArrayList<String> wordLength= new ArrayList<String>(); 
		
		System.out.println("*******WELCOME TO EVIL HANGMAN********");
		System.out.println("Enter a desired word length: ");
	    desiredLength= input.nextInt(); 
		
		//checks if user chose an existing word length 
		while(this.isInDictionary(desiredLength)==false ){
			System.out.println("No Such Word Length! Enter a new length: ");
			desiredLength= input.nextInt();
		}
		
		//adds words that match users desired length to current list  
		for(String elem: currentList){
			if(elem.length()==desiredLength){
				wordLength.add(elem); 
			}
		}
		currentList.clear();
		for(String elem: wordLength){
			currentList.add(elem);
		}
		
		System.out.print("WORD: ");

		for(int c=0; c<desiredLength;c++){
			blankWord.add("_ ");
		}
		//prints blank word (dashes)
		this.printBlankWord();
		System.out.println(" ");
		
		System.out.println("Enter desired number of guesses:");
		numberGuesses=input.nextInt(); 
		
		System.out.println("Enter 1 if you want a running total"
				+ " else enter 0 "); 
	    runningTotal = input.nextInt(); 
	    
		if(runningTotal==1){
		System.out.println("Running Total: " + this.runningTotal()
				+" words remaining");
		}

		//runs if still have guesses and havent guessed the word yet 
	    while(numberGuesses!=0 && play==false){
	    	
	    	System.out.println("You have "+ numberGuesses+
	    				" guess(es) remaining!");
	    	System.out.println("GUESS A LETTER");
	    
			
			String letter= input.next(); 
	 
			//makes sure letter was not guessed and that its a single letter 
			while(letter.length()!=1 || Character.isLetter(letter.charAt(0))==false
					|| isGuessed(letter)){
				System.out.println("Invalid Letter! Enter a valid letter:");
				letter= input.next(); 
				}
			
			//creates word family
			this.createWordFamily(letter);
			
			//sets current list to value in wordFamily with biggest length 
			this.newCurrentList();
            
			//changes characters in the displayed word 
			this.modifyBlankWord(letter, longestKey); 
			
			//prints out guessed letters 
			System.out.println("WORD: ");
			this.printBlankWord();
			System.out.println("");
			//checks if guessed correct word
			if(this.isWord()){
				System.out.println("YOU GUESSED IT! YOU WIN! ");
				play=true; 
			}
			
			if(runningTotal==1){
			System.out.println("Running Total: " + this.runningTotal()
					+" words remaining");
			}

			wordFamily.clear();
			longestKey.clear();
			longestValue.clear();
			numberGuesses--; 
		}
	    
	    if(numberGuesses==0){
	    	System.out.println("YOU LOSE!");
	    	//picks first value in current list as chosen word 
	    	System.out.println("The word was: " + currentList.get(0));
	    }
	}
	
	//method returns true if user inputs a valid word length 
	public boolean isInDictionary(int wordLength){
		ArrayList<Integer> lengths = new ArrayList<Integer>(); 
		//adds length of each word to array list 
		for(String elem: currentList){
			lengths.add(elem.length()); 
		}
		return lengths.contains(wordLength); 
	}
	
	//checks if letter was guessed before 
	public boolean isGuessed(String guess){ 
		if (guessedLetters.contains(guess)){
			return true; 
		}
		else{
			//keeps track of guessed letters
			guessedLetters.add(guess);
			return false; 
		}
	}
	
	//modifies the wordFamily Hash map based off guessed Letter 
	public void createWordFamily(String guessedLetter){
		//iterates through every word in current list 
		for(int k=0; k<currentList.size(); k++){
			ArrayList<Integer> positions = new ArrayList<Integer>();
			ArrayList<String> words = new ArrayList<String>(); 
			words.add(currentList.get(k));
			//iterate through each letter in word 
			for(int j=0; j<currentList.get(k).length(); j++ ){
				if(currentList.get(k).charAt(j)==guessedLetter.charAt(0)){
					positions.add(j);
				}
			}
			//creates wordFamily based on whether key exists or not 
			if(wordFamily.containsKey(positions)){
				wordFamily.get(positions).add(words.get(0));
			}
			else{
				wordFamily.put(positions, words);
			}
		}	
	}
	
	//returns the letters guessed at their corresponding positions
	public ArrayList<String> modifyBlankWord(String guessedLetter,
			ArrayList<Integer>positions){
		if(positions.size()!=0){
			numberGuesses++;
			for (int elem: positions ){
				blankWord.set(elem, guessedLetter); 
			}
		}
		if(positions.size()==0){
			System.out.println("GUESSED LETTER IS NOT IN THE WORD");
		}
		return blankWord; 
	}
	
	//sets the new current list to the largest list of words 
	public void newCurrentList(){
		currentList.clear();
		for(ArrayList<Integer>key: wordFamily.keySet()){
			if(wordFamily.get(key).size()> longestValue.size()){
				longestValue= wordFamily.get(key);
				longestKey= key; 
			}
		}
		//current list is the value with the most words 
		for(String elem: longestValue){
			currentList.add(elem);
		}	
	}
	
	//returns the size of the current list (running total) 
	public int runningTotal(){
		return currentList.size(); 
	}
	
	//prints out the blank word
	public void printBlankWord(){
		for(String elem: blankWord){
			System.out.print(elem);
		}
	}
	
	//tests if user guessed the correct word 
	public boolean isWord(){
		String correctWord= " "; 
		String wordGuess= "";
		boolean isLetter= true; 
		boolean isWord= false;
		
		//chooses last single word in hashmap 
		if(wordFamily.size()==1){
			for(ArrayList<Integer> key: wordFamily.keySet()){
				if(wordFamily.get(key).size()== 1){
					correctWord= wordFamily.get(key).get(0); 
				}
			}
		}
		
		//converts array of guessed letters to string 
		for(int o =0; o<blankWord.size(); o++){
			if(Character.isLetter(blankWord.get(o).charAt(0))==false){
				isLetter=false; 
			}
		}
		if(isLetter==true){
			for(int y=blankWord.size()-1; y>= 0 ; y--){
				wordGuess= blankWord.get(y)+wordGuess;
			}
		}
		//compares guessed word to word in hashmap 
		if(correctWord.equals(wordGuess)){
			isWord=true; 
		}

		return isWord; 
	}
	
	//returns remaining words 
	public ArrayList<String>returnCurrentList(){
		return currentList; 
	}
}
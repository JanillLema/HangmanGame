/*Janill Lema
 * jl4827
 * This program plays and tests the Hangman game  
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class HangmanTest {
	public static void main(String args[]){
		
		int playAgain= 0; 
		int printWords=0; 
		Scanner scan = new Scanner(System.in);
		System.out.println("Would You Like To Play Evil HangMan");
		System.out.println("Enter '1' for Yes and '0' for No");
		System.out.println("Remember to input the correct Dictionary File");
		playAgain=scan.nextInt();
		
		//plays the game 
		while (playAgain==1){
			try{
				//takes command line input 
				File inFile = new File(args[0]);
				Scanner input = new Scanner(inFile);
				String word = ""; 
			    ArrayList<String> wordList= new ArrayList<String>();
			    PrintWriter output = new PrintWriter("Remaining Words");
				//used to add all words from dictionary to initial current list 
				while(input.hasNext())
				{
				  word=input.next();
				  wordList.add(word); 
				}
				//processes dictionary 
				Game g = new Game(wordList); 
				g.play();
				
				System.out.println("Would You Like to Print the Remaining Words?"); 
				System.out.println("Enter '1' for Yes and '0' for No");
				
				printWords=scan.nextInt(); 
				
				//prints word remaining list 
				if(printWords==1){
					for(String elem:g.returnCurrentList()){
						output.println(elem);
					}
				}
				output.close();
			}
			
			//incorrect file name 
			catch(FileNotFoundException dictionary){
				System.out.println("Please try again with correct input file name");
				System.out.println(dictionary);
				playAgain=0; 
			}
			//no file name input
			catch(ArrayIndexOutOfBoundsException e){
				System.out.println("Enter a file! ");
				playAgain=0; 
			}
			//asks if want to play again 
			if(playAgain==1){
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ");
			System.out.println("Would You Like To Play Evil HangMan Again?");
			System.out.println("Enter '1' for Yes and '0' for No");
			System.out.println("Remember to input the correct Dictionary File");
			playAgain=scan.nextInt(); 
			}
		}
		
	}
}



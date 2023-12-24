package pkg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class game {
static boolean DEBUG = false;
static int guess = 3;
static int score = 0;
private static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		print_greeter();
		// Read word list and format data.
		String[] words = new String[41337];
		read_file(words);
		boolean run = true;
		while (run&&guess>0) {			
			String random_word = words[(int) (Math.random()*41337)];
			int lenght = random_word.length();
			// Assign how many word will be shown to player.
			int counter = generate_shownIndex_counter(lenght);
			print_the_word(random_word);
			int[] shown_indexes = generate_shown_indexes(lenght, counter);
			replaceDuplicates(shown_indexes, lenght);
			print_shown_indexes(shown_indexes);
			// Print word word.
			print_question_word(random_word, shown_indexes);
			System.out.println();
			run_game(random_word);
		}
	}


	private static int generate_shownIndex_counter(int lenght) {
		int counter;
		if (lenght < 4) {
			counter = 1;
		}else if(lenght< 6) {
			counter = 2;
		}else if(lenght<8){
			counter = 4;
		}else {
			counter = 5;
		}
		return counter;
	}


	private static void print_the_word(String random_word) {
		if (DEBUG) System.out.println("Word: "+random_word);
	}


	private static void print_shown_indexes(int[] shown_indexes) {
		if (DEBUG) {
			System.out.print("Shown Indexes: ");
			for (int i = 0; i < shown_indexes.length; i++) {
				System.out.print(shown_indexes[i]+" ");
			}
			System.out.println();		
		}
	}


	private static void run_game(String random_word) {
		boolean win = false;
		String user_guess;
		while (guess>0&&!win) {
			System.out.print("-->");
			user_guess = scanner.nextLine();
			if (random_word.equalsIgnoreCase(user_guess)) {
				win=true;
			}else {
				guess-=1;
			}
		}
		if(win) {
			System.out.println("Correct!");
			System.out.println("-----------------------");
			guess = 3;
			score+=5;
		}else {
			System.out.println("You lose!");
			System.out.printf("The word was '%s'.",random_word);
			System.out.printf("\nYour score is %d",score);
		}
	}


	private static void print_question_word(String random_word, int[] shown_indexes) {
		System.out.println();
		boolean printed = false;
		for (int i = 0; i < random_word.length(); i++) {
			printed = false;
			for (int j = 0; j < shown_indexes.length; j++) {
				if (i == shown_indexes[j]) {
					System.out.print(random_word.charAt(i));
					printed = true;
				}
			}
			if (!printed) {
				System.out.print("*");
			}
		}
	}


	private static int[] generate_shown_indexes(int lenght, int counter) {
		int[] shown_indexes = new int[counter];
		for (int i = 0; i < shown_indexes.length; i++) {
			shown_indexes[i] = (int)(Math.random()*lenght);
		}
		return shown_indexes;
	}


	private static void replaceDuplicates(int[] shown_indexes,int lenght) {
		// I have used Map avoid to Stack Over Flow Error.
		// Replace duplicated shown indexes to avoid corruptions.
        Map<Integer, Integer> countMap = new HashMap();
		for (int i = 0; i < shown_indexes.length; i++) {
			if (countMap.containsKey(shown_indexes[i])) {
				shown_indexes[i] = (int)(Math.random()*lenght);
				replaceDuplicates(shown_indexes, lenght);
			}else {
				countMap.put(shown_indexes[i], 1);
			}
		}
	}

	
	static void read_file(String[] words) {
		// TODO Auto-generated method stub
		String filePath = "kelime_listesi.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int counter = 0;
            while ((line = br.readLine()) != null) {
                // Process each line as needed
                words[counter] = line;
                counter++;
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
	}
	
	static void print_greeter() {
		System.out.println("Welcome to Yılma");
		for (int i = 0; i < 13; i++) {
			System.out.print(" ");
		}
		System.out.println("Tutun");
		for (int i = 0; i < 15; i++) {
			System.out.print(" ");
		}
		System.out.println("Üstele");
	}
	
}

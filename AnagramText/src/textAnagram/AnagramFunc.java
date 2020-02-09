package textAnagram;
import java.util.Arrays;
import java.util.Scanner;

public class AnagramFunc {
	
	public static void main(String args[]) {
	Scanner scanner = new Scanner(System.in);
    System.out.println("Enter first text");
    String word1 = scanner.nextLine();
    System.out.println("Enter Second text");
    String word2 = scanner.nextLine();
    
    char str1[] = word1.toCharArray();
    char str2[] = word2.toCharArray();
    
    if(checkAnagram(str1,str2))
    	System.out.println("Entered words are anagram");
    else
    	System.out.println("Entered words are not anagram");
    scanner.close();
	}
	
	
	static int charcount = 256; // 256 max characters
	private static boolean checkAnagram(char[] str1, char[] str2) {
		// TODO Auto-generated method stub
		
		//Create Array with value 0 
		int char1[] = new int[charcount];
		Arrays.fill(char1,0);
		int char2[] = new int[charcount];
		Arrays.fill(char2,0);
		//Append words to the newly created array
		for(int i=0; i<str1.length && i< str2.length; i++) {
			char1[str1[i]]++;
			char2[str2[i]]++;
		}
		
		// check if the length of two words are same
		if(str1.length!= str2.length)
		return false;
		// Check the characters inside two array
		for (int j=0; j< charcount; j++)
			if(char1[j]!= char2[j])
				return false;
		
      // Simple way is to use sort array and use equals to compare
		
		return true;
	}
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.Thread;
import java.util.Map;
import java.util.HashMap;

public class ThreadPooler {
	public static void main(String [] args) {
		Map<String, String> wordMap = new HashMap<>();
		WordFinder finder = new WordFinder();
		String fileName = "bible.txt";
		String outputFileName = "sorted.txt";

		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		try {
			long timer = System.currentTimeMillis();
			for(char letter: alphabet.toCharArray()) {
				finder.getAllWords(wordMap, fileName, String.valueOf(letter));
			}

			File outputFile = new File(outputFileName);
			Writer out = new FileWriter(outputFile);

			for(String key: wordMap.keySet()) {
				out.write(wordMap.get(key));
			}
			timer = System.currentTimeMillis() - timer;

			System.out.println("Completed in " + timer / 1000.0 + " seconds.");

			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("oopsie");
		} catch (IOException io) {
			System.out.println("i/oopsie");
		}
	}
}

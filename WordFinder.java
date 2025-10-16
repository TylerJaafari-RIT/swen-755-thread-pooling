import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class WordFinder {
	/**
	 * Searches a document for all words starting with a given character and puts it into a map.
	 * Case-insensitive.
	 * @param wordMap an object inheriting the Map class
	 * @param fileName the name of the file to scan. If it is in another directory, then provide the absolute path.
	 * @param character whatever words start with this character will be added to wordMap.
	 * @throws FileNotFoundException me pushing my problems down the line (no big deal)
	 */
	public void getAllWords(Map<String, String> wordMap, String fileName, String character) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);

		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			for(String word: line.split(" ")) {
				if(word.toLowerCase().startsWith(character)) {
					String val = wordMap.get(character);
					if(val != null) {
						wordMap.put(character, val + "\n" + word);
					} else {
						wordMap.put(character, word);
					}
				}
			}
		}

		scanner.close();
	}
}

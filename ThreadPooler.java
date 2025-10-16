import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class RunnableThread implements Runnable{
	private Map<String, String> wordMap;
	private String fileName;
	private String character;
	private WordFinder wordFinder;

	public RunnableThread(ConcurrentHashMap<String, String> wordMap, String fileName, String character){
		this.wordMap = wordMap;
		this.fileName = fileName;
		this.character = character;
		this.wordFinder = new WordFinder();
	}

	@Override
	public void run() {
		try{
			wordFinder.getAllWords(wordMap, fileName, character);
			System.out.println("finished with: " + character);
		}
		catch (FileNotFoundException e) {
			System.out.println("oopsie..");
		}
	}
}

public class ThreadPooler {
	public static void main(String [] args) {
		ExecutorService poolManager = Executors.newFixedThreadPool(10);
		ConcurrentHashMap<String, String> wordMap = new ConcurrentHashMap<>();
		String fileName = "bible.txt";
		String outputFileName = "sorted.txt";
		int totalThreadCalls = 0;

		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		try {
			long timer = System.currentTimeMillis();
			while (totalThreadCalls < alphabet.length()){
				int threadsPooled = 0;
				while((threadsPooled < 10) && totalThreadCalls<alphabet.length()){
					poolManager.execute(new RunnableThread(wordMap, fileName,alphabet.substring(totalThreadCalls, totalThreadCalls+1)));
					threadsPooled+=1;
					totalThreadCalls+=1;
				}
			}
			poolManager.shutdown();
			while(!poolManager.isTerminated()){

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

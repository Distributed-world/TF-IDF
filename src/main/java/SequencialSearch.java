import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.DocumentData;
import search.TFIDF;

public class SequencialSearch {
	public static final String BOOK_DIR = "C:\\Users\\07662W744\\git\\TF-IDF\\src\\main\\resources\\books";
	public static final String SQ_1 = "The best detective that catches many criminals using his deductive methods";
	public static final String SQ_2 = "The girl that falls";
	public static final String SQ_3 = "";

	public static void main(String[] args) throws FileNotFoundException {
		File documentsDirectory = new File(BOOK_DIR);
		List<String> docs = Arrays.asList(documentsDirectory.list()).stream().map(docName -> BOOK_DIR + "/" + docName)
				.collect(Collectors.toList());
		List<String> terms = TFIDF.getWordsFromLine(SQ_1);
		findMostReleventDocuments(docs, terms);

	}

	private static void findMostReleventDocuments(List<String> docs, List<String> terms) throws FileNotFoundException {
		Map<String, DocumentData> docResult = new HashMap<>();
		for (String doc : docs) {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(doc));
			List<String> lines = bufferedReader.lines().collect(Collectors.toList());
			List<String> words = TFIDF.getWordsFromLines(lines);
			DocumentData docData = TFIDF.createDocumentData(words, terms);
			docResult.put(doc, docData);
		}
		Map<Double, List<String>> documentByStore = TFIDF.getDocumentsSortedByScore(terms, docResult);
		printResult(documentByStore);
	}

	private static void printResult(Map<Double, List<String>> documentByStore) {
		for (Map.Entry<Double, List<String>> docScorePair : documentByStore.entrySet()) {
			double score = docScorePair.getKey();
			for (String doc : docScorePair.getValue()) {
				// System.out.println(doc);
				System.out.println(String.format("Book: %s - score : %f", doc.split("/")[1], score));
			}
		}

	}

}

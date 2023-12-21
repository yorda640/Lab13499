

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LiteraryAnalysis {

    public static void main(String[] args) {
        String fileName = "sample.txt";
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            StringBuilder text = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line).append("\n");
            }
            bufferedReader.close();

            // Perform literary analysis
            performAnalysis(text.toString());

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void performAnalysis(String text) {
        // Remove non-word characters and split into words
        String[] words = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

        // Word Count
        int wordCount = words.length;

        // Most Frequent Word(s)
        Map<String, Integer> wordFrequency = new HashMap<>();
        for (String word : words) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }
        String mostFrequentWord = "";
        int maxFrequency = 0;
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mostFrequentWord = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }

        // Character Analysis
        Map<Character, Integer> charFrequency = new HashMap<>();
        for (char c : text.replaceAll("[^a-zA-Z]", "").toLowerCase().toCharArray()) {
            charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
        }
        char mostFrequentChar = ' ';
        int maxCharFrequency = 0;
        for (Map.Entry<Character, Integer> entry : charFrequency.entrySet()) {
            if (entry.getValue() > maxCharFrequency) {
                mostFrequentChar = entry.getKey();
                maxCharFrequency = entry.getValue();
            }
        }

        // Sentence Analysis
        String[] sentences = text.split("[.!?]\\s*");
        int sentenceCount = sentences.length;
        String longestSentence = "";
        String shortestSentence = text.trim();
        for (String sentence : sentences) {
            if (sentence.length() > longestSentence.length()) {
                longestSentence = sentence;
            }
            if (sentence.length() < shortestSentence.length() && sentence.length() > 0) {
                shortestSentence = sentence;
            }
        }

        // Unique Word Analysis
        Map<String, Integer> uniqueWords = new HashMap<>();
        for (String word : words) {
            uniqueWords.put(word, 1);
        }

        // Output
        System.out.println("Word Count: " + wordCount);
        System.out.println("Most Frequent Word(s): " + mostFrequentWord);
        System.out.println("Character Analysis: " + charFrequency);
        System.out.println("Most Frequent Character(s): " + mostFrequentChar);
        System.out.println("Sentence Count: " + sentenceCount);
        System.out.println("Longest Sentence: " + longestSentence);
        System.out.println("Shortest Sentence: " + shortestSentence);
        System.out.println("Unique Word Analysis: " + uniqueWords.keySet());
    }
}

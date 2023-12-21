
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class VocabularyBuilder {
    private Map<String, String> vocabulary;
    private Map<String, Integer> wordScores;
    private BufferedReader reader;

    public VocabularyBuilder() {
        vocabulary = new HashMap<>();
        wordScores = new HashMap<>();
        reader = new BufferedReader(new InputStreamReader(System.in));
        loadVocabulary(); // Load existing vocabulary from file if available
    }

    public void addWord(String word, String meaning) {
        vocabulary.put(word, meaning);
        wordScores.put(word, 0); // Initializing score to 0 for newly added words
        saveVocabulary(); // Save vocabulary after addition
        System.out.println("Word '" + word + "' added to the vocabulary.");
    }

    public void retrieveMeaning(String word) {
        if (vocabulary.containsKey(word)) {
            System.out.println("Meaning of '" + word + "': " + vocabulary.get(word));
            updateScore(word); // Allow the user to rate how well they know the word
        } else {
            System.out.println("Word '" + word + "' not found in the vocabulary.");
        }
    }

    public void removeWord(String word) {
        if (vocabulary.containsKey(word)) {
            vocabulary.remove(word);
            wordScores.remove(word);
            saveVocabulary(); // Save vocabulary after removal
            System.out.println("Word '" + word + "' removed from the vocabulary.");
        } else {
            System.out.println("Word '" + word + "' not found in the vocabulary.");
        }
    }

    private void updateScore(String word) {
        try {
            System.out.print("Rate how well you know '" + word + "' (1-10): ");
            int score = Integer.parseInt(reader.readLine());
            if (score >= 1 && score <= 10) {
                wordScores.put(word, score);
                saveVocabulary(); // Save vocabulary after updating scores
            } else {
                System.out.println("Invalid score. Please enter a number between 1 and 10.");
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input for rating.");
        }
    }

    private void saveVocabulary() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("vocabulary.ser"))) {
            outputStream.writeObject(vocabulary);
            outputStream.writeObject(wordScores);
        } catch (IOException e) {
            System.out.println("Error saving vocabulary: " + e.getMessage());
        }
    }

    private void loadVocabulary() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("vocabulary.ser"))) {
            vocabulary = (Map<String, String>) inputStream.readObject();
            wordScores = (Map<String, Integer>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing vocabulary found. Starting with an empty vocabulary.");
        }
    }

    public static void main(String[] args) {
        VocabularyBuilder vocabularyBuilder = new VocabularyBuilder();

        try {
            while (true) {
                System.out.println("\n1. Add Word");
                System.out.println("2. Retrieve Meaning");
                System.out.println("3. Remove Word");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = Integer.parseInt(vocabularyBuilder.reader.readLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter word: ");
                        String newWord = vocabularyBuilder.reader.readLine();
                        System.out.print("Enter meaning: ");
                        String meaning = vocabularyBuilder.reader.readLine();
                        vocabularyBuilder.addWord(newWord, meaning);
                        break;

                    case 2:
                        System.out.print("Enter word to retrieve meaning: ");
                        String wordToRetrieve = vocabularyBuilder.reader.readLine();
                        vocabularyBuilder.retrieveMeaning(wordToRetrieve);
                        break;

                    case 3:
                        System.out.print("Enter word to remove: ");
                        String wordToRemove = vocabularyBuilder.reader.readLine();
                        vocabularyBuilder.removeWord(wordToRemove);
                        break;

                    case 4:
                        System.out.println("Exiting the vocabulary builder. Goodbye!");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}

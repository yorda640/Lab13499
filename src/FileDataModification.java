


import java.io.*;

public class FileDataModification {

    public static void main(String[] args) {
        String filePath = "path/to/your/inputfile.txt"; // Update with the actual file path

        try {
            // Step 1: Read data from the existing text file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            reader.close();

            // Step 2: Perform modification (e.g., add a prefix)
            String modifiedContent = addPrefix(content.toString(), "Modified: ");

            // Step 3: Write the modified data back to the same file
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
            printWriter.write(modifiedContent);
            printWriter.close();

            System.out.println("File modification completed successfully.");

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static String addPrefix(String text, String prefix) {
        // Implement logic to add a prefix to each line of the text
        StringBuilder modifiedText = new StringBuilder();
        String[] lines = text.split("\n");

        for (String line : lines) {
            modifiedText.append(prefix).append(line).append("\n");
        }

        return modifiedText.toString();
    }
}


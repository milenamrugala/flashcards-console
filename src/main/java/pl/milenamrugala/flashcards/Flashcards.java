package pl.milenamrugala.flashcards;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Flashcards {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String choice = "yes";
        while (choice.equalsIgnoreCase("yes")) {
            int correctAnswer = 0;

            System.out.println("Welcome to Spanish learning" +
                    " - choose the category you want to master today:");

            File availableCategory = new File(".");
            File[] files = availableCategory.listFiles((directory, name) -> name.endsWith(".txt"));

            System.out.println("Available categories:");
            for (File file : files) {
                String fileName = file.getName();
                String category = fileName.substring(0, fileName.lastIndexOf('.'));
                System.out.println(category);
            }

            System.out.println("Enter the name of the category you want to learn:");
            String userInput = scanner.nextLine();
            String fileName = userInput + ".txt";
            Map<String, String> map = getMapFromFile(fileName);
            List<String> words = new ArrayList<>(map.keySet());
            Collections.shuffle(words);

            System.out.println("Translate the following words:");
            for (String word : words) {
                System.out.println(String.format("\"%s\"", word));
                String answer = scanner.nextLine();

                if (answer.equalsIgnoreCase(map.get(word))) {
                    correctAnswer++;
                    System.out.println("Correct!");
                } else {
                    System.out.println(String.format
                            ("Sorry, the correct translation for word \"%s\" is %s.", word, map.get(word)));
                }
            }

            int totalQuestions = map.size();
            double percentage = ((double) correctAnswer / totalQuestions) * 100;
            System.out.println(String.format
                    ("You got %d out of %d answers correct (%.2f%%).", correctAnswer, totalQuestions, percentage));

            System.out.println("Do you want to continue learning? Enter \"yes\" to continue or any other key to quit.");
            choice = scanner.nextLine();
        }
    }

    private static Map<String, String> getMapFromFile(String fileName) {
        Map<String, String> map = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(":");
                if (line.length == 2) {
                    if (Math.random() < 0.5) {
                        map.put(line[0].trim(), line[1].trim());
                    } else {
                        map.put(line[1].trim(), line[0].trim());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return map;
    }
}
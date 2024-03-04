import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PlagiarismDetector {

    /** Receives the path of the txt file to be converted into a string
     * @param filePath txt file path
     * @return the txt content parsed into a string
     */
    public static String fileParser(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("The file should be .txt");
        }

        return contentBuilder.toString();
    }

    /** Applies the Longest Common Sequence algorithm to calculate common chars between two strings
     * @param wordsArray1 first string array
     * @param wordsArray2 second string array
     * @return integer result of LCS algorithm
     */
    public static int longestCommonSequence(String[] wordsArray1, String[] wordsArray2) {
        int[][] dp = new int[wordsArray1.length + 1][wordsArray2.length + 1];

        for (int i = 0; i <= wordsArray1.length; i++) {
            for (int j = 0; j <= wordsArray2.length; j++) {
                if (i == 0 || j == 0)
                    dp[i][j] = 0;
                else if (wordsArray1[i - 1].equals(wordsArray2[j - 1]))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        return dp[wordsArray1.length][wordsArray2.length];
    }

    /** Returns a string with all spelling errors between words that have 55% or more of similar letters
     * @param text1 first string array
     * @param text2 second string array
     * @return int amount of spelling word errors
     */
    public static int findSpellingErrors(String[] text1, String[] text2) {
        int errorsAmount = 0;

        for (String word2 : text2) {
            for (String word1 : text1) {
                if (!word1.isEmpty() && !word2.isEmpty()) {
                    float similarity = calculateSimilarityWord(word1, word2);
                    if (similarity >= 55 && similarity < 100) {
                        System.out.println(word2 + " - " + word1);
                        errorsAmount++;
                    }
                }
            }
        }
        return errorsAmount;
    }

    /** Calculates the percentage of the similarity according to the longest string length
     * @param word1 first string word
     * @param word2 second string word
     * @return float value that represents the percentage of similarity between words
     */
    public static float calculateSimilarityWord(String word1, String word2) {
        String[] chars1 = word1.toLowerCase().split("");
        String[] chars2 = word2.toLowerCase().split("");

        int lcsLength = longestCommonSequence(chars1, chars2);
        float maxLength = Math.max(chars1.length, chars2.length);

        return (lcsLength / maxLength) * 100;
    }

    /** Calculates the percentage of the similarity according to the longest string length
     * @param text1 first string
     * @param text2 second string
     * @return float value that represents the percentage of similarity
     */
    public static float calculateSimilarity(String text1, String text2) {
        String[] chars1 = text1.toLowerCase().split("\\s+");
        String[] chars2 = text2.toLowerCase().split("\\s+");

        int wordsWithErrors = findSpellingErrors(chars1, chars2);
        int lcs = longestCommonSequence(chars1, chars2);
        int lcsFinal = Math.min(lcs + wordsWithErrors, chars2.length);

        return (float)(lcsFinal * 100) / chars2.length;
    }

    /** Main method to print the percentage of similarity and spelling errors in texts
     * @param file1Path txt file path
     * @param file2Path txt file path
     */
    public static void verifyPlagiarism(String file1Path, String file2Path){
        String text1 = fileParser(file1Path).replaceAll("\\.", "");
        String text2 = fileParser(file2Path).replaceAll("\\.", "");

        System.out.println(calculateSimilarity(text1, text2) + "%");
    }
}

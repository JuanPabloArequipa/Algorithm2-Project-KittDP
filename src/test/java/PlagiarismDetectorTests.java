import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlagiarismDetectorTests {
    @Test
    public void Test1(){
        String text1 = PlagiarismDetector.fileParser("src/main/resources/file1.txt");
        String text2 = PlagiarismDetector.fileParser("src/main/resources/file2.txt");

        assertEquals(PlagiarismDetector.calculateSimilarity(text1, text2), 95.83333587646484);
    }

    @Test
    public void Test2(){
        String text3 = PlagiarismDetector.fileParser("src/main/resources/file3.txt");
        String text4 = PlagiarismDetector.fileParser("src/main/resources/file4.txt");

        assertEquals(PlagiarismDetector.calculateSimilarity(text3, text4), 50.0);
    }

    @Test
    public void Test3(){
        String text5 = PlagiarismDetector.fileParser("src/main/resources/file5.txt");
        String text6 = PlagiarismDetector.fileParser("src/main/resources/file6.txt");

        assertEquals(PlagiarismDetector.calculateSimilarity(text5, text6), 100.0);
    }
}

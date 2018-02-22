import org.junit.Test;
import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReutersParserTest {

    @Test
    public void testIterator() {
        File file = new File("/tmp/Reuters");
        ReutersParser p = new ReutersParser(file);
        final String randomTitle = "VOGTLE NUCLEAR PLANT GETS FULL-POWER LICENSE";
        int count = 0;
        boolean foundRandomTitle = false;
        for(ReutersArticle a : p){
            String title = a.getTag("TITLE");
            String body = a.getTag("BODY");
            // Get labels from other tags.
            count += 1;
            if (title.equalsIgnoreCase(randomTitle)) {
                foundRandomTitle = true;
            }
        }

        assertEquals(21578, count); //There is a reason it is called the Reuters-21578 data set.
        assertTrue(foundRandomTitle);
    }
}

package ovh.pacordonnier.main;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by pcordonnier on 28/09/16.
 */
public class MainTest {

    public static void comparison(int nb) throws Exception {
        int a = nb;
        if (a < 10) {
            throw new TooTooSmall();
        }
        if (a < 100) {
            throw new TooSmall();
        }
    }

    private static class TooTooSmall extends Exception {
    }

    private static class TooSmall extends Exception {
    }

    @Test(expected = MainTest.TooSmall.class)
    public void testComparison() throws Exception {
        comparison(12);
    }

    @Test(expected = TooTooSmall.class)
    public void testComparisonBis() throws Exception {
        comparison(2);
    }


    @Test
    public void getNumber() throws Exception {
        final String regex = ".*E(\\d)\\s-\\s.*";
        final String string = "E1 - EM214 - Eiffel 1 - Salle EM214";

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println(matcher.groupCount());
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        }
    }

    @Test
    public void getRoom() throws Exception {
        final String regex = "E\\d - ([^-]+) - ";
        final String string = "E1 - EM214 - Eiffel 1 - Salle EM214 - E2 - Michel - zd";

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        }

        /*if (matcher.groupCount() == 1) {
            System.out.println(matcher.group(1));
        } else if (matcher.groupCount() == 0) {
            System.out.println("nothing");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            while (matcher.find()) {
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    stringBuilder.append(matcher.group(i)).append(" - ");
                }
            }
            System.out.println(stringBuilder.toString());
        }*/
    }

    @Test
    public void startsWithINGTest() throws Exception {
        assertTrue(startsWithING("ING565"));
        assertTrue(startsWithING("ING654218484"));
        assertTrue(startsWithING("INGszefojzejfe"));
        assertFalse(startsWithING("Michel"));;
    }

    private boolean startsWithING(String string) {

        final String regex = "^ING.*";

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

}
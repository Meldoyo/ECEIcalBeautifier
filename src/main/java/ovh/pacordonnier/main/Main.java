package ovh.pacordonnier.main;

import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.io.text.ICalReader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pcordonnier on 28/09/16.
 */
public class Main {
    public static void main(String[] args) {
        ICalReader iCalReader = getIcal(args[0]);

        if (iCalReader == null) {
            System.out.println("Problem happend while getting the calendar");
        }

        try {
            ICalendar iCalendar;
            while ((iCalendar = iCalReader.readNext()) != null) {
                for (VEvent event : iCalendar.getEvents()) {
                    if (event.getLocation() != null) {
                        event.setSummary(getECESummary(event.getSummary().getValue(), event.getLocation().getValue()));
                        event.setLocation(getECELocation(event.getLocation().getValue()));
                    }
                }
                iCalendar.write(new File(args[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getECELocation(String location) {
        String eceBuildingNumber = getECEBuildingNumber(location);
        switch (Integer.valueOf(eceBuildingNumber)) {
            case 1:
                return "10 rue Sextius Michel";
            case 2:
                return "37 quai de Grenelle";
            case 3:
                return "37 Quai de Grenelle";
            default:
                return location;
        }
    }

    private static String getECEBuildingNumber(String location) {
        final String regex = ".*E(\\d)\\s-\\s.*";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(location);

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                return matcher.group(i);
            }
        }
        return location;
    }

    public static String getECESummary(String summary, String location) {
        String[] split = summary.split(" - ");
        String eceLocation = getECERoom(location);
        StringBuilder stringBuilder = new StringBuilder();
        if (startsWithING(split[0])) {
            stringBuilder.append(getECECourse(summary));
            stringBuilder.append(" - ");
            stringBuilder.append(getECERoom(location));
            stringBuilder.append(" - ");
            stringBuilder.append(getECETeacher(summary));
        } else stringBuilder.append(split[0]);
        return stringBuilder.toString();
    }

    private static String getECECourse(String summary) {
        return summary.split(" - ")[1];
    }

    private static String getECETeacher(String summary) {
        return summary.split(" - ")[2];
    }

    private static String getECERoom(String location) {
        final String regex = "E\\d - ([^-]+) - ";

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(location);

        matcher.find();

        if (matcher.groupCount() == 1) {
            return matcher.group(1);
        } else if (matcher.groupCount() == 0) {
            return "";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            while (matcher.find()) {
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    stringBuilder.append(matcher.group(i)).append(" - ");
                }
            }
            return stringBuilder.toString();
        }
    }

    public static boolean startsWithING(String string) {
        return string.matches("^ING.*");
    }

    public static ICalReader getIcal(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return new ICalReader(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

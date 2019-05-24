import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodTruckFinder {
        public static void main(String[] args) {
            /**
             * Making a call to the Socrata API and saving the XML response line by line in a StringBuilder.
             * We then pass this to our ResponseParser class.
             */
            try {
                String socrataKey = "Aq4AmdCs4u8QjV3JCkl4lmH4E";
                String currentTime = getCurrentTime();

                /*Ran into trouble with URI encoding for special characters, which might have been related to intellij
                IDE specific, but I ran out of time to debug. This could be cleaner. */
                String request = "https://data.sfgov.org/resource/jjew-r69b.xml?$$app_token=" +
                        socrataKey + "&dayofweekstr=" + getCurrentDay() + "&$where=start24%3C=%27" + currentTime +
                "%27%20AND%20end24%3E=%27"+ currentTime + "%27";

                URL url = new URL(request);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                /* Building a string from our response data */
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                }
                rd.close();

                ResponseParser parser = new ResponseParser(response.toString());
                parser.buildResultsList();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        /**
         * Getting the current day of the week and the current time to narrow my foodtruck query.
         * TODO: For future iterations, the Socrata API has options to Order the results alphabetically, so I would add
         * this into my request above. I noticed this at the last minute, after already implementing FoodTruckLister.
         * */
        public static String getCurrentDay() {
            Date now = new Date();
            SimpleDateFormat day = new SimpleDateFormat("EEEE");
            return day.format(now);
        }

        public static String getCurrentTime() {
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("HH:mm");
            return format.format(date);
        }
}
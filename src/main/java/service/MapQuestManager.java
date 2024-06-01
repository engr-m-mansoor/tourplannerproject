package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapQuestManager {

    private static final Logger log = LogManager.getLogger(MapQuestManager.class);

    public static String requestRoute(String start, String end) {
        HttpURLConnection connection = null;
        try {
            String key = ConfigurationManager.GetConfigProperty("MapKey");
            URL url = new URL("https://openrouteservice.org/dev/v2/directions/user?key=" + key + "&from=" + start + "&to=" + end);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Length", Integer.toString(0));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes("");
            }

            // Get Response
            InputStream is = connection.getInputStream();
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                return response.toString();
            }
        } catch (Exception e) {
            log.error("Request route failed", e);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static BufferedImage requestRouteImage(String start, String end) {
        String jsonString = requestRoute(start, end);
        if (jsonString == null) {
            log.error("JsonObject is null");
            return null;
        }
        JSONObject obj = new JSONObject(jsonString);
        if (!obj.getJSONObject("route").has("sessionId") || !obj.getJSONObject("route").has("boundingBox")) {
            log.error("Wrong JsonObject");
            return null;
        }
        String session = obj.getJSONObject("route").getString("sessionId");
        JSONObject boundingBox = obj.getJSONObject("route").getJSONObject("boundingBox");
        try {
            StringBuilder params = new StringBuilder("&size=700,300");
            params.append("&defaultMarker=none");
            params.append("&zoom=11");
            params.append("&rand=737758036");
            params.append("&session=").append(session);
            String box = boundingBox.getJSONObject("lr").getFloat("lat") + "," + boundingBox.getJSONObject("lr").getFloat("lng") + "," + boundingBox.getJSONObject("ul").getFloat("lat") + "," + boundingBox.getJSONObject("ul").getFloat("lng");
            params.append("&boundingBox=").append(box);

            String key = ConfigurationManager.GetConfigProperty("MapKey");
            URL url = new URL("http://www.mapquestapi.com/staticmap/v5/map?key=" + key + params);
            try (InputStream is = url.openStream()) {
                return ImageIO.read(is);
            } catch (IOException e) {
                log.error("Cannot open stream: " + e.getMessage());
                return null;
            }
        } catch (IOException e) {
            log.error("Cannot create URL: " + e.getMessage(), e);
            return null;
        }
    }

    public static String getRouteDistance(String start, String end) {
        String jsonString = requestRoute(start, end);
        if (jsonString == null) {
            log.error("JsonObject is null");
            return "0";
        }
        JSONObject obj = new JSONObject(jsonString);
        return String.valueOf(obj.getJSONObject("route").getFloat("distance"));
    }
}
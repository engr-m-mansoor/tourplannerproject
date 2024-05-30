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

    public static String requestRoute(String start, String end){
        HttpURLConnection connection = null;
        try{
            String key = ConfigurationManager.GetConfigProperty("MapKey");
            URL url = new URL("http://www.mapquestapi.com/directions/v2/route?key=" + key + "&from="+start+"&to=" + end);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Length",
                    Integer.toString(0));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes("");
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            Logger log = LogManager.getLogger(MapQuestManager.class);
            log.error("Request route failed");
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    public static BufferedImage requestRouteImage(String start, String end){
        Logger log = LogManager.getLogger(MapQuestManager.class);
        String jsonString =requestRoute(start, end);
        if(jsonString == null) {
            log.error("JsonObject is null");
            return null;
        }
        JSONObject obj = new JSONObject(jsonString);
        if(!obj.getJSONObject("route").has("sessionId") || !obj.getJSONObject("route").has("boundingBox")) {
            log.error("Wrong JsonObject");
            return null;
        }
        String session = obj.getJSONObject("route").getString("sessionId");
        JSONObject boundingBox = obj.getJSONObject("route").getJSONObject("boundingBox");
        try {
            String params;
            params = "&size=700,300";
            params += "&defaultMarker=none";
            params += "&zoom=11";
            params += "&rand=737758036";
            params += "&session="+session;
            String box = boundingBox.getJSONObject("lr").getFloat("lat") + "," + boundingBox.getJSONObject("lr").getFloat("lng") + "," + boundingBox.getJSONObject("ul").getFloat("lat") + "," + boundingBox.getJSONObject("ul").getFloat("lng");
            params += "&boundingBox="+box;


            String key = ConfigurationManager.GetConfigProperty("MapKey");
            URL url = new URL("http://www.mapquestapi.com/staticmap/v5/map?key=" + key + params);
            InputStream is;
            try {
                is = url.openStream();
            }
            catch (Exception e){
                log.error("Cant open Stream: " + e.getMessage());
                return null;
            }
            return ImageIO.read(is);

        } catch (IOException e) {
            log.error("Cant create URL: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static String getRouteDistance(String start, String end){
        String jsonString =requestRoute(start, end);
        JSONObject obj = new JSONObject(jsonString);
        return String.valueOf(obj.getJSONObject("route").getFloat("distance"));
    }
}

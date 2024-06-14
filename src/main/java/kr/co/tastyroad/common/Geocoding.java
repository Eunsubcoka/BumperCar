package kr.co.tastyroad.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Geocoding {
    private static final String API_KEY = "597a12321ce91d26c9101324b5955ebd"; // Kakao API Key

    public static double[] getLatLong(String address) {
        try {
            String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=" + URLEncoder.encode(address, "UTF-8");
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "KakaoAK " + API_KEY);
            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(result.toString());
            JsonNode documentNode = rootNode.path("documents").get(0);
            double lat = documentNode.path("y").asDouble();
            double lon = documentNode.path("x").asDouble();

            return new double[]{lat, lon};
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

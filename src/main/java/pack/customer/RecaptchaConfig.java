package pack.customer;

import org.springframework.context.annotation.Configuration;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

@Configuration
public class RecaptchaConfig {

    // Google reCAPTCHA 검증 API의 URL
    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    // HTTP 요청 헤더에 사용할 User-Agent 정보
    private static final String USER_AGENT = "Mozilla/5.0";

    // Google reCAPTCHA 비밀 키
    private static String secret;

    // Google reCAPTCHA 비밀 키 설정 메서드
    public static void setSecretKey(String key) {
        secret = key;
    }

    // Google reCAPTCHA 검증 메서드
    public static boolean verify(String gRecaptchaResponse) {
        // reCAPTCHA 응답이 없거나 비어있으면 실패로 간주
        if (gRecaptchaResponse == null || gRecaptchaResponse.isEmpty()) {
            return false;
        }

        try {
            // Google reCAPTCHA 검증 API에 연결 설정
            URL url = new URL(RECAPTCHA_VERIFY_URL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            // HTTP 요청 설정
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // 매개변수 인코딩
            String postParams = "secret=" + URLEncoder.encode(secret, "UTF-8") +
                    "&response=" + URLEncoder.encode(gRecaptchaResponse, "UTF-8");

            // POST 요청 전송 설정
            connection.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(postParams);
                wr.flush();
            }

            // HTTP 응답 코드 확인
            int responseCode = connection.getResponseCode();

            // HTTP 응답이 성공(200)한 경우
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                // JSON 응답 읽고 파싱
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
                    JsonObject jsonObject = jsonReader.readObject();
                    jsonReader.close();

                    // JSON에서 'success' 필드의 값 반환
                    return jsonObject.getBoolean("success");
                }
            } else {
                // HTTP 응답이 실패한 경우 (예: 로깅)
                return false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

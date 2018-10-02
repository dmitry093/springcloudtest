package methods;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CommonMethods {
    public static ResponseEntity getResponse(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> s = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("Response (" + url + "): " + s.getBody());
        return s;
    }
}

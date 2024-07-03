package internettehnologii.individualnizadaci.zad4.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Service
public class ClientServiceImpl implements ClientService {
    @Override
    public String sendGetRequest(String path) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(path))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Document doc = Jsoup.parse(response.body());
            Element element = doc.getElementById("data");
            if (element != null) {
                return element.text();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    @Override
    public String sendPostRequest(String path, String data, HttpServletRequest requestBrowser, HttpServletResponse responseBrowser) {
        HttpClient client = HttpClient.newBuilder().build();
        String formData = "operand1=" + URLEncoder.encode(data, StandardCharsets.UTF_8);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(path))
                .POST(HttpRequest.BodyPublishers.ofString(formData));

        Cookie[] cookies = requestBrowser.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("word")) {
                    requestBuilder.setHeader("Cookie", c.getName() + "=" + URLEncoder.encode(c.getValue(), StandardCharsets.UTF_8));
                    break;
                }
            }
        }

        HttpRequest request = requestBuilder.build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<String> headers = response.headers().allValues("Set-Cookie");
            for (String header : headers) {
                Cookie c = parseSetCookieHeader(header);
                if (c.getName().equals("word")) {
                    c.setValue(URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8));
                    responseBrowser.addCookie(c);
                    break;
                }
            }

            Document doc = Jsoup.parse(URLDecoder.decode(response.body(), StandardCharsets.UTF_8));
            Element element = doc.getElementById("data");
            if (element != null) {
                return element.text();
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

    private Cookie parseSetCookieHeader(String setCookieHeader) {
        String[] cookieParts = setCookieHeader.split(";");
        if (cookieParts.length > 0) {
            String[] nameValuePair = cookieParts[0].split("=");
            if (nameValuePair.length > 1) {
                String name = nameValuePair[0].trim();
                String value = nameValuePair[1].trim();
                return new Cookie(name, URLDecoder.decode(value, StandardCharsets.UTF_8));
            }
        }
        return new Cookie("word", "");
    }
}

package internettehnologii.individualnizadaci.zad4.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
            System.out.println(response.body());
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
    public String sendPostRequest(String path,String data) {
        HttpClient client = HttpClient.newBuilder().build();
        String formData = "operand1=" + data;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(path))
                .POST(HttpRequest.BodyPublishers.ofString(formData))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

}

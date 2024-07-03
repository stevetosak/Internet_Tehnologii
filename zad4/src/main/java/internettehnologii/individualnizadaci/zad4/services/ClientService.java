package internettehnologii.individualnizadaci.zad4.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ClientService {
    String sendGetRequest(String path);
    String sendPostRequest(String path, String data, HttpServletRequest request, HttpServletResponse response);

}

package internettehnologii.individualnizadaci.zad4.services;

public interface ClientService {
    String sendGetRequest(String path);
    String sendPostRequest(String path,String data);

}

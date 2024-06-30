package internettehologii.individualnizadaci.zad2.services;

public interface InputManager {
    String[] parseQueryString(String query);
    String[] parsePostReq(String body);
    String validateInput(String[] paramNvalue);
    String getErrorMessage();


}

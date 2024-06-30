package internettehnologii.individualnizadaci.zad4.services;

import jakarta.servlet.http.HttpSession;

public interface LoginService {
    String validate(String username,String password);
}

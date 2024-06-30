package internettehnologii.individualnizadaci.zad4.services;

import internettehnologii.individualnizadaci.zad4.entities.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService{
    //user1, lozinka1, DATE
    //user2, lozinka2, HANGMAN
    //user3, lozinka3, HANGMAN
    //user4, lozinka4, DATE
    private final List<User> userList = new ArrayList<>();
    public LoginServiceImpl(){
        userList.add(new User("user1","lozinka1","DATE"));
        userList.add(new User("user2","lozinka2","HANGMAN"));
        userList.add(new User("user3","lozinka3","HANGMAN"));
        userList.add(new User("user4","lozinka4","DATE"));
    }
    @Override
    public String validate(String username, String password) {
        return userList
                .stream()
                .filter(user -> user.match(username,password))
                .map(User::getRole)
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        LoginServiceImpl loginService = new LoginServiceImpl();
        String role = loginService.validate("use","lozinka1");
        System.out.println(role);
    }
}

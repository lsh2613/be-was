package controller.user;

import controller.ModelView;
import exception.AlreadyExistUserException;
import model.HttpRequest;
import model.HttpResponse;
import service.UserService;

public class UserCreateController implements UserController {
    private final UserService userService;

    public UserCreateController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ModelView process(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");

        httpResponse.set302Redirect();
        String path = "";

        try {
            userService.addUser(userId, password, name, email);
        } catch (AlreadyExistUserException e) {
            path = "/user/form_failed.html";
            httpResponse.putToHeaderMap("Location", path);

            return new ModelView(path);
        }

        // 회원가입 성공
        path = "/index.html";
        httpResponse.putToHeaderMap("Location", path);

        return new ModelView(path);
    }
}

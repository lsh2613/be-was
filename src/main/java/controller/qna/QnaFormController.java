package controller.qna;

import controller.ModelView;
import exception.UserNotFoundException;
import model.Request;
import model.Response;
import model.User;
import service.QnaService;
import service.UserService;

public class QnaFormController implements QnaController{
    private final UserService userService;

    public QnaFormController(UserService userService) {
        this.userService = userService;
    }
    @Override
    public ModelView process(Request request, Response response) {
        try {
            response.set200Ok();

            String userId = request.getCookie("sid");
            User findUser = userService.findUserById(userId);
        } catch (IllegalArgumentException | UserNotFoundException e) {
            e.printStackTrace();
            return new ModelView("/templates/user/login.html");
        }

        // 로그인 상태일 경우
        return new ModelView("/templates/qna/form.html");
    }
}

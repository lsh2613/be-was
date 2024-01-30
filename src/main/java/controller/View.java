package controller;

import config.AppConfig;
import constant.HeaderType;
import exception.UserNotFoundException;
import model.Qna;
import model.HttpRequest;
import model.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.HtmlBuilder;
import util.SessionManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class View {
    private static final Logger logger = LoggerFactory.getLogger(View.class);
    private final UserService userService = AppConfig.userService();
    private String viewPath;

    public View(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(HttpRequest httpRequest, HttpResponse httpResponse, ModelView mv) {
        File file = new File(viewPath);
        byte[] body = new byte[(int) file.length()];

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            fileInputStream.read(body);
        } catch (IOException e) {
            e.printStackTrace();
        }

        httpResponse.setHttpStatus(mv.getHttpStatus());

        String uri = mv.getViewName();
        int start = uri.lastIndexOf(".");
        String type = uri.substring(start + 1);

        httpResponse.addHeader(HeaderType.CONTENT_TYPE, "text/" + type + ";charset=utf-8");

        // 동적 페이지 변환을 위한 데이터가 존재한다면
        if (mv.getModel().size() > 0) {
            String fileString = new String(body);
            Map<String, Object> model = mv.getModel();
            for (String key : model.keySet()) {
                String renderedHtml = HtmlBuilder.replace(key, model.get(key));
                fileString = fileString.replace(key, renderedHtml);
            }

            body = fileString.getBytes();
        }

        // todo
        if (mv.getViewName().contains("index.html")) {
            String fileString = new String(body);

            Object attribute = mv.getAttribute("{{qna-list}}");
            if (attribute != null) {
                Collection<Qna> qnaCollection = (Collection<Qna>) attribute;
                ArrayList<Qna> qnas = new ArrayList<>(qnaCollection);
                if (qnas.size() == 0) {
                    fileString = fileString.replace("{{qna-list}}", "");
                } else {
                    String rendered = HtmlBuilder.replace("{{qna-list}}", qnas);
                    fileString = fileString.replace("{{qna-list}}", rendered);
                }
            }

            User loginUser = null;
            try {
                String sid = httpRequest.getCookie("sid");
                loginUser = SessionManager.getSessionById(sid);
            } catch (IllegalArgumentException | UserNotFoundException e) {
                fileString = fileString.replace("{{welcome}}", "");
                body = fileString.getBytes();
                httpResponse.setBody(body);
                return;
            }
            String renderedHtml = HtmlBuilder.replace("{{welcome}}", loginUser.getName());
            fileString = fileString.replace("{{welcome}}", renderedHtml);
            body = fileString.getBytes();
        }

        httpResponse.setBody(body);
    }

}

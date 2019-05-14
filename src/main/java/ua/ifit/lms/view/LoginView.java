package ua.ifit.lms.view;

import ua.ifit.lms.dao.entity.User;

public class LoginView {

    public String getloginPage(boolean isSuccessfullLogin) {
        IndexSingletonView indexSingletonView = IndexSingletonView.getInstance();
        String indBase = indexSingletonView.getIndexHtml();
        String loginForm = indexSingletonView.getLoginForm();
        String loginFormFail = indexSingletonView.getLoginFormFail();
        String menu = indexSingletonView.getMenu();
        return indBase
                .replace("<!--### insert html here ### -->", menu)
                .replace("<!--### insert html here ### -->",
                        isSuccessfullLogin ? loginForm : loginFormFail);
    }

    public String welcomUserPage(User user) {
        IndexSingletonView indexSingletonView = IndexSingletonView.getInstance();
        String indBase = indexSingletonView.getIndexHtml();
        String loginForm = indexSingletonView.getLoginForm();
        return indBase.replace("<!--### insert html here ### -->", "Hello " + user.getName());
    }

    public String getRegisterPage() {
        IndexSingletonView indexSingletonView = IndexSingletonView.getInstance();
        String indBase = indexSingletonView.getIndexHtml();
        String registerForm = indexSingletonView.getRegister();
        String menu = indexSingletonView.getMenu();
        return indBase
                .replace("<!--### insert html here ### -->", menu)
                .replace("<!--### insert html here ### -->", registerForm);
    }
}

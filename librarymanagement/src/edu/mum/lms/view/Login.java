package edu.mum.lms.view;

import edu.mum.lms.controller.LoginDao;
import edu.mum.lms.controller.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends ControllerBase {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Text alertMessage;

    @FXML
    public void doLogin() throws Exception {
        String uname = username.getText();
        String pass = password.getText();
        if (uname.isEmpty() && pass.isEmpty()) {
            showMessage("Username and password both required", alertMessage);
        } else {
            LoginDao loginDao = new LoginDao();
            UserSession session = loginDao.validateLogin(uname, pass);
            if (session == null) {
                showMessage("Invalid Username/Password", alertMessage);
            } else {
                Stage stage = new Stage();
                stage.setTitle("MUM Library Management System");
                
                stage.setScene(
                    createScene(
                        loadMainPane()
                    )
                );

                stage.show();
                
                Stage oldStage = (Stage) password.getScene().getWindow();
                oldStage.close();
                
                UserSession.GetSession.setSession(session);
                System.out.println(UserSession.GetSession.getSession().getMemberId());
            }
                
        }
    }
    
    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
            mainPane
        );

        scene.getStylesheets().setAll(
            getClass().getResource("style.css").toExternalForm()
        );

        return scene;
    }
    
    private Pane loadMainPane() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.MAIN));

        MainController mainController = loader.getController();

        Navigator.setMainController(mainController);
        Navigator.loadScene(Navigator.CHECKOUT_FORM);
        return mainPane;
    }

}

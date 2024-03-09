package views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ctrl.IControllerForView;


public class View implements IViewForController, Initializable {

    private final IControllerForView controller;
    @FXML
    private Pane dropZone;

    public View(IControllerForView controller) {
        this.controller = controller;
    }

    @Override
    public void start() {
        Platform.startup(() -> {
            try {
                Stage mainStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view.fxml"));
                fxmlLoader.setControllerFactory(type -> {
                    return this;
                });
                Parent root = (Parent) fxmlLoader.load();
                Scene principalScene = new Scene(root);
                principalScene.setUserAgentStylesheet("views/css/primer-light.css");
                mainStage.setScene(principalScene);
                mainStage.setTitle("NC1");
                mainStage.setResizable(false);
                mainStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
                Platform.exit();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dropZone.setBackground(new Background(new BackgroundImage(new Image("views/images/background.png"), null, null, null, null)));
    }

    @Override
    public void messageInfo(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        });
    }

    @Override
    public void messageErreur(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        });
    }

    @FXML
    public void handleDragOver(javafx.scene.input.DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
            dropZone.setStyle("-fx-opacity: 0.5;");
        }
        event.consume();
    }

    @FXML
    public void handleDragDropped(javafx.scene.input.DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            controller.processNotionZip(db.getFiles().get(0).getAbsolutePath());
            success = true;
            dropZone.setStyle("-fx-opacity: 1;");
            messageInfo("Décompression et nettoyage terminés avec succès.");
        }
        event.setDropCompleted(success);
        event.consume();
    }

}

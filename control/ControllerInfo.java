package control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControllerInfo implements Initializable {
  @FXML
  private ImageView botaoVoltar;

  @FXML
  void voltar(MouseEvent event) throws Exception {
    Stage stage = (Stage) botaoVoltar.getScene().getWindow();
    Scene cenaPrincipal = new Scene(createContent());
    stage.setScene(cenaPrincipal);
  }

  @Override
  public void initialize(URL arg0, ResourceBundle rb) {
  }

  private Parent createContent() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/view_inicial.fxml"));
    Pane root = loader.load();
    return root;
  }

}

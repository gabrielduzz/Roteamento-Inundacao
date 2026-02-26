package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
// import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControllerInicial implements Initializable {
  @FXML
  private ImageView botaoFechar;
  @FXML
  private ImageView botaoInfo;
  @FXML
  private ImageView botaoIniciar;
  @FXML
  private ImageView botaoVersao1;
  @FXML
  private ImageView botaoVersao2;
  @FXML
  private ImageView botaoVersao3;
  @FXML
  private ImageView botaoVersao4;
  @FXML
  private ImageView textoTTL;
  @FXML
  private Spinner<Integer> spinnerTTL;

  @FXML
  private ImageView botaoTeste;

  private static int versao = -1;
  private static int valorTTL;
  private ControllerInfo controllerInfo = new ControllerInfo();

  public static int getVersao() {
    return versao;
  }

  public static int getValorTTL() {
    return valorTTL;
  }

  /********************************************************************
   * Metodo: setVersao
   * Funcao:
   * Parametros: event
   * Retorno: void
   ********************************************************************/
  @FXML
  void setVersao(MouseEvent event) {
    String idBotao = event.getPickResult().getIntersectedNode().getId();
    switch (idBotao) {
      case "botaoVersao1": {
        versao = 1;
        botaoVersao1.setImage(new Image("/img/selecao versao 1.png"));
        botaoVersao2.setImage(new Image("/img/botao versao 2.png"));
        botaoVersao3.setImage(new Image("/img/botao versao 3.png"));
        botaoVersao4.setImage(new Image("/img/botao versao 4.png"));
        textoTTL.setVisible(false);
        spinnerTTL.setVisible(false);
        break;
      }
      case "botaoVersao2": {
        versao = 2;
        botaoVersao1.setImage(new Image("/img/botao versao 1.png"));
        botaoVersao2.setImage(new Image("/img/selecao versao 2.png"));
        botaoVersao3.setImage(new Image("/img/botao versao 3.png"));
        botaoVersao4.setImage(new Image("/img/botao versao 4.png"));
        textoTTL.setVisible(false);
        spinnerTTL.setVisible(false);
        break;
      }
      case "botaoVersao3": {
        versao = 3;
        botaoVersao1.setImage(new Image("/img/botao versao 1.png"));
        botaoVersao2.setImage(new Image("/img/botao versao 2.png"));
        botaoVersao3.setImage(new Image("/img/selecao versao 3.png"));
        botaoVersao4.setImage(new Image("/img/botao versao 4.png"));
        spinnerTTL.setVisible(true);
        textoTTL.setVisible(true);
        break;
      }
      case "botaoVersao4": {
        versao = 4;
        botaoVersao1.setImage(new Image("/img/botao versao 1.png"));
        botaoVersao2.setImage(new Image("/img/botao versao 2.png"));
        botaoVersao3.setImage(new Image("/img/botao versao 3.png"));
        botaoVersao4.setImage(new Image("/img/selecao versao 4.png"));
        spinnerTTL.setVisible(true);
        textoTTL.setVisible(true);
        break;
      }
    }
  }

  /********************************************************************
   * Metodo: fechar
   * Funcao: fecha a tela
   * Parametros: event
   * Retorno: void
   ********************************************************************/
  @FXML
  void fechar(MouseEvent event) {
    System.exit(0);
  }

  void telaInfo() throws Exception {
    Stage stage = (Stage) botaoInfo.getScene().getWindow();
    Scene cenaInfo = new Scene(createContentInfo());
    stage.setScene(cenaInfo);
  }

  /********************************************************************
   * Metodo: iniciar
   * Funcao: chamar a cena principal
   * Parametros: event
   * Retorno: void
   ********************************************************************/
  @FXML
  void iniciar(MouseEvent event) throws Exception {
    if (versao != -1) {
      valorTTL = spinnerTTL.getValue();
      Stage stage = (Stage) botaoIniciar.getScene().getWindow();
      Scene cenaPrincipal = new Scene(createContentPrincipal());
      stage.setScene(cenaPrincipal);
    }
  }

  @Override
  public void initialize(URL arg0, ResourceBundle rb) {
    versao = -1;
    spinnerTTL.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 5));
    textoTTL.setVisible(false);
    spinnerTTL.setVisible(false);
    botaoInfo.setOnMouseClicked(event -> {
      try {
        telaInfo();
      } catch (Exception e) {
        System.out.println(e.toString());
        e.printStackTrace();
      }

    });
  }

  private Parent createContentPrincipal() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/view_principal.fxml"));
    loader.setController(ControllerPrincipal.getInstanciaController());
    Pane root = loader.load();
    return root;
  }

  private Parent createContentInfo() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/view_info.fxml"));
    loader.setController(controllerInfo);
    Pane root = loader.load();
    return root;
  }

}

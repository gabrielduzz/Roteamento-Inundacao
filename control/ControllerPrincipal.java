package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
// import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
// import model.Aresta;
import model.Grafo;
import model.Pacote;
import model.Roteador;

public class ControllerPrincipal implements Initializable {

  @FXML
  private AnchorPane panePrincipal;
  @FXML
  private ImageView textoEscolha;
  @FXML
  private ImageView bandeiraFinal;
  @FXML
  private ImageView botaoFechar;
  @FXML
  private ImageView botaoVoltar;
  @FXML
  private Label labelPacotesGerados;

  private Grafo grafo;
  private boolean iniciou = false;
  private Roteador origem;
  private Roteador destinoFinal;
  // private int versao = ControllerInicial.getVersao();
  private static ControllerPrincipal instanciaController;

  public AnchorPane getPanePrincipal() {
    return panePrincipal;
  }

  public Roteador getDestinoFinal() {
    return destinoFinal;
  }

  public ImageView getBandeiraFinal() {
    return bandeiraFinal;
  }

  public Label getLabelPacotesGerados() {
    return labelPacotesGerados;
  }

  private ControllerPrincipal() {
  }

  public static ControllerPrincipal getInstanciaController() {
    if (instanciaController == null) {
      instanciaController = new ControllerPrincipal();
    }
    return instanciaController;
  }

  public double getCentroImagemX(ImageView no) {
    return no.getLayoutX() + Grafo.TAMANHO_NO / 2;
  }

  public double getCentroImagemY(ImageView no) {
    return no.getLayoutY() + Grafo.TAMANHO_NO / 1.5;
  }

  public void escolherOrigemEDestino(ImageView imagemNo) {
    ImageView luz = new ImageView();
    luz.setFitHeight(Grafo.TAMANHO_NO * 1.5);
    luz.setFitWidth(Grafo.TAMANHO_NO * 1.5);
    luz.setLayoutX(getCentroImagemX(imagemNo) - Grafo.TAMANHO_NO / 1.5);
    luz.setLayoutY(getCentroImagemY(imagemNo) - Grafo.TAMANHO_NO / 1.5);
    panePrincipal.getChildren().add(luz);
    imagemNo.toFront();

    if (iniciou) {
      return;
    }

    if (origem == null) {
      luz.setImage(new Image("/img/luz azul.png"));
      origem = grafo.getNosPorImagem().get(imagemNo);
      textoEscolha.setImage(new Image("/img/texto de escolha destino.png"));
    } else if (destinoFinal == null) {
      luz.setImage(new Image("/img/luz vermelha.png"));
      destinoFinal = grafo.getNosPorImagem().get(imagemNo);
      iniciou = true;
      textoEscolha.setImage(new Image("/img/texto de escolha origem.png"));
      textoEscolha.setVisible(false);

      origem.enviarPacote(new Pacote(ControllerInicial.getValorTTL()));
    }

  }

  @FXML
  void fechar(MouseEvent event) {
    System.exit(0);
  }

  @FXML
  void voltar(MouseEvent event) throws Exception {
    for (Pacote pacote : Pacote.getPacotes()) {
      panePrincipal.getChildren().remove(pacote.getImagemPacote());
    }

    for (PathTransition transicao : Pacote.getTransicoes()) {
      transicao.stop();
    }

    Grafo.listaDeArestas.clear();
    Grafo.listaDeNosPorId.clear();
    Grafo.listaDeNosPorImagem.clear();
    Pacote.getPacotes().clear();
    Pacote.getTransicoes().clear();
    iniciou = false;
    origem = null;
    destinoFinal = null;
    Roteador.setQtdPacotesGerados(0);
    Roteador.setChegou(false);
    Stage stage = (Stage) botaoVoltar.getScene().getWindow();
    Scene cenaPrincipal = new Scene(createContent());
    stage.setScene(cenaPrincipal);
  }

  @Override
  public void initialize(URL arg0, ResourceBundle rb) {
    grafo = new Grafo();
    grafo.criarGrafoAPartirDeArquivo("backbone.txt");
    for (ImageView imagemNo : grafo.getNosPorImagem().keySet()) {
      imagemNo.setOnMouseClicked(event -> {
        escolherOrigemEDestino(imagemNo);
      });
    }
  }

  private Parent createContent() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/view_inicial.fxml"));
    Pane root = loader.load();
    return root;
  }
}

package model;

import java.util.ArrayList;
import java.util.List;

import control.ControllerPrincipal;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.util.Pair;

public class Pacote extends Thread {
  private Roteador origem;
  private Roteador destino;
  private int ttl;
  private static List<PathTransition> transicoes = new ArrayList<>();
  private static List<Pacote> pacotes = new ArrayList<>();
  private ImageView imagemPacote;
  private ControllerPrincipal cP = ControllerPrincipal.getInstanciaController();
  private Pane pane = cP.getPanePrincipal();

  public Pacote(int ttl) {
    this.ttl = ttl;
  }

  public Pacote(Roteador origem, Roteador destino) {
    this.origem = origem;
    this.destino = destino;
  }

  public Pacote(Roteador origem, Roteador destino, int ttl) {
    this.origem = origem;
    this.destino = destino;
    this.ttl = ttl;
  }

  public int getTTL() {
    return ttl;
  }

  public ImageView getImagemPacote() {
    return imagemPacote;
  }

  public Roteador getOrigem() {
    return origem;
  }

  public Roteador getDestino() {
    return destino;
  }

  public static List<PathTransition> getTransicoes() {
    return transicoes;
  }

  public static List<Pacote> getPacotes() {
    return pacotes;
  }

  public void run() {
    seguirAresta();
  }

  private void seguirAresta() {
    Platform.runLater(() -> {
      ImageView imagemOrigem = origem.getImagem();
      ImageView imagemDestino = destino.getImagem();
      Line line = new Line(
          cP.getCentroImagemX(imagemOrigem),
          cP.getCentroImagemY(imagemOrigem),
          cP.getCentroImagemX(imagemDestino),
          cP.getCentroImagemY(imagemDestino));

      PathTransition pathTransition = new PathTransition();
      transicoes.add(pathTransition);
      pacotes.add(this);
      imagemPacote = new ImageView("/img/pacote.png");
      imagemPacote.setFitWidth(20);
      imagemPacote.setFitHeight(20);
      pane.getChildren().add(imagemPacote);
      imagemOrigem.toFront();
      imagemDestino.toFront();
      cP.getBandeiraFinal().toFront();

      pathTransition.setNode(imagemPacote);
      pathTransition.setPath(line);
      pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

      Aresta aresta = Grafo.listaDeArestas.get(new Pair<Roteador, Roteador>(origem, destino));
      // System.out.println(String.format("%d %d %d", origem.getId(), destino.getId(),
      // aresta.getPeso()));
      double duracaoBase = 1000;
      double duracaoAjustada = duracaoBase * (1 + Math.log(1 + aresta.getPeso()));

      pathTransition.setDuration(Duration.millis(duracaoAjustada));
      pathTransition.setCycleCount(1);
      pathTransition.setAutoReverse(false);

      pathTransition.setOnFinished(event -> {
        pane.getChildren().remove(imagemPacote);
        transicoes.remove(pathTransition);
        pacotes.remove(this);
        destino.enviarPacote(this);
      });

      pathTransition.play();
    });

  }
}

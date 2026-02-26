package model;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Pair;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// import java.util.ArrayList;
import java.util.HashMap;
// import java.util.List;
import java.util.Map;
import java.util.Random;

import control.ControllerPrincipal;

public class Grafo {
  public static final int TAMANHO_NO = 50;
  private static final int RAIO = 220;
  private static final int CENTER_X = 512;
  private static final int CENTER_Y = 360;

  public static Map<Integer, Roteador> listaDeNosPorId = new HashMap<>();
  public static Map<ImageView, Roteador> listaDeNosPorImagem = new HashMap<>();
  public static Map<Pair<Roteador, Roteador>, Aresta> listaDeArestas = new HashMap<>();
  public static ControllerPrincipal cP = ControllerPrincipal.getInstanciaController();

  // private List<Roteador> listaDeNosPorId = new ArrayList<>();
  // private List<Integer> nosCriados = new ArrayList<>();
  // private Map<Integer, List<Integer>> listaAdjacencia = new HashMap<>();
  private int numeroDeNos; // Variável para armazenar o número total de nós
  private Pane pane = ControllerPrincipal.getInstanciaController().getPanePrincipal();

  public Map<Integer, Roteador> getNosPorId() {
    return listaDeNosPorId;
  }

  public Map<ImageView, Roteador> getNosPorImagem() {
    return listaDeNosPorImagem;
  }

  public Map<Pair<Roteador, Roteador>, Aresta> getListaDeArestas() {
    return listaDeArestas;
  }

  public void criarGrafoAPartirDeArquivo(String arquivo) {
    try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
      String linha = br.readLine();
      numeroDeNos = Integer.parseInt(linha.split(";")[0]);

      while ((linha = br.readLine()) != null) {
        String[] partes = linha.split(";");
        int no1 = Integer.parseInt(partes[0]);
        int no2 = Integer.parseInt(partes[1]);
        int valor = Integer.parseInt(partes[2]);

        Roteador roteador1 = listaDeNosPorId.computeIfAbsent(no1, n -> new Roteador(no1, criarNo(n, pane)));
        Roteador roteador2 = listaDeNosPorId.computeIfAbsent(no2, n -> new Roteador(no2, criarNo(n, pane)));

        listaDeNosPorImagem.putIfAbsent(roteador1.getImagem(), roteador1);
        listaDeNosPorImagem.putIfAbsent(roteador2.getImagem(), roteador2);

        // listaAdjacencia.get(no1).add(no2);
        // listaAdjacencia.get(no2).add(no1);

        roteador1.getListaAdjacencia().add(roteador2);
        roteador2.getListaAdjacencia().add(roteador1);

        criarAresta(roteador1.getImagem(), roteador2.getImagem(), valor, pane);
      }

      // System.out.println(listaAdjacencia.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private ImageView criarNo(int noId, Pane pane) {
    double angulo = 2 * Math.PI * (noId - 1) / numeroDeNos;
    double x = CENTER_X + RAIO * Math.cos(angulo);
    double y = CENTER_Y + RAIO * Math.sin(angulo);

    Random r = new Random();
    int numeroImagemRoteador = r.nextInt(4) + 1;
    String url = "/img/roteador " + numeroImagemRoteador + ".png";

    ImageView no = new ImageView();
    no.setImage(new Image(url));
    no.setFitHeight(TAMANHO_NO);
    no.setFitWidth(TAMANHO_NO);
    no.setLayoutX(x - TAMANHO_NO / 2);
    no.setLayoutY(y - TAMANHO_NO / 2);

    DropShadow dropShadow = new DropShadow(TAMANHO_NO - 30, 0, 5, Color.BLACK);
    no.setEffect(dropShadow);

    Text labelNo = new Text(String.valueOf((char) (noId + 64)));
    labelNo.setFill(Color.WHITE);
    labelNo.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR, 20));

    if (x > CENTER_X) {
      no.setScaleX(-1);
      labelNo.setLayoutX(x + TAMANHO_NO / 2.5);
    } else {
      labelNo.setLayoutX(x - TAMANHO_NO / 2);
    }

    if (y > CENTER_Y) {
      labelNo.setLayoutY(y + TAMANHO_NO);
    } else {
      labelNo.setLayoutY(y - TAMANHO_NO / 2);
    }

    pane.getChildren().addAll(no, labelNo);
    return no;
  }

  private void criarAresta(ImageView no1, ImageView no2, int valor, Pane pane) {
    Line line = new Line(cP.getCentroImagemX(no1), cP.getCentroImagemY(no1),
        cP.getCentroImagemX(no2), cP.getCentroImagemY(no2));
    line.setStroke(Color.WHITE);
    line.setStrokeWidth(2);
    pane.getChildren().add(line);

    Roteador roteador1 = listaDeNosPorImagem.get(no1);
    Roteador roteador2 = listaDeNosPorImagem.get(no2);
    Aresta aresta = new Aresta(roteador1, roteador2, valor, line);
    listaDeArestas.putIfAbsent(new Pair<Roteador, Roteador>(roteador1, roteador2), aresta);
    listaDeArestas.putIfAbsent(new Pair<Roteador, Roteador>(roteador2, roteador1), aresta);
    no1.toFront();
    no2.toFront();

    double midX = (cP.getCentroImagemX(no1) + cP.getCentroImagemX(no2)) / 2;
    double midY = (cP.getCentroImagemY(no1) + cP.getCentroImagemY(no2)) / 2;
    Text textoPeso = new Text(midX - 10, midY - 10, String.valueOf(valor));
    textoPeso.setFill(Color.WHITE);
    textoPeso.setFont(Font.font("system", FontWeight.BOLD, FontPosture.REGULAR,
        20));
    pane.getChildren().add(textoPeso);
  }

}

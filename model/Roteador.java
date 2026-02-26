package model;

import java.util.ArrayList;
import java.util.List;

import control.ControllerInicial;
import control.ControllerPrincipal;
// import javafx.scene.image.Image;
// import control.ControllerPrincipal;
import javafx.scene.image.ImageView;

public class Roteador {
  private ImageView imagem;
  private int id;
  private static int qtdPacotesGerados = 0;
  private static boolean chegou = false;
  private boolean passou = false;
  private List<Roteador> listaAdjacencia = new ArrayList<>();
  private ControllerPrincipal cP = ControllerPrincipal.getInstanciaController();

  public Roteador(int id, ImageView imagem) {
    this.id = id;
    this.imagem = imagem;
  }

  public List<Roteador> getListaAdjacencia() {
    return listaAdjacencia;
  }

  public ImageView getImagem() {
    return imagem;
  }

  public int getId() {
    return id;
  }

  public static void setQtdPacotesGerados(int qtdPacotesGerados) {
    Roteador.qtdPacotesGerados = qtdPacotesGerados;
  }

  public static void setChegou(boolean chegou) {
    Roteador.chegou = chegou;
  }

  public void enviarPacote(Pacote pacoteRecebido) {
    int versaoAlgoritmo = ControllerInicial.getVersao();
    switch (versaoAlgoritmo) {
      case 1: {
        enviarPacoteVersao1(pacoteRecebido);
        break;
      }
      case 2: {
        enviarPacoteVersao2(pacoteRecebido);
        break;
      }
      case 3: {
        enviarPacoteVersao3(pacoteRecebido);
        break;
      }
      case 4: {
        enviarPacoteVersao4(pacoteRecebido);
        break;
      }
    }
  }

  private void enviarPacoteVersao1(Pacote pacoteRecebido) {
    if (this.equals(cP.getDestinoFinal())) {
      ImageView bandeiraFinal = cP.getBandeiraFinal();
      bandeiraFinal.setLayoutX(cP.getCentroImagemX(imagem) - Grafo.TAMANHO_NO / 4);
      bandeiraFinal.setLayoutY(cP.getCentroImagemY(imagem) - Grafo.TAMANHO_NO / 2);
      bandeiraFinal.setVisible(true);
      chegou = true;
      return;
    }

    for (Roteador destino : listaAdjacencia) {
      Pacote novoPacote = new Pacote(this, destino);
      novoPacote.start();
      if (!chegou) {
        qtdPacotesGerados++;
        cP.getLabelPacotesGerados().setText(Integer.toString(qtdPacotesGerados));
      }
    }
  }

  private void enviarPacoteVersao2(Pacote pacoteRecebido) {
    if (this.equals(cP.getDestinoFinal())) {
      ImageView bandeiraFinal = cP.getBandeiraFinal();
      bandeiraFinal.setLayoutX(cP.getCentroImagemX(imagem) - Grafo.TAMANHO_NO / 4);
      bandeiraFinal.setLayoutY(cP.getCentroImagemY(imagem) - Grafo.TAMANHO_NO / 2);
      bandeiraFinal.setVisible(true);
      chegou = true;
      return;
    }

    for (Roteador destino : listaAdjacencia) {
      if (destino.equals(pacoteRecebido.getOrigem())) {
        continue;
      }

      Pacote novoPacote = new Pacote(this, destino);
      novoPacote.start();
      if (!chegou) {
        qtdPacotesGerados++;
        cP.getLabelPacotesGerados().setText(Integer.toString(qtdPacotesGerados));
      }
    }
  }

  private void enviarPacoteVersao3(Pacote pacoteRecebido) {
    if (this.equals(cP.getDestinoFinal())) {
      ImageView bandeiraFinal = cP.getBandeiraFinal();
      bandeiraFinal.setLayoutX(cP.getCentroImagemX(imagem) - Grafo.TAMANHO_NO / 4);
      bandeiraFinal.setLayoutY(cP.getCentroImagemY(imagem) - Grafo.TAMANHO_NO / 2);
      bandeiraFinal.setVisible(true);
      chegou = true;
      return;
    }

    if (pacoteRecebido.getTTL() <= 0) {
      return;
    }

    for (Roteador destino : listaAdjacencia) {
      if (destino.equals(pacoteRecebido.getOrigem())) {
        continue;
      }

      int ttlAtual = pacoteRecebido.getTTL();
      // System.out.println(ttlAtual);
      Pacote novoPacote = new Pacote(this, destino, --ttlAtual);
      novoPacote.start();
      if (!chegou) {
        qtdPacotesGerados++;
        cP.getLabelPacotesGerados().setText(Integer.toString(qtdPacotesGerados));
      }
    }
  }

  /********************************************************************
   * descricao do algoritmo:
   * para Cada pacote que chega em um roteador, é checado
   * se este já foi encaminhado pelo roteador atual. Caso não
   * tenha sido, é enviado paratodas as interfaces de rede deste
   * roteador,EXCETO por aquela pela qual ele chegou. E cada
   * roteador VERIFICA a informação de TTL para decidir se o
   * pacote continua a circular na rede
   * 
   ********************************************************************/

  private void enviarPacoteVersao4(Pacote pacoteRecebido) {
    if (this.equals(cP.getDestinoFinal())) {
      ImageView bandeiraFinal = cP.getBandeiraFinal();
      bandeiraFinal.setLayoutX(cP.getCentroImagemX(imagem) - Grafo.TAMANHO_NO / 4);
      bandeiraFinal.setLayoutY(cP.getCentroImagemY(imagem) - Grafo.TAMANHO_NO / 2);
      bandeiraFinal.setVisible(true);
      chegou = true;
      return;
    }

    if (pacoteRecebido.getTTL() <= 0 || passou) {
      return;
    }

    for (Roteador destino : listaAdjacencia) {
      if (destino.equals(pacoteRecebido.getOrigem())) {
        continue;
      }

      int ttlAtual = pacoteRecebido.getTTL();
      Pacote novoPacote = new Pacote(this, destino, --ttlAtual);
      novoPacote.start();
      if (!chegou) {
        qtdPacotesGerados++;
        cP.getLabelPacotesGerados().setText(Integer.toString(qtdPacotesGerados));
      }
    }

    passou = true;
  }
}

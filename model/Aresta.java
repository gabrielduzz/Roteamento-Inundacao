package model;

import javafx.scene.shape.Line;

public class Aresta {
  private Roteador origem;
  private Roteador destino;
  private int peso;
  private Line linha;

  public Aresta(Roteador origem, Roteador destino, int peso, Line linha) {
    this.origem = origem;
    this.destino = destino;
    this.peso = peso;
    this.linha = linha;
  }

  public Roteador getOrigem() {
    return origem;
  }

  public Roteador getDestino() {
    return destino;
  }

  public int getPeso() {
    return peso;
  }

  public Line getLinha() {
    return linha;
  }
}

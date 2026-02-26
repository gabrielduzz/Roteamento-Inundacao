# Simulador de Roteamento por Inundação

Este projeto é uma ferramenta educacional que implementa e visualiza o funcionamento do algoritmo de **Roteamento por Inundação** e suas variações.

## 🚀 Sobre o Projeto

O objetivo é mensurar a eficiência de diferentes estratégias de inundação em uma sub-rede, utilizando métricas como a quantidade de pacotes gerados até que o destino final seja atingido.

### Variações Implementadas

1.  **Inundação Total:** Todo pacote recebido é enviado para todas as interfaces.
2.  **Inundação Seletiva:** O pacote é enviado para todas as interfaces, exceto por aquela de onde ele veio.
3.  **Inundação com TTL:** Inclui a verificação do tempo de vida (*Time-To-Live*) do pacote para evitar circulações infinitas.
4.  **Inundação Otimizada** .

## 🛠️ Funcionalidades

-   **Interface Gráfica (GUI):** Visualização em tempo real da movimentação dos pacotes
-   **Configuração Dinâmica:** A estrutura da rede (nós e pesos) é carregada via arquivo `backbone.txt`.
-   **Controle de Parâmetros:** Escolha manual do transmissor, receptor e valor do TTL.
-   **Métricas:** Comparativo numérico de pacotes gerados entre as versões.

## 📂 Estrutura do Arquivo de Entrada

O simulador lê um arquivo `backbone.txt` com o seguinte formato:
- 1ª linha: Número de nós da sub-rede.
- Linhas seguintes: Conexões no formato `NÓ1;NÓ2;PESO`.


## 📝 Como Executar

1. Certifique-se de que o arquivo `backbone.txt` está na raiz do projeto.
2. Execute o arquivo principal:
   ```bash
   # Exemplo para Python ou Java, dependendo da sua implementação
   python main.py

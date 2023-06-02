import java.awt.Point;
import java.util.*;

/**
 * Classe de exemplo para a implementação de um Jogador para o Jogo Snake.
 * Nesta implementação, a próxima direção da cobra é escolhida aleatoriamente
 * entre as direções possíveis (que não geram colisões).
 * <p>
 * Use esta classe como base inicial para a sua solução do jogo. Basicamente
 * você precisará reimplementar o método {@code getDirecao}.
 * 
 * @author Horácio
 */

public class SnakeJogador {
    private Snake jogo;
    private List<Point> caminho;
    
    /**
     * Cria um novo jogador para o jogo passado como parâmetro.
     * @param jogo jogo snake.
     */
    public SnakeJogador(Snake jogo) {
        this.jogo = jogo;
        this.caminho = new ArrayList<Point>();
    }

    boolean isValido(int x, int y){
        return x >= 0 && x < jogo.getAltura() && y >= 0 && y < jogo.getLargura();
    }

    boolean ehLivre(int[][] campo, int x, int y){
        return isValido(x, y) && campo[y][x] == 0;
    }

    boolean isDestino(int i, int j, Point destino){
        return i == destino.y && j == destino.x;
    }

    double calculaH(int linha, int coluna, Point destino){
        // Método de distância de Manhattan
        return Math.abs(linha - destino.x) + Math.abs(coluna - destino.y);
    }

    void imprimirCampo(Celula [][] campo){
        for (Celula[] is : campo) {
            for (Celula i : is) {
                System.out.print(i+" ");
            }
            System.out.println();
        }
        System.out.println("FIM");
    }

    void imprimeCaminho(){
        for (Point point : caminho) {
            System.out.print(point+" ");
        }
        System.out.println("\nFIM");
    }

    List<Point> criaCaminho(Celula [][] campo, Point destino){
        List<Point> caminho = new ArrayList<>();
        int linha = destino.x;
        int coluna = destino.y;
        while(!(campo[linha][coluna].parent_i == linha && campo[linha][coluna].parent_j == coluna)){
            caminho.add(new Point(coluna, linha));
            int temp_i = campo[linha][coluna].parent_i;
            int temp_j = campo[linha][coluna].parent_j;
            linha = temp_i;
            coluna = temp_j;
        }
        caminho.add(new Point(coluna, linha));
        Collections.reverse(caminho);
        return caminho;
    }

    /**
     * Executado pelo método {@link Snake#inicia()} a cada 'tick' do jogo para
     * perguntar qual a próxima direção da cobra ('C'ima, 'D'ireita, 'B'aixo,
     * 'E'squerda ou 'N'enhum).
     * 
     * @return a próxima direção da cobra.
     */
    public char getDirecao() {
        /*
         * IMPLEMENTE AQUI A SUA SOLUÇÃO PARA O JOGO
         */
        char direcao = 'N';

        Point cabeca = jogo.getSegmentos().get(0);
        Point comida = jogo.getComida();
        if(caminho.isEmpty()){
            // Construir campo
            int campo[][] = new int[jogo.getAltura()][jogo.getLargura()];
            for(Point p : jogo.getSegmentos()){
                campo[p.y][p.x] = 1;
            }
            Celula [][] celulas = new Celula[jogo.getAltura()][jogo.getLargura()];
            imprimirCampo(celulas);
            // A*Star pathfiding
            // Start closed list
            boolean [][] closedList = new boolean[jogo.getAltura()][jogo.getLargura()];
            // Start open list with the starting point
            HashMap<Double, Point> openList = new HashMap<>();
            openList.put(0.0, cabeca);
            Boolean achouComida = false;
            while(!openList.isEmpty()){
                Point atual = openList.remove(openList.keySet().iterator().next());
                int i = atual.y;
                int j = atual.x;
                closedList[i][j] = true;
                double novoG, novoH, novoF;
                // Pra cima
                if(isValido(i-1, j)){
                    if(isDestino(i-1, j, comida)){
                        celulas[i-1][j].parent_i = i;
                        celulas[i-1][j].parent_j = j;
                        criaCaminho(celulas, comida);
                        achouComida = true;
                        break;
                    }
                    else if(!closedList[i + 1][j] && ehLivre(campo, i-1, j)){
                        novoG = celulas[i][j].g + 1;
                        novoH = calculaH(i-1, j, comida);
                        novoF = novoG + novoH;
                        if(celulas[i-1][j].f == Float.MAX_VALUE || celulas[i-1][j].f > novoF){
                            openList.put(novoF, new Point(j, i-1));
                            celulas[i-1][j].f = novoF;
                            celulas[i-1][j].g = novoG;
                            celulas[i-1][j].h = novoH;
                            celulas[i-1][j].parent_i = i;
                            celulas[i-1][j].parent_j = j;
                        }
                    }
                }
                // Pra baixo
                if(isValido(i+1, j)){
                    if(isDestino(i+1, j, comida)){
                        celulas[i+1][j].parent_i = i;
                        celulas[i+1][j].parent_j = j;
                        criaCaminho(celulas, comida);
                        achouComida = true;
                        break;
                    }
                    else if(!closedList[i + 1][j] && ehLivre(campo, i+1, j)){
                        novoG = celulas[i][j].g + 1;
                        novoH = calculaH(i+1, j, comida);
                        novoF = novoG + novoH;
                        if(celulas[i+1][j].f == Float.MAX_VALUE || celulas[i+1][j].f > novoF){
                            openList.put(novoF, new Point(j, i+1));
                            celulas[i+1][j].f = novoF;
                            celulas[i+1][j].g = novoG;
                            celulas[i+1][j].h = novoH;
                            celulas[i+1][j].parent_i = i;
                            celulas[i+1][j].parent_j = j;
                        }
                    }
                }
                // Pra esquerda
                if(isValido(i, j-1)){
                    if(isDestino(i, j-1, comida)){
                        celulas[i][j-1].parent_i = i;
                        celulas[i][j-1].parent_j = j;
                        criaCaminho(celulas, comida);
                        achouComida = true;
                        break;
                    }
                    else if(!closedList[i][j - 1] && ehLivre(campo, i, j-1)){
                        novoG = celulas[i][j].g + 1;
                        novoH = calculaH(i, j-1, comida);
                        novoF = novoG + novoH;
                        if(celulas[i][j-1].f == Float.MAX_VALUE || celulas[i][j-1].f > novoF){
                            openList.put(novoF, new Point(j-1, i));
                            celulas[i][j-1].f = novoF;
                            celulas[i][j-1].g = novoG;
                            celulas[i][j-1].h = novoH;
                            celulas[i][j-1].parent_i = i;
                            celulas[i][j-1].parent_j = j;
                        }
                    }
                }
                // Pra direita
                if(isValido(i, j+1)){
                    if(isDestino(i, j+1, comida)){
                        celulas[i][j+1].parent_i = i;
                        celulas[i][j+1].parent_j = j;
                        criaCaminho(celulas, comida);
                        achouComida = true;
                        break;
                    }
                    else if(!closedList[i][j + 1] && ehLivre(campo, i, j+1)){
                        novoG = celulas[i][j].g + 1;
                        novoH = calculaH(i, j+1, comida);
                        novoF = novoG + novoH;
                        if(celulas[i][j+1].f == Float.MAX_VALUE || celulas[i][j+1].f > novoF){
                            openList.put(novoF, new Point(j+1, i));
                            celulas[i][j+1].f = novoF;
                            celulas[i][j+1].g = novoG;
                            celulas[i][j+1].h = novoH;
                            celulas[i][j+1].parent_i = i;
                            celulas[i][j+1].parent_j = j;
                        }
                    }
                }
                if(!achouComida){
                    System.out.println("Não achou comida");
                }
            }
        }
        if(!caminho.isEmpty()){
            Point proximo = caminho.remove(0);
            if(proximo.x == cabeca.x){
                if(proximo.y == cabeca.y+1){
                    direcao = 'D';
                }else{
                    direcao = 'E';
                }
            }else{
                if(proximo.x == cabeca.x+1){
                    direcao = 'B';
                }else{
                    direcao = 'C';
                }
            }
        }
        return direcao;
    }
    
    class Celula{
        int parent_i, parent_j;
        double f, g, h;
        Celula(){
            this.parent_i = -1;
            this.parent_j = -1;
            this.f = Float.MAX_VALUE;
            this.g = Float.MAX_VALUE;
            this.h = Float.MAX_VALUE;
        }
        @Override
        public String toString() {
            return "f:" + f + ", g:" + g + ", h:" + h+"parent_i:"+parent_i+", parent_j:"+parent_j;
        }
    }
}

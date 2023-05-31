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

    void imprimeCampo(int [][] campo){
        System.out.println("CAMPO:");
        for (int[] is : campo) {
            System.out.println(Arrays.toString(is));
        }
        System.out.println("FIM CAMPO");
    }

    void imprimeCaminho(){
        for (Point point : caminho) {
            System.out.print(point+" ");
        }
        System.out.println("\nFIM");
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
            int[][] campo = new int[jogo.getAltura()][jogo.getLargura()];
            boolean[][] visitado = new boolean[jogo.getAltura()][jogo.getLargura()];
            int segVal = 0;
            for(Point segmento: jogo.getSegmentos()){
                campo[segmento.y][segmento.x] = segVal;
                segVal--;
            }
            Queue<Point> bfsFila = new LinkedList<Point>();
            bfsFila.add(comida);
            visitado[comida.y][comida.x] = true;
            while(!bfsFila.isEmpty()){
                Point atual = bfsFila.remove();
                int x = atual.x;
                int y = atual.y;
                if(x == cabeca.x && y == cabeca.y){
                    break;
                }
                if(y > 0 && !visitado[y-1][x] && campo[y-1][x] <= 0){
                    bfsFila.add(new Point(x, y-1));
                    visitado[y-1][x] = true;
                    campo[y-1][x] = campo[y][x] + 1;
                }
                if(y < jogo.getAltura()-1 && !visitado[y+1][x] && campo[y+1][x] <= 0){
                    bfsFila.add(new Point(x, y+1));
                    visitado[y+1][x] = true;
                    campo[y+1][x] = campo[y][x] + 1;
                }
                if(x > 0 && !visitado[y][x-1] && campo[y][x-1] <= 0){
                    bfsFila.add(new Point(x-1, y));
                    visitado[y][x-1] = true;
                    campo[y][x-1] = campo[y][x] + 1;
                }
                if(x < jogo.getLargura()-1 && !visitado[y][x+1] && campo[y][x+1] <= 0){
                    bfsFila.add(new Point(x+1, y));
                    visitado[y][x+1] = true;
                    campo[y][x+1] = campo[y][x] + 1;
                }
            }
            imprimeCampo(campo);
            if(visitado[cabeca.y][cabeca.x]){
                // montar caminho
                // while()
            }
        }
        if(!caminho.isEmpty()){

        }
        return direcao;
    }
    
}

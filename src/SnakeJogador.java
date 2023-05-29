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
            int[][] campo = new int[jogo.getLargura()][jogo.getAltura()];
            for (Point segmento : jogo.getSegmentos()) {
                campo[segmento.x][segmento.y] = 1;
            }
            campo[cabeca.x][cabeca.y] = 2;
            
            Queue<Point> fila = new LinkedList<Point>();
            boolean[][] visitado = new boolean[jogo.getLargura()][jogo.getAltura()];
            
            fila.add(comida);
            visitado[comida.x][comida.y] = true;
            campo[comida.x][comida.y] = 0;

            while(!fila.isEmpty()){
                Point atual = fila.remove();

                if(atual.y > 0 && !visitado[atual.x][atual.y-1] && campo[atual.x][atual.y-1] == 0){
                    fila.add(new Point(atual.x, atual.y-1));
                    visitado[atual.x][atual.y-1] = true;
                    campo[atual.x][atual.y-1] = campo[atual.x][atual.y] + 1;
                }
                if(atual.y < jogo.getAltura()-1 && !visitado[atual.x][atual.y+1] && campo[atual.x][atual.y+1] == 0){
                    fila.add(new Point(atual.x, atual.y+1));
                    visitado[atual.x][atual.y+1] = true;
                    campo[atual.x][atual.y+1] = campo[atual.x][atual.y] + 1;
                }
                if(atual.x > 0 && !visitado[atual.x-1][atual.y] && campo[atual.x-1][atual.y] == 0){
                    fila.add(new Point(atual.x-1, atual.y));
                    visitado[atual.x-1][atual.y] = true;
                    campo[atual.x-1][atual.y] = campo[atual.x][atual.y] + 1;
                }
                if(atual.x < jogo.getLargura()-1 && !visitado[atual.x+1][atual.y] && campo[atual.x+1][atual.y] == 0){
                    fila.add(new Point(atual.x+1, atual.y));
                    visitado[atual.x+1][atual.y] = true;
                    campo[atual.x+1][atual.y] = campo[atual.x][atual.y] + 1;
                }
                
                if(atual.y > 0 && !visitado[atual.x][atual.y-1] && campo[atual.x][atual.y-1] == 2){
                    visitado[atual.x][atual.y-1] = true;
                    break;
                }
                if(atual.y < jogo.getAltura()-1 && !visitado[atual.x][atual.y+1] && campo[atual.x][atual.y+1] == 2){
                    visitado[atual.x][atual.y+1] = true;
                    break;
                }
                if(atual.x > 0 && !visitado[atual.x-1][atual.y] && campo[atual.x-1][atual.y] == 2){
                    visitado[atual.x-1][atual.y] = true;
                    break;
                }
                if(atual.x < jogo.getLargura()-1 && !visitado[atual.x+1][atual.y] && campo[atual.x+1][atual.y] == 2){
                    visitado[atual.x+1][atual.y] = true;
                    break;
                }
            }
            // backtrack for make the path
            for (int i = 0; i < jogo.getLargura(); i++) {
                for (int j = 0; j < jogo.getAltura(); j++) {
                    if(campo[i][j] == 0){
                        campo[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
            campo[comida.x][comida.y] = -1;
            Scanner sc = new Scanner(System.in);
            Point caminhador = new Point(cabeca);
            while(campo[caminhador.x][caminhador.y] != -1){
                System.out.println("caminho: " + caminhador.getX()+ " " + caminhador.getY()+" "+campo[caminhador.x][caminhador.y]);
                if(caminhador.y > 0 && campo[caminhador.x][caminhador.y-1] == campo[caminhador.x][caminhador.y] - 1){
                    caminho.add(new Point(caminhador.x, caminhador.y-1));
                    caminhador = new Point(caminhador.x, caminhador.y-1);
                }
                else if(caminhador.y < jogo.getAltura()-1 && campo[caminhador.x][caminhador.y+1] == campo[caminhador.x][caminhador.y] - 1){
                    caminho.add(new Point(caminhador.x, caminhador.y+1));
                    caminhador = new Point(caminhador.x, caminhador.y+1);
                }
                else if(caminhador.x > 0 && campo[caminhador.x-1][caminhador.y] == campo[caminhador.x][caminhador.y] - 1){
                    caminho.add(new Point(caminhador.x-1, caminhador.y));
                    caminhador = new Point(caminhador.x-1, caminhador.y);
                }
                else if(caminhador.x < jogo.getLargura()-1 && campo[caminhador.x+1][caminhador.y] == campo[caminhador.x][caminhador.y] - 1){
                    caminho.add(new Point(caminhador.x+1, caminhador.y));
                    caminhador = new Point(caminhador.x+1, caminhador.y);
                }
                else{
                    System.out.println("ERRO");
                }
                sc.nextInt();
            }
            sc.close();
            System.out.println(caminho);
        }
        direcao = caminho.get(0).x == cabeca.x ? (caminho.get(0).y > cabeca.y ? 'B' : 'C') : (caminho.get(0).x > cabeca.x ? 'D' : 'E');
        caminho.remove(0);
        return direcao;
    }
    
}

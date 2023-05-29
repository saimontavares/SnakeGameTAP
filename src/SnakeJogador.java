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

    void imprimirCampo(int [][] campo){
        for (int[] is : campo) {
            System.out.println(Arrays.toString(is));
        }
        System.out.println("FIM");
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
            for (Point segmento : jogo.getSegmentos()) {
                campo[segmento.y][segmento.x] = -1;
            }
            campo[cabeca.y][cabeca.x] = -2;
            
            Queue<Point> fila = new LinkedList<Point>();
            boolean[][] visitado = new boolean[jogo.getAltura()][jogo.getLargura()];
            
            fila.add(comida);
            visitado[comida.y][comida.x] = true;
            campo[comida.y][comida.x] = 0;

            while(!fila.isEmpty()){
                Point atual = fila.remove();
                if(atual.y > 0 && !visitado[atual.y-1][atual.x] && campo[atual.y-1][atual.x] == 0){
                    fila.add(new Point(atual.x, atual.y-1));
                    visitado[atual.y-1][atual.x] = true;
                    campo[atual.y-1][atual.x] = campo[atual.y][atual.x] + 1;
                    if(atual.y-1 == 0){
                        campo[atual.y-1][atual.x]++;
                    }
                }
                if(atual.y < jogo.getAltura()-1 && !visitado[atual.y+1][atual.x] && campo[atual.y+1][atual.x] == 0){
                    fila.add(new Point(atual.x, atual.y+1));
                    visitado[atual.y+1][atual.x] = true;
                    campo[atual.y+1][atual.x] = campo[atual.y][atual.x] + 1;
                    if(atual.y+1 == jogo.getAltura()-1){
                        campo[atual.y+1][atual.x]++;
                    }
                }
                if(atual.x > 0 && !visitado[atual.y][atual.x-1] && campo[atual.y][atual.x-1] == 0){
                    fila.add(new Point(atual.x-1, atual.y));
                    visitado[atual.y][atual.x-1] = true;
                    campo[atual.y][atual.x-1] = campo[atual.y][atual.x] + 1;
                    if(atual.x-1 == 0){
                        campo[atual.y][atual.x-1]++;
                    }
                }
                if(atual.x < jogo.getLargura()-1 && !visitado[atual.y][atual.x+1] && campo[atual.y][atual.x+1] == 0){
                    fila.add(new Point(atual.x+1, atual.y));
                    visitado[atual.y][atual.x+1] = true;
                    campo[atual.y][atual.x+1] = campo[atual.y][atual.x] + 1;
                    if(atual.x+1 == jogo.getLargura()-1){
                        campo[atual.y][atual.x+1]++;
                    }
                }
                
                if(atual.y > 0 && !visitado[atual.y-1][atual.x] && campo[atual.y-1][atual.x] == -2){
                    campo[atual.y-1][atual.x] = campo[atual.y][atual.x] + 1;
                    break;
                }
                if(atual.y < jogo.getAltura()-1 && !visitado[atual.y+1][atual.x] && campo[atual.y+1][atual.x] == -2){
                    campo[atual.y+1][atual.x] = campo[atual.y][atual.x] + 1;
                    break;
                }
                if(atual.x > 0 && !visitado[atual.y][atual.x-1] && campo[atual.y][atual.x-1] == -2){
                    campo[atual.y][atual.x-1] = campo[atual.y][atual.x] + 1;
                    break;
                }
                if(atual.x < jogo.getLargura()-1 && !visitado[atual.y][atual.x+1] && campo[atual.y][atual.x+1] == -2){
                    campo[atual.y][atual.x+1] = campo[atual.y][atual.x] + 1;
                    break;
                }

            }
            imprimirCampo(campo);
            // backtrack to make the path
            for (int i = 0; i < jogo.getLargura(); i++) {
                for (int j = 0; j < jogo.getAltura(); j++) {
                    if(campo[j][i] == 0){
                        campo[j][i] = Integer.MAX_VALUE;
                    }
                }
            }
            campo[comida.y][comida.x] = 0;
            Point caminhador = new Point(cabeca);
            while(campo[caminhador.y][caminhador.x]!= 0){
                Point possivel = null;
                if(caminhador.y > 0 && campo[caminhador.y-1][caminhador.x] < campo[caminhador.y][caminhador.x] && campo[caminhador.y-1][caminhador.x] != -1 && (possivel == null || campo[possivel.y][possivel.x] > campo[caminhador.y-1][caminhador.x])){
                    possivel = new Point(caminhador.x, caminhador.y-1);
                    System.out.println(possivel);
                }
                if(caminhador.y < jogo.getAltura()-1 && campo[caminhador.y+1][caminhador.x] < campo[caminhador.y][caminhador.x] && campo[caminhador.y+1][caminhador.x] != -1 && (possivel == null || campo[possivel.y][possivel.x] > campo[caminhador.y+1][caminhador.x])){
                    possivel = new Point(caminhador.x, caminhador.y+1);
                    System.out.println(possivel);
                }
                if(caminhador.x > 0 && campo[caminhador.y][caminhador.x-1] < campo[caminhador.y][caminhador.x] && campo[caminhador.y][caminhador.x-1] != -1 && (possivel == null || campo[possivel.y][possivel.x] > campo[caminhador.y][caminhador.x-1])){
                    possivel = new Point(caminhador.x-1, caminhador.y);
                    System.out.println(possivel);
                }
                if(caminhador.x < jogo.getLargura()-1 && campo[caminhador.y][caminhador.x+1] < campo[caminhador.y][caminhador.x] && campo[caminhador.y][caminhador.x+1] != -1 && (possivel == null || campo[possivel.y][possivel.x] > campo[caminhador.y][caminhador.x+1])){
                    possivel = new Point(caminhador.x+1, caminhador.y);
                }
                if(possivel != null){
                caminhador = new Point(possivel.x, possivel.y);
                System.out.println(possivel);
                caminho.add(caminhador);
                }
            }
            imprimeCaminho();
        }
        if(!caminho.isEmpty()){
            direcao = caminho.get(0).x == cabeca.x ? (caminho.get(0).y > cabeca.y ? 'B' : 'C') : (caminho.get(0).x > cabeca.x ? 'D' : 'E');
            caminho.remove(0);
        }
        return direcao;
    }
    
}

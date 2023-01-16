import java.util.Random;

/**
 *
 * Classe che genera un array bidimensionale.
 * Numeri possibili per la griglia:
 * 0 -> per lo spazio vuoto
 * 1 -> per il fiore / bomba
 * 2 -> per lo spazio già giocato
 * 3 -> per lo spazio con la mina colpita
 *
 * @author luca zandonella
 * @version 28 dicembre 2022
 *
 */
public class Grid {
    private int[][] grid;
    private int dimension;

    /**
     * Costruttore di Grid.
     *
     * @param dimension la dimensione della griglia.
     */
    public Grid(int dimension) {
        this.dimension = dimension;
        generateGrid();
    }

    /**
     * Metodo che genera un array bidimensionale con le dimensioni
     * 'dimension' e lo riempie di 0, aggiungendo anche 'dimension'
     * bombe casualmente all'interno della Grid.
     */
    private void generateGrid(){
        grid = new int[dimension][dimension];
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                grid[i][j] = 0;
            }
        }

        for (int i = dimension; i > 0;) {
            Random ran = new Random();
            int x = ran.nextInt(dimension);
            int y = ran.nextInt(dimension);

            if (grid[x][y] == 0) {
                grid[x][y] = 1;
                i--;
            }
        }
    }

    /**
     * Metodo get di grid.
     *
     * @return l'array grid.
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     * Metodo set di grid.
     *
     * @param grid l'indice di grid.
     */
    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    /**
     * Metodo get di dimension.
     *
     * @return la dimension.
     */
    public int getDimension() {
        return dimension;
    }

    /**
     * Metodo set di dimension.
     *
     * @param dimension la dimension.
     */
    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
}

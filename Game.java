import Audio.Sound;
import ConsoleColors.Color;
import java.util.Scanner;

/**
 *
 * Classe che genera una partita di Prato Fiorito.
 *
 * @author luca zandonella
 * @version 28 dicembre 2022
 *
 */
public class Game {
    private int[][] array;
    private Player player1;
    private final int dimension;
    private int freeSpot;

    /**
     * Costruttore classe Game.
     *
     * @param gridDimension dimensione della griglia.
     * @param playerName nome del giocatore.
     */
    public Game(int gridDimension, String playerName) {
        Grid grid1 = new Grid(gridDimension);
        this.array = grid1.getGrid();
        this.player1 = new Player(playerName);
        dimension = grid1.getDimension();
        freeSpot = (dimension * dimension) - dimension;

        try {
            loop();
        }catch (InterruptedException e){
            System.err.println(Color.RED + "ERRORE: Thread.sleep() è stato interrotto" + Color.RESET);
        }
    }

    /**
     *
     * Metodo contente il 'Game Loop', si occupa di chiamare i metodi
     * che stampano, chiedono le coordinate e che controllano se si ha vinto,
     * in oltre fa partire la musica di sottofondo, con anche il sound effect per
     * vittoria, o perdita.
     *
     * @throws InterruptedException eccezione generata dal 'Thread.sleep()'.
     */
    private void loop() throws InterruptedException {
        boolean isPlaying = true;

        // Avviso d'inizio gioco e avvio della canzone.
        System.out.println(Color.GREEN + "\nPRATO FIORITO INIZIATO:" + Color.RESET);
        Sound.play("./src/Audio/background_song.wav");

        // Game Loop
        while(isPlaying){
            // Stampa della griglia
            printGrid(false);

            // Richiesta coordinate e assegnazioni variabili.
            int[] numbers = coordinates();

            // Controllo se il punto colpito ha una bomba, è gia stato colpito,
            // è libero (con ancora spazi o no) e assegno il valore boolean
            // alla variabile del Game Loop.
            isPlaying = checkBomb(numbers[0], numbers[1]);
        }
        // Stampa informazioni
        printGrid(true);
        // In caso di sconfitta
        // Fermo canzone gioco.
        Sound.stop();
        // Se il gioco è finito controllo se gli spazi rimasti sono
        // zero, se vero è una vittoria altrimenti è una sconfitta.
        if(freeSpot != 0){
            // Faccio partire un piccolo effetto sonoro.
            Sound.play("./src/Audio/lose_sound.wav");
            System.out.println(Color.RED + "GAME OVER" + Color.RESET);
        }else{
            // Faccio partire un piccolo effetto sonoro.
            Sound.play("./src/Audio/win_sound.wav");
            System.out.println(Color.GREEN_BRIGHT + "WINNER!!!" + Color.RESET);
        }
        System.out.println("Player: " + player1.getName());
        System.out.println("Score: " + player1.getScore());
        // Attendo la fine della canzone (dura circa 2 secondi) e finisco il programma.
        Thread.sleep(1800);
    }

    /**
     *
     * Un metodo che controlla se viene colpita una bomba, se così ritorna 'false'
     * altrimenti controlla se è uno spazio gia selezionato e infine se non è nessuno
     * dei due aumenta il punteggio del player e segna la casella come gia selezionata,
     * riducendo anche il conteggio di spazi ancora liberi.
     *
     * @param row la coordinata della riga.
     * @param colum la coordinata della colonna.
     * @return false se si becca una bomba o si liberano tutti gli spazi,
     * altrimenti ritorna true.
     */
    private boolean checkBomb(int row, int colum){
        if(array[row][colum] == 1) {
            // Se la casella selezionata ha una bomba (1) allora viene segnato
            // come bomba colpita (3) e ritorna false (ferma il gioco).
            array[row][colum] = 3;
            return false;
        }else if(array[row][colum] == 2){
            // Se la casella selezionata è gia stata colpita (2) allora viene
            // segnalato e ritorna true (continua il gioco).
            System.out.println(Color.RED + "Casella gia selezionata, riprova" + Color.RESET);
            return true;
        }else{
            // Se la casella è disponibile (0) allora viene aumentato il punteggio,
            // viene segnata come "colpita" (2), si diminuisce di 1 il conteggio degli
            // spazi liberi e si ritorna true se ci sono ancora spazi liberi.
            player1.setScore(player1.getScore() + 1);
            array[row][colum] = 2;
            freeSpot--;
            return freeSpot != 0;
        }
    }

    /**
     *
     * Un metodo che chiede all'utente le coordinate della griglia
     * controllando anche i valori passati.
     *
     * @return ritorna un array int[ riga ][ colonna ].
     */
    private int[] coordinates(){
        Scanner myScanner = new Scanner(System.in).useDelimiter("\n");
        int row = 0;
        int colum = 0;

        // Viene chiesta la coordinata (per la riga e la colonna)
        // e viene successivamente controllato che sia nei limiti
        // dell' array, in caso non lo è viene richiesto.
        do{
            System.out.print("Inserire coordinata riga: ");
            if(myScanner.hasNextInt()){
                row = myScanner.nextInt();
                if (row < 0 || row > dimension - 1) {
                    System.out.println(
                            Color.RED + "Inserire solo numeri interi tra 0 e " +
                            (dimension - 1) + Color.RESET
                    );
                }
            }else{
                myScanner.nextLine();
                System.out.println(
                        Color.RED + "Inserire solo numeri interi tra 0 e " +
                        (dimension - 1) + Color.RESET
                );
            }
        }while(row < 0 || row > dimension - 1);

        do{
            System.out.print("Inserire coordinata colonna: ");
            if(myScanner.hasNextInt()){
                colum = myScanner.nextInt();
                if (colum < 0 || colum > dimension - 1) {
                    System.out.println(
                            Color.RED + "Inserire solo numeri interi tra 0 e " +
                            (dimension - 1) + Color.RESET
                    );
                }
            }else{
                myScanner.nextLine();
                System.out.println(
                        Color.RED + "Inserire solo numeri interi tra 0 e " +
                        (dimension - 1) + Color.RESET
                );
            }
        }while(colum < 0 || colum > dimension - 1);
        // Ritorna un array int con i valori della riga e della colonna.
        return new int[] {row, colum};
    }

    /**
     *
     * Un metodo che stampa a terminale l' array della Grid, se viene passato
     * special=true allora vengono stampate anche le bombe a schermo.
     *
     * @param special valore booleano per il tipo di stampa.
     */
    private void printGrid(boolean special){
        System.out.print("\n\t");
        // Stampa indice superiore.
        for(int i = 0; i < dimension; i++){
            System.out.print(i + "\t");
        }
        System.out.println("\t");
        // Doppio for per passare l' array bidimensionale.
        for(int i = 0; i < dimension; i++){
            // Stampa indice laterale.
            System.out.print(i + "\t");
            for(int j = 0; j < dimension; j++){
                // Controllo stampa 'speciale'
                if(special){
                    // In caso la partita sia finita vengono stampate anche le bombe.
                    switch (array[i][j]){
                        case 0 -> System.out.print(
                                Color.GREEN + "#\t" + Color.RESET
                        );
                        case 1 -> System.out.print(
                                Color.BLUE_BRIGHT + "*\t" + Color.RESET
                        );
                        case 2 -> System.out.print("\t");
                        case 3 -> System.out.print(
                                Color.RED + "*\t" + Color.RESET
                        );
                    }
                }else{
                    // Altrimenti vengono ignorate
                    if(array[i][j] != 2){
                        System.out.print(Color.CYAN + "#\t" + Color.RESET);
                    }else{
                        System.out.print("\t");
                    }
                }
            }
            System.out.println(Color.RESET);
        }
        System.out.println();
    }

    /**
     * Get array int[][].
     *
     * @return the int[][].
     */
    public int[][] getArray() {
        return array;
    }

    /**
     * Sets array.
     *
     * @param grid1 the grid1.
     */
    public void setArray(Grid grid1) {
        this.array = grid1.getGrid().clone();
    }

    /**
     * Gets player 1.
     *
     * @return the player1.
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Sets player 1.
     *
     * @param player1 the player1.
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
}
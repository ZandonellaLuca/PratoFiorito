/**
 *
 * Classe che genera un player.
 *
 * @author luca zandonella
 * @version 28 dicembre 2022
 *
 */

public class Player {
    private int score;
    private String name;

    /**
     * Metodo costruttore di Player.
     * @param name il nome del giocatore.
     */
    public Player(String name){
        this.name = name;
        score = 0;
    }

    /**
     * Metodo get di score.
     * @return il valore di score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Metodo set di score.
     * @param score il nuovo valore di score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Metodo get di name.
     * @return il nome del player.
     */
    public String getName() {
        return name;
    }

    /**
     * Metodo set di nome.
     * @param name il nuovo nome del player.
     */
    public void setName(String name) {
        this.name = name;
    }
}

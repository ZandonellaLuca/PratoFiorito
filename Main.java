import ConsoleColors.Color;

import java.util.Scanner;

/**
 *
 * Classe main che serve per istanziare il gioco di Prato fiorito.
 *
 * @author luca zandonella
 * @version 28 dicembre 2022
 *
 */

public class Main {
    public static void main(String[] args) {

        Scanner myScanner = new Scanner(System.in).useDelimiter("\n");

        String playerName = "";
        int dimensionGrid = 0;

        // Richiesta nome del giocatore, e controllo che non sia vuoto
        do{
            System.out.print("Nome giocatore: ");
            playerName = myScanner.nextLine().trim();
        }while(playerName.isBlank());

        // Richiesta dimensione della griglia con controllo per la dimensione
        do{
            System.out.print("Dimensione della griglia: ");
            if(myScanner.hasNextInt()){
                dimensionGrid = myScanner.nextInt();
                if (dimensionGrid < 2 || dimensionGrid > 25) {
                    System.out.println(Color.RED + "Inserire solo numeri interi tra 2 e 25" + Color.RESET);
                }
            }else{
                System.out.println(Color.RED + "Inserire solo numeri interi tra 2 e 25" + Color.RESET);
                myScanner.nextLine();
            }
        }while(dimensionGrid < 2 || dimensionGrid > 25);

        // Creazione oggetto Game, che contiene il codice principale
        Game game = new Game(dimensionGrid, playerName);
    }
}
package juj;



import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class Game  {
    static CyclicBarrier cb = new CyclicBarrier(Players.counter);

    private Game(Players... players) {
        for (Players player :
                players) {
            int playerNu = player.getPlayerNum();
            System.out.format("\nCreating threads for %s", player.name);
            System.out.format("\n%s thread is created = thread-%d", player.name, playerNu);



        }
    }

    public void play(){
        
    }



    public static void main(String[] args) {
        System.out.println("How many players?");
        Scanner input = new Scanner(System.in);
        Players.listPlayers = new ArrayList<>();
        int numPlayers = Integer.parseInt(input.nextLine());
        int i = 0;

        while (i < numPlayers) {

            Players.listPlayers.add(new Players());
            i++;
        }

        Game game = new Game( Players.listPlayers.toArray(new Players[0]));

    }
}




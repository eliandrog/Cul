package juj;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Stream;

public class Players implements Runnable{

    private ArrayList<Integer> handCards,cardStorage;
    public static Set<Thread> listofThreads;
    static int counter;
    private static Integer chosenCard;
    public  String name;
    public static List<Players> listPlayers;
    public  Thread t;
    private final int playerNum;



    Players(){

        this.handCards = handCards;
        this.cardStorage = cardStorage;
        counter++;
        playerNum= counter;
        name="Player"+counter;
        Stream<Integer> s =Stream.iterate(1,n->(n+1)).limit(13);
        handCards=new ArrayList<Integer>(13);
        ArrayList cardStorage=new ArrayList();
        
        s.forEach(handCards::add);
        //System.out.println(handCards.toString());





    }

    public int getPlayerNum(){
        return this.playerNum;
    }


    public void run() {
        int i=0;
        while(i<13) {
            try {
                System.out.println("What card do you pick?");
                Scanner in = new Scanner(System.in);
                int card = Integer.parseInt(in.next());
                System.out.println(Thread.currentThread().getName());
                Game.cb.await();    //untill each player has chosen a card
                i++;
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

        }


    }

    void play(){
        Thread t=new Thread();



    }


    public static void round(Integer ... card){

    }
    private Integer PikCard(){
        System.out.format("What card do you pick %s?", name);
        Scanner s=new Scanner(System.in);
        chosenCard= Integer.parseInt(s.nextLine());
        return chosenCard;
    }


    public static void main(String[] args) {
        System.out.println("How many players?");
        Scanner input=new Scanner(System.in);
        listPlayers=new ArrayList<>();
        int numPlayers=Integer.parseInt(input.nextLine());
        int i=0;

        while (i<numPlayers){

            listPlayers.add(new Players());
            i++;
        }

        listPlayers.forEach(players -> {
            System.out.println(players.name+"\n"+players.handCards);
             new Thread("thread-" + players.name).start();
        });





    }
}

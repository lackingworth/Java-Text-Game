import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class MedievalGameDriver {

  // Instance Variables 
  private Player player;

  // Main Method 
  public static void main(String[] args) {
    
    Scanner console = new Scanner(System.in);
    MedievalGameDriver game = new MedievalGameDriver();

    game.player = game.start(console);

    game.addDelay(500);
    System.out.println("\nLet's take a quick look at you to make sure you're ready to head out the door.");
    System.out.println(game.player);

    game.addDelay(1000);
    System.out.println("\nWell, you're off to a good start, let's get your game saved so we don't lose it.");
    game.save();

    game.addDelay(2000);
    System.out.println("We just saved your game...");
    System.out.println("Now we are going to try to load your character to make sure the save worked...");

    game.addDelay(1000);
    System.out.println("Deleting character...");
    String charName = game.player.getName();
    game.player = null;

    game.addDelay(1500);
    game.player = game.load(charName, console);
    System.out.println("Loading character...");

    game.addDelay(2000);
    System.out.println("Now let's print out your character again to make sure everything loaded:");

    game.addDelay(500);
    System.out.println(game.player);
  }

  // Start Instance Method
  private Player start(Scanner console) {
    Player player;
    Art.homeScreen();

    System.out.println("\nWelcome to the java text game!");
    System.out.println("Would you like to start a new game (S)");
    System.out.println("or load the existing file? (L)");
    System.out.println("\n[Enter corresponding letter: S/L]");

    String answer = console.next().toLowerCase();

    while(true) {
      if(answer.equals("l")) {
        System.out.println("\nEnter the username you wish to load:");
        player = load(console.next(), console);
        break;
      } else if(answer.equals("s")) {
        System.out.println("\n Enter your desired username:");
        String answerName = console.next();
        while(true) {
          System.out.println("\nYour name is " + answerName + ", is that correct? [Y/N]");
          String nextAnswer = console.next().toLowerCase();
          if(nextAnswer.equals("y")) break;
          System.out.println("\nPlease, enter your username:");
          answerName = console.next();
        }
        player = new Player(answerName);
        break;
      } else {
          System.out.println("\nPlease enter valid answer: [S/L]");
          answer = console.next().toLowerCase();
      }
    }
    return player;
  } 

  // Save Instance Method
  private void save() {
    String fileName = player.getName() + ".svr";

    try {
      FileOutputStream userSaveFile = new FileOutputStream(fileName);
      ObjectOutputStream playerSaver = new ObjectOutputStream(userSaveFile);
      playerSaver.writeObject(this.player);
      playerSaver.close();
    } catch (IOException e) {
      System.out.println("\nUnable to save:\n " + e);
    }
  } 

  // Load Instance Method
  private Player load(String playerName, Scanner console) {
    Player loadedPlayer;

    try {
      FileInputStream userLoadFile = new FileInputStream(playerName + ".svr");
      ObjectInputStream playerLoader = new ObjectInputStream(userLoadFile);
      loadedPlayer = (Player) playerLoader.readObject();
      playerLoader.close();
    } catch(IOException | ClassNotFoundException e) {
        addDelay(1500);
        System.out.println("\nThere was a problem loading your character, we've created a new player with the name you entered.");
        System.out.println("If you're sure the spelling is correct, your character file may no longer exist, please reload the game if you'd like to try again.");
        System.out.println("In the mean time, we'll create you a new character with the name: " + playerName);
        addDelay(2000);
        loadedPlayer = new Player(playerName);
    }

    return loadedPlayer;
  } 

  // Add Delay Instance Method
  private void addDelay(int time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
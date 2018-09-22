import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Games {
    private Tournament tournament;
    private String gameType;
    private ArrayList<User> players = new ArrayList<>();
    private User[] winners = new User[3];
    private static ArrayList<Games> games = new ArrayList<>();

    public Games(Tournament tournament, String gameType, ArrayList<User> players, User[] winners) {
        this.tournament = tournament;
        this.gameType = gameType;
        this.players = players;
        this.winners = winners;
    }

    public Games(Tournament tournament, String gameType, ArrayList<User> players) {
        this.tournament = tournament;
        this.gameType = gameType;
        this.players = players;
    }

    public Games() {
    }

    public static void exportData() throws FileNotFoundException {

        PrintStream ps = new PrintStream(new File("src/games.txt"));
        for (int i = 0; i < games.size(); i++) {

            String players = "";

            for (int j = 0; j < games.get(i).getPlayers().size() ; j++) {
                players += ""+User.getUsers().get(i).getName() + ";" + User.getUsers().get(i).getCpr() + ";" + User.getUsers().get(i).isDealer() + ";" +
                        User.getUsers().get(i).getUsername() + ";" + User.getUsers().get(i).getPassword() + ";" + User.getUsers().get(i).getEmployeeNo() + ";";
            }

            ps.print(games.get(i).getTournament().getName() + ";" + games.get(i).getGameType() + ";" + games.get(i).getWinners()[0] +
                    ";" + games.get(i).getWinners()[1]+";"+games.get(i).getWinners()[2]+";"+players);
        }

    }


    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<User> players) {
        this.players = players;
    }

    public User[] getWinners() {
        return winners;
    }

    public void setWinners(User[] winners) {
        this.winners = winners;
    }

    public void addWinners(ArrayList<User> players) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please choose winners");
        User.displayPlayers(players);

        for (int i = 0; i < 3; i++) {
            System.out.print("Choice: ");

        }



    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Tournament {
    private String name;
    private double prizeMoney;
    private double minBuyIn = prizeMoney*0.1;
    private static ArrayList<Tournament> tournamentList = new ArrayList<>();


    public Tournament(String name, double prizeMoney) {
        this.name = name;
        this.prizeMoney = prizeMoney;
    }

    public Tournament(String name, double prizeMoney, double minBuyIn) {
        this.name = name;
        this.prizeMoney = prizeMoney;
        this.minBuyIn = minBuyIn;
    }

    public Tournament() {
    }

    public static Object newTournament() {
    return new Tournament();
    }

    public static void exportData() throws FileNotFoundException {
        PrintStream ps = new PrintStream(new File("src/tournament.txt"));
        for (int i = 0; i < tournamentList.size(); i++) {
            ps.print(tournamentList.get(i).getName()+";"+tournamentList.get(i).getPrizeMoney()+";"+tournamentList.get(i).getMinBuyIn()+"\n");
        }


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(double prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    public double getMinBuyIn() {
        return minBuyIn;
    }

    public void setMinBuyIn(double minBuyIn) {
        this.minBuyIn = minBuyIn;
    }


    public static ArrayList<Tournament> getTournamentList() {
        return tournamentList;
    }

    public static void setTournamentList(ArrayList<Tournament> tournamentList) {
        Tournament.tournamentList = tournamentList;
    }
}

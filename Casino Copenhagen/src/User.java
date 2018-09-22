import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class User {
    private String name;
    private String cpr;
    private boolean dealer = false;
    private String username;
    private String password;
    private static int employeeNumberGen;
    private int employeeNo;
    private static ArrayList<User> users = new ArrayList<>();


    public User() {
    }

    public User(String name, String cpr, boolean dealer, String username, String password) {
        this.name = name;
        this.cpr = cpr;
        this.dealer = dealer;
        this.username = username;
        this.password = password;
        employeeNo = employeeNumberGen;
        employeeNumberGen++;
    }


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static boolean checkLogin(String username, String password, ArrayList<User> users) {

        for (int i = 0; i < users.size(); i++) {
            if (username.equals(users.get(i).getUsername()) && password.equals(users.get(i).getPassword()) && users.get(i).isDealer()) {
                return true;
            }
        }
        return false;
    }

    public static Object newUser() {
        return new User();
    }

    public void dealerMenu() {
        Scanner input = new Scanner(System.in);
        Boolean runDealerMenu = true;
        do {
            System.out.println("Please select an option below");
            System.out.println("1) Add new user");
            System.out.println("2) Add new tournament");
            System.out.println("3) Add new game");
            System.out.println("0) Exit");
            System.out.print("Choice: ");

            switch (input.next()) {

                case "1":
                    System.out.println("\nCREATING NEW USER\n");
                    System.out.print("Name: ");
                    String name = input.next();
                    System.out.print("Cpr: ");
                    String cpr = input.next();
                    System.out.print("Is the new user a Dealer? Y/N: ");
                    String dealer = input.next();
                    if (dealer.equalsIgnoreCase("y") || dealer.equalsIgnoreCase("yes")) {
                        System.out.print("Username: ");
                        String username = input.next();
                        System.out.print("Password: ");
                        String password = input.next();
                        password = verifyPass(password, input);


                        users.add(new User(name, cpr, true, username, password));
                    } else {
                        users.add(new User(name, cpr));
                    }
                    System.out.println("New user added");
                    break;

                case "2":
                    createTournament(input);
                    break;

                case "3":
                    ArrayList<User> players = new ArrayList<>();
                    User[] winners = new User[3];
                    System.out.println("\nAdding new game\n");
                    System.out.println("1) Create with existing tournament");
                    System.out.println("2) Create with a new tournament");
                    System.out.print("Choice: ");
                    Tournament gameTournament = null;
                    if (input.next().equals("1")) {
                        for (int i = 0; i < Tournament.getTournamentList().size(); i++) {
                            System.out.println((i + 1) + ")" + Tournament.getTournamentList().get(i).getName());
                        }
                        System.out.print("Choice: ");
                        gameTournament = Tournament.getTournamentList().get(input.nextInt() - 1);


                    } else {
                        createTournament(input);
                    }

                    String gameType;
                    System.out.println("Game type: ");
                    System.out.println("1) Poker");
                    System.out.println("2) Blackjack");
                    System.out.println("3) Roulette");
                    System.out.print("Choice: ");

                    if (input.nextInt() == 1) {
                        gameType = "Poker";
                    } else if (input.nextInt() == 2) {
                        gameType = "Blackjack";
                    } else {
                        gameType = "Roulette";
                    }

                    System.out.println("Choose players");
                    System.out.println("1) Choose from existing players");
                    System.out.println("2) Create new Players");
                    System.out.print("Choice: ");

                    if (input.next().equals("1")) {
                        System.out.println("How many players would you like to add?");
                        System.out.print("Choice: ");
                        int playersToAdd = input.nextInt();

                        displayPlayers(users);


                        for (int i = 0; i < playersToAdd; i++) {
                            System.out.println("Choose player: ");
                            players.add(users.get(input.nextInt() - 1));
                        }
                        System.out.println("Players added");

                    } else {
                        //TODO  createUsers();
                        System.out.println("The system will only allow for existing players atm");
                    }

                    Games game = new Games(gameTournament, gameType, players);
                    System.out.println("Game created!");
                    System.out.println("Would you like to add winners?");
                    System.out.print("Choice: ");
                    if (input.next().equalsIgnoreCase("y") || input.next().equalsIgnoreCase("yes")) {
                        game.addWinners(players);
                    }

                    //TODO ADD WINNERS.

                    break;

                case "0":
                    runDealerMenu = false;
                    break;

            }

        } while (runDealerMenu);

        try {
            User.exportData();
        } catch (FileNotFoundException e) {
            System.out.println("System could not export user data");
            e.printStackTrace();
        }
    }

    public static void displayPlayers(ArrayList<User> users) {
        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).isDealer()) {
                System.out.println((i + 1) + ")" + users.get(i).getName() + "ID: " + users.get(i).getEmployeeNo());
            }
        }
    }

    public String verifyPass(String password, Scanner input) {
        if (password.length() < 4) {
            while (password.length() < 4) {
                System.out.println("Please enter a password longer than 4!");
                System.out.print("New password: ");
                password = input.next();
            }
        }
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public boolean isDealer() {
        return dealer;
    }

    public void setDealer(boolean dealer) {
        this.dealer = dealer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(int employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        User.users = users;
    }


    public void createTournament(Scanner input) {
        System.out.println("\nAdding new tournament\n");
        System.out.print("Tournament name: ");
        String tournamentName = input.next();
        System.out.print("Prize money (ex. 100.50) in DKK: ");
        double priceMoney = input.nextDouble();
        Tournament tournament = new Tournament(tournamentName, priceMoney);
        System.out.println("The min buyIn will be: " + tournament.getMinBuyIn());
        Tournament.getTournamentList().add(tournament);
        System.out.println("\nTournament created");
    }


    public static void exportData() throws FileNotFoundException {
        PrintStream ps = new PrintStream(new File("src/user.txt"));
        for (int i = 0; i < users.size(); i++) {
            ps.print(users.get(i).getName() + ";" + users.get(i).getCpr() + ";" + users.get(i).isDealer() + ";" +
                    users.get(i).getUsername() + ";" + users.get(i).getPassword() + ";" + users.get(i).getEmployeeNo() + ";\n");
        }

        Tournament.exportData();

        Games.exportData();

    }
}

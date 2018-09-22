import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Login {

    public void login() throws FileNotFoundException {
        importData();

//        User.getUsers().add(new User("Alice", "7878454545", true, "Alice", "1234"));
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Casino Copenhagen!\n");
        System.out.println("Please enter your credentials to sign in");
        System.out.print("Username: ");
        String username = input.next();
        System.out.print("Password: ");
        String password = input.next();


        if (User.checkLogin(username, password, User.getUsers())) {
            System.out.println("Logged in successfully!");


            User.getUsers().get(0).dealerMenu(); //Access menu from superuser
        } else {
            System.out.println("Wrong username or password");

        }
    }

    private void importData() throws FileNotFoundException {
        Scanner scannerUsers = new Scanner(new File("src/user.txt"));
        Scanner scannerTournaments = new Scanner(new File("src/tournament.txt"));
        Scanner scannerGames = new Scanner(new File("src/games.txt"));
        //load users
        while (scannerUsers.hasNextLine()) {
            String line = scannerUsers.nextLine();
            String[] arr = line.split(";");
            User.getUsers().add(new User(arr[0], arr[1], Boolean.parseBoolean(arr[2]), arr[3], arr[4]));
        }

        //load tourny
        while (scannerTournaments.hasNextLine()) {
            String line = scannerTournaments.nextLine();
            String[] arr = line.split(";");
            Tournament.getTournamentList().add(new Tournament(arr[0], Double.parseDouble(arr[1]), Double.parseDouble(arr[2])));
        }

        //Load games

        while (scannerGames.hasNextLine()){
            String line = scannerGames.nextLine();
            String[] arr = line.split(";");
            int tournyIndex;
            for (int i = 0; i < Tournament.getTournamentList().size() ; i++) {
                if(Tournament.getTournamentList().get(i).getName().equals(arr[0])){
                    tournyIndex = i;
                }
            }






            //TODO add import game method
        }

    }
}

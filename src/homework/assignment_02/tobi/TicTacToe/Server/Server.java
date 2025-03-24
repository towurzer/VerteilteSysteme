package homework.assignment_02.tobi.TicTacToe.Server;

public class Server {

    public static void main(String[] args) {
        args = (args.length == 0) ? new String[]{"8080"} : args;

        for(String currentPortNumber: args){
            int portNumber = Integer.parseInt(currentPortNumber);
            new GameManager(portNumber); // TODO: Change GameManager Instantiation to be a new Thread to run multiple games at one time
        }
    }
}


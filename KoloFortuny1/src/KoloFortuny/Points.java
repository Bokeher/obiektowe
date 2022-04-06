package KoloFortuny;

import java.util.Arrays;
import java.util.Collections;

public class Points {
    private int player1Points = 0;
    private int player2Points = 0;
    private int player3Points = 0;
    private int player4Points = 0;
    private int maxPlayers;

    void addPoint(int playerNumber, int maxPlayers) {
        this.maxPlayers = maxPlayers;

        if(playerNumber == 1) player1Points++;
        else if(playerNumber == 2) player2Points++;
        else if(playerNumber == 3) player3Points++;
        else if(playerNumber == 4) player4Points++;
    }

    @Override
    public String toString() {
        String[] players = {player1Points+"-1", player2Points+"-2", player3Points+"-3", player4Points+"-4"};

        Arrays.sort(players, Collections.reverseOrder());

        String res = "-=-=-=- Punktacja -=-=-=-\n";
        for(int i=0; i<4; i++) {
            String[] arr = players[i].split("-");
            if(maxPlayers == 2 && !(Integer.parseInt(arr[1]) == 4) && !(Integer.parseInt(arr[1]) == 3)) {
                res += "Gracz "+arr[1]+": "+arr[0]+"\n";
            } else if(maxPlayers == 3 && !(Integer.parseInt(arr[1]) == 4)) {
                res += "Gracz "+arr[1]+": "+arr[0]+"\n";
            } else if(maxPlayers == 4) {
                res += "Gracz "+arr[1]+": "+arr[0]+"\n";
            }
        }
        return res;
    }
}
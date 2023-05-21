import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private int gameTimeoutSecs = 600; // Limit czasu gry w sekundach (np. 5 minut)
    private Timer gameTimer;
    public boolean gameState = true;

    public GameTimer(int timerSeconds) {
        this.gameTimeoutSecs = timerSeconds;
    }
    public GameTimer() {
    
    }

    public void startGameTimer() {
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameState = false;
            }
        }, gameTimeoutSecs * 1000L); // Konwersja sekund na milisekundy
    }

    public void stopGameTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
            gameTimer = null;
        }
    }
}

package application.greyhats.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    int placeStat = 0;
    int gamestat[] = {2, 2, 2, 2, 2, 2, 2, 2, 2, };
    int[][] winner = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameActive = true;

    TextView winnerTextView;
    MediaPlayer mediaPlayer;

    public void dropIn(View view) {
        // 0:yellow, 1:red, 2:empty
        ImageView tappedImage = (ImageView) view;
        Log.i("info", view.getTag().toString()+" pressed" );

        int i = Integer.parseInt(tappedImage.getTag().toString());

        if (gameActive == true) {
            if (gamestat[i] == 2) {
                tappedImage.setY(-1000);
                if (placeStat == 0) {
                    placeStat = 1;
                    gamestat[i] = 1;
                    tappedImage.setImageResource(R.drawable.yellow);
                } else if (placeStat == 1) {
                    placeStat = 0;
                    gamestat[i] = 0;
                    tappedImage.setImageResource(R.drawable.red);
                }
                tappedImage.animate().translationY(0).rotationBy(90).setDuration(300);
            }
            if (checkWinner()) {
                gameActive = false;
                Log.i("winner", "winner detected");
                String message = " is the winner";
                if (placeStat == 0) {
                    message = "Red" + message;
                } else {
                    message = "Yellow" + message;
                }
                winnerTextView.setText(message);
                winnerTextView.setVisibility(View.VISIBLE);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void restart(){
        Intent t= new Intent(MainActivity.this,MainActivity.class);
        mediaPlayer.stop();
        startActivity(t);
        finish();
    }

    private boolean checkWinner(){
        int i ;
        for (i = 0 ; i < 8 ; i++ ){
            if (gamestat[winner[i][0]] == gamestat[winner[i][1]] && gamestat[winner[i][1]] == gamestat[winner[i][2]] && gamestat[winner[i][0]] != 2){
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });
        winnerTextView = findViewById(R.id.winnerTextView);
        winnerTextView.setVisibility(View.INVISIBLE);

        mediaPlayer = MediaPlayer.create(this, R.raw.bgmusic);
        mediaPlayer.start();
    }
}
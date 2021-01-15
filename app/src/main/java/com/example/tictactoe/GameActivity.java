package com.example.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textP1Name, textP1Score, textP2Name, textP2Score, textDrawScore;
    ImageButton buttonBack;
    Button buttonReset;
    ImageButton[][] gameInput = new ImageButton[3][3];
    String mode;
    int player = 1, p1Score = 0, p2Score = 0, drawScore = 0;
    int[][] array = {{0,0,0},{0,0,0},{0,0,0}};
    boolean gameEnded = false;
    UserModel user;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent i = getIntent();
        user = (UserModel)i.getSerializableExtra("user");

        Bundle bundle = getIntent().getExtras();
        mode = bundle.getString("mode");

        dataBaseHelper = new DataBaseHelper(GameActivity.this);
        
        gameInput[0][0] = findViewById(R.id.imageButton00);
        gameInput[0][0].setOnClickListener(this);
        gameInput[0][1] = findViewById(R.id.imageButton01);
        gameInput[0][1].setOnClickListener(this);
        gameInput[0][2] = findViewById(R.id.imageButton02);
        gameInput[0][2].setOnClickListener(this);
        gameInput[1][0] = findViewById(R.id.imageButton10);
        gameInput[1][0].setOnClickListener(this);
        gameInput[1][1] = findViewById(R.id.imageButton11);
        gameInput[1][1].setOnClickListener(this);
        gameInput[1][2] = findViewById(R.id.imageButton12);
        gameInput[1][2].setOnClickListener(this);
        gameInput[2][0] = findViewById(R.id.imageButton20);
        gameInput[2][0].setOnClickListener(this);
        gameInput[2][1] = findViewById(R.id.imageButton21);
        gameInput[2][1].setOnClickListener(this);
        gameInput[2][2] = findViewById(R.id.imageButton22);
        gameInput[2][2].setOnClickListener(this);

        buttonReset = findViewById(R.id.resetButton);
        buttonReset.setOnClickListener(this);
        buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(this);

        textP1Name = findViewById(R.id.player1Name);
        textP1Name.setText(user.getUsername());
        textP2Name = findViewById(R.id.player2Name);
        if (mode.equals("easy"))
            textP2Name.setText("Easy CPU");
        else if (mode.equals("hard"))
            textP2Name.setText("Hard CPU");
        else
            textP2Name.setText("Friend");

        textP1Score = findViewById(R.id.player1Score);
        textP1Score.setText(String.valueOf(p1Score));
        textP2Score = findViewById(R.id.player2Score);
        textP2Score.setText(String.valueOf(p2Score));
        textDrawScore = findViewById(R.id.drawScore);
        textDrawScore.setText(String.valueOf(drawScore));
        gameStart();
    }

    public void gameStart(){
        Random random = new Random();
        player = random.nextInt(2) + 1;
        if (player == 1){
            textP1Name.setBackgroundColor(Color.parseColor("#BBBBBB"));
            textP2Name.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }
        else{
            textP1Name.setBackgroundColor(Color.parseColor("#EEEEEE"));
            textP2Name.setBackgroundColor(Color.parseColor("#BBBBBB"));
            if (mode.equals("easy")){
                cPUEasy();
            }
            else if (mode.equals("hard")){
                cPUHard();
            }
        }
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton){
            finish();
        }
        else if (v.getId() == R.id.resetButton){
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    gameInput[i][j].setImageResource(0);
                    gameInput[i][j].setEnabled(true);
                    array[i][j] = 0;
                    gameEnded = false;
                }
            }
            gameStart();
        }
        else{
            switch (v.getId()) {
                case R.id.imageButton00:
                    if (player == 1)
                        gameInput[0][0].setImageResource(R.drawable.cross);
                    else
                        gameInput[0][0].setImageResource(R.drawable.circle);
                    gameInput[0][0].setEnabled(false);
                    array[0][0] = player;
                    break;
                case R.id.imageButton01:
                    if (player == 1)
                        gameInput[0][1].setImageResource(R.drawable.cross);
                    else
                        gameInput[0][1].setImageResource(R.drawable.circle);
                    gameInput[0][1].setEnabled(false);
                    array[0][1] = player;
                    break;
                case R.id.imageButton02:
                    if (player == 1)
                        gameInput[0][2].setImageResource(R.drawable.cross);
                    else
                        gameInput[0][2].setImageResource(R.drawable.circle);
                    gameInput[0][2].setEnabled(false);
                    array[0][2] = player;
                    break;
                case R.id.imageButton10:
                    if (player == 1)
                        gameInput[1][0].setImageResource(R.drawable.cross);
                    else
                        gameInput[1][0].setImageResource(R.drawable.circle);
                    gameInput[1][0].setEnabled(false);
                    array[1][0] = player;
                    break;
                case R.id.imageButton11:
                    if (player == 1)
                        gameInput[1][1].setImageResource(R.drawable.cross);
                    else
                        gameInput[1][1].setImageResource(R.drawable.circle);
                    gameInput[1][1].setEnabled(false);
                    array[1][1] = player;
                    break;
                case R.id.imageButton12:
                    if (player == 1)
                        gameInput[1][2].setImageResource(R.drawable.cross);
                    else
                        gameInput[1][2].setImageResource(R.drawable.circle);
                    gameInput[1][2].setEnabled(false);
                    array[1][2] = player;
                    break;
                case R.id.imageButton20:
                    if (player == 1)
                        gameInput[2][0].setImageResource(R.drawable.cross);
                    else
                        gameInput[2][0].setImageResource(R.drawable.circle);
                    gameInput[2][0].setEnabled(false);
                    array[2][0] = player;
                    break;
                case R.id.imageButton21:
                    if (player == 1)
                        gameInput[2][1].setImageResource(R.drawable.cross);
                    else
                        gameInput[2][1].setImageResource(R.drawable.circle);
                    gameInput[2][1].setEnabled(false);
                    array[2][1] = player;
                    break;
                case R.id.imageButton22:
                    if (player == 1)
                        gameInput[2][2].setImageResource(R.drawable.cross);
                    else
                        gameInput[2][2].setImageResource(R.drawable.circle);
                    gameInput[2][2].setEnabled(false);
                    array[2][2] = player;
                    break;
            }
            check();
            if (player == 1){
                player = 2;
                textP2Name.setBackgroundColor(Color.parseColor("#BBBBBB"));
                textP1Name.setBackgroundColor(Color.parseColor("#EEEEEE"));
                if (mode.equals("easy") && !gameEnded){
                    cPUEasy();
                }
                else if (mode.equals("hard") && !gameEnded){
                    cPUHard();
                }
            }
            else{
                player = 1;
                textP1Name.setBackgroundColor(Color.parseColor("#BBBBBB"));
                textP2Name.setBackgroundColor(Color.parseColor("#EEEEEE"));
            }
        }
    }

    public void check(){
        if (!checkdraw()){
            for (int i = 0; i < 3; i++) {
                if (array[i][0] == array[i][1] && array[i][0] == array[i][2] && array[i][0] != 0) {
                    if (array[i][0] == 1) {
                        gameInput[i][0].setImageResource(R.drawable.cross_win);
                        gameInput[i][1].setImageResource(R.drawable.cross_win);
                        gameInput[i][2].setImageResource(R.drawable.cross_win);
                    } else {
                        gameInput[i][0].setImageResource(R.drawable.circle_win);
                        gameInput[i][1].setImageResource(R.drawable.circle_win);
                        gameInput[i][2].setImageResource(R.drawable.circle_win);
                    }
                    winner();
                }
            }
            for (int i = 0; i < 3; i++) {
                if (array[0][i] == array[1][i] && array[0][i] == array[2][i] && array[0][i] != 0) {
                    if (array[0][i] == 1) {
                        gameInput[0][i].setImageResource(R.drawable.cross_win);
                        gameInput[1][i].setImageResource(R.drawable.cross_win);
                        gameInput[2][i].setImageResource(R.drawable.cross_win);
                    } else {
                        gameInput[0][i].setImageResource(R.drawable.circle_win);
                        gameInput[1][i].setImageResource(R.drawable.circle_win);
                        gameInput[2][i].setImageResource(R.drawable.circle_win);
                    }
                    winner();
                }
            }
            if (array[0][0] == array[1][1] && array[0][0] == array[2][2] && array[0][0] != 0) {
                if (array[0][0] == 1) {
                    gameInput[0][0].setImageResource(R.drawable.cross_win);
                    gameInput[1][1].setImageResource(R.drawable.cross_win);
                    gameInput[2][2].setImageResource(R.drawable.cross_win);
                } else {
                    gameInput[0][0].setImageResource(R.drawable.circle_win);
                    gameInput[1][1].setImageResource(R.drawable.circle_win);
                    gameInput[2][2].setImageResource(R.drawable.circle_win);
                }
                winner();
            }
            if (array[0][2] == array[1][1] && array[0][2] == array[2][0] && array[0][2] != 0) {
                if (array[0][2] == 1) {
                    gameInput[0][2].setImageResource(R.drawable.cross_win);
                    gameInput[1][1].setImageResource(R.drawable.cross_win);
                    gameInput[2][0].setImageResource(R.drawable.cross_win);
                } else {
                    gameInput[0][2].setImageResource(R.drawable.circle_win);
                    gameInput[1][1].setImageResource(R.drawable.circle_win);
                    gameInput[2][0].setImageResource(R.drawable.circle_win);
                }
                winner();
            }
        }
        else {
            textDrawScore.setText(String.valueOf(++drawScore));
            messageDraw();
        }
    }
    public void winner(){
        gameEnded = true;
        AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j <3; j++){
                gameInput[i][j].setEnabled(false);
            }
        }
        String winner;
        if (player == 1){
            textP1Score.setText(String.valueOf(++p1Score));
            if (mode.equals("easy"))
                user.setEasyWin(user.getEasyWin() + 1);
            else if (mode.equals("hard"))
                user.setHardWin(user.getHardWin() + 1);
            else
                user.setFriendWin(user.getFriendWin() + 1);
            winner = user.getUsername();
        }
        else{
            textP2Score.setText(String.valueOf(++p2Score));
            if (mode.equals("easy")) {
                user.setEasyLose(user.getEasyLose() + 1);
                winner = "EasyCPU";
            }
            else if (mode.equals("hard")){
                user.setHardLose(user.getHardLose() + 1);
                winner = "HardCPu";
            }
            else{
                user.setFriendLose(user.getFriendLose() + 1);
                winner = "Your friend";
            }
        }
        dataBaseHelper.updateUser(user);
        alertDialog.setTitle(winner + " wins!!!");
        alertDialog.setMessage(winner + " has won.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        alertDialog.show();
    }
    public boolean checkdraw(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (gameInput[i][j].isEnabled())
                    return false;
            }
        }
        return true;
    }
    public void messageDraw(){
        gameEnded = true;
        if (mode.equals("easy"))
            user.setEasyDraw(user.getEasyDraw() + 1);
        else if (mode.equals("hard"))
            user.setHardDraw(user.getHardDraw() + 1);
        else
            user.setFriendDraw(user.getFriendDraw() + 1);
        dataBaseHelper.updateUser(user);
        AlertDialog winDialog = new AlertDialog.Builder(GameActivity.this).create();
        winDialog.setTitle("Draw!!!");
        winDialog.setMessage("Game ended in a draw.");
        winDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        winDialog.show();
    }
    public void cPUEasy() {
        boolean notAvailable = true;
        while(notAvailable) {
            Random random = new Random();
            int getalx = random.nextInt(3);
            int getaly = random.nextInt(3);
            if (gameInput[getalx][getaly].isEnabled()){
                notAvailable = false;
                gameInput[getalx][getaly].performClick();
            }
        }
    }
    public void cPUHard(){
        boolean turnDone = false;
        //Check kans om te winnen
        for (int i = 0; i < 3; i++){
            if (array[i][0] + array[i][1] + array[i][2] == 4 && (array[i][0] == 0 || array[i][1] == 0 || array[i][2] == 0)){
                if (!turnDone) {
                    hardCPUClick(i,0,i,1,i,2);
                    turnDone = true;
                }
            }
        }
        for (int i = 0; i < 3; i++){
            if (array[0][i] + array[1][i] + array[2][i] == 4 && (array[0][i] == 0 || array[1][i] == 0 || array[2][i] == 0)) {
                if (!turnDone){
                    hardCPUClick(0,i,1,i,2,i);
                    turnDone =true;
                }
            }
        }
        if (array[0][0] + array[1][1] + array[2][2] == 4 && (array[0][0] == 0 || array[1][1] == 0 || array[2][2] == 0)) {
            if (!turnDone){
                hardCPUClick(0,0,1,1,2,2);
                turnDone = true;
            }
        }
        if (array[2][0] + array[1][1] + array[0][2] == 4 && (array[2][0] == 0 || array[1][1] == 0 || array[0][2] == 0)) {
            if (!turnDone){
                hardCPUClick(2,0,1,1,0,2);
                turnDone = true;
            }
        }
        //Check of er geblokeerd moet worden
        for (int i = 0; i < 3; i++) {
            if (array[i][0] + array[i][1] + array[i][2] == 2 && (array[i][0] == 1 || array[i][1] == 1)) {
                if (!turnDone) {
                    hardCPUClick(i, 0, i, 1, i, 2);
                    turnDone = true;
                }
            }
        }
        for (int i = 0; i < 3; i++){
            if (array[0][i] + array[1][i] + array[2][i] == 2 && (array[0][i] == 1 || array[1][i] == 1)) {
                if (!turnDone){
                    hardCPUClick(0,i,1,i,2,i);
                    turnDone =true;
                }
            }
        }
        if (array[0][0] + array[1][1] + array[2][2] == 2 && (array[0][0] == 1 || array[1][1] == 1)) {
            if (!turnDone){
                hardCPUClick(0,0,1,1,2,2);
                turnDone = true;
            }
        }
        if (array[2][0] + array[1][1] + array[0][2] == 2 && (array[2][0] == 1 || array[1][1] == 1)) {
            if (!turnDone){
                hardCPUClick(2,0,1,1,0,2);
                turnDone = true;
            }
        }
        //anders random neer plaatsen
        if (!turnDone){
            cPUEasy();
        }
    }
    private void hardCPUClick(int x1, int y1, int x2, int y2, int x3, int y3){
        if (array[x1][y1] == 0){
            gameInput[x1][y1].performClick();
        }
        else if (array[x2][y2] == 0){
            gameInput[x2][y2].performClick();
        }
        else if (array[x3][y3] == 0){
            gameInput[x3][y3].performClick();
        }
    }
}
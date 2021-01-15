package com.example.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    DataBaseHelper dataBaseHelper = new DataBaseHelper(SettingsActivity.this);
    TextView userName, emailAddress;
    TextView easyWin, easyDraw, easyLose, easyTotal, easyWinPercentage;
    TextView hardWin, hardDraw, hardLose, hardTotal, hardWinPercentage;
    TextView friendWin, friendDraw, friendLose, friendTotal, friendWinPercentage;
    TextView totalWin, totalDraw, totalLose, totalTotal, totalWinPercentage;
    ImageButton backButton, changeNameButton;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent i = getIntent();
        user = (UserModel)i.getSerializableExtra("user");

        int userEasyWin = user.getEasyWin();
        int userEasyDraw = user.getEasyDraw();
        int userEasyLose = user.getEasyLose();
        int userEasyTotal = userEasyWin + userEasyDraw + userEasyLose;
        BigDecimal userEasyWinPercentage = calcWinPercentage(userEasyWin, userEasyTotal);
        int userHardWin = user.getHardWin();
        int userHardDraw = user.getHardDraw();
        int userHardLose = user.getHardLose();
        int userHardTotal = userHardWin + userHardDraw + userHardLose;
        BigDecimal userHardWinPercentage = calcWinPercentage(userHardWin, userHardTotal);
        int userFriendWin = user.getFriendWin();
        int userFriendDraw = user.getFriendDraw();
        int userFriendLose = user.getFriendLose();
        int userFriendTotal = userFriendWin + userFriendDraw + userFriendLose;
        BigDecimal userFriendWinPercentage = calcWinPercentage(userFriendWin, userFriendTotal);
        int userTotalWin = userEasyWin + userHardWin + userFriendWin;
        int userTotalDraw = userEasyDraw + userHardDraw + userFriendDraw;
        int userTotalLose = userEasyLose + userHardLose + userFriendLose;
        int userTotalTotal = userEasyTotal + userHardTotal + userFriendTotal;
        BigDecimal userTotalWinPercentage = calcWinPercentage(userTotalWin, userTotalTotal);


        userName = findViewById(R.id.userUserName);
        emailAddress = findViewById(R.id.userEmailAddress);

        easyWin = findViewById(R.id.easyWin);
        easyDraw = findViewById(R.id.easyDraw);
        easyLose = findViewById(R.id.easyLose);
        easyTotal = findViewById(R.id.easyTotal);
        easyWinPercentage = findViewById(R.id.easyWinPercentage);
        hardWin = findViewById(R.id.hardWin);
        hardDraw = findViewById(R.id.hardDraw);
        hardLose = findViewById(R.id.hardLose);
        hardTotal = findViewById(R.id.hardTotal);
        hardWinPercentage = findViewById(R.id.hardWinPercentage);
        friendWin = findViewById(R.id.friendWin);
        friendDraw = findViewById(R.id.friendDraw);
        friendLose = findViewById(R.id.friendLose);
        friendTotal = findViewById(R.id.friendTotal);
        friendWinPercentage = findViewById(R.id.friendWinPercentage);
        totalWin = findViewById(R.id.totalWin);
        totalDraw = findViewById(R.id.totalDraw);
        totalLose = findViewById(R.id.totalLose);
        totalTotal = findViewById(R.id.totalTotal);
        totalWinPercentage = findViewById(R.id.totalWinPercentage);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
        changeNameButton = findViewById(R.id.changeNameButton);
        changeNameButton.setOnClickListener(this);

        userName.setText(user.getUsername());
        emailAddress.setText(user.getEmailAddress());

        easyWin.setText(String.valueOf(userEasyWin));
        easyDraw.setText(String.valueOf(userEasyDraw));
        easyLose.setText(String.valueOf(userEasyLose));
        easyTotal.setText(String.valueOf(userEasyTotal));
        easyWinPercentage.setText(String.valueOf(userEasyWinPercentage));
        hardWin.setText(String.valueOf(userHardWin));
        hardDraw.setText(String.valueOf(userHardDraw));
        hardLose.setText(String.valueOf(userHardLose));
        hardTotal.setText(String.valueOf(userHardTotal));
        hardWinPercentage.setText(String.valueOf(userHardWinPercentage));
        friendWin.setText(String.valueOf(userFriendWin));
        friendDraw.setText(String.valueOf(userFriendDraw));
        friendLose.setText(String.valueOf(userFriendLose));
        friendTotal.setText(String.valueOf(userFriendTotal));
        friendWinPercentage.setText(String.valueOf(userFriendWinPercentage));
        totalWin.setText(String.valueOf(userTotalWin));
        totalDraw.setText(String.valueOf(userTotalDraw));
        totalLose.setText(String.valueOf(userTotalLose));
        totalTotal.setText(String.valueOf(userTotalTotal));
        totalWinPercentage.setText(String.valueOf(userTotalWinPercentage));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.changeNameButton:
                changeName();
                break;
        }
    }

    private BigDecimal calcWinPercentage(int win, int total){
        if (total != 0){
            return BigDecimal.valueOf(win).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    private void changeName(){
        AlertDialog changeNameDialog = new AlertDialog.Builder(SettingsActivity.this).create();
        changeNameDialog.setTitle("Change your username");
        final EditText input = new EditText(this);
        changeNameDialog.setView(input);
        // Set up the buttons
        changeNameDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.setUsername(input.getText().toString());
                dataBaseHelper.updateUser(user);
                userName.setText(user.getUsername());
                dialog.dismiss();
            }
        });
        changeNameDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        changeNameDialog.show();
    }
}
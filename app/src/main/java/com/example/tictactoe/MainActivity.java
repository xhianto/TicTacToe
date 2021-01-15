package com.example.tictactoe;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;
    ImageButton settingsImageButton;
    SignInButton buttonSignIn;
    Button buttonSignOut, buttonEasy, buttonHard, buttonP1vsP2;
    TextView textLoginNotice;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSignIn = findViewById(R.id.sign_in_button);
        buttonSignIn.setOnClickListener(this);
        buttonSignOut = findViewById(R.id.signout);
        buttonSignOut.setOnClickListener(this);
        buttonEasy = findViewById(R.id.easy);
        buttonEasy.setOnClickListener(this);
        buttonHard = findViewById(R.id.hard);
        buttonHard.setOnClickListener(this);
        buttonP1vsP2 = findViewById(R.id.p1vsp2);
        buttonP1vsP2.setOnClickListener(this);
        settingsImageButton = findViewById(R.id.settingsButton);
        settingsImageButton.setOnClickListener(this);
        textLoginNotice = findViewById(R.id.TextLoginNotice);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            DataBaseHelper  dataBaseHelper = new DataBaseHelper(MainActivity.this);
            user = dataBaseHelper.getUserByEmail(account.getEmail());
            if (user.getEmailAddress() == null){
                user = new UserModel(-1, account.getDisplayName(), account.getEmail());
                dataBaseHelper.addUser(user);
                Toast.makeText(MainActivity.this, "New User Added", Toast.LENGTH_LONG).show();
            }
            buttonSignIn.setVisibility(View.INVISIBLE);
            textLoginNotice.setVisibility(View.INVISIBLE);
            buttonEasy.setVisibility(View.VISIBLE);
            buttonHard.setVisibility(View.VISIBLE);
            buttonP1vsP2.setVisibility(View.VISIBLE);
            buttonSignOut.setVisibility(View.VISIBLE);
            settingsImageButton.setVisibility(View.VISIBLE);
//            Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
//            startActivity(intent);
            // Signed in successfully, show authenticated UI.
            ////updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            ////updateUI(null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        signIn();
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    buttonSignIn.setVisibility(View.VISIBLE);
                    textLoginNotice.setVisibility(View.VISIBLE);
                    buttonEasy.setVisibility(View.INVISIBLE);
                    buttonHard.setVisibility(View.INVISIBLE);
                    buttonP1vsP2.setVisibility(View.INVISIBLE);
                    buttonSignOut.setVisibility(View.INVISIBLE);
                    settingsImageButton.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Signed Out Successfully", Toast.LENGTH_LONG).show();
                }
            });
    }

    @Override
    public void onClick(View v) {
        Intent intentGame = new Intent(MainActivity.this, GameActivity.class);
        intentGame.putExtra("user", user);
        Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
        intentSettings.putExtra("user", user);
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.signout:
                signOut();
                break;
            case R.id.easy:
                intentGame.putExtra("mode", "easy");
                startActivity(intentGame);
                break;
            case R.id.hard:
                intentGame.putExtra("mode", "hard");
                startActivity(intentGame);
                break;
            case R.id.p1vsp2:
                intentGame.putExtra("mode", "p1vsp2");
                startActivity(intentGame);
                break;
            case R.id.settingsButton:
                startActivity(intentSettings);
        }
    }
}
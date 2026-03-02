package com.zybooks.inventoryapp;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.zybooks.inventoryapp.model.User;
import com.zybooks.inventoryapp.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private TextView createAccountTextView;
    private Button loginBtn;
    private Button createAccountBtn;
    private EditText userName;
    private EditText userPassword;
    private EditText verifyPass;
    private TextView signInText;
    private TextView errorMessage;

    private UserViewModel userViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


        loginBtn = findViewById(R.id.login_sign_in_button);
        verifyPass = findViewById(R.id.login_verify_password);
        createAccountBtn = findViewById(R.id.login_new_account_button);
        createAccountTextView = findViewById(R.id.login_create_text_view);
        errorMessage = findViewById(R.id.login_error_message);

        signInText = findViewById(R.id.login_sign_in_text);
        userName = findViewById(R.id.login_username);
        userPassword = findViewById(R.id.login_password);


        // Sets the onclick listener for the create account and login buttons
        createAccountTextView.setOnClickListener(this::toggleCreateAccountAndLogin);
        signInText.setOnClickListener(this::toggleCreateAccountAndLogin);

        // Sets the onClick listener for the create account button
        createAccountBtn.setOnClickListener(this::createAccount);

        loginBtn.setOnClickListener(this::loginClick);

        // These two lines create the toolbar appbar.
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);




    } // end of onCreate



    public void loginClick(View view){
        String loginName = userName.getText().toString();
        String loginPassword = userPassword.getText().toString();
        boolean validInput = true;
        boolean validUser = false;

        if(loginName.isEmpty()){
            userName.setError("Must have a valid username");
            validInput = false;
            return;
        }
        if (loginPassword.isEmpty()){
            userPassword.setError("Must have a password");
            validInput = false;
            return;
        }

        if(validInput){
            User loginUser = new User(loginName, loginPassword);
            validUser = userViewModel.loginUser(loginUser);
        }

        if(validUser){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            errorMessage.setText("Invalid login credintials");
        }
    }

    public void createAccount(View view){

         String newUserName = userName.getText().toString();
         String newPassword = userPassword.getText().toString();
         String newVerifyPass = verifyPass.getText().toString();
         boolean validInput = true;

         if (newUserName.length() < 5){
             userName.setError("User name must be at least 5 characters");
             validInput = false;
         } else if (newPassword.length() < 5){
             //ToDo: change password length to 8
             userPassword.setError("Password must be at least 5 characters");
             validInput = false;

         } else if (!newPassword.equals(newVerifyPass)){
             verifyPass.setError("Passwords don't match");
             validInput = false;

         }

        if(validInput){
            User newUser = new User(newUserName, newPassword);
            userViewModel.createUser(newUser);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }


    }


    public void toggleCreateAccountAndLogin(View view){

        if (view.getId() == R.id.login_create_text_view){
            createAccountTextView.setVisibility(GONE);
            loginBtn.setVisibility(GONE);

            verifyPass.setVisibility(VISIBLE);
            createAccountBtn.setVisibility(VISIBLE);
            signInText.setVisibility(VISIBLE);
        } else if (view.getId() == R.id.login_sign_in_text){
            createAccountTextView.setVisibility(VISIBLE);
            loginBtn.setVisibility(VISIBLE);

            verifyPass.setVisibility(GONE);
            createAccountBtn.setVisibility(GONE);
            signInText.setVisibility(GONE);
        }


    }



}
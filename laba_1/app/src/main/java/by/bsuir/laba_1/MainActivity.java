package by.bsuir.laba_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.signin.internal.SignInClientImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Date;

import by.bsuir.laba_1.db.Database;

public class MainActivity extends AppCompatActivity {
    private int count = 0;
    private TextView textCounter;
    private ImageView cat;
    private Database database;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textCounter = findViewById(R.id.counter);
        cat = findViewById(R.id.image);
        database = new Database(getApplicationContext());
        db = database.getWritableDatabase();
    }


    public void increment(View view){
        ++count;
        textCounter.setText(String.valueOf(count));
        if(count % 15 == 0){
            animation();
        }
    }

    public void share(View view){
        String message = "Share with you my result " + count;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Share"));
    }

    public void goToAboutMe(View view){
        Intent intent = new Intent(this, AboutMeActivity.class);
        startActivity(intent);
    }

    public void saveResult(View view){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.COLUMN_DATE, new Date().toString());
        contentValues.put(Database.COLUMN_RESULT, textCounter.getText().toString());
        contentValues.put(Database.COLUMN_EMAIL,  GoogleSignIn.getLastSignedInAccount(this).getEmail());
        db.insert(Database.TABLE, null, contentValues);
    }
    public void goToResults(View view){
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }

    private void animation(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        cat.startAnimation(animation);
    }

    public void signOut(View view){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, gso);
        signInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });

    }
}
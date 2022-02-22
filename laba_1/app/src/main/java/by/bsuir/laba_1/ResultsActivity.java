package by.bsuir.laba_1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

import by.bsuir.laba_1.db.Database;

public class ResultsActivity extends AppCompatActivity {
    private TextView textView;
    private Database database;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        textView = findViewById(R.id.results);
        database = new Database(getApplicationContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Database.TABLE + " WHERE  " + Database.COLUMN_EMAIL +" = " + "\'" + GoogleSignIn.getLastSignedInAccount(this).getEmail() + "\'", null);
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String res = cursor.getString(1);
            String date = cursor.getString(2);
            long date1 = Date.parse(date);
            LocalDateTime time =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(date1), TimeZone
                            .getDefault().toZoneId());
            String str = time.getDayOfMonth() + " " + time.getMonthValue() + " " + time.getYear() + " " + time.getHour() + " " +
                    time.getMinute() + " " + time.getSecond();
            textView.append(id + " " + res + "        " + str +" \n");
        }
    }
}
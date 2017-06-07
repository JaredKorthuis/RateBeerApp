package edu.korthuijoregonstate.HOMEFINAL;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayTable extends AppCompatActivity {
    DB_Controller controller;
    private String user_id;
    Button database;
    Button MainMenu;
    TextView table;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("beer is","DID I SEE ME?");

        setContentView(R.layout.activity_display_table);

        database   = (Button) findViewById(R.id.database);
        MainMenu   = (Button) findViewById(R.id.tableMenu);
        table      = (TextView) findViewById(R.id.table);

        controller = new DB_Controller(this, "", null, 1);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null){
            String getName = (String) bd.get("STRING_JARED_WANTS");
            user_id = getName;
        }
        //table.setText(user_id);
        controller.list_all_data(table, user_id);

        MainMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goMainScreen();
            }
        });

        database.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goDataScreen();

            }
        });



    }
    public void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goDataScreen() {
        Intent intent = new Intent(this, NewData.class).putExtra("STRING_I_NEED", user_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}


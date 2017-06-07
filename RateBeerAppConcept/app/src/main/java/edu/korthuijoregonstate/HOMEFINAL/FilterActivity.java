package edu.korthuijoregonstate.HOMEFINAL;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FilterActivity extends AppCompatActivity {
    DB_Controller controller;
    private String user_id;
    TextView table;
    EditText Type;
    Button MainMenu;
    Button FilterBase;
    Button database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        controller = new DB_Controller(this, "", null, 1);
        MainMenu   = (Button)   findViewById(R.id.FilterMain);
        FilterBase = (Button)   findViewById(R.id.FilterButton);
        database   = (Button)   findViewById(R.id.FilterGoBack);
        Type       = (EditText) findViewById(R.id.BeerType);
        table      = (TextView) findViewById(R.id.FilterTable);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null){
            String getName = (String) bd.get("STRING_USER_ID");
            user_id = getName;
        }
        //table.setText(user_id);

        MainMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goMainScreen();

            }
        });
        FilterBase.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String beer = Type.getText().toString().toUpperCase();
                Log.i(beer,beer);
                Log.i(user_id,user_id);
                controller.list_filter_data(table, user_id, beer );
            }
        });
        database.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goDataScreen();
            }
        });
    }
    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goDataScreen(){
        Intent intent = new Intent(this, NewData.class).putExtra("STRING_I_NEED",user_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}

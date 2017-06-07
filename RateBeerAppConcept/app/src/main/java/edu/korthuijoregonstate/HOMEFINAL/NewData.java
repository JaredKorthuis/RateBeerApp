package edu.korthuijoregonstate.HOMEFINAL;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NewData extends AppCompatActivity {

    EditText Beer;
    EditText mytype;
    EditText Rating;
    EditText content;

    private GoogleApiClient googleApiClient;
    private Double myLatitude;
    private Double myLongitude;
    private static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 101;
    private static final int MY_PERMISSION_REQUEST_COARSE_LOCATION = 102;
    private boolean permissionIsGranted = false;
    Button insertButton;
    Button updateButton;
    Button deleteButton;
    Button filterButton;
    Button displayButton;
    EditText textbox;
    DB_Controller controller;
    String user_id;
    MainActivity grabber;
    Button userID;
    TextView fakeview;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            String getName = (String) bd.get("STRING_I_NEED");
            user_id = getName;
        }
        displayButton = (Button) findViewById(R.id.Display);
        insertButton = (Button) findViewById(R.id.Insert);
        deleteButton = (Button) findViewById(R.id.Delete);
        updateButton = (Button) findViewById(R.id.Update);
        filterButton = (Button) findViewById(R.id.Filter);
        Beer = (EditText) findViewById(R.id.Beer);
        mytype = (EditText) findViewById(R.id.Type);
        Rating = (EditText) findViewById(R.id.rating);
        content = (EditText) findViewById(R.id.content);
        fakeview = (TextView) findViewById(R.id.fake_view);
        grabber = new MainActivity();
        controller = new DB_Controller(this, "", null, 1);
        userID = (Button) findViewById(R.id.userID);
        fakeview.setText(Beer.getText());
        fakeview.setVisibility(View.INVISIBLE);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    controller.list_beer_names(fakeview, user_id, Beer.getText().toString().toUpperCase());
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(fakeview.getText().toString()));
                    Log.i("MY_ROWS",fakeview.getText().toString().toUpperCase());
                    boolean result = checkString(list, Beer.getText().toString());
                    if(result == true) {
                        controller.insert_beer(user_id, Beer.getText().toString().toUpperCase(), mytype.getText().toString().toUpperCase(), Rating.getText().toString(), content.getText().toString());
                        Toast.makeText(NewData.this, "INSERTED BEER SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(NewData.this, "VALUE ALREADY EXISTS", Toast.LENGTH_SHORT).show();

                    }

                } catch (SQLiteException e) {
                    Toast.makeText(NewData.this, "ALREADY EXISTS"+e, Toast.LENGTH_SHORT).show();
                }

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controller.delete_beer(Beer.getText().toString().toUpperCase());
                    Toast.makeText(NewData.this, "DELETED BEER: " + Beer.getText().toString(), Toast.LENGTH_SHORT).show();
                } catch (SQLiteException e) {
                    Toast.makeText(NewData.this, "Could not DELETE", Toast.LENGTH_SHORT).show();
                }


            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(NewData.this);
                    dialog.setTitle("ENTER NEW BEER");
                    final EditText new_BEER = new EditText(NewData.this);
                    dialog.setView(new_BEER);

                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            controller.update_beer(Beer.getText().toString().toUpperCase(), new_BEER.getText().toString().toUpperCase());
                            Toast.makeText(NewData.this, "SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert11 = dialog.create();
                    alert11.show();
                    //dialog.setIcon(android.R.drawable.ic_dialog_alert);
                    //dialog.show();
                } catch (SQLiteException e) {
                    Toast.makeText(NewData.this, "UNABLE TO UPDATE", Toast.LENGTH_SHORT).show();
                }

            }
        });
        userID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainScreen();
            }
        });
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDisplayScreen();
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goFilterScreen();
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void goDisplayScreen() {
        System.out.printf("going to display screen");
        Intent intent = new Intent(this, DisplayTable.class).putExtra("STRING_JARED_WANTS", user_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goFilterScreen() {
        Intent intent = new Intent(this, FilterActivity.class).putExtra("STRING_USER_ID", user_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("NewData Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

   boolean checkString(ArrayList my_list, String my_string) {
       Set<String> set = new HashSet<String>(my_list);

       if(set.contains(my_string.toUpperCase())){
            Log.i("RESULT", "I SHOULD RETURN FALSE");
            return false;

        }

       return true;
   }

}

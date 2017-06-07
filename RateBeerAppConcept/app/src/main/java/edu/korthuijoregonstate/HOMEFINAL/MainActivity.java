package edu.korthuijoregonstate.HOMEFINAL;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.PlusShare;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView idTextView;
    private Button   SignIn;
    private Button   LogOut;
    private Button   mydata;
    private Button   make_post;
    private TextView tokenView;
    private GoogleApiClient googleApiClient;
    String accountID;
    private ImageView user_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // photoImageView = (ImageView) findViewById(R.id.photoImageView);
        nameTextView  = (TextView) findViewById(R.id.nameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        idTextView    = (TextView) findViewById(R.id.idTextView);
        tokenView     = (TextView) findViewById(R.id.tokenView);
        SignIn        = (Button) findViewById(R.id.SignIn);
        LogOut        = (Button) findViewById(R.id.LogOut);
        mydata        = (Button) findViewById(R.id.mydata);
        make_post     = (Button) findViewById(R.id.postData);
        user_image = (ImageView) findViewById(R.id.imageView);
        LogOut.setVisibility(View.GONE);
        mydata.setVisibility(View.GONE);
        make_post.setVisibility(View.GONE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(AppIndex.API).build();


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogInScreen();
            }
        });
        mydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDataScreen();
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            goLogInScreen();
                        } else {
                            //  Toast.makeText(getApplicationContext(), R.string.not_close_session, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        make_post.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PlusShare.Builder builder = new PlusShare.Builder(MainActivity.this);

                // Set call-to-action metadata.
                builder.addCallToAction(
                        "MESSAGE", /** call-to-action button label */
                        Uri.parse("http://plus.google.com/pages/create"), /** call-to-action url (for desktop use) */
                        "/pages/create" /** call to action deep-link ID (for mobile use), 512 characters or fewer */);

                // Set the content url (for desktop use).
                builder.setContentUrl(Uri.parse("https://plus.google.com/pages/"));
                // Set the share text.
                builder.setText("Create your Google+ Page too!");

                startActivityForResult(builder.getIntent(), 0);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        googleApiClient.connect();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);

        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(googleApiClient, getIndexApiAction());
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();

            String personPhoto = account.getPhotoUrl().toString();
           // Log.i("PHOTO_RESULT", personPhoto.toString());

           if(personPhoto != null) {

               Glide.with(getApplicationContext()).load(personPhoto)
                       .thumbnail(0.5f)
                       .crossFade()
                       .diskCacheStrategy(DiskCacheStrategy.ALL)
                       .into(user_image);
               String msg = "Happy Monday! #caseofthemondays";
               nameTextView.setText(account.getDisplayName());
               emailTextView.setText(account.getEmail());
               idTextView.setText(account.getId());
               tokenView.setText(account.getIdToken());
               set_accountid(account.getId().toString());

           }




            //set_accountid(account.getId().toString());


            SignIn.setVisibility(View.GONE);
            mydata.setVisibility(View.VISIBLE);
            LogOut.setVisibility(View.VISIBLE);
            make_post.setVisibility(View.VISIBLE);

        }
         else {

            nameTextView.setText("NO USER SIGNED IN");
        }

    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(View view) {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), "could not sign out", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void revoke(View view) {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                   Toast.makeText(getApplicationContext(), "could not revoke privileges", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void goDataScreen() {
        Intent intent = new Intent(this, NewData.class).putExtra("STRING_I_NEED",accountID);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void set_accountid(String id){
        accountID = id;

    }
    public String get_accountid(){
        return accountID;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(googleApiClient, getIndexApiAction());
        googleApiClient.disconnect();
    }
}
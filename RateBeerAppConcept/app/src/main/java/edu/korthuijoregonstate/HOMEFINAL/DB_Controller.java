package edu.korthuijoregonstate.HOMEFINAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

/**
 * Created by THE HAPPIEST ELF on 3/10/2017.
 */

public class DB_Controller extends SQLiteOpenHelper {
    public DB_Controller(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "TestDatabaseFile.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FINAL (ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_SUB TEXT ,BEER TEXT, TYPE TEXT, RATING TEXT, CONTENT TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS FINAL;");
        onCreate(db);
    }
    public void insert_beer(String ID,String beer, String my_type, String my_rating, String alcohol_content){
        int checker;
        String beer_value;
        ContentValues contentValues = new ContentValues();
        contentValues.put("USER_SUB", ID);
        contentValues.put("BEER", beer);
        contentValues.put("TYPE", my_type);
        contentValues.put("RATING", my_rating);
        contentValues.put("CONTENT", alcohol_content);
        this.getWritableDatabase().insertOrThrow("FINAL", "", contentValues);
        }





    public void delete_beer(String beer){
        this.getWritableDatabase().delete("FINAL", "BEER='"+beer+"'",null);
    }
    public void update_beer(String old_written, String new_written){
        this.getWritableDatabase().execSQL("UPDATE FINAL SET BEER='"+new_written+"' WHERE BEER='"+old_written+"'");

    }
    public void list_all_data(TextView textView, String user){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM FINAL WHERE USER_SUB='"+user+"'",null);
        textView.setText("");
        while(cursor.moveToNext()){
            textView.append("BEER: "+cursor.getString(2)+"  TYPE: "+cursor.getString(3)+"  SCORE: "+cursor.getString(4)+" ABV: "+cursor.getString(5)+"\n");
        }
    }
    public void list_filter_data(TextView textView, String user, String filter){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM FINAL WHERE USER_SUB='"+user+"' AND TYPE='"+filter+"'",null);
        textView.setText("");
        while(cursor.moveToNext()){
            textView.append(" BEER: "+cursor.getString(2)+"  TYPE: "+cursor.getString(3)+"  SCORE " +cursor.getString(4)+ " ABV: "+cursor.getString(5)+"\n");
        }
    }

    public void list_beer_names(TextView textView, String user, String Beer){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM FINAL WHERE BEER='"+Beer+"' AND USER_SUB='"+user+"'",null);
        textView.setText("");
        while(cursor.moveToNext()){
            textView.append(cursor.getString(2));
        }
    }
}

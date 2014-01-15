package com.example.stockprice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDatabaseOpenHelper extends SQLiteOpenHelper{

	String tableName;

    public AccountDatabaseOpenHelper(Context context) {
            super(context, "optionprice.db", null, 2);
            tableName = "optionprice.db";
            // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            setup(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            setup(db);
            
    }
    
    private void setup(SQLiteDatabase db){
            db.execSQL("CREATE TABLE IF NOT EXISTS tb_login"
                            + "(fname TEXT, lname TEXT, password TEXT , email TEXT, phone TEXT);");
            
            String insertString = String.format("INSERT INTO tb_login"
                    + " (fname , lname , password , email , phone)"
                    + " VALUES (\"%s\", \"%s\", \"%s\", \"%s\" , \"%s\");",
                    "admin", " ", "admin", "admin@email.com", "-");

            /**Execute the insert query**/
            db.execSQL(insertString);
            
            insertString = String.format("INSERT INTO tb_login"
                    + " (fname , lname , password , email , phone)"
                    + " VALUES (\"%s\", \"%s\", \"%s\", \"%s\" , \"%s\");",
                    "Christopher", "Bobby", "christopher", "chris.bobby@email.com", "-");

            /**Execute the insert query**/
            db.execSQL(insertString);
            
            insertString = String.format("INSERT INTO tb_login"
                    + " (fname , lname , password , email , phone)"
                    + " VALUES (\"%s\", \"%s\", \"%s\", \"%s\" , \"%s\");",
                    "Carlo", "Putra", "carlo", "carlo.putra@email.com", "-");

            /**Execute the insert query**/
            db.execSQL(insertString);
            
            insertString = String.format("INSERT INTO tb_login"
                    + " (fname , lname , password , email , phone)"
                    + " VALUES (\"%s\", \"%s\", \"%s\", \"%s\" , \"%s\");",
                    "Erlangga", "Andanto", "erlangga", "erlangga@email.com", "-");

            /**Execute the insert query**/
            db.execSQL(insertString);
            
            insertString = String.format("INSERT INTO tb_login"
                    + " (fname , lname , password , email , phone)"
                    + " VALUES (\"%s\", \"%s\", \"%s\", \"%s\" , \"%s\");",
                    "Kai", "Nathan", "kai", "kai.nathan@email.com", "-");

            /**Execute the insert query**/
            db.execSQL(insertString);
            
    }

	
}

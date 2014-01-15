package com.example.stockprice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AccountDatabase {


    private AccountDatabaseOpenHelper databaseOpenHelper;
    SQLiteDatabase db;
    String fname , lname, password , email, phone;
    boolean writable;
    Context context;
    
    public AccountDatabase(Context context){
            this.context = context;
            this.writable = false;
            OpenDatabase();
    }
    
    public AccountDatabase(Context context, String fname , String lname, String password, String email, String phone){
            this.context = context;
            this.fname = fname;
            this.lname = lname;
            this.password = password;
            this.email = email;
            this.phone = phone;
            this.writable = true;
            
            OpenDatabase();
    }
    
	public boolean authenticate(String Email , String Password){
		//db = new MaintainDatabase(getApplicationContext());
	
		Cursor mCount= db.rawQuery("select count(*) from tb_login where Email='" + Email+ "' and password='" + Password +"'", null);
		
		mCount.moveToFirst();
	
		if(mCount.getInt(0) > 0){
			mCount.close();
			return true;
		}
		else {
			mCount.close();
			return false;
		}
	}
    
    public void OpenDatabase(){
            this.databaseOpenHelper = new AccountDatabaseOpenHelper(context);
            if(this.writable){
                    db = this.databaseOpenHelper.getWritableDatabase();
            }
            else{
                    db = this.databaseOpenHelper.getReadableDatabase();
            }
    }
    
    public void insertToDatabase(){
            String insertString = String.format("INSERT INTO tb_login"
                            + " (fname , lname , password , email , phone)"
                            + " VALUES (\"%s\", \"%s\", \"%s\", \"%s\" , \"%s\");",
                            this.fname, this.lname, this.password,this.email, this.phone);
    
            /**Execute the insert query**/
            db.execSQL(insertString);
    }
    
    public Cursor selectFromDatabase(){
            Cursor cursor = db.query(true , "tb_login" , null , null 
                                                            , null , null , null , null , null);
            return cursor;
    }
    
    public String getTableName(){
    	return databaseOpenHelper.tableName;
    }

	public String[] getAccountDataFromEmail(String email) {
		String accountString = "";

		Cursor c= db.rawQuery("select * from tb_login where Email='" + email + "'", null);

		if (c != null){

			c.moveToFirst();

			String[] columns = {"fname", "lname", "password", "email", "phone" };
			for (int i = 0; i < columns.length; i++){

				accountString += (c.getString(c.getColumnIndex(columns[i])) + ",");
				
			}
			
			
		}
		
		 //remove ',' at the end
		
		String[] accountData = accountString.split(",");
		
		
		return accountData;
		
	}
	
	public void updateRow(String fname, String lname, String password, String email, String phone){
		
		ContentValues values = new ContentValues();
		values.put("fname", fname);
		values.put("lname", lname);
		if(password != null) values.put("password", password);
		values.put("phone", phone);

		
		db.update("tb_login", values, "email ='" + email +"'", null);
		
		
	}
	
	public void deleteRow(String email){
		
		db.delete("tb_login", "email ='" + email +"'", null);
		
	}
	
}

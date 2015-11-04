package database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
	private static String name="data";
	private static int version=1;
	private static Context con;

	public Database(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
		con=context;
	
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL("CREATE TABLE USERDETAIL(GMAIL STRING, NAME STRING, GENDER STRING, DOB STRING)");
		db.execSQL("CREATE TABLE FAVORITES(ID STRING, TITLE STRING,IMAGE STRING ,TYPE STRING)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void insertUserDetail(String gmail, String name, String gender, String dob) {
		
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("GMAIL",gmail);
		cv.put("NAME", name);
		cv.put("GENDER", gender);
		cv.put("DOB", dob);
		
		db.insert("USERDETAIL", null, cv);
		db.close();
		
		
		
		
	}
	
	public ArrayList<User> getprofileData()
	{
		SQLiteDatabase db=getReadableDatabase();
		
		Cursor c=db.rawQuery("SELECT * FROM USERDETAIL",null);
		ArrayList<User> a=null;
		if(c.moveToFirst())
		{
			
			a=new ArrayList<User>();
			do{
				User s=new User(c.getString(0),c.getString(1),c.getString(2),c.getString(3));
				a.add(s);
			}while(c.moveToNext());
			
		}
		Log.i("as","asdasd");
		db.close();
		if(c!=null){
		return a;
		}
		else return null;
	}

     public boolean checkDatabasePresent() {
	   
	   SQLiteDatabase db= getReadableDatabase();
	    Cursor c =  db.rawQuery("SELECT * FROM USERDETAIL", null);
	    if(c.getCount() != 0) {
	    	
	    	return true;
	    }
	    else
	    	return false;
	    
	   
	   
   }

    public void updateUserdetail(String newemail,String newname,String newgender,String newdob) {
    	
    	SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("GMAIL",newemail);
		cv.put("NAME", newname);
		cv.put("GENDER",newgender);
		cv.put("DOB", newdob);
		
		db.update("USERDETAIL", cv, null, null);
		
        
    	
    	
    	
    }
    
    public void deleteProfile() {
    	
    	SQLiteDatabase db = getWritableDatabase();
    	db.execSQL("DELETE FROM USERDETAIL");
    	
    	
    	
   
    
  
    }
    
    
    public void insertFavorites(String id,String title, String type, String image) {
    	
        SQLiteDatabase db = getWritableDatabase();
       ContentValues cv= new ContentValues();
       cv.put("ID", id);
       cv.put("TITLE", title);
       cv.put("TYPE",type);
       cv.put("IMAGE", image);
       
       db.insert("FAVORITES", null, cv);
       
       
     
     }
    
    public ArrayList<FavoriteItem> getFavoritesData()
	{
		SQLiteDatabase db=getReadableDatabase();
		
		Cursor c=db.rawQuery("SELECT * FROM FAVORITES",null);
		ArrayList<FavoriteItem> a=null;
		if(c.moveToFirst())
		{
			
			a=new ArrayList<FavoriteItem>();
			do{
				FavoriteItem s=new FavoriteItem(c.getString(0), c.getString(1), c.getString(2),c.getString(3));
				a.add(s);
			}while(c.moveToNext());
			
		}
		Log.i("as","asdasd");
		db.close();
		if(c!=null){
		return a;
		}
		else return null;
	}
    
    public boolean checkDatabasefav() {
 	   
 	   SQLiteDatabase db= getReadableDatabase();
 	    Cursor c =  db.rawQuery("SELECT * FROM FAVORITES", null);
 	    if(c.getCount() != 0) {
 	    	
 	    	return true;
 	    }
 	    else
 	    	return false;
 	    
 	   
 	   
    }
    
    public boolean checkprevFav(String title) {
    	
     SQLiteDatabase db=getReadableDatabase();
     String t[] = new String[]{title};
     Cursor c =  db.rawQuery("SELECT * FROM FAVORITES WHERE TITLE=?", t);
	    if(c.getCount() != 0) {
	    	
	    	return true;
	    }
	    else
	    	return false;
	    
		
		}	
    
    }
    
    



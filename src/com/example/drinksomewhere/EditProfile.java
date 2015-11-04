package com.example.drinksomewhere;

import java.io.File;
import java.util.ArrayList;

import com.example.drinksomewhere.MenuAnimation.Editcheck;

import database.Database;
import database.User;
import drink.some.drinksomewhere.Favorities;
import drink.some.drinksomewhere.Promotions;
import drink.some.drinksomewhere.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfile extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
////////////////////////////////INITIALIZATION/////////////////////////////////////////////////////		
		
		ImageView imagebackbutton = (ImageView) findViewById(R.id.imagebackbutton);
		final EditText editmail = (EditText) findViewById(R.id.edittxtUsermail);
	final	EditText edituser = (EditText) findViewById(R.id.edittxtUsername);
		final EditText editgender = (EditText) findViewById(R.id.edittxtgender);
		final EditText editdob = (EditText) findViewById(R.id.edittxtdob);
		Button btneditmail= (Button) findViewById(R.id.buttoneditmail);
		Button btnedituser= (Button) findViewById(R.id.buttoneditname);
		Button btneditgender= (Button) findViewById(R.id.buttoneditgender);
		Button btneditdob= (Button) findViewById(R.id.buttoneditdob);
		Button Done = (Button)findViewById(R.id.buttonDone);
		ImageView imgeditprofilepic= (ImageView) findViewById(R.id.imageEditprofileimage);
		
//////////////////////////////////////////////////////////google image in menu////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////

File mfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "profile_drinksomewhere.jpg");
if(mfile.exists()) {
Bitmap picture=BitmapFactory.decodeFile(mfile.getAbsolutePath());
Bitmap circleBitmap = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
BitmapShader shader = new BitmapShader (picture,  TileMode.CLAMP, TileMode.CLAMP);
Paint paint = new Paint();
paint.setShader(shader);
Canvas c = new Canvas(circleBitmap);
c.drawCircle(picture.getWidth()/2, picture.getHeight()/2, picture.getWidth()/2, paint);		                
imgeditprofilepic.setImageBitmap(circleBitmap);

}
else{
Toast.makeText(EditProfile.this, "Picture not found",Toast.LENGTH_SHORT).show();
}
//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////	   

		
		ArrayList<User> userdetails = new ArrayList<User>();
		
	
		
////////////////////////////////DECLARATION//////////////////////////////////////////////////////
		
	   Database db= new Database(EditProfile.this);
	   
	   userdetails= db.getprofileData();
	   
	   
	   for(User u:userdetails) {
		   
		   editmail.setText(u.getEmail());
		   edituser.setText(u.getName());
		   editdob.setText(u.getDob());
		   editgender.setText(u.getGender());
	   }
	   
	   
	   
	   
	  
	   
	   

		editmail.setEnabled(false);
		edituser.setEnabled(false);
		editgender.setEnabled(false);
		editdob.setEnabled(false);
		
		btneditmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				editmail.setEnabled(true);
				
			}
		});
		
		btnedituser.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				edituser.setEnabled(true);
				
				
			}
		});
		
		btneditdob.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				editdob.setEnabled(true);
				
			}
		});
		
		btneditgender.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				editgender.setEnabled(true);
				
			}
		});
		
		
//////////////////////////////////////////////////////////////////////////////////////
		Done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String newname = edituser.getText().toString();
				String newemail = editmail.getText().toString();
				String newgender =editgender.getText().toString();
				String newdob = editdob.getText().toString();
				
				Database db= new Database(EditProfile.this);
				db.updateUserdetail(newemail,newname,newgender,newdob);
				Intent i= new Intent(EditProfile.this, EditProfile.class);
				startActivity(i);
				finish();
				
				
				
				
			}
		});
		
		
		
		imagebackbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(Editcheck.fromwhere.equals("EventCalender")) {
					Intent i = new Intent(EditProfile.this, EventCalender.class);
					startActivity(i);
					finish();
				}
				else
				
				if(Editcheck.fromwhere.equals("HomePage")) {
					Intent i = new Intent(EditProfile.this, HomePage.class);
					startActivity(i);
					finish();
				}
				else
				
				if(Editcheck.fromwhere.equals("QuickSearchactivity")) {
					Intent i = new Intent(EditProfile.this, QuickSearchactivity.class);
					startActivity(i);
					finish();
				}
				
				else
				if(Editcheck.fromwhere.equals("SettingActivity"))
				{
					
					Intent i = new Intent(EditProfile.this, SettingActivity.class);
					startActivity(i);
					finish();
					
					
				}
				else
					if(Editcheck.fromwhere.equals("Promotions"))
					{
						
						Intent i = new Intent(EditProfile.this, Promotions.class);
						startActivity(i);
						finish();
						
						
					}
					else
						if(Editcheck.fromwhere.equals("Favorities"))
						{
							
							Intent i = new Intent(EditProfile.this, Favorities.class);
							startActivity(i);
							finish();
							
							
						}else
				{
					Intent i = new Intent(EditProfile.this, SettingActivity.class);
					startActivity(i);
					finish();
					
					
				}
				
				
				
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

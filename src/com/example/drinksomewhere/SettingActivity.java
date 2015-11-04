package com.example.drinksomewhere;

import java.io.File;
import java.util.ArrayList;

import com.example.drinksomewhere.MenuAnimation.CollapseAnimation;
import com.example.drinksomewhere.MenuAnimation.Editcheck;
import com.example.drinksomewhere.MenuAnimation.ExpandAnimation;

import database.Database;
import database.User;
import drink.some.drinksomewhere.Editorials;
import drink.some.drinksomewhere.Favorities;
import drink.some.drinksomewhere.Promotions;
import drink.some.drinksomewhere.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity {
	
	private RelativeLayout slidingPanel;
	private boolean isExpanded;
	private DisplayMetrics metrics;	
	private ListView listView;
	private RelativeLayout headerPanel;
	private RelativeLayout menuPanel;
	private int panelWidth;
	private ImageView menuViewButton;
	
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters ;
	LinearLayout.LayoutParams listViewParameters;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels)*0.75);
		
		slidingPanel = (RelativeLayout) findViewById(R.id.slidingpanel);
		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel.getLayoutParams();
		slidingPanelParameters.width = metrics.widthPixels;
		slidingPanel.setLayoutParams(slidingPanelParameters);
		
		  final TableRow tbsearch = (TableRow) findViewById(R.id.ViewSearch);
		   final TableRow tbEventcalender = (TableRow) findViewById(R.id.ViewEventCalender);
		   final  TableRow tbeditorial = (TableRow) findViewById(R.id.ViewEditorial);
		   final TableRow tbpromotion = (TableRow) findViewById(R.id.ViewPromo);
		   final  TableRow tbyourfav = (TableRow) findViewById(R.id.ViewFavorites);
		   final TableRow tbDiscover = (TableRow) findViewById(R.id.ViewDiscover);
		   final  TableRow tbsetting = (TableRow) findViewById(R.id.ViewSettings);
		   ImageView imguseredit = (ImageView) findViewById(R.id.imageUserpic);
		 
		   
		   final AlertDialog.Builder ad= new AlertDialog.Builder(SettingActivity.this);
		   ad.setTitle("DrinkSomeWhere");
		   ad.setMessage("Do you want to logout !");
		   ad.setIcon(R.drawable.ic_launcher);
		   ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Database db = new Database(SettingActivity.this);
				db.deleteProfile();
				Intent i= new Intent(SettingActivity.this, MainActivity.class);
				startActivity(i);
				finish();
				
				
			}
		});
		   
		   ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		   
		   Button logout = (Button) findViewById(R.id.buttonlogout);
		   logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ad.show();
				
				
			}
		});
		   
		   
		   
		   tbsearch.setEnabled(false);
		   tbEventcalender.setEnabled(false);
		   tbeditorial.setEnabled(false);
		   tbpromotion.setEnabled(false);
		   tbyourfav.setEnabled(false);
		   tbDiscover.setEnabled(false);
		   tbsetting.setEnabled(false);
	/////////////////////////////////////////////////////////////////////////////////

		ImageView imgHomeButton= (ImageView) findViewById(R.id.imageHomeButton);
		Button editprofilebtn= (Button) findViewById(R.id.editprofilebutton);
		menuViewButton = (ImageView) findViewById(R.id.imagemenuopen);
		
	//////////////////////////////////////////////////////////////////////////////////
		  tbsearch.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(SettingActivity.this, QuickSearchactivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			
			
		}
	});
	   
	   tbsetting.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(SettingActivity.this, SettingActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			
			
			
		}
	});
	   tbyourfav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SettingActivity.this, Favorities.class);
				startActivity(i);
				overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
				
				
				
			}
		});
		
	   tbEventcalender.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent i= new Intent(SettingActivity.this, EventCalender.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
		}
	});
	   
	   tbpromotion.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(SettingActivity.this, Promotions.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
		}
	});
	   tbeditorial.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(SettingActivity.this, Editorials.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
			
		}
	});
	   
	   tbDiscover.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(SettingActivity.this, SearchResultMap.class);
			i.putExtra("Searchfrom", "quicksearch");
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
		}
	});
		
	   imguseredit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editcheck.fromwhere= "SettingActivity";
				//Toast.makeText(SettingActivity.this, Editcheck.fromwhere, Toast.LENGTH_LONG).show();
				Intent i= new Intent(SettingActivity.this, EditProfile.class);
				startActivity(i);
				
				
			}
		});
	   
	   
		    //////////////////////////////////////////////////////////google image in menu////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////

TextView nameinMenu = (TextView) findViewById(R.id.textView1);
TextView emailinMenu = (TextView) findViewById(R.id.textView2);
File mfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "profile_drinksomewhere.jpg");
if(mfile.exists()) {
Bitmap picture=BitmapFactory.decodeFile(mfile.getAbsolutePath());
Bitmap circleBitmap = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
BitmapShader shader = new BitmapShader (picture,  TileMode.CLAMP, TileMode.CLAMP);
Paint paint = new Paint();
paint.setShader(shader);
Canvas c = new Canvas(circleBitmap);
c.drawCircle(picture.getWidth()/2, picture.getHeight()/2, picture.getWidth()/2, paint);		                
imguseredit.setImageBitmap(circleBitmap);

}
else{
Toast.makeText(SettingActivity.this, "Picture not found",Toast.LENGTH_SHORT).show();
}
Database db = new Database(SettingActivity.this);
ArrayList<User> details = new ArrayList<User>();
details= db.getprofileData();
for(User u: details) {
nameinMenu.setText(u.getName());
emailinMenu.setText(u.getEmail());
}
//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////	   

		
		
		imgHomeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(SettingActivity.this, HomePage.class);
				startActivity(i);
				
			}
		});

		menuViewButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 tbsearch.setEnabled(true);
				   tbEventcalender.setEnabled(true);
				   tbeditorial.setEnabled(true);
				   tbpromotion.setEnabled(true);
				   tbyourfav.setEnabled(true);
				   tbDiscover.setEnabled(true);
				   tbsetting.setEnabled(true);
				
				if(!isExpanded){
		    		isExpanded = true;   		    				        		
		        	
		    		//Expand
		    		new ExpandAnimation(slidingPanel, panelWidth,
		    	    Animation.RELATIVE_TO_SELF, 0.0f,
		    	    Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);		    			         	    
		    	}else{
		    		isExpanded = false;
		    		
		    		//Collapse
		    		 tbsearch.setEnabled(false);
		    		   tbEventcalender.setEnabled(false);
		    		   tbeditorial.setEnabled(false);
		    		   tbpromotion.setEnabled(false);
		    		   tbyourfav.setEnabled(false);
		    		   tbDiscover.setEnabled(false);
		    		   tbsetting.setEnabled(false);
		    		new CollapseAnimation(slidingPanel,panelWidth,
        	    TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
        	    TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);
		   
					
		    	}
				
			}
		});
	
		editprofilebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editcheck.fromwhere="Setting";
				Intent i = new Intent(SettingActivity.this, EditProfile.class);
				startActivity(i);
				finish();
				
			}
		});
		
	
	
	
	
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
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

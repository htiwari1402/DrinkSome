package drink.some.drinksomewhere;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.drinksomewhere.EditProfile;
import com.example.drinksomewhere.EventCalender;
import com.example.drinksomewhere.HomePage;
import com.example.drinksomewhere.QuickSearchactivity;
import com.example.drinksomewhere.SearchResultDetails;
import com.example.drinksomewhere.SearchResultMap;
import com.example.drinksomewhere.ServiceHandler;
import com.example.drinksomewhere.SettingActivity;
import com.example.drinksomewhere.MenuAnimation.CollapseAnimation;
import com.example.drinksomewhere.MenuAnimation.Editcheck;
import com.example.drinksomewhere.MenuAnimation.ExpandAnimation;

import database.Database;
import database.FavoriteItem;
import database.User;

import eventitemDesigns.FavoritiesAdapter;
import eventitemDesigns.PromotionAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Favorities extends Activity {
	
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
	ArrayList<String> id = new ArrayList<String>();
	ArrayList<String> type = new ArrayList<String>();
	ArrayList<String> title = new ArrayList<String>();
	ArrayList<String> path = new ArrayList<String>();
	ArrayList<String> image = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorities);
		
		
		////////////////////////common in all activity////////////////////////////////		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels)*0.75);
		
		slidingPanel = (RelativeLayout) findViewById(R.id.slidingpanel);
		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel.getLayoutParams();
		slidingPanelParameters.width = metrics.widthPixels;
		slidingPanel.setLayoutParams(slidingPanelParameters);
		///////////////////////////////////code for menu////////////////////////////////////////////////////		
		final TableRow tbsearch = (TableRow) findViewById(R.id.ViewSearch);
		final TableRow tbEventcalender = (TableRow) findViewById(R.id.ViewEventCalender);
		final  TableRow tbeditorial = (TableRow) findViewById(R.id.ViewEditorial);
		final TableRow tbpromotion = (TableRow) findViewById(R.id.ViewPromo);
		final  TableRow tbyourfav = (TableRow) findViewById(R.id.ViewFavorites);
		final TableRow tbDiscover = (TableRow) findViewById(R.id.ViewDiscover);
		final  TableRow tbsetting = (TableRow) findViewById(R.id.ViewSettings);
		ImageView imguseredit= (ImageView) findViewById(R.id.imageUserpic);
		ImageView imghomepage = (ImageView) findViewById(R.id.imageHomeButton);
		
		
		tbsearch.setEnabled(false);
		tbEventcalender.setEnabled(false);
		tbeditorial.setEnabled(false);
		tbpromotion.setEnabled(false);
		tbyourfav.setEnabled(false);
		tbDiscover.setEnabled(false);
		tbsetting.setEnabled(false);
		
		
		  tbsearch.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(Favorities.this, QuickSearchactivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
		}
	});
	   
	   tbsetting.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(Favorities.this, SettingActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
			
		}
	});
	   tbyourfav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Favorities.this, Favorities.class);
				startActivity(i);
				overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
				finish();
				
				
			}
		});
		
	   tbEventcalender.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent i= new Intent(Favorities.this, EventCalender.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
		}
	});
	   
	   tbpromotion.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(Favorities.this, Promotions.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
		}
	});
	   tbeditorial.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(Favorities.this, Editorials.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
			
		}
	});
	   
	   tbDiscover.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(Favorities.this, SearchResultMap.class);
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
				Editcheck.fromwhere= "Favorities";
				//Toast.makeText(Favorities.this, Editcheck.fromwhere, Toast.LENGTH_LONG).show();
				Intent i= new Intent(Favorities.this, EditProfile.class);
				startActivity(i);
				finish();
				
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
Toast.makeText(Favorities.this, "Picture not found",Toast.LENGTH_SHORT).show();
}
Database db = new Database(Favorities.this);
ArrayList<User> details = new ArrayList<User>();
details= db.getprofileData();
for(User u: details) {
nameinMenu.setText(u.getName());
emailinMenu.setText(u.getEmail());
}
//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////	   

		
		//Slide the Panel	
		menuViewButton = (ImageView) findViewById(R.id.imagemenuopen);
		
		menuViewButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
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
		
		imghomepage.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(Favorities.this, HomePage.class);
		startActivity(i);
		finish();
		
		}
		});
		
		
/////////////////////////////////////code for activity////////////////////////////////////////
ListView listoffav= (ListView) findViewById(R.id.listoffavorites);
   boolean check= db.checkDatabasefav();
  if(check == true){

   ArrayList<FavoriteItem> favitem = db.getFavoritesData();
   
   ArrayList<String> id =new ArrayList<String>();
   ArrayList<String> title = new ArrayList<String>();
   ArrayList<String> image = new ArrayList<String>();
   ArrayList<String> type = new ArrayList<String>();
   
   
   for(FavoriteItem fav : favitem){
	   
	   id.add(fav.getId());
	   title.add(fav.getTitle());
	   image.add(fav.getType());
	   type.add(fav.getImage());
	   
	   
   }
   Log.i("asdfsdfsdfsdf","sdfsdfsdfsf");
   FavoritiesAdapter fadt = new FavoritiesAdapter(Favorities.this, id,title, image, type);
   listoffav.setAdapter(fadt);
   
  }
  else
  {
	  
	  String[] a={"No Favorites"};
	  ArrayAdapter adt= new ArrayAdapter(Favorities.this, android.R.layout.simple_list_item_1, a);
	  listoffav.setAdapter(adt);
  }
	 
	 
 } 
 
 
 

	

	



	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorities, menu);
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

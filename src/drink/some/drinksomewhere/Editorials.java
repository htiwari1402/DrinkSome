package drink.some.drinksomewhere;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.drinksomewhere.EditProfile;
import com.example.drinksomewhere.EventCalender;
import com.example.drinksomewhere.HAdapter;
import com.example.drinksomewhere.HomePage;
import com.example.drinksomewhere.QuickSearchactivity;
import com.example.drinksomewhere.SearchResultMap;
import com.example.drinksomewhere.ServiceHandler;
import com.example.drinksomewhere.SettingActivity;
import com.example.drinksomewhere.MenuAnimation.CollapseAnimation;
import com.example.drinksomewhere.MenuAnimation.Editcheck;
import com.example.drinksomewhere.MenuAnimation.ExpandAnimation;

import database.Database;
import database.User;

import eventitemDesigns.EditorialHeadListAdpater;
import eventitemDesigns.EditorialListAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Editorials extends Activity {
	
////////////////common in all activity////////////////////
	
private RelativeLayout slidingPanel;
private boolean isExpanded;
private DisplayMetrics metrics;	
private ListView listView;
private RelativeLayout headerPanel;
private RelativeLayout menuPanel;
private int panelWidth;
private ImageView menuViewButton;
ListView listhead;
AutoCompleteTextView autoeditorial;
ProgressDialog progress=null;

FrameLayout.LayoutParams menuPanelParameters;
FrameLayout.LayoutParams slidingPanelParameters;
LinearLayout.LayoutParams headerPanelParameters ;
LinearLayout.LayoutParams listViewParameters;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editorials);
///////////////////////common in all activity////////////////////////////////	
		
		progress=ProgressDialog.show(Editorials.this, "", "Loading Data...",true);
metrics = new DisplayMetrics();
getWindowManager().getDefaultDisplay().getMetrics(metrics);
panelWidth = (int) ((metrics.widthPixels)*0.75);
 autoeditorial = (AutoCompleteTextView) findViewById(R.id.autoeditorial);
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
menuViewButton = (ImageView) findViewById(R.id.imagemenuopen);
ImageView imgHomebutton = (ImageView)findViewById(R.id.imageHomeButton);


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
		Intent i = new Intent(Editorials.this, QuickSearchactivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
		
		
	}
});
 
 tbsetting.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(Editorials.this, SettingActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
		finish();
		
		
	}
});
 tbyourfav.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(Editorials.this, Favorities.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			
			finish();
			
		}
	});
	
 tbEventcalender.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Intent i= new Intent(Editorials.this, EventCalender.class);
		startActivity(i);
		overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
		finish();
		
	}
});
 
 tbpromotion.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i= new Intent(Editorials.this, Promotions.class);
		startActivity(i);
		overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
		finish();
		
	}
});
 tbeditorial.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i= new Intent(Editorials.this, Editorials.class);
		startActivity(i);
		overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
		finish();
		
		
	}
});
 
 tbDiscover.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i= new Intent(Editorials.this, SearchResultMap.class);
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
			Editcheck.fromwhere= "Editorials";
			//Toast.makeText(Editorials.this, Editcheck.fromwhere, Toast.LENGTH_LONG).show();
			Intent i= new Intent(Editorials.this, EditProfile.class);
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
Toast.makeText(Editorials.this, "Picture not found",Toast.LENGTH_SHORT).show();
}
Database db = new Database(Editorials.this);
ArrayList<User> details = new ArrayList<User>();
details= db.getprofileData();
for(User u: details) {
nameinMenu.setText(u.getName());
emailinMenu.setText(u.getEmail());
}
//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////	   



imgHomebutton.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Intent i = new Intent(Editorials.this, HomePage.class);
		startActivity(i);
		finish();
		
	}
});

imguseredit.setOnClickListener(new OnClickListener() {

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	Editcheck.fromwhere= "HomePage";
	//Toast.makeText(Editorials.this, Editcheck.fromwhere, Toast.LENGTH_LONG).show();
	Intent i= new Intent(Editorials.this, EditProfile.class);
	startActivity(i);
	
	
}
});



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
//////////////////////////////////////////////////////////////////////////
 autoeditorial.setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		String name= autoeditorial.getText().toString();
		
		Intent i= new Intent(Editorials.this, Editorialsdetails.class);
		i.putExtra("from", "top");
		i.putExtra("name", name);
		startActivity(i);
	}
});
 listhead = (ListView) findViewById(R.id.listEditorials);
 
 final ListView list = (ListView) findViewById(R.id.listeditorialsyears);
 final TextView yearheading = (TextView) findViewById(R.id.textEditorialYearHead);
 
 
String  year[]=new String[5];

 Calendar calendar = Calendar.getInstance();
 int currentyear = calendar.get(Calendar.YEAR);
 yearheading.setText(""+currentyear);
 
 new Loaddata().execute("http://drinksomewhere.com/wb_services/articles.php?year="+currentyear);
	
   
  year[0] = ""+currentyear;
  
  for(int i=1;i<5;i++)
  {
	  currentyear= currentyear-1;
	  year[i]= ""+currentyear;
	  
	  
  }
 

 EditorialListAdapter eadt= new EditorialListAdapter(Editorials.this, year);
 list.setAdapter(eadt);
 
 list.setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		int selectedyear= Integer.parseInt(list.getItemAtPosition(arg2).toString());
		yearheading.setText(""+selectedyear);
		new Loaddata().execute("http://drinksomewhere.com/wb_services/articles.php?year="+selectedyear);
		
	}
});
 
 
}

	  private class Loaddata extends AsyncTask<String,Void , ArrayList<String[]>> {
	        ImageView bmImage;
	 
	        public Loaddata() {
	           
	        }
	 
	        protected ArrayList<String[]> doInBackground(String... urls) {
	        	
	        		ArrayList<String[]> list=new ArrayList<String[]>();
	        		try{
	    			 ServiceHandler sh = new ServiceHandler();

	    				// Making a request to url and getting response
	    			String jsonStr = sh.makeServiceCall(urls[0], ServiceHandler.GET);
	    				

	    				Log.d("Response: ", "> " + jsonStr);

	    				if (jsonStr != null) {
	    					try {
	    						
	    						JSONArray jsonarray = new JSONArray(jsonStr);
	    						
	    						
	    						//Toast.makeText(MainActivity.this, "ojectcreated", Toast.LENGTH_SHORT).show();
	    						// Getting JSON Array node

	    						// looping through All Contacts
	    						String[] id =new String[jsonarray.length()];
	    						String[] path= new String[jsonarray.length()];
	    						String[] image= new String[jsonarray.length()];
	    						String[] date= new String[jsonarray.length()];
	    						String[] name= new String[jsonarray.length()];
	    						
	    						for (int i = 0; i < jsonarray.length(); i++) {
	    							JSONObject jobject = jsonarray.getJSONObject(i);
	    							 id[i]= jobject.getString("id");
	    							 path[i]= jobject.getString("image_folder_path");
	    							 image[i]=jobject.getString("image_filename");
	    							 date[i]= jobject.getString("article_date");
	    							 name[i]=jobject.getString("article_name");
	    							 }
	    						
	    						
	    						list.add(0, id);
	    						list.add(1,path);
	    						list.add(2,image);
	    						list.add(3,date);
	    						list.add(4,name);
	    						//Toast.makeText(MainActivity.this, "abc"+list.toString(), Toast.LENGTH_LONG).show();
	    					} catch (JSONException e) {
	    						AlertDialog.Builder ab= new AlertDialog.Builder(Editorials.this);
	    						ab.setTitle("DrinkSomeWhere");
	    						ab.setMessage("Unable to fetch data, Please check your network connectivity.");
	    						ab.show();	}
	    				} else {
	    					Log.e("ServiceHandler", "Couldn't get any data from the url");
	    				}
	    				
	    				
	    				
	    		  

	    		  
	    		}
	    		catch(Exception e)
	    		{
	    			AlertDialog.Builder ab= new AlertDialog.Builder(Editorials.this);
					ab.setTitle("DrinkSomeWhere");
					ab.setMessage("Unable to fetch data, Please check your network connectivity.");
					ab.show();	}
				return list;
	    	 
	        }
	 
	        
	        @Override
	        protected void onPostExecute(ArrayList<String[]> result) {
	        // TODO Auto-generated method stub
	        	try {
	        		
	        		String[] id= result.get(0);
	        		String[] path= result.get(1);
	        		String[] image=result.get(2);
	        		String[] date=result.get(3);
	        		String[] name= result.get(4);
	        		
	        		
	        		ArrayAdapter adt= new ArrayAdapter(Editorials.this, android.R.layout.simple_list_item_1, name);
	        		autoeditorial.setAdapter(adt);
	        		
	        		EditorialHeadListAdpater hadt= new EditorialHeadListAdpater(Editorials.this, id, path, image,date);
	        		
	        		listhead.setAdapter(hadt);
	        		
	        		progress.dismiss();
	        		
	        		
	        	}
	        	catch(Exception e)
	        	{Toast.makeText(Editorials.this, e.toString(), Toast.LENGTH_LONG).show();}
	        
	        
	        
	      
	    }
	 
	
	        }

		
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editorials, menu);
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

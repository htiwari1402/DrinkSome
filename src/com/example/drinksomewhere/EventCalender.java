package com.example.drinksomewhere;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.drinksomewhere.MenuAnimation.CollapseAnimation;
import com.example.drinksomewhere.MenuAnimation.Editcheck;
import com.example.drinksomewhere.MenuAnimation.ExpandAnimation;
import com.mustafaferhan.MFCalendarView;
import com.mustafaferhan.onMFCalendarViewListener;


import database.Database;
import database.User;
import drink.some.drinksomewhere.Editorials;
import drink.some.drinksomewhere.Favorities;
import drink.some.drinksomewhere.Promotions;
import drink.some.drinksomewhere.R;

import eventitemDesigns.EventAdapter;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class EventCalender extends Activity {

////////////////common in all activity////////////////////
	
private RelativeLayout slidingPanel;
private boolean isExpanded;
private DisplayMetrics metrics;	
private ListView listView;
private RelativeLayout headerPanel;
private RelativeLayout menuPanel;
private int panelWidth;
private ImageView menuViewButton;
MFCalendarView m;
ListView eventList;

FrameLayout.LayoutParams menuPanelParameters;
FrameLayout.LayoutParams slidingPanelParameters;
LinearLayout.LayoutParams headerPanelParameters ;
LinearLayout.LayoutParams listViewParameters;
ProgressDialog progress=null;
//////////////////////////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_calender);
		
		
		 progress= ProgressDialog.show(EventCalender.this,"","Loading Data...", true);
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
		   ImageView imguseredit= (ImageView) findViewById(R.id.imageUserpic);
		   eventList  = (ListView) findViewById(R.id.listViewEvents);
		   ImageView imgleft= (ImageView) findViewById(R.id.imgclenderleft);
		   ImageView imgright= (ImageView) findViewById(R.id.imgcalenderright);
		   final TextView calhead= (TextView) findViewById(R.id.textcalenderhead);
		   
		
		   
		   
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
			Intent i = new Intent(EventCalender.this, QuickSearchactivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
		}
	});
	   
	   tbsetting.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(EventCalender.this, SettingActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
			
		}
	});
	   tbyourfav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(EventCalender.this, Favorities.class);
				startActivity(i);
				overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
				finish();
				
				
			}
		});
		
	   tbEventcalender.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent i= new Intent(EventCalender.this, EventCalender.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
		}
	});
	   
	   tbpromotion.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(EventCalender.this, Promotions.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
		}
	});
	   tbeditorial.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(EventCalender.this, Editorials.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
			
		}
	});
	   
	   tbDiscover.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(EventCalender.this, SearchResultMap.class);
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
				Editcheck.fromwhere= "EventCalender";
				//Toast.makeText(EventCalender.this, Editcheck.fromwhere, Toast.LENGTH_LONG).show();
				Intent i= new Intent(EventCalender.this, EditProfile.class);
				startActivity(i);
				finish();
				
				
			}
		});
	   
	   
		   
		   
	/////////////////////////////////////////////////////////////////////////////////
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
Toast.makeText(EventCalender.this, "Picture not found",Toast.LENGTH_SHORT).show();
}
Database db = new Database(EventCalender.this);
ArrayList<User> details = new ArrayList<User>();
details= db.getprofileData();
for(User u: details) {
nameinMenu.setText(u.getName());
emailinMenu.setText(u.getEmail());
}
//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////	   


		ImageView imgHomeButton= (ImageView) findViewById(R.id.imageHomeButton);
		Button editprofilebtn= (Button) findViewById(R.id.editprofilebutton);
		menuViewButton = (ImageView) findViewById(R.id.imagemenuopen);
		
	imgHomeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(EventCalender.this, HomePage.class);
				startActivity(i);
				finish();
				
			}
		});
	
	
	imguseredit.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Editcheck.fromwhere= "EventCalender";
		
			Intent i= new Intent(EventCalender.this, EditProfile.class);
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
		String currentdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		final MySlidingDrawer sd= (MySlidingDrawer) findViewById(R.id.slidingDrawer1);
		sd.open();
		
		
		new loadDataofCurrentDate().execute("http://drinksomewhere.com/wb_services/dateWiseEvents.php?date="+currentdate);
		
	
	
	
	
	m = (MFCalendarView) findViewById(R.id.calendarView1);
	
	calhead.setText(m.getdate());
	new loadDate().execute("http://drinksomewhere.com/wb_services/eventDates.php");
	
	   imgleft.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					m.setPreviousMonth();
					m.refreshCalendar();
					calhead.setText(m.getdate());
				
				}
			});
	   imgright.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			m.setNextMonth();
			m.refreshCalendar();
			calhead.setText(m.getdate());
		}
	});
	   
	
	m.setOnCalendarViewListener(new onMFCalendarViewListener() {
		
		@Override
		public void onDisplayedMonthChanged(int month, int year, String monthStr) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onDateChanged(String date) {
			// TODO Auto-generated method stub
			new loadDataofDate().execute("http://drinksomewhere.com/wb_services/dateWiseEvents.php?date="+date);
		   sd.close();
            		
		}
	});
	
	
}
	
	public class loadDate extends AsyncTask<String, Void, ArrayList<String>> {
		
		
		public loadDate(){}

		@Override
		protected ArrayList<String> doInBackground(String... params) {
			// TODO Auto-generated method stub
			ArrayList<String> datelist = new ArrayList<String>();
			try{
   			 ServiceHandler sh = new ServiceHandler();

   				// Making a request to url and getting response
   			String jsonStr = sh.makeServiceCall(params[0], ServiceHandler.GET);
   				

   				Log.d("Response: ", "> " + jsonStr);

   				if (jsonStr != null) {
   					try {
   						
   						JSONArray jsonarray = new JSONArray(jsonStr);
   					
   						
   						for (int i = 0; i < jsonarray.length(); i++) {
   							JSONObject jobject = jsonarray.getJSONObject(i);
   							
   							datelist.add(jobject.getString("event_date"));
   				
   							 }
   						
   						
   						
   						//Toast.makeText(MainActivity.this, "abc"+list.toString(), Toast.LENGTH_LONG).show();
   					} catch (JSONException e) {
   						AlertDialog.Builder ab= new AlertDialog.Builder(EventCalender.this);
						ab.setTitle("DrinkSomeWhere");
						ab.setMessage("Unable to fetch data, Please check your network connectivity.");
						ab.show();
   					}
   				} else {
   					Log.e("ServiceHandler", "Couldn't get any data from the url");
   				}
   				
   				
   				
   		  

   		  
   		}
   		catch(Exception e)
   		{
   			AlertDialog.Builder ab= new AlertDialog.Builder(EventCalender.this);
			ab.setTitle("DrinkSomeWhere");
			ab.setMessage("Unable to fetch data, Please check your network connectivity.");
			ab.show();	
   		}
			return datelist;
   	
		}
		
		
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// TODO Auto-generated method stub
			
			
		   
			m.setEvents(result);
			progress.dismiss();
		}
		
		
		
		
		
		
	}
	
	
	
public class loadDataofDate extends AsyncTask<String, Void, ArrayList<String[]>> {
		
		
		public loadDataofDate(){}

		@Override
		protected ArrayList<String[]> doInBackground(String... params) {
			// TODO Auto-generated method stub
			ArrayList<String[]> list = new ArrayList<String[]>();
			try{
   			 ServiceHandler sh = new ServiceHandler();

   				// Making a request to url and getting response
   			String jsonStr = sh.makeServiceCall(params[0], ServiceHandler.GET);
   				

   				Log.d("Response: ", "> " + jsonStr);

   				if (jsonStr != null) {
   					try {
   						
   						JSONArray jsonarray = new JSONArray(jsonStr);
   					     String title[]= new String[jsonarray.length()];
   					     String starttime[]= new String[jsonarray.length()];
   					     String endtime[]= new String[jsonarray.length()];
   					     String path[]= new String[jsonarray.length()];
   					     String venue[]= new String[jsonarray.length()];
   					     String id[]=new String[jsonarray.length()];
   					  String image[]=new String[jsonarray.length()];
   						
   						for (int i = 0; i < jsonarray.length(); i++) {
   							JSONObject jobject = jsonarray.getJSONObject(i);
   							
   							title[i]= jobject.getString("title");
   							starttime[i]= jobject.getString("event_time");
   							endtime[i]= jobject.getString("event_time");
   							path[i]= jobject.getString("image_folder_path");
   							venue[i]= jobject.getString("venue");
   							id[i]= jobject.getString("id");
   							image[i]= jobject.getString("image_thumbnail_filename");
   						
   				
   							 }
   						
   						list.add(0,id);
   						list.add(1,title);
   						list.add(2,venue);
   						list.add(3,starttime);
   						list.add(4,endtime);
   						list.add(5,path);
   						list.add(6,image);
   						
   						//Toast.makeText(MainActivity.this, "abc"+list.toString(), Toast.LENGTH_LONG).show();
   					} catch (JSONException e) {
   					Toast.makeText(EventCalender.this, e.toString(), Toast.LENGTH_LONG).show();
   					}
   				} else {
   					Log.e("ServiceHandler", "Couldn't get any data from the url");
   				}
   				
   				
   				
   		  

   		  
   		}
   		catch(Exception e)
   		{
   		   Toast.makeText(EventCalender.this, e.toString(), Toast.LENGTH_LONG).show();	
   		}
			return list;
   	
		}
		
		
		
		@Override
		protected void onPostExecute(ArrayList<String[]> result) {
			// TODO Auto-generated method stub
		    
		   String id[]= result.get(0);
		   String title[]= result.get(1);
		   String venue[]= result.get(2);
		   String starttime[]= result.get(3);
		   String endtime[]= result.get(4);
		   String path[]= result.get(5);
		   String image[]= result.get(6);
		   
		  
		   
		   
		   EventAdapter eadt= new EventAdapter(EventCalender.this, id,title, venue, starttime,endtime,path, image);
		   
		  eventList.setAdapter(eadt);
		   
		   
			
		}
		
		
		
		
		
		
	}
	
	
	
	
public class loadDataofCurrentDate extends AsyncTask<String, Void, ArrayList<String[]>> {
	
	
	public loadDataofCurrentDate(){}

	@Override
	protected ArrayList<String[]> doInBackground(String... params) {
		// TODO Auto-generated method stub
		ArrayList<String[]> list = new ArrayList<String[]>();
		try{
			 ServiceHandler sh = new ServiceHandler();

				// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(params[0], ServiceHandler.GET);
				

				Log.d("Response: ", "> " + jsonStr);

				if (jsonStr != null) {
					try {
						
						JSONArray jsonarray = new JSONArray(jsonStr);
					     String title[]= new String[jsonarray.length()];
					     String starttime[]= new String[jsonarray.length()];
					     String endtime[]= new String[jsonarray.length()];
					     String path[]= new String[jsonarray.length()];
					     String venue[]= new String[jsonarray.length()];
					     String id[]=new String[jsonarray.length()];
					  String image[]=new String[jsonarray.length()];
						
						for (int i = 0; i < jsonarray.length(); i++) {
							JSONObject jobject = jsonarray.getJSONObject(i);
							
							title[i]= jobject.getString("title");
							starttime[i]= jobject.getString("event_time");
							endtime[i]= jobject.getString("event_time");
							path[i]= jobject.getString("image_folder_path");
							venue[i]= jobject.getString("venue");
							id[i]= jobject.getString("id");
							image[i]= jobject.getString("image_thumbnail_filename");
						
				
							 }
						
						list.add(0,id);
						list.add(1,title);
						list.add(2,venue);
						list.add(3,starttime);
						list.add(4,endtime);
						list.add(5,path);
						list.add(6,image);
						
						//Toast.makeText(MainActivity.this, "abc"+list.toString(), Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
					Toast.makeText(EventCalender.this, e.toString(), Toast.LENGTH_LONG).show();
					}
				} else {
					Log.e("ServiceHandler", "Couldn't get any data from the url");
				}
				
				
				
		  

		  
		}
		catch(Exception e)
		{
		   Toast.makeText(EventCalender.this, e.toString(), Toast.LENGTH_LONG).show();	
		}
		return list;
	
	}
	
	
	
	@Override
	protected void onPostExecute(ArrayList<String[]> result) {
		// TODO Auto-generated method stub
	    
	   String id[]= result.get(0);
	   String title[]= result.get(1);
	   String venue[]= result.get(2);
	   String starttime[]= result.get(3);
	   String endtime[]= result.get(4);
	   String path[]= result.get(5);
	   String image[]= result.get(6);
	   
	   
	   EventAdapter eadt= new EventAdapter(EventCalender.this, id,title, venue, starttime,endtime,path, image);
	   
	  eventList.setAdapter(eadt);
	   
	   
		
	}
	
	
	
	
	
	
}

	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_calender, menu);
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

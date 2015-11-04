package com.example.drinksomewhere;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import json.JSONParser;

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
import android.os.StrictMode;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class QuickSearchactivity extends Activity {
	
	////////////////common in all activity////////////////////
	
	private RelativeLayout slidingPanel;
	private boolean isExpanded;
	private DisplayMetrics metrics;	
	private ListView listView;
	private RelativeLayout headerPanel;
	private RelativeLayout menuPanel;
	private int panelWidth;
	private ImageView menuViewButton;
	AutoCompleteTextView t;
	AutoCompleteTextView toutlet;
	AutoCompleteTextView tlocation;
	AutoCompleteTextView toccassion;
	boolean checkseektouched = false;
	
	
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters ;
	LinearLayout.LayoutParams listViewParameters;
	JSONArray jsonarray = null;
	String[] list;
//////////////////////////////////////////////////////////////////////

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quick_searchactivity);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
////////////////////////common in every code////////////////////////////////////////		
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
		         
		   tbsearch.setEnabled(false);
		   tbEventcalender.setEnabled(false);
		   tbeditorial.setEnabled(false);
		   tbpromotion.setEnabled(false);
		   tbyourfav.setEnabled(false);
		   tbDiscover.setEnabled(false);
		   tbsetting.setEnabled(false);
		
		menuViewButton = (ImageView) findViewById(R.id.imagemenuopen);
		ImageView imghomepage= (ImageView) findViewById(R.id.imageHomeButton);
		
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
     
      imghomepage.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent i = new Intent(QuickSearchactivity.this, HomePage.class);
			startActivity(i);
			finish();
			
			
		}
	});
	
		   
      tbsearch.setOnClickListener(new OnClickListener() {
  		
  		@Override
  		public void onClick(View v) {
  			// TODO Auto-generated method stub
  			Intent i = new Intent(QuickSearchactivity.this, QuickSearchactivity.class);
  			startActivity(i);
  			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
  			finish();
  			
  		}
  	});
  	   
  	   tbsetting.setOnClickListener(new OnClickListener() {
  		
  		@Override
  		public void onClick(View v) {
  			// TODO Auto-generated method stub
  			Intent i = new Intent(QuickSearchactivity.this, SettingActivity.class);
  			startActivity(i);
  			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
  			finish();
  			
  			
  		}
  	});
  	   tbyourfav.setOnClickListener(new OnClickListener() {
  			
  			@Override
  			public void onClick(View v) {
  				// TODO Auto-generated method stub
  				Intent i = new Intent(QuickSearchactivity.this, Favorities.class);
  				startActivity(i);
  				overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
  				finish();
  				
  				
  			}
  		});
  		
  	   tbEventcalender.setOnClickListener(new OnClickListener() {
  		
  		@Override
  		public void onClick(View v) {
  			// TODO Auto-generated method stub
  			
  			Intent i= new Intent(QuickSearchactivity.this, EventCalender.class);
  			startActivity(i);
  			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
  			finish();
  			
  		}
  	});
  	   
  	   tbpromotion.setOnClickListener(new OnClickListener() {
  		
  		@Override
  		public void onClick(View v) {
  			// TODO Auto-generated method stub
  			Intent i= new Intent(QuickSearchactivity.this, Promotions.class);
  			startActivity(i);
  			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
  			finish();
  			
  		}
  	});
  	   tbeditorial.setOnClickListener(new OnClickListener() {
  		
  		@Override
  		public void onClick(View v) {
  			// TODO Auto-generated method stub
  			Intent i= new Intent(QuickSearchactivity.this, Editorials.class);
  			startActivity(i);
  			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
  			finish();
  			
  			
  		}
  	});
  	   
  	   tbDiscover.setOnClickListener(new OnClickListener() {
  		
  		@Override
  		public void onClick(View v) {
  			// TODO Auto-generated method stub
  			Intent i= new Intent(QuickSearchactivity.this, SearchResultMap.class);
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
  				Editcheck.fromwhere= "QuickSearchactivity";
  				//Toast.makeText(QuickSearchactivity.this, Editcheck.fromwhere, Toast.LENGTH_LONG).show();
  				Intent i= new Intent(QuickSearchactivity.this, EditProfile.class);
  				startActivity(i);
  				finish();
  				
  			}
  		});
  	   
  	 
  	   
  	   //ImageView imgsearchtop= (ImageView) findViewById(R.id.imageQuicksearchTop);
  	/*   imgsearchtop.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String title= t.getText().toString();
			Intent i = new Intent(QuickSearchactivity.this, SearchResultMap.class);
			i.putExtra("Searchfrom", "top");
			i.putExtra("title", title);
			startActivity(i);
			
		}
	});*/
  	   
  	   
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
Toast.makeText(QuickSearchactivity.this, "Picture not found",Toast.LENGTH_SHORT).show();
}
Database db = new Database(QuickSearchactivity.this);
ArrayList<User> details = new ArrayList<User>();
details= db.getprofileData();
for(User u: details) {
nameinMenu.setText(u.getName());
emailinMenu.setText(u.getEmail());
}
//////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////	   
		t =(AutoCompleteTextView) findViewById(R.id.txtquicksearchTop);
		toutlet =(AutoCompleteTextView) findViewById(R.id.textsearchoutlet);
		tlocation =(AutoCompleteTextView) findViewById(R.id.txtsearchlocation);
		toccassion =(AutoCompleteTextView) findViewById(R.id.textsearchoccassion);
		ImageView quicksearch = (ImageView) findViewById(R.id.imagequickserch);
		
		  t.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					String title= t.getText().toString();
					Intent i = new Intent(QuickSearchactivity.this, SearchResultMap.class);
					i.putExtra("Searchfrom", "top");
					i.putExtra("title", title);
					startActivity(i);
					
				}
			});
		
		
		
		
		final TextView tbjt= (TextView) findViewById(R.id.textbjt);
		new Loadsearchdata().execute("http://drinksomewhere.com/wb_services/keyword.php");
		new LoadOccassiondata().execute("http://drinksomewhere.com/wb_services/alloccasions.php");
		new LoadOutletdata().execute("http://drinksomewhere.com/wb_services/allOutlets.php");
		new LoadLocationdata().execute("http://drinksomewhere.com/wb_services/allLocation.php");
		final Spinner spinner = (Spinner) findViewById(R.id.spinner);
	    SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar1);
		
	    seekbar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkseektouched = true;
			}
		});
		   

		Button searchbtn= (Button) findViewById(R.id.buttonsearch);
		
		
		
		searchbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		try{   
			
			
				 String budjet= tbjt.getText().toString();
				 String outlet= toutlet.getText().toString();
				 String location= tlocation.getText().toString();
				 String occasion= toccassion.getText().toString();
				 String type= spinner.getSelectedItem().toString();
				
				 if(outlet==null || outlet.equals(""))
				 {
					 outlet="Search Outlets,Bars,Pubs";
				}
				 if(location==null || location.equals(""))
				 {
					 location="All Location";
					 
				 }
				 if(occasion==null || occasion.equals(""))
				 {
					occasion="All Ocassions";
					 
				 }
					//Toast.makeText(QuickSearchactivity.this, outlet+location+occasion+type+budjet, Toast.LENGTH_LONG).show();
	
					Intent i = new Intent(QuickSearchactivity.this, SearchResultMap.class);
					i.putExtra("Searchfrom", "normal");
					i.putExtra("outlet", outlet);
					i.putExtra("location", location);
					i.putExtra("type", type);
					i.putExtra("occasion", occasion);
					i.putExtra("budget", budjet);
				
					startActivity(i);
					
				
				
				}
				catch(Exception e){}
				
				
			
				
				
			}
		});

		final TextView tmaxamount= (TextView) findViewById(R.id.textmaxamount);
		
	
	    
	    seekbar.setMax(100);
	    seekbar.setProgress(0);
	    
	    seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				tmaxamount.setText("$"+arg1);
				if(arg1<20) {
					
					tbjt.setText("$");
				}
				if(arg1>20 && arg1< 40)
				{
					tbjt.setText("$$");
					
				}
				if(arg1>40 && arg1<60) {
					
					tbjt.setText("$$$");
				}
				if(arg1>60 && arg1< 80)
				{
					tbjt.setText("$$$$");
					
				}
				if(arg1> 80)
				{
					tbjt.setText("$$$$$");
					
				}
				
				
				
				
			}
		});
	    
	   
	    quicksearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i =new Intent(QuickSearchactivity.this, SearchResultMap.class);
				i.putExtra("Searchfrom", "quicksearch");
				startActivity(i);
				
			}
		});
	    
	
		
	}
	
	
	  private class Loadsearchdata extends AsyncTask<String, Void, ArrayList<String>> {
	        
	 
	        public Loadsearchdata() {
	           
	        }
	 
	        protected ArrayList<String> doInBackground(String... urls) {
	        	ArrayList<String> list=new ArrayList<String>();
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
	    						for (int i = 0; i < jsonarray.length(); i++) {
	    							JSONObject jobject = jsonarray.getJSONObject(i);
	    							if(jobject.getString("type").equals("outlet"))
	    							{
	    						    list.add(jobject.getString("keyword"));
	    						    
	    							}
	    					
	    						}
	    						//Toast.makeText(MainActivity.this, "abc"+list.toString(), Toast.LENGTH_LONG).show();
	    					} catch (JSONException e) {
	    					Toast.makeText(QuickSearchactivity.this, e.toString(), Toast.LENGTH_LONG).show();
	    					}
	    				} else {
	    					Log.e("ServiceHandler", "Couldn't get any data from the url");
	    				}
	    				
	    				
	    				
	    		  

	    		  
	    		}
	    		catch(Exception e)
	    		{
	    		   Toast.makeText(QuickSearchactivity.this, e.toString(), Toast.LENGTH_LONG).show();	
	    		}
				return list;
	    	 
	        }
	 
	        
	        @Override
	        protected void onPostExecute(ArrayList<String> result) {
	        // TODO Auto-generated method stub
	        	try {ArrayAdapter adt = new ArrayAdapter(QuickSearchactivity.this,android.R.layout.simple_list_item_1, result);
				
				
				t.setAdapter(adt);}
	        	
	        	catch(Exception e)
	        	{Toast.makeText(QuickSearchactivity.this, e.toString(), Toast.LENGTH_LONG).show();}
	        
	        }
	        
	      
	    }
	 
	
	  private class LoadOutletdata extends AsyncTask<String, Void, ArrayList<String>> {
	        
			 
	        public LoadOutletdata() {
	           
	        }
	 
	        protected ArrayList<String> doInBackground(String... urls) {
	        	ArrayList<String> list=new ArrayList<String>();
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
	    						for (int i = 0; i < jsonarray.length(); i++) {
	    							JSONObject jobject = jsonarray.getJSONObject(i);
	    							
	    						    list.add(jobject.getString("outlet_name"));
	    						    
	    							
	    					
	    						}
	    						//Toast.makeText(MainActivity.this, "abc"+list.toString(), Toast.LENGTH_LONG).show();
	    					} catch (JSONException e) {
	    					Toast.makeText(QuickSearchactivity.this, e.toString(), Toast.LENGTH_LONG).show();
	    					}
	    				} else {
	    					Log.e("ServiceHandler", "Couldn't get any data from the url");
	    				}
	    				
	    				
	    				
	    		  

	    		  
	    		}
	    		catch(Exception e)
	    		{
	    		   Toast.makeText(QuickSearchactivity.this, e.toString(), Toast.LENGTH_LONG).show();	
	    		}
				return list;
	    	 
	        }
	 
	        
	        @Override
	        protected void onPostExecute(ArrayList<String> result) {
	        // TODO Auto-generated method stub
	        	try {ArrayAdapter adt = new ArrayAdapter(QuickSearchactivity.this,android.R.layout.simple_list_item_1, result);
				
				
				toutlet.setAdapter(adt);}
	        	
	        	catch(Exception e)
	        	{Toast.makeText(QuickSearchactivity.this, e.toString(), Toast.LENGTH_LONG).show();}
	        
	        }
	        
	      
	    }
	 
	  
	 private class LoadLocationdata extends AsyncTask<String, Void, ArrayList<String>> {
	        
			 
	        public LoadLocationdata() {
	           
	        }
	 
	        protected ArrayList<String> doInBackground(String... urls) {
	        	ArrayList<String> list=new ArrayList<String>();
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
	    						for (int i = 0; i < jsonarray.length(); i++) {
	    							JSONObject jobject = jsonarray.getJSONObject(i);
	    							
	    						    list.add(jobject.getString("location_name"));
	    						    
	    							
	    					
	    						}
	    						//Toast.makeText(MainActivity.this, "abc"+list.toString(), Toast.LENGTH_LONG).show();
	    					} catch (JSONException e) {
	    			
	    						AlertDialog.Builder ab= new AlertDialog.Builder(QuickSearchactivity.this);
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
	    			AlertDialog.Builder ab= new AlertDialog.Builder(QuickSearchactivity.this);
					ab.setTitle("DrinkSomeWhere");
					ab.setMessage("Unable to fetch data, Please check your network connectivity.");
					ab.show();	}
				return list;
	    	 
	        }
	 
	        
	        @Override
	        protected void onPostExecute(ArrayList<String> result) {
	        // TODO Auto-generated method stub
	        	try {ArrayAdapter adt = new ArrayAdapter(QuickSearchactivity.this,android.R.layout.simple_list_item_1, result);
				
				
				tlocation.setAdapter(adt);}
	        	
	        	catch(Exception e)
	        	{Toast.makeText(QuickSearchactivity.this, e.toString(), Toast.LENGTH_LONG).show();}
	        
	        }
	        
	      
	    }
	 
	  private class LoadOccassiondata extends AsyncTask<String, Void, ArrayList<String>> {
	        
			 
	        public LoadOccassiondata() {
	           
	        }
	 
	        protected ArrayList<String> doInBackground(String... urls) {
	        	ArrayList<String> list=new ArrayList<String>();
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
	    						for (int i = 0; i < jsonarray.length(); i++) {
	    							JSONObject jobject = jsonarray.getJSONObject(i);
	    							
	    						    list.add(jobject.getString("title"));
	    						    
	    							
	    					
	    						}
	    						//Toast.makeText(MainActivity.this, "abc"+list.toString(), Toast.LENGTH_LONG).show();
	    					} catch (JSONException e) {
	    					Toast.makeText(QuickSearchactivity.this, e.toString(), Toast.LENGTH_LONG).show();
	    					}
	    				} else {
	    					Log.e("ServiceHandler", "Couldn't get any data from the url");
	    				}
	    				
	    				
	    				
	    		  

	    		  
	    		}
	    		catch(Exception e)
	    		{
	    		   Toast.makeText(QuickSearchactivity.this, e.toString(), Toast.LENGTH_LONG).show();	
	    		}
				return list;
	    	 
	        }
	 
	        
	        @Override
	        protected void onPostExecute(ArrayList<String> result) {
	        // TODO Auto-generated method stub
	        	try {ArrayAdapter adt = new ArrayAdapter(QuickSearchactivity.this,android.R.layout.simple_list_item_1, result);
				
				
				toccassion.setAdapter(adt);}
	        	
	        	catch(Exception e)
	        	{Toast.makeText(QuickSearchactivity.this, e.toString(), Toast.LENGTH_LONG).show();}
	        
	        }
	        
	      
	    }
	 


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quick_searchactivity, menu);
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

package com.example.drinksomewhere;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.example.drinksomewhere.MenuAnimation.CollapseAnimation;
import com.example.drinksomewhere.MenuAnimation.Editcheck;
import com.example.drinksomewhere.MenuAnimation.ExpandAnimation;
import com.example.drinksomewhere.MenuAnimation.NavigationAdapter;
import com.example.drinksomewhere.MenuAnimation.homepageListAdapterextends;

import database.Database;
import database.User;
import drink.some.drinksomewhere.Editorials;
import drink.some.drinksomewhere.Favorities;
import drink.some.drinksomewhere.Promotions;
import drink.some.drinksomewhere.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends Activity {
	
	////////////////common in all activity////////////////////
	
	private RelativeLayout slidingPanel;
	private boolean isExpanded;
	private DisplayMetrics metrics;	
	private ListView listView;
	private RelativeLayout headerPanel;
	private RelativeLayout menuPanel;
	private int panelWidth;
	private ImageView menuViewButton;
	HorizontalListView listview;
	TextView latesttext;
	ProgressDialog progess = null;
	
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters ;
	LinearLayout.LayoutParams listViewParameters;
//////////////////////////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
////////////////////////common in all activity////////////////////////////////	
		 progess= ProgressDialog.show(HomePage.this, "","Loading Data...", true);
		boolean ckecknet= isOnline();
		if(ckecknet==false)
		{
			AlertDialog.Builder ab= new AlertDialog.Builder(HomePage.this);
			ab.setTitle("DrinkSomeWhere");
			ab.setIcon(R.drawable.ic_launcher);
			ab.setMessage("Unable to fetch Data,Please check your network connectivity");
			ab.show();
			progess.dismiss();
			
		}
		
		
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels)*0.75);
		latesttext = (TextView) findViewById(R.id.textlatestevent);
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
	   ImageView imgHomeprofilepic = (ImageView)findViewById(R.id.imageHomeProfilePic);
	   ImageView imgHomelocation = (ImageView)findViewById(R.id.imageHomeDiscover);
	   ImageView imgHomefav = (ImageView)findViewById(R.id.imageHomeFav);
	   ImageView imgHomepromo = (ImageView)findViewById(R.id.imageHomepromo);
	   ImageView imgHomesearch =(ImageView)findViewById(R.id.imageHomeSearch);
	
	   
	 
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
			imgHomeprofilepic.setImageBitmap(circleBitmap);
       }
		else{
			Toast.makeText(HomePage.this, "Picture not found",Toast.LENGTH_SHORT).show();
		}
	   Database db = new Database(HomePage.this);
	   ArrayList<User> details = new ArrayList<User>();
	   details= db.getprofileData();
	   for(User u: details) {
		nameinMenu.setText(u.getName());
	    emailinMenu.setText(u.getEmail());
	   }
//////////////////////////////////////////////////////////////////////////////////////////////////
	   
	          
	   
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
			Intent i = new Intent(HomePage.this, QuickSearchactivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
		}
	});
	   
	   tbsetting.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(HomePage.this, SettingActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
			
		}
	});
	   tbyourfav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(HomePage.this, Favorities.class);
				startActivity(i);
				overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
				finish();
				
				
			}
		});
		
	   tbEventcalender.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent i= new Intent(HomePage.this, EventCalender.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
		}
	});
	   
	   tbpromotion.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(HomePage.this, Promotions.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
		}
	});
	   tbeditorial.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(HomePage.this, Editorials.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
			
		}
	});
	   
	   tbDiscover.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(HomePage.this, SearchResultMap.class);
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
				Editcheck.fromwhere= "HomePage";
				//Toast.makeText(HomePage.this, Editcheck.fromwhere, Toast.LENGTH_LONG).show();
				Intent i= new Intent(HomePage.this, EditProfile.class);
				startActivity(i);
				finish();
				
			}
		});
	   
	ImageView imagRef= (ImageView) findViewById(R.id.imageHomeMainIcon)  ;
	imagRef.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(HomePage.this, HomePage.class);
			startActivity(i);
			finish();
		}
	});
		
///////////////////////////////////////////code for activity////////////////////////////
		

    
	//ListView l = (ListView) findViewById(R.id.listView1homepage);
	
	RelativeLayout reditorials = (RelativeLayout) findViewById(R.id.relativelayoutEditorial);
	RelativeLayout rdiscover = (RelativeLayout) findViewById(R.id.relativelayoutdiscover);
	
	reditorials.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(HomePage.this, Editorials.class);
			startActivity(i);
		}
	});
	
	rdiscover.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(HomePage.this, SearchResultMap.class );
			i.putExtra("Searchfrom", "quicksearch");
			startActivity(i);
		}
	});
	
	
	
	
	
	
	//homepageListAdapterextends hadt= new homepageListAdapterextends(HomePage.this, menu, icon);
	//l.setAdapter(hadt);
	
	/*l.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
			if(menu[arg2].equals("New Editorials")){
				
				Intent i = new Intent(HomePage.this, Editorials.class);
				startActivity(i);
			}
			if(menu[arg2].equals("Discover")) {
				
				Intent i = new Intent(HomePage.this, SearchResultMap.class );
				i.putExtra("Searchfrom", "quicksearch");
				startActivity(i);
				
			}
			
		}
	});*/
	
	

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
		
		imgHomelocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(HomePage.this, SearchResultMap.class );
				i.putExtra("Searchfrom", "quicksearch");
				startActivity(i);
				
			}
		});

		imgHomefav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(HomePage.this, Favorities.class);
				startActivity(i);
				
			}
		});
   
		imgHomepromo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stu
				Intent i = new Intent(HomePage.this, Promotions.class);
				startActivity(i);
				
			}
		});

		imgHomesearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(HomePage.this, QuickSearchactivity.class);
				startActivity(i);
				
			}
		});
		
		imgHomeprofilepic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Editcheck.fromwhere= "HomePage";
				Intent i = new Intent(HomePage.this, EditProfile.class);
				startActivity(i);
				finish();
			}
		});
	
		
	 listview = (HorizontalListView) findViewById(R.id.listview);
		//listview.setAdapter(new HAdapter(dataObjects, icons));
	
	 new Loadsearchdata().execute("http://drinksomewhere.com/wb_services/latestEvents.php");
///////////////////////end of activity////////////////////////////////		
	}
	
	  private class Loadsearchdata extends AsyncTask<String,Void , ArrayList<String[]>> {
	        ImageView bmImage;
	 
	        public Loadsearchdata() {
	        //	progess= ProgressDialog.show(HomePage.this, "","Loading Data...", true);
	        	
	           
	        }
	        
	        protected ArrayList<String[]> doInBackground(String... urls) {
	        	ArrayList<String[]> list=new ArrayList<String[]>();
	    		try{
	    			
	    			Log.d("asdasdasdasfdfs", "ooiiiiiiiii322");
	    			
	    			//latesttext.setText("LOADING LATEST EVENTS...");
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
	    						String[] title =new String[jsonarray.length()];
	    						String[] path= new String[jsonarray.length()];
	    						String[] image= new String[jsonarray.length()];
	    						String[] date= new String[jsonarray.length()];
	    						String[] venue= new String[jsonarray.length()];
	    						String[] time= new String[jsonarray.length()];
	    						
	    						for (int i = 0; i < jsonarray.length(); i++) {
	    							JSONObject jobject = jsonarray.getJSONObject(i);
	    							 title[i]= jobject.getString("title");
	    							 path[i]= jobject.getString("image_folder_path");
	    							 image[i]=jobject.getString("image_filename");
	    							 date[i]= jobject.getString("event_date");
	    							 time[i] = jobject.getString("event_time");
	    							 
	    							 }
	    						
	    						
	    						list.add(0, title);
	    						list.add(1,path);
	    						list.add(2,image);
	    						list.add(3,date);
	    						list.add(4,time);
	    						//Toast.makeText(MainActivity.this, "abc"+list.toString(), Toast.LENGTH_LONG).show();
	    					} catch (JSONException e) {
	    					Toast.makeText(HomePage.this, e.toString(), Toast.LENGTH_LONG).show();
	    					}
	    				} else {
	    					Log.e("ServiceHandler", "Couldn't get any data from the url");
	    				}
	    				
	    				
	    				
	    		  

	    		  
	    		}
	    		catch(Exception e)
	    		{
	    		   Toast.makeText(HomePage.this, e.toString(), Toast.LENGTH_LONG).show();	
	    		}
				return list;
	    	 
	        }
	 
	        
	        @Override
	        protected void onPostExecute(ArrayList<String[]> result) {
	        // TODO Auto-generated method stub
	        	try {
	        		
	        		
	        		latesttext.setText("LATEST EVENTS");
	        		String[] title= result.get(0);
	        		//Toast.makeText(HomePage.this, "  sef"+title[0], Toast.LENGTH_LONG).show();
	        		String[] path= result.get(1);
	        		String[] image=result.get(2);
	        		String[] date = result.get(3);
	        		String[] time= result.get(4);
	        		
	        		
	        		HAdapter hadt= new HAdapter(date,time, path, image);
	        		
	        		listview.setAdapter(hadt);
	        		
	        		progess.dismiss();
	        		
	        		
	        	}
	        	catch(Exception e)
	        	{
	        		latesttext.setText("NO LATEST EVENT");
	        		//Toast.makeText(HomePage.this,"adfas" +e.toString(), Toast.LENGTH_LONG).show();}
	        	}
	          }
	        
	 
	
	        }
	  
	   
	   public boolean isOnline() {
		    ConnectivityManager cm =
		        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    return netInfo != null && netInfo.isConnectedOrConnecting();
		}

		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_page, menu);
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

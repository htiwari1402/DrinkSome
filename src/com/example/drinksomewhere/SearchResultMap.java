package com.example.drinksomewhere;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.drinksomewhere.MenuAnimation.CollapseAnimation;
import com.example.drinksomewhere.MenuAnimation.Editcheck;
import com.example.drinksomewhere.MenuAnimation.ExpandAnimation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import database.Database;
import database.User;
import drink.some.drinksomewhere.Editorials;
import drink.some.drinksomewhere.Favorities;
import drink.some.drinksomewhere.Promotions;
import drink.some.drinksomewhere.R;

import eventitemDesigns.SearchResultAdapter;

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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView;
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

public class SearchResultMap extends Activity {
	
	GoogleMap googlemap;
	

	private RelativeLayout slidingPanel;
	private boolean isExpanded;
	private DisplayMetrics metrics;	
	private ListView listView;
	private RelativeLayout headerPanel;
	private RelativeLayout menuPanel;
	private int panelWidth;
	private ImageView menuViewButton;
	ListView l;
	TextView No_ofOutlet;
	AutoCompleteTextView auto;
	double currentlat, lat,lng;
	double currentlang;
	ProgressDialog progress= null;
	
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters ;
	LinearLayout.LayoutParams listViewParameters;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result_map);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
////////////////////////////////////////////////////////////////////////////////////////		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels)*0.75);
		
		slidingPanel = (RelativeLayout) findViewById(R.id.slidingpanel);
		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel.getLayoutParams();
		slidingPanelParameters.width = metrics.widthPixels;
		slidingPanel.setLayoutParams(slidingPanelParameters);
		 No_ofOutlet = (TextView)findViewById(R.id.textNo_ofOutlets);
		
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
		   
		   
/////////////////////////////////////////////////////////////////////////////////////////////////
		  progress= ProgressDialog.show(SearchResultMap.this,"","Loading Data...", true);
		   
TextView nameinMenu = (TextView) findViewById(R.id.textView1);
TextView emailinMenu = (TextView) findViewById(R.id.textView2);
File mfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "profile_drinksomewhere.jpg");
if(mfile.exists())
   {
		Bitmap picture=BitmapFactory.decodeFile(mfile.getAbsolutePath());
		Bitmap circleBitmap = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
		BitmapShader shader = new BitmapShader (picture,  TileMode.CLAMP, TileMode.CLAMP);
		Paint paint = new Paint();
		paint.setShader(shader);
		Canvas c = new Canvas(circleBitmap);
		c.drawCircle(picture.getWidth()/2, picture.getHeight()/2, picture.getWidth()/2, paint);		                
		imguseredit.setImageBitmap(circleBitmap);
	}
else
  {
        Toast.makeText(SearchResultMap.this, "Picture not found",Toast.LENGTH_SHORT).show();
  }
Database db = new Database(SearchResultMap.this);
ArrayList<User> details = new ArrayList<User>();
details= db.getprofileData();
for(User u: details) 
   {
     nameinMenu.setText(u.getName());
     emailinMenu.setText(u.getEmail());
}
//
		   
		   
		
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
					
					if(!isExpanded)
					  {
			    		isExpanded = true;   		    				        		
			        	//Expand
			    		new ExpandAnimation(slidingPanel, panelWidth,
			    	    Animation.RELATIVE_TO_SELF, 0.0f,
			    	    Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);		    			         	    
			    	  }
					else
					  {
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
				
				Intent i = new Intent(SearchResultMap.this, HomePage.class);
				startActivity(i);
				finish();
			}
		});
		
			   
		  tbsearch.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(SearchResultMap.this, QuickSearchactivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
		}
	});
	   
	   tbsetting.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(SearchResultMap.this, SettingActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
		}
	});
	   tbyourfav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SearchResultMap.this, Favorities.class);
				startActivity(i);
				overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			}
		});
		
	   tbEventcalender.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent i= new Intent(SearchResultMap.this, EventCalender.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
		}
	});
	   
	   tbpromotion.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(SearchResultMap.this, Promotions.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
		}
	});
	   tbeditorial.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(SearchResultMap.this, Editorials.class);
			startActivity(i);
			overridePendingTransition(R.anim.sliderightin, R.anim.fadeout);
			finish();
			
			
		}
	});
	   
	   tbDiscover.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(SearchResultMap.this, SearchResultMap.class);
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
				Editcheck.fromwhere= "SearchResultMap";
				Toast.makeText(SearchResultMap.this, Editcheck.fromwhere, Toast.LENGTH_LONG).show();
				Intent i= new Intent(SearchResultMap.this, EditProfile.class);
				startActivity(i);
				
				
			}
		});
	   
	   	
	  
///////////////////////////////////////////////////////////////////////////////////////	
	   auto= (AutoCompleteTextView) findViewById(R.id.autoSearchmap);
	   
	   auto.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			String title =  auto.getText().toString();
			try
			  {
			   title=URLEncoder.encode(title,"UTF-8");
			   new LoadParticularsearchdata().execute("http://drinksomewhere.com/wb_services/keywordSearch.php?keyword="+title);
			  }
			catch(Exception e){}
		}
	});
	
	   
	   RelativeLayout rmap= (RelativeLayout) findViewById(R.id.Relativemap);
	   int heightofmap = rmap.getHeight();
	   
		try{
			 googlemap = ((MapFragment) getFragmentManager().findFragmentById(
	                 R.id.fragment1)).getMap();
			 googlemap.setMyLocationEnabled(true);
		   }
		catch(Exception e){}
			
	    l= (ListView) findViewById(R.id.listSearchResult);
	    Intent i= getIntent();
		String searchfrom = i.getStringExtra("Searchfrom");
		
		if(searchfrom.equals("normal"))
		  {
			String outlet = i.getStringExtra("outlet");
		    String location = i.getStringExtra("location");
		    String occasion = i.getStringExtra("occasion");
		    String type=i.getStringExtra("type");
		    String budget= i.getStringExtra("budget");
		
	        try
	          {	
		        String o=URLEncoder.encode(outlet,"UTF-8");
		        String l= URLEncoder.encode(location,"UTF-8");
		        String t=URLEncoder.encode(type,"UTF-8");
		        String oc=URLEncoder.encode(occasion,"UTF-8");
	            String url= "http://drinksomewhere.com/wb_services/search.php?outletName="+o+"&location="+l+"&outletType="+t+"&occassion="+oc+"&budget="+budget;
			    new Loadsearchdata().execute(url);		
              }
        	catch(Exception e){}
		
		}
		if(searchfrom.equals("quicksearch")) 
		  {
			Toast.makeText(SearchResultMap.this, "Sorry, we are not near you..", Toast.LENGTH_LONG).show();
			progress.dismiss();
			
			//String url= "http://drinksomewhere.com/wb_services/locationSearch.php?lat="+currentlang+"&lng="+currentlat;
			
			//new Loadsearchdata().execute(url);
		  }
		if(searchfrom.equals("top"))
		  {
			String title = i.getStringExtra("title");
			try
			  {
			   title= URLEncoder.encode(title, "UTF-8");
			   new LoadParticularsearchdata().execute("http://drinksomewhere.com/wb_services/keywordSearch.php?keyword="+title);
			  }
			catch(Exception e){}
		  }
		
	}
	
	 private class Loadsearchdata extends AsyncTask<String,Void , ArrayList<String[]>> {
	       
	        
	        public Loadsearchdata(){}
	 
	        protected ArrayList<String[]> doInBackground(String... urls) {
	              
	        	   ArrayList<String[]> list=new ArrayList<String[]>();
	        	
	    		   try
	    		     {
	    			  ServiceHandler sh = new ServiceHandler();
	    			  String jsonStr = sh.makeServiceCall(urls[0], ServiceHandler.GET);
	    	          
	    			  if (jsonStr != null)
	    			     {
	    					try
	    					  {
	    						JSONArray jsonarray = new JSONArray(jsonStr);
	    						String[] title =new String[jsonarray.length()];
	    						String[] path= new String[jsonarray.length()];
	    						String[] image= new String[jsonarray.length()];
	    						String[] location= new String[jsonarray.length()];
	    						String[] type= new String[jsonarray.length()];
	    						String[] occasion= new String[jsonarray.length()];
	    						String[] id= new String[jsonarray.length()];
	    						String[] lat= new String[jsonarray.length()];
	    						String[] lang= new String[jsonarray.length()];
	    						
	    						for (int i = 0; i < jsonarray.length(); i++) 
	    						    {
	    							  JSONObject jobject = jsonarray.getJSONObject(i);
	    							  title[i]= jobject.getString("outlet_name");
	    						      path[i]= jobject.getString("image_folder_path");
	    							  image[i]=jobject.getString("image_thumbnail_filename");
	    							  location[i]=jobject.getString("location_name");
	    						      type[i]=jobject.getString("type");
	    					          occasion[i]=jobject.getString("occasion");
	    					          id[i]=jobject.getString("id");
	    							  lat[i]= jobject.getString("lat");
	    							  lang[i]=jobject.getString("lng");
	    						    }
	    						
	    						list.add(0,id);
	    						list.add(1, title);
	    						list.add(2, location);
	    						list.add(3, type);
	    						list.add(4, occasion);
	    						list.add(5, path);
	    						list.add(6, image);
	    						list.add(7,lat);
	    						list.add(8,lang);
	    						
	    						//Toast.makeText(MainActivity.this, "abc"+list.toString(), Toast.LENGTH_LONG).show();
	    					} catch (JSONException e) 
	    					        {
	    						      progress.dismiss();
	    						      Toast.makeText(SearchResultMap.this,"Sorry ! no data found..", Toast.LENGTH_LONG).show();
		    				          No_ofOutlet.setText(" 0 Outlets near you");
		    			            }
	    				} else 
	    				     {
	    	                  progress.dismiss();
	    					  Toast.makeText(SearchResultMap.this,"Sorry ! no data found..", Toast.LENGTH_LONG).show();
	    					  No_ofOutlet.setText(" 0 Outlets near you");
	    				     }
	    		}
	    		catch(Exception e)
	    		     {	
	    		       No_ofOutlet.setText(" 0 Outlets near you");
	    		     }
				return list;
	    	}
	 
	        
	        @Override
	        protected void onPostExecute(ArrayList<String[]> result) {
	        // TODO Auto-generated method stub
	        	try {
	        		 progress.dismiss();
	        	    String[] id= result.get(0);
	        		String[] title= result.get(1);
	        		String[] location=result.get(2);
	        		String[] type=result.get(3);
	        		String[] occasion=result.get(4);
	        		String[] path=result.get(5);
	        		String[] image=result.get(6);
	        		String[] lat= result.get(7);
	        		String[] lng= result.get(8);
	        		double[] distance =new double[id.length];
	        		try{
	        			for(int i=0;i<=id.length; i++)
	        			   {
	        				if((lat[i]!=null || lat[i].equals("")) &&(lng[i]!=null || lng[i].equals("")))
	        				  {
	        					double dlat= Double.parseDouble(lat[i]);
	        					double dlng= Double.parseDouble(lng[i]);
	        					distance[i] = (Math.sqrt(Math.pow((111.3 * (dlat - currentlat)),2) + Math.pow((71.5 * (dlng -currentlang )),2)) * 1000);
                              }
	        				}
	        		   } 
	        		catch(Exception e)
	        		     {No_ofOutlet.setText(" 0 Outlets near you");}

                    
	        		
	        		ArrayAdapter a= new ArrayAdapter(SearchResultMap.this, android.R.layout.simple_list_item_1, title);
	        		auto.setAdapter(a);
	        		SearchResultAdapter hadt= new SearchResultAdapter(SearchResultMap.this, id, title, location, type, occasion, path, image,distance);
	        		l.setAdapter(hadt);
	        		No_ofOutlet.setText(id.length+" Outlets near you");
	        		
	        		for(int i=0; i<=lat.length;i ++)
	        		   {
	        			 try{
	        			     MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(lat[i]), Double.parseDouble(lng[i]))).title(title[i]);
	        			     marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marklogo));
	        			     googlemap.addMarker(marker);
	        			    }
	        			 catch(Exception e){}
	        			 CameraPosition cameraPosition = new CameraPosition.Builder().target(
	        		                new LatLng(Double.parseDouble(lat[0]), Double.parseDouble(lng[0]))).zoom(12).build();
	        		     googlemap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	        		    }
	            }
	        	catch(Exception e)
	        	{Toast.makeText(SearchResultMap.this,"Sorry ! no data found..", Toast.LENGTH_LONG).show();
				}
	        }
	     }
	
	 private class LoadParticularsearchdata extends AsyncTask<String,Void , ArrayList<String[]>> {
	        ImageView bmImage;
	 
	        public LoadParticularsearchdata() {}
	 
	        protected ArrayList<String[]> doInBackground(String... urls) {
	        	ArrayList<String[]> list=new ArrayList<String[]>();
	    		try{
	    			ServiceHandler sh = new ServiceHandler();
	    			String jsonStr = null;
	    		    try
	    		      {
	    			   jsonStr = sh.makeServiceCall(urls[0], ServiceHandler.GET);
	    		      } 
	    		    catch(Exception e){Toast.makeText(SearchResultMap.this,"Sorry ! no data found..", Toast.LENGTH_LONG).show();}
				    if (jsonStr != null) 
				       {
	    				try
	    				  {
	    					JSONArray jsonarray = new JSONArray(jsonStr);
	    					String[] title =new String[1];
	    				    String[] path= new String[1];
	    				    String[] image= new String[1];
	    				    String[] location= new String[1];
	    					String[] type= new String[1];
	    					String[] occasion= new String[1];
	    					String[] id= new String[1];
	    					String[] lat= new String[1];
	    					String[] lang= new String[1];
	    					int i= jsonarray.length();
	    					JSONObject jobject = jsonarray.getJSONObject(i-1);
	    				    title[0]= jobject.getString("outlet_name");
	    				    path[0]= jobject.getString("image_folder_path");
	    					image[0]=jobject.getString("image_thumbnail_filename");
	    					location[0]=jobject.getString("location_name");
	    					type[0]=jobject.getString("type");
	    					occasion[0]=jobject.getString("occasion");
	    					id[0]=jobject.getString("id");
	    					lat[0]= jobject.getString("lat");
	    					lang[0]=jobject.getString("lng");
	    							 
	    					list.add(0,id);
	    					list.add(1, title);
	    					list.add(2, location);
	    					list.add(3, type);
	    					list.add(4, occasion);
	    					list.add(5, path);
	    					list.add(6, image);
	    					list.add(7,lat);
	   						list.add(8,lang);
	    						
	    				   } 
	    				catch (JSONException e) 
	    				      {
	    						AlertDialog.Builder ab= new AlertDialog.Builder(SearchResultMap.this);
	    						ab.setTitle("DrinkSomeWhere");
	    						ab.setMessage("Unable to fetch data, Please check your network connectivity.");
	    						ab.show();	
	    					  }
	    			} 
				    else 
				        {
	    				 Toast.makeText(SearchResultMap.this,"Sorry ! no data found..", Toast.LENGTH_LONG).show();
		    			}
	    		 }
	    		catch(Exception e)
	    		     {
	    			  AlertDialog.Builder ab= new AlertDialog.Builder(SearchResultMap.this);
	    			  ab.setTitle("DrinkSomeWhere");
	    			  ab.setMessage("Unable to fetch data, Please check your network connectivity.");
	    			  ab.show();	
					 }
			return list;
	    	 
	        }
	 
	        
	        @Override
	        protected void onPostExecute(ArrayList<String[]> result) {
	        // TODO Auto-generated method stub
	        	try {
	        		 progress.dismiss();
					 String[] id= result.get(0);
	        		 String[] title= result.get(1);
	        		 String[] location=result.get(2);
	        		 String[] type=result.get(3);
	        		 String[] occasion=result.get(4);
	        	     String[] path=result.get(5);
	        		 String[] image=result.get(6);
	        		 String[] lat= result.get(7);
	        		 String[] lng= result.get(8);
	        		 double[] distance =new double[id.length];
	        		 try
	        		   {
	        			if((lat[0]!=null || lat[0].equals("")) &&(lng[0]!=null || lng[0].equals("")))
	        			  {
	        				double dlat= Double.parseDouble(lat[0]);
	        				double dlng= Double.parseDouble(lng[0]);
	        				distance[0] = (Math.sqrt(Math.pow((111.3 * (dlat - currentlat)),2) + Math.pow((71.5 * (dlng -currentlang )),2)) * 1000);
                          }
	        			} 
	        		 catch(Exception e){}
	        		 SearchResultAdapter hadt= new SearchResultAdapter(SearchResultMap.this, id, title, location, type, occasion, path, image,distance);
	        		 l.setAdapter(hadt);
	        		 No_ofOutlet.setText(id.length+" Outlets near you");
	        		 try
	        		   {
	        			googlemap.clear();
	        			MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(lat[0]), Double.parseDouble(lng[0]))).title(title[0]);
	        			marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marklogo));
	        			googlemap.addMarker(marker);
	        			CameraPosition cameraPosition = new CameraPosition.Builder().target(
	        		                new LatLng(Double.parseDouble(lat[0]), Double.parseDouble(lng[0]))).zoom(12).build();
	        		    googlemap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	        		   }
	        		 catch(Exception e){}
	        	   }
	        	     catch(Exception e)
	        	          {Toast.makeText(SearchResultMap.this, e.toString(), Toast.LENGTH_LONG).show();}
	        }
	      }
	
	

}

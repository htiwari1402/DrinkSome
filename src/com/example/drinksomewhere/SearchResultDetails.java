package com.example.drinksomewhere;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.Database;
import drink.some.drinksomewhere.EventDetails;
import drink.some.drinksomewhere.R;
import drink.some.drinksomewhere.R.id;
import drink.some.drinksomewhere.R.layout;
import drink.some.drinksomewhere.R.menu;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultDetails extends Activity {
	ImageView imgoutlet;
	TextView title;
	TextView distanceMin;
	TextView location;
	TextView type;
	TextView Occassion;
	TextView outletcategory;
	TextView outlettype;
	TextView outletoccassion;
	TextView outletlocation;
	TextView contact;
	TextView address;
	TextView operatinghrs;
	TextView pricing;
	TextView website;
	TextView email;
	TextView discription;
	TextView Header;
	String imageurl;
	ProgressDialog progress=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result_details);
		ImageView fav= (ImageView) findViewById(R.id.imageamitabh);
		ImageView imgback = (ImageView) findViewById(R.id.imagebackbutton);
		ImageView imghomepage= (ImageView) findViewById(R.id.imageHomeButton);
		 Header= (TextView)findViewById(R.id.txtTitleofDetail);
		 imgoutlet=(ImageView)findViewById(R.id.imagesearchResult);
		title= (TextView)findViewById(R.id.textSearchResult_title);
		 distanceMin= (TextView)findViewById(R.id.textSeachresult_min);
		 location= (TextView)findViewById(R.id.textSearchresultlocation);
		 type= (TextView)findViewById(R.id.textSearchresultType);
		 Occassion= (TextView)findViewById(R.id.textSearchresutlOccassion);
		 outletcategory= (TextView)findViewById(R.id.txtoutletcategory);
		 outlettype= (TextView)findViewById(R.id.txtoutlettypes);
		 outletoccassion= (TextView)findViewById(R.id.txtoccassions);
		 outletlocation= (TextView)findViewById(R.id.txtlocation);
		 contact= (TextView)findViewById(R.id.txtcontact);
		 address= (TextView)findViewById(R.id.txtaddress);
		 operatinghrs= (TextView)findViewById(R.id.txtoperatinghours);
		 pricing= (TextView)findViewById(R.id.txtpricing);
		 website= (TextView)findViewById(R.id.txtwebsite);
		 email= (TextView)findViewById(R.id.txtemail);
		 discription= (TextView) findViewById(R.id.txtdescription);
		 
	
		 Intent i=getIntent();
		 final String id= i.getStringExtra("id");
		 
		 progress= ProgressDialog.show(SearchResultDetails.this,"","Loading Data...", true);
		 
		 new loadData().execute("http://drinksomewhere.com/wb_services/outletById.php?id="+id);
		
		
		 fav.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//Database d= new Database(SearchResultDetails.this);
					String titletxt = title.getText().toString();
					//d.insertFavorites(id,titletxt, imageurl, "outlets");
					
					Database db= new Database(SearchResultDetails.this);
					if(!db.checkprevFav(titletxt))
					{
					db.insertFavorites(id,titletxt,imageurl, "outlets");
					AlertDialog.Builder ab= new AlertDialog.Builder(SearchResultDetails.this);
					ab.setTitle("DrinkSomeWhere");
					ab.setIcon(R.drawable.eventfav);
					ab.setMessage(titletxt+" successfully added to favorites");
					ab.show();
					
					
					}
					else
						if(db.checkprevFav(titletxt)) {
							
							AlertDialog.Builder ab= new AlertDialog.Builder(SearchResultDetails.this);
							ab.setTitle("DrinkSomeWhere");
							ab.setIcon(R.drawable.eventfav);
							ab.setMessage(titletxt+" already added to favorites !");
							ab.show();
							
						}
					
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				}
			});
		 
		 
		 
		 
		 
		 
		imgback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				finish();
				
				
			}
		});
		
		
		imghomepage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SearchResultDetails.this, HomePage.class);
				startActivity(i);
				finish();
				
			}
		});
		

	}
	
public class loadData extends AsyncTask<String, Void, ArrayList<String>> {
		
		
		public loadData(){}

		@Override
		protected ArrayList<String> doInBackground(String... params) {
			// TODO Auto-generated method stub
			ArrayList<String> list = new ArrayList<String>();
			try{
   			 ServiceHandler sh = new ServiceHandler();

   			Log.d("Response: ", "> " + "logdkjfgksj");
   				// Making a request to url and getting response
   			String jsonStr = sh.makeServiceCall(params[0], ServiceHandler.GET);
   				

   				Log.d("Response: ", "> " + jsonStr);
   				

   				if (jsonStr != null) {
   					try {
   						Log.d("Response: ", "> " + "log");
   						JSONArray jsonarray = new JSONArray(jsonStr);
   						Log.d("Response: ", "> " + "log");
   						
   						int i= jsonarray.length();
   						Log.d("Response: ", "> " + ""+i);
   							JSONObject jobject = jsonarray.getJSONObject(i-1);
   							Log.d("Response: ", "> " + "log");
   						list.add(0, jobject.getString("outlet_name"));
   						Log.d("Response: ", "> " + "log");
   						list.add(1,jobject.getString("location_name"));
   						Log.d("Response: ", "> " + "log");
   						list.add(2,jobject.getString("type"));
   						Log.d("Response: ", "> " + "log");
   						list.add(3,jobject.getString("outlet_category"));
   						Log.d("Response: ", "> " + "log");
   						list.add(4,jobject.getString("contact"));
   						list.add(5,jobject.getString("address"));
   						list.add(6,jobject.getString("operating_hour"));
   						list.add(7,jobject.getString("price_range"));
   						list.add(8,jobject.getString("website_url"));
   						list.add(9,jobject.getString("email"));
   						list.add(10,jobject.getString("description"));
   						list.add(11,jobject.getString("image_folder_path"));
   						list.add(12,jobject.getString("image_filename"));
   						list.add(13,jobject.getString("occasion"));
   				
   							
   						
   						
   						
   						//Toast.makeText(MainActivity.this, "abc"+list.toString(), Toast.LENGTH_LONG).show();
   					} catch (JSONException e) {
   						AlertDialog.Builder ab= new AlertDialog.Builder(SearchResultDetails.this);
						ab.setTitle("DrinkSomeWhere");
						ab.setMessage("Unable to fetch data, Please check your network connectivity.");
						ab.show();	}
   				} else {
   					Log.e("ServiceHandler", "Couldn't get any data from the url");
   				}
   				
   				
   				
   		  

   		  
   		}
   		catch(Exception e)
   		{
   			AlertDialog.Builder ab= new AlertDialog.Builder(SearchResultDetails.this);
			ab.setTitle("DrinkSomeWhere");
			ab.setMessage("Unable to fetch data, Please check your network connectivity.");
			ab.show();
			}
			return list;
   	
		}
		
		
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// TODO Auto-generated method stub
		    
		
		
				String titlename= result.get(0);
		   String locat= result.get(1);
		   String typeout=result.get(2);
		   String outletcat= result.get(3);
		   String cont= result.get(4);
		   String add= result.get(5);
		   String operthr= result.get(6);
		   String price= result.get(7);
		   String web= result.get(8);
		   String mail= result.get(9);
		   String desc= result.get(10);
		   String path= result.get(11);
		   String image= result.get(12);
		   String occas= result.get(13);
		   
		   
		   
		 
		 imgoutlet.setImageBitmap(getBitmapFromURL("http://drinksomewhere.com"+ path+image+""));
		imageurl="http://drinksomewhere.com"+ path+image+"";
		 
		 title.setText(titlename);
		 Header.setText(titlename);
		 location.setText("Location -"+locat);
		 type.setText("Type -"+typeout);
		 Occassion.setText("Occassion -"+occas);
		 outletcategory.setText(outletcat);
		 outlettype.setText(typeout);
		 outletoccassion.setText(occas);
		 outletlocation.setText(locat);
		 contact.setText(cont);
		 address.setTag(add);
		 operatinghrs.setText(operthr);
		 pricing.setText(price);
		 website.setText(web);
		 email.setText(mail);
		 discription.setText(desc);
		 
		 progress.dismiss();
		   
		   
			
		}
		public Bitmap getBitmapFromURL(String src) {
		    try {
		       
		        URL url = new URL(src);
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setDoInput(true);
		        connection.connect();
		        InputStream input = connection.getInputStream();
		        Bitmap picture=BitmapFactory.decodeStream(input);
		        Bitmap circleBitmap = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
		        BitmapShader shader = new BitmapShader (picture,  TileMode.CLAMP, TileMode.CLAMP);
		        Paint paint = new Paint();
		                paint.setShader(shader);
		                Canvas c = new Canvas(circleBitmap);
		                c.drawRoundRect(new RectF(0, 0, picture.getWidth(), picture.getHeight()),50,50,paint); 

		       
		        return circleBitmap;
		    } catch (IOException e) {
		        e.printStackTrace();
		       // Log.e("Exception",e.getMessage());
		        return null;
		    }
		}

		
		
		
		
		
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_result_details, menu);
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

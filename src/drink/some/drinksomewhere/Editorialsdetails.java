package drink.some.drinksomewhere;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.drinksomewhere.HAdapter;
import com.example.drinksomewhere.HomePage;
import com.example.drinksomewhere.ServiceHandler;

import eventitemDesigns.EditoriladetailAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Editorialsdetails extends Activity {
	
	TextView txtTitle;
	TextView txtname;
	TextView txtdate;
	WebView w;
	ImageView mainimage;
	TextView head;
	ProgressDialog progress=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editorialsdetails);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		progress= ProgressDialog.show(Editorialsdetails.this, "", "Loading Data...",true);
		
	// TextView heading= (TextView) findViewById(R.id.texteditorialdetailHead);
	// l= (ListView) findViewById(R.id.listEDitorialdetails);
		 txtTitle = (TextView) findViewById(R.id.textEDitorialdetailtitle);
		 txtname = (TextView) findViewById(R.id.textediorialDetailname);
		 txtdate = (TextView) findViewById(R.id.textEditorialdetaildate);
		 head = (TextView)findViewById(R.id.texteditorialdetailHead);
		 w= (WebView) findViewById(R.id.webVieweditorialdetails);
		// w.getSettings().setLoadWithOverviewMode(true);
		//	w.getSettings().setUseWideViewPort(true);
		 ImageView imgback= (ImageView) findViewById(R.id.imagebackbutton);
		 ImageView imghome= (ImageView)findViewById(R.id.imageHomeButton);
		 
		 
		 imgback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				
				
			}
		});
		 
		 imghome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(Editorialsdetails.this, HomePage.class);
				startActivity(i);
				finish();
			}
		});
		
		 mainimage= (ImageView) findViewById(R.id.imageEditorialdetailImage);

	 
	 
	 
	 Intent i= getIntent();
	 
	 String from = i.getStringExtra("from");
	 if(from.equals("top"))
	 {
		String name= i.getStringExtra("name");
		try{
		name = URLEncoder.encode(name,"UTF-8");
		 new Loadsearchdata().execute("http://drinksomewhere.com/wb_services/nameWiseArticles.php?name="+name);
		 
		}catch(Exception e){}
	 }
	 if(from.equals("down"))
	 
	 {
	 String id= i.getStringExtra("id");
	 
	 new Loadsearchdata().execute("http://drinksomewhere.com/wb_services/idWiseArticles.php?id="+id);
	 
	 
	 }
		
		
		
		
	}
	
	
	  private class Loadsearchdata extends AsyncTask<String,Void , ArrayList<String[]>> {
	        ImageView bmImage;
	 
	        public Loadsearchdata() {
	           
	        }
	 
	        protected ArrayList<String[]> doInBackground(String... urls) {
	        	ArrayList<String[]> list=new ArrayList<String[]>();
	    		try{
	    			 ServiceHandler sh = new ServiceHandler();

	    				// Making a request to url and getting response
	    			String jsonStr = sh.makeServiceCall(urls[0], ServiceHandler.GET);
	    		try{	
	    			//jsonStr= jsonStr.replace(".jpg", "_thumb.jpg");
	    			jsonStr= jsonStr.replace("height:","");
	    			jsonStr= jsonStr.replace("width:","");
	    		}catch(Exception e){}
	    				

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
	    						String[] name= new String[jsonarray.length()];
	    						String[] date= new String[jsonarray.length()];
	    						String[] content= new String[jsonarray.length()];
	    						String[] id= new String[jsonarray.length()];
	    						
	    						for (int i = 0; i < jsonarray.length(); i++) {
	    							JSONObject jobject = jsonarray.getJSONObject(i);
	    							 title[i]= jobject.getString("article_name");
	    							 path[i]= jobject.getString("image_folder_path");
	    							 image[i]=jobject.getString("image_filename");
	    							 name[i]=jobject.getString("author");
	    							 date[i]=jobject.getString("article_date");
	    							 content[i]=jobject.getString("content");
	    							 id[i]=jobject.getString("id");
	    							 }
	    						
	    						
	    						list.add(0, title);
	    						list.add(1,path);
	    						list.add(2,image);
	    						list.add(3,name);
	    						list.add(4,date);
	    						list.add(5,content);
	    						list.add(6,id);
	    						//Toast.makeText(MainActivity.this, "abc"+list.toString(), Toast.LENGTH_LONG).show();
	    					} catch (JSONException e) {
	    						AlertDialog.Builder ab= new AlertDialog.Builder(Editorialsdetails.this);
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
	    			AlertDialog.Builder ab= new AlertDialog.Builder(Editorialsdetails.this);
					ab.setTitle("DrinkSomeWhere");
					ab.setMessage("Unable to fetch data, Please check your network connectivity.");
					ab.show();	}
				return list;
	    	 
	        }
	 
	        
	        @Override
	        protected void onPostExecute(ArrayList<String[]> result) {
	        // TODO Auto-generated method stub
	        	try {
	        		progress.dismiss();
	        		String[] title= result.get(0);
	        		String[] path= result.get(1);
	        		String[] image=result.get(2);
	        		String[] name= result.get(3) ;
	        		String[] date= result.get(4);
	        		String[] content= result.get(5);
	        		String[] id= result.get(6);
	        		//Toast.makeText(Editorialsdetails.this, content[0], Toast.LENGTH_LONG).show();
	        		  txtTitle.setText(title[0]);
	        		  head.setText(title[0]);
	        		    txtname.setText(name[0]);
	        		    txtdate.setText(date[0]);
	        		  //  txtdiscription.setText(description[position]);
	        		    String html = content[0];
	        		    html = html.replace(".jpg","_thumb.jpg");
	        		  
	        		    final String mimeType = "text/html";
	        		    final String encoding = "UTF-8";
	        		    w.loadDataWithBaseURL("", html, mimeType, encoding, "");
	        			mainimage.setImageBitmap(getBitmapFromURL("http://drinksomewhere.com"+ path[0]+image[0]+""));
	        			
	        		    
	        
	        		
	        		
	        		
	        		
	        		
	        	}
	        	catch(Exception e)
	        	{Toast.makeText(Editorialsdetails.this, e.toString(), Toast.LENGTH_LONG).show();}
	        
	        
	        
	      
	    }
	        
	        public  Bitmap getBitmapFromURL(String src) {
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
	    	                c.drawRoundRect(new RectF(0, 0, 700, 500),30,30,paint); 

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
		getMenuInflater().inflate(R.menu.editorialsdetails, menu);
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

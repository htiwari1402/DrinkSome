package drink.some.drinksomewhere;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.drinksomewhere.EventCalender;
import com.example.drinksomewhere.HomePage;
import com.example.drinksomewhere.ServiceHandler;

import database.Database;

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

public class EventDetails extends Activity {
	
	
	ImageView img;
	TextView title;
	TextView venue;
	TextView date;
	TextView time;
	TextView detail;
	TextView head;
	String image;
	ProgressDialog progress=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		head= (TextView) findViewById(R.id.eventdetailtitle);
		
		progress = ProgressDialog.show(EventDetails.this, "", "Loading Data...",true);
		
	 img= (ImageView) findViewById(R.id.imageEventdetailmain);
		title= (TextView) findViewById(R.id.textEventdetailTitle);
		 venue= (TextView) findViewById(R.id.texteventdetailvenue);
		 date= (TextView) findViewById(R.id.texteventdetaildate);
		 time= (TextView) findViewById(R.id.texteventdetailTime);
	 detail= (TextView) findViewById(R.id.textEventDetails);
	 ImageView back= (ImageView) findViewById(R.id.imagebackbutton);
	 ImageView home= (ImageView) findViewById(R.id.imageHomeButton);
	 ImageView fav= (ImageView) findViewById(R.id.imageeventdetailfav);
	 
	
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				finish();
			}
		});
		
		home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(EventDetails.this, HomePage.class);
				startActivity(i);
				finish();
			}
		});
		
		
		Intent i = getIntent();
		
		final String id= i.getStringExtra("id");
		
		new loadData().execute("http://drinksomewhere.com/wb_services/idWiseEvents.php?id="+id);
		
		fav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Database d= new Database(EventDetails.this);
				String titletxt = title.getText().toString();
				//d.insertFavorites(id,titletxt, image, "Event");
				//Toast.makeText(EventDetails.this, titletxt+"Successfully added in favorites", Toast.LENGTH_LONG).show();
				
				Database db= new Database(EventDetails.this);
				if(!db.checkprevFav(titletxt))
				{
				db.insertFavorites(id,titletxt,image, "Event");
				AlertDialog.Builder ab= new AlertDialog.Builder(EventDetails.this);
				ab.setTitle("DrinkSomeWhere");
				ab.setIcon(R.drawable.eventfav);
				ab.setMessage(titletxt+" successfully added to favorites");
				ab.show();
				
				
				}
				else
					if(db.checkprevFav(titletxt)) {
						
						AlertDialog.Builder ab= new AlertDialog.Builder(EventDetails.this);
						ab.setTitle("DrinkSomeWhere");
						ab.setIcon(R.drawable.eventfav);
						ab.setMessage(titletxt+" already added to favorites !");
						ab.show();
						
					}
				
				
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

   				// Making a request to url and getting response
   			String jsonStr = sh.makeServiceCall(params[0], ServiceHandler.GET);
   				

   				Log.d("Response: ", "> " + jsonStr);

   				if (jsonStr != null) {
   					try {
   						
   						JSONArray jsonarray = new JSONArray(jsonStr);
   					   
   						
   						for (int i = 0; i < jsonarray.length(); i++) {
   							JSONObject jobject = jsonarray.getJSONObject(i);
   						
   						list.add(0, jobject.getString("title"));
   						list.add(1,jobject.getString("venue"));
   						list.add(2,jobject.getString("event_date"));
   						list.add(3,jobject.getString("event_time"));
   						list.add(4,jobject.getString("event_end_time"));
   						list.add(5,jobject.getString("image_folder_path"));
   						list.add(6,jobject.getString("image_filename"));
   						list.add(7,jobject.getString("content"));
   					
   				
   							 }
   						
   						
   						
   						//Toast.makeText(MainActivity.this, "abc"+list.toString(), Toast.LENGTH_LONG).show();
   					} catch (JSONException e) {
   						AlertDialog.Builder ab= new AlertDialog.Builder(EventDetails.this);
						ab.setTitle("DrinkSomeWhere");
						ab.setMessage("Unable to fetch data, Please check your network connectivity.");
						ab.show();	}
   				} else {
   					Log.e("ServiceHandler", "Couldn't get any data from the url");
   				}
   				
   				
   				
   		  

   		  
   		}
   		catch(Exception e)
   		{
   			AlertDialog.Builder ab= new AlertDialog.Builder(EventDetails.this);
			ab.setTitle("DrinkSomeWhere");
			ab.setMessage("Unable to fetch data, Please check your network connectivity.");
			ab.show();	}
			return list;
   	
		}
		
		
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// TODO Auto-generated method stub
			
		  
		   String titleevent= result.get(0);
		   String venueevent= result.get(1);
		   String dateevent=result.get(2);
		   String starttimeevent= result.get(3);
		   String endtimeevent= result.get(4);
		   String pathevent= result.get(5);
		   String imageevent= result.get(6);
		   String content= result.get(7);
		  
		   
		 head.setText(titleevent);  
		 title.setText(titleevent);
		 date.setText(dateevent);
		 venue.setText(venueevent);
		 time.setText(starttimeevent+" to "+endtimeevent);
		 detail.setText(content);
		 img.setImageBitmap(getBitmapFromURL("http://drinksomewhere.com"+ pathevent+imageevent+""));
		image=	"http://drinksomewhere.com"+ pathevent+imageevent+"";
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
		                c.drawRoundRect(new RectF(0, 0, 600, 450),30,30,paint); 

		       
		        return picture;
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
		getMenuInflater().inflate(R.menu.event_details, menu);
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

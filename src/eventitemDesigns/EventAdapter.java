package eventitemDesigns;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import database.Database;
import drink.some.drinksomewhere.EventDetails;
import drink.some.drinksomewhere.R;

public class EventAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] title;
	private final String[] venue;
	private final String[] starttime;
	private final String[] endtime;
	private final String[] path;
    private final String[] image;
    private final String[] id;
    

	public EventAdapter(Activity context,String[] id, String[] title,String[] venue,String[] starttime,String[] endtime , String[] path, String[] image) {
	super(context, R.layout.eventcalender_listdesign, title);
	this.context = context;
	this.title = title;
	this.venue=venue;
	this.starttime= starttime;
	this.endtime= endtime;
	this.path= path;
	this.image = image;
	this.id = id;
	}
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.eventcalender_listdesign, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.textEventItemTitle);
	TextView txtVenue = (TextView) rowView.findViewById(R.id.textEventItemVenue);
	TextView txttime = (TextView) rowView.findViewById(R.id.textEventItemTime);
    ImageView img=(ImageView) rowView.findViewById(R.id.webEventltemTitle);
    img.setImageBitmap(getBitmapFromURL("http://drinksomewhere.com"+ path[position]+image[position]+""));
   
    
    ImageView imgfav =  (ImageView) rowView.findViewById(R.id.imageEventItemFav);
    ImageView imgshare = (ImageView) rowView.findViewById(R.id.imageEventItemShare);
    RelativeLayout r= (RelativeLayout) rowView.findViewById(R.id.relativeEventlayout);
    
    
    
	txtTitle.setText(title[position]);
	txtVenue.setText(venue[position]);
	txttime.setText(starttime[position]+" to "+endtime[position]);
//	img.loadUrl("http://drinksomewhere.com"+ path[position]+image[position]+"");
	
	
	
	
	imgfav.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Database db= new Database(context);
			if(!db.checkprevFav(title[position]))
			{
			db.insertFavorites(id[position],title[position],"http://drinksomewhere.com"+ path[position]+image[position]+"", "Event");
			AlertDialog.Builder ab= new AlertDialog.Builder(context);
			ab.setTitle("DrinkSomeWhere");
			ab.setIcon(R.drawable.eventfav);
			ab.setMessage(title[position]+" successfully added to favorites");
			ab.show();
			
			
			}
			else
				if(db.checkprevFav(title[position])) {
					
					AlertDialog.Builder ab= new AlertDialog.Builder(context);
					ab.setTitle("DrinkSomeWhere");
					ab.setIcon(R.drawable.eventfav);
					ab.setMessage(title[position]+" already added to favorites !");
					ab.show();
					
				}
			
			
		}
	});

	
	
	img.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent i= new Intent(context, EventDetails.class);
			i.putExtra("id", id[position]);
			context.startActivity(i);
			
		}
	});
	
	
	
	return rowView;
	}
	
	
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	       
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap picture=BitmapFactory.decodeStream(input);
	       
	        return picture;
	    } catch (IOException e) {
	        e.printStackTrace();
	       // Log.e("Exception",e.getMessage());
	        return null;
	    }
	}

	}

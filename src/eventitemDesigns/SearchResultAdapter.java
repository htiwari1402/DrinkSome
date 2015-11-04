package eventitemDesigns;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.drinksomewhere.SearchResultDetails;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import database.Database;
import drink.some.drinksomewhere.R;
import drink.some.drinksomewhere.Reviews;

public class SearchResultAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] title;
	private final String[] location;
	private final String[] type;
    private final String[]    image;
    private final String[] path;
   private final double[] minute;
    private final String[] occassion;
  //  private final String[]  moreinfo;
   // private final String[] reviews;
    private final String[] id;
    
    

	public SearchResultAdapter(Activity context, String id[],String[] title,String[] location,String[] type,String[] occassion,String[] path, String[] image,double[] minute) {
	super(context, R.layout.searchresult_design, title);
	this.context = context;
	this.id=id;
	this.title = title;
	this.location= location;
	this.type =type;
	this.minute= minute;
	this.occassion = occassion;
	//this.moreinfo= moreinfo;
	//this.reviews= reviews;
	this.path=path;
	this.image = image;
	
	}
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.searchresult_design, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.textSearchResult_title);
	TextView txtlocation = (TextView) rowView.findViewById(R.id.textSearchresult_location);
	TextView txttype = (TextView) rowView.findViewById(R.id.textSearchresult_type);
	TextView txtminute= (TextView) rowView.findViewById(R.id.textmin);
	TextView txtoccassion= (TextView) rowView.findViewById(R.id.textSearchresult_Occassion);
	Button txtreviews = (Button) rowView.findViewById(R.id.buttonreview);
	TextView txtmoreinfo = (TextView) rowView.findViewById(R.id.txtmoreinfo);
	//Button fav = (Button) rowView.findViewById(R.id.searchDetailFav);
	
	
	
    ImageView img=(ImageView) rowView.findViewById(R.id.websearchResult);
   
	
	txtTitle.setText(title[position]);
	txtlocation.setText("Location -"+location[position]);
	txttype.setText("Type -"+type[position]);
	txtminute.setText(minute[position]+"m distance from here");
	txtoccassion.setText("Occasion -"+occassion[position]);
	//txtreviews.setText(reviews[position]);
	 img.setImageBitmap(getBitmapFromURL("http://drinksomewhere.com"+ path[position]+image[position]+""));
	//img.loadUrl("http://drinksomewhere.com"+ path[position]+image[position]+"");
	
	txtmoreinfo.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stubid
			
			String selectedid= id[position];
			
		   
			Intent i =new Intent(context, SearchResultDetails.class);
			i.putExtra("id", selectedid);
			
			context.startActivity(i);
		
		
			
		}
	});
	 
	txtreviews.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(context, Reviews.class);
			i.putExtra("id", id[position]);
			context.startActivity(i);
		}
	});
	

	return rowView;
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
	                c.drawRoundRect(new RectF(0, 0, picture.getWidth(), picture.getHeight()),35,35,paint); 

	       
	        return circleBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	       // Log.e("Exception",e.getMessage());
	        return null;
	    }
	}

	
	
	}
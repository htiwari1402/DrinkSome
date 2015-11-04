package eventitemDesigns;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import drink.some.drinksomewhere.EventDetails;
import drink.some.drinksomewhere.FavEventDetails;
import drink.some.drinksomewhere.FavSearchDetails;
import drink.some.drinksomewhere.PromotionEnterCode;
import drink.some.drinksomewhere.R;

public class FavoritiesAdapter extends ArrayAdapter<String> {

	private final Activity context;

	private final ArrayList<String>  title;
	private final ArrayList<String>  type;
	
	private final ArrayList<String>  image;
	private final ArrayList<String> id;
     String check=null;
	public FavoritiesAdapter(Activity context,ArrayList<String> id, ArrayList<String> title,ArrayList<String> image,  ArrayList<String> type) {
	super(context, R.layout.favorities_design, title);
	this.context = context;
	
	this.title = title;

	this.image = image;
	this.type = type;
	this.id= id;
	}
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.favorities_design, null, true);
	RelativeLayout r= (RelativeLayout) rowView.findViewById(R.id.relativelayoutfav);
	
	TextView txtTitle = (TextView) rowView.findViewById(R.id.textFavTitle);
	Log.i("asdas","rthrthrt");
	//TextView txtTitle2 = (TextView) rowView.findViewById(R.id.textFavTitle2);
	ImageView web = (ImageView) rowView.findViewById(R.id.imageMainFavo);
	Log.i("asdas","rthrthrt");
	// web.loadUrl(image.get(position));
	 web.setImageBitmap(getBitmapFromURL(image.get(position)));
	 Log.i("asdas","rthrthrt");
	   
	    Log.i("asdas","rthrthrt");
	
    ImageView imgFavTypeImage=(ImageView) rowView.findViewById(R.id.imageFavType);
     
    txtTitle.setText(title.get(position));
    if(type.get(position).equals("outlets"))
    {
    	imgFavTypeImage.setImageResource(R.drawable.bar);
        
    	
    	
    }
    if(type.get(position).equals("Event"))
    {
    	imgFavTypeImage.setImageResource(R.drawable.eventcalender);
    	
    	
    }
    
    
    web.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(type.get(position).equals("outlets")) {
	 			
	 			//Toast.makeText(context, "outlets", Toast.LENGTH_LONG).show();
	 			
	 			Intent i = new Intent(context, FavSearchDetails.class);
	 			i.putExtra("id", id.get(position));
	 			context.startActivity(i);
	 			
	 			
	 		}
	 		else
	 			if(type.get(position).equals("Event")) {

	 				
	 			//	Toast.makeText(context, "Event", Toast.LENGTH_LONG).show();
	 				
	 				Intent i = new Intent(context, FavEventDetails.class);
	 				i.putExtra("id",id.get(position));
	 				context.startActivity(i);
	 				
	 				
	 			}
	 		
			
			
			
			
			
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
	        Bitmap circleBitmap = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
	        BitmapShader shader = new BitmapShader (picture,  TileMode.CLAMP, TileMode.CLAMP);
	        Paint paint = new Paint();
	                paint.setShader(shader);
	                Canvas c = new Canvas(circleBitmap);
	                c.drawRoundRect(new RectF(0, 0, picture.getWidth(), picture.getHeight()),10,10,paint); 

	        return picture;
	    } catch (IOException e) {
	        e.printStackTrace();
	       // Log.e("Exception",e.getMessage());
	        return null;
	    }
	}
	}
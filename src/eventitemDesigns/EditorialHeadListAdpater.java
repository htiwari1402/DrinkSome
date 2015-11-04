package eventitemDesigns;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.squareup.picasso.Picasso;

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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import drink.some.drinksomewhere.Editorialsdetails;
import drink.some.drinksomewhere.R;

public class EditorialHeadListAdpater extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] id;
	private final String[] path;
	private final String[] image;
	private final String[] date;
	
	

	public EditorialHeadListAdpater(Activity context, String[] id,String[] path, String[] image, String[] date) {
	super(context, R.layout.editorial_headlistdesign, id);
	this.context = context;
	this.id=id;
	this.path=path;
	this.image=image;
	this.date=date;


	
	}
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.editorial_headlistdesign, null, true);
	
	RelativeLayout relative = (RelativeLayout) rowView.findViewById(R.id.relative);
	TextView txtdate = (TextView) rowView.findViewById(R.id.textEditorialdate);
	
	ImageView web= (ImageView) rowView.findViewById(R.id.webviewEditorial);
   // web.loadUrl("http://drinksomewhere.com"+ path[position]+image[position]+"");
   // web.setImageBitmap(getBitmapFromURL("http://drinksomewhere.com"+ path[position]+image[position]+""));
	Picasso.with(context).load("http://drinksomewhere.com"+ path[position]+image[position]+"").into(web);
    txtdate.setText(date[position]);
    
    
    
    web.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String selectedid= id[position];
			
			Intent i  = new Intent(context, Editorialsdetails.class);
			i.putExtra("from", "down");
			i.putExtra("id", selectedid);
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
	        Bitmap circleBitmap = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
	        BitmapShader shader = new BitmapShader (picture,  TileMode.CLAMP, TileMode.CLAMP);
	        Paint paint = new Paint();
	                paint.setShader(shader);
	                Canvas c = new Canvas(circleBitmap);
	                c.drawRoundRect(new RectF(0, 0, picture.getWidth(), picture.getHeight()),50,50,paint); 

	        return picture;
	    } catch (IOException e) {
	        e.printStackTrace();
	       // Log.e("Exception",e.getMessage());
	        return null;
	    }
	}

	
	
	
	}

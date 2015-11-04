package eventitemDesigns;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import drink.some.drinksomewhere.R;

public class ReviewAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] username;
	private final String[] usercomment;
	private final int[] rating;
	private final String[] imageurl;
	
	

	public ReviewAdapter(Activity context, String[] username, String[] usercomment, int[] rating, String[] imageurl) {
	super(context, R.layout.editorial_design, username);
	this.context = context;
	this.username = username;
	this.usercomment = usercomment;
	this.rating= rating;
	this.imageurl= imageurl;


	
	}
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.reviews_design, null, true);
	TextView txtusername = (TextView) rowView.findViewById(R.id.textReviewUsername);
	TextView txtusercomment = (TextView) rowView.findViewById(R.id.textUsercomments);
	RatingBar rate = (RatingBar) rowView.findViewById(R.id.ratingofuser);
	ImageView imguser = (ImageView) rowView.findViewById(R.id.imageReviewuserspic);
	
	
	
	//ImageView imgEditorialYearButton= (ImageView) rowView.findViewById(R.id.imageEditorialButtonAdd);
	
	txtusername.setText(username[position]);
	txtusercomment.setText(usercomment[position]);
	rate.setRating(rating[position]);
	
	//imguser.setImageBitmap(bm)
    
	
	
	
	
	
	
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
	                c.drawCircle(picture.getWidth()/2, picture.getHeight()/2, picture.getWidth()/2, paint);		                

	        
	       // Log.e("Bitmap","returned");
	        return circleBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	       // Log.e("Exception",e.getMessage());
	        return null;
	    }
	}




}
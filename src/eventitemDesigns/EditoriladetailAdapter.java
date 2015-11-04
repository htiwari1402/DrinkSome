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
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import drink.some.drinksomewhere.R;

public class EditoriladetailAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] title;
	private final String[] name;
	private final String[] date;
	private final String[] description;
	private final String[] path;
	private final String[] image;
	private final String[] id;
	

	public EditoriladetailAdapter(Activity context, String[] title,String[] name,String[] date,String[] description,String[] path,String[] image,String[] id) {
	super(context, R.layout.editorial_design, title);
	this.context = context;
	this.title = title;
	this.name = name;
	this.date = date;
	this.description = description;
	
	this.path = path;
	this.image = image;
	this.id = id;

	
	}
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.editorialdetail_design, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.textEDitorialdetailtitle);
	TextView txtname = (TextView) rowView.findViewById(R.id.textediorialDetailname);
	TextView txtdate = (TextView) rowView.findViewById(R.id.textEditorialdetaildate);
	
	WebView w= (WebView) rowView.findViewById(R.id.webVieweditorialdetails);
	
	ImageView mainimage= (ImageView) rowView.findViewById(R.id.imageEditorialdetailImage);
	
    txtTitle.setText(title[position]);
    txtname.setText(name[position]);
    txtdate.setText(date[position]);
  //  txtdiscription.setText(description[position]);
    String html = description[position];
    final String mimeType = "text/html";
    final String encoding = "UTF-8";
    w.loadDataWithBaseURL("", html, mimeType, encoding, "");
	mainimage.setImageBitmap(getBitmapFromURL("http://drinksomewhere.com"+ path[position]+image[position]+""));
	
    
    
	
	
	
	
	
	
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
	                c.drawRoundRect(new RectF(0, 0, 700, 500),30,30,paint); 

	        return circleBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	       // Log.e("Exception",e.getMessage());
	        return null;
	    }
	}

	
	
	
	
	
	
	}
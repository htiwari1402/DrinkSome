package com.example.drinksomewhere;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.squareup.picasso.Picasso;



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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import drink.some.drinksomewhere.R;

public class  HAdapter extends BaseAdapter {
	
	
	private final String[] date;
	private final String[] time;
//	private final String[] venue;
	//private final String[] titleEvent;
	private final String[] path;
	private final String[] image;
	

	public HAdapter(String[] date, String[] time,String[] path, String[] image){
		super();
		
		
		this.path= path;
		this.image= image;
		this.date= date;
		this.time= time;
		//this.venue=venue;
		
	}
	

	
	public int getCount() {
		return date.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_eventdesign, null);
		//TextView title = (TextView) retval.findViewById(R.id.texthomepageEventtitle);
		ImageView web = (ImageView) retval.findViewById(R.id.imagehomepageEvent);
		TextView tdate= (TextView) retval.findViewById(R.id.txthomepageeventdate);
		TextView ttime= (TextView) retval.findViewById(R.id.txtHomepageeventtime);
	//	TextView tvenue= (TextView) retval.findViewById(R.id.textHomepageeventvenue);
		// web.getSettings().setLoadWithOverviewMode(true);
		//	web.getSettings().setUseWideViewPort(true);
			//eventimage.setClickable(true);
	//	title.setText(titleEvent[position]);
		//UrlImageViewHelper.setUrlDrawable(web, "http://drinksomewhere.com"+ path[position]+image[position]+"");
		//web.setImageBitmap(getBitmapFromURL("http://drinksomewhere.com"+ path[position]+image[position]+""));
		Picasso.with(parent.getContext()).load("http://drinksomewhere.com"+ path[position]+image[position]+"").into(web);
		tdate.setText("Date:"+date[position]);
		//tvenue.setText(venue[position]);
		ttime.setText("Time:"+time[position]);
		//web.loadUrl("http://drinksomewhere.com"+ path[position]+image[position]+"");
		// web.getSettings().setUseWideViewPort(true);
		  //  web.getSettings().setLoadWithOverviewMode(true);
		   // web.getSettings().setUseWideViewPort(true);
		
		return retval;
	}
	

	



}

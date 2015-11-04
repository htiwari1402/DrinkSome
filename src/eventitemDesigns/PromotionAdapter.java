package eventitemDesigns;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import drink.some.drinksomewhere.EventDetails;
import drink.some.drinksomewhere.PromotionEnterCode;
import drink.some.drinksomewhere.R;

public class PromotionAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] title;
	private final String[] title2;
	private final int[] promotionImage;
    private final int[] promotionTitleimage;

	public PromotionAdapter(Activity context, String[] title,String[] title2,int[] promotionImage, int[] promotionTitleimage) {
	super(context, R.layout.promotion_design, title);
	this.context = context;
	this.title = title;
	this.title2= title2;
	this.promotionImage= promotionImage;
	this.promotionTitleimage= promotionTitleimage;
	
	}
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.promotion_design, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.textpromoTitle);
	TextView txtTitle2 = (TextView) rowView.findViewById(R.id.textpromoTitle2);
	ImageView imgPromoMainImage = (ImageView) rowView.findViewById(R.id.imageMainPromotion);
	
    ImageView imgPromoTitleImage=(ImageView) rowView.findViewById(R.id.imagePromotitle);
    
    ImageView imgfav =  (ImageView) rowView.findViewById(R.id.imagePromoFav);
    ImageView imgshare = (ImageView) rowView.findViewById(R.id.imagePromoShare);
    
    txtTitle.setText(title[position]);
    txtTitle2.setText(title2[position]);
    imgPromoMainImage.setImageResource(promotionImage[position]);
    imgPromoTitleImage.setImageResource(promotionTitleimage[position]);
	
	
	imgfav.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Toast.makeText(context, "fav"+title[position], Toast.LENGTH_SHORT).show();
			
		}
	});
	
	imgshare.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Toast.makeText(context,"share"+title[position], Toast.LENGTH_SHORT).show();
			
		}
	});
	
	imgPromoMainImage.setOnClickListener(new  OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i = new Intent(context, PromotionEnterCode.class);
			context.startActivity(i);
			
			
			
		}
	});
	
	
	
	
	return rowView;
	}
	}

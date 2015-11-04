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
import drink.some.drinksomewhere.PromotionEnterCode;
import drink.some.drinksomewhere.R;

public class EditorialListAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] title;
	
	

	public EditorialListAdapter(Activity context, String[] title) {
	super(context, R.layout.editorial_design, title);
	this.context = context;
	this.title = title;


	
	}
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.editorial_design, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.textEDitorialButtonYear);
	
	ImageView imgEditorialYearButton= (ImageView) rowView.findViewById(R.id.imageEditorialButtonAdd);
	
    txtTitle.setText(title[position]);
    
	
	
	
	
	
	
	return rowView;
	}
	}
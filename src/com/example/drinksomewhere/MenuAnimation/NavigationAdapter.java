package com.example.drinksomewhere.MenuAnimation;

import drink.some.drinksomewhere.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] web;
    private final int[] y;

	public NavigationAdapter(Activity context, String[] web, int[] y) {
	super(context, R.layout.navigation_item_design, web);
	this.context = context;
	this.web = web;
	this.y=y;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.navigation_item_design, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.txtnavigationtitle);
	
	ImageView img=(ImageView) rowView.findViewById(R.id.imgnavigationicon);
	img.setImageResource(y[position]);
	txtTitle.setText(web[position]);
	
	
	return rowView;
	}
	}
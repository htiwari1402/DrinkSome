package com.example.drinksomewhere.MenuAnimation;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import drink.some.drinksomewhere.R;

public class homepageListAdapterextends extends ArrayAdapter<String> {

	private final Activity context;
	private final String[] web;
    private final int[] y;

	public homepageListAdapterextends(Activity context, String[] web, int[] y) {
	super(context, R.layout.homepage_list_design, web);
	this.context = context;
	this.web = web;
	this.y=y;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.homepage_list_design, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.homepageListItem);
	
	ImageView img=(ImageView) rowView.findViewById(R.id.homepageListimage);
	img.setImageResource(y[position]);
	txtTitle.setText(web[position]);
	
	
	return rowView;
	}
	}
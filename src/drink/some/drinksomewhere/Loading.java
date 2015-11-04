package drink.some.drinksomewhere;

import com.example.drinksomewhere.HomePage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Loading extends Activity {

	//Introduce an delay
	    private final int WAIT_TIME = 3500;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("LoadingScreenActivity  screen started");
		setContentView(R.layout.activity_loading);
		findViewById(R.id.mainSpinner1).setVisibility(View.VISIBLE);

		new Handler().postDelayed(new Runnable(){ 
		@Override 
		    public void run() { 
	              //Simulating a long running task
	              try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	              
		     System.out.println("Going to Profile Data");
		  /* Create an Intent that will start the ProfileData-Activity. */
	              Intent mainIntent = new Intent(Loading.this,HomePage.class); 
		    Loading.this.startActivity(mainIntent); 
		    Loading.this.finish(); 
		} 
		}, WAIT_TIME);
	      }
	}

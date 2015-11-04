package com.example.drinksomewhere;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
 
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook.DialogListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import database.Database;
import drink.some.drinksomewhere.Loading;
import drink.some.drinksomewhere.R;

 
public class MainActivity extends Activity implements OnClickListener,
        ConnectionCallbacks, OnConnectionFailedListener {
	
	
//////////////////////////////////////////code for facebook/////////////////////////////////////////////////////	
	private static String APP_ID = "1607843522813337"; // Replace with your App ID

	// Instance of Facebook Class
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;
 //////////////////////////////////////////////////////////////////////////////////////////////
    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";
 
    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
 
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
 
    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;
 
    private boolean mSignInClicked;
 
    private ConnectionResult mConnectionResult;
 
    private ImageView googlebtnSignIn;
    private ImageView facebookSiginIn;
  
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
        
        mAsyncRunner = new AsyncFacebookRunner(facebook);
        
        Database db= new Database(this);
        boolean check = db.checkDatabasePresent();
        
        if(check)
        {
        	Intent i= new Intent(MainActivity.this, HomePage.class);
        	startActivity(i);
        	finish();
        	
        }
        
        
 
        googlebtnSignIn = (ImageView) findViewById(R.id.imgGooglelogo);
        facebookSiginIn =  (ImageView) findViewById(R.id.imgFacebooklogo);
        
        
        facebookSiginIn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				loginFacebook();
				
				
				
			}
		});
    
       
        // Button click listeners
        googlebtnSignIn.setOnClickListener(this);
       
     
 
        mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this).addApi(Plus.API)
		.addScope(Plus.SCOPE_PLUS_LOGIN).build();
       
    }
 
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
       // Toast.makeText(MainActivity.this, "Inside Start", Toast.LENGTH_SHORT).show();
        Log.i("dfhhhhhhhhhhhhhhhhhhhhhhhhhh","dhddddddddddddddddddddddddddddddddddddddd");
    }
 
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
 
    /**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
         //       Toast.makeText(MainActivity.this,"error resolving method", Toast.LENGTH_SHORT).show();
                
                
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (SendIntentException e) {
           // 	Toast.makeText(MainActivity.this,"error"+ e.toString(), Toast.LENGTH_SHORT).show();
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
 
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            Toast.makeText(MainActivity.this,"connection failed, Please check your network connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
 
        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;
 
            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
 
    }
    
    /**
	 * Function to login into facebook
	 * */
	private void loginFacebook() {

	    if (!facebook.isSessionValid()) {

	        facebook.authorize(this, new String[] { "email",
	                "public_profile" }, new LoginDialogListener());

	    } else {

	        getFacebookProfileInformation();

	    }

	}
	class LoginDialogListener implements DialogListener {

	    public void onComplete(Bundle values) {
	        try {

	            getFacebookProfileInformation();

	        } catch (Exception error) {
	            Toast.makeText(MainActivity.this, error.toString(),
	                    Toast.LENGTH_SHORT).show();
	        }
	    }

	    public void onFacebookError(FacebookError error) {
	        Toast.makeText(MainActivity.this,
	                "Something went wrong. Please try again.",
	                Toast.LENGTH_LONG).show();
	    }

	    public void onError(DialogError error) {
	        Toast.makeText(MainActivity.this,
	                "Something went wrong. Please try again.",
	                Toast.LENGTH_LONG).show();
	    }

	    public void onCancel() {
	        Toast.makeText(MainActivity.this,
	                "Something went wrong. Please try again.",
	                Toast.LENGTH_LONG).show();
	    }
	}
	
	public void getFacebookProfileInformation() {


	    try {
	    	 Intent i =new Intent(MainActivity.this,Loading.class);
	           startActivity(i);
	           finish();
	    	 String access_token = facebook.getAccessToken();
	    	 ServiceHandler sh = new ServiceHandler();
	    	 String jsnfacebook = sh.makeServiceCall("https://graph.facebook.com/me?fields=id,picture.type(normal),name,email,gender&access_token="
	    		     + access_token, ServiceHandler.GET);
	    	 
	    	 
	    	 

	        JSONObject profile = new  JSONObject(jsnfacebook);
	       // Toast.makeText(MainActivity.this,jsnfacebook, Toast.LENGTH_LONG).show();

	final   String     mUserId = profile.getString("id");
	   final  String   mUserToken = facebook.getAccessToken();
	      final  String mUserName = profile.getString("name");
	     JSONObject jsonObjData = profile.getJSONObject("picture");
	     JSONObject jsonObjUrl = jsonObjData.getJSONObject("data");
	   String   imageURL = jsonObjUrl.getString("url");
	  
	      
	   //   Toast.makeText(MainActivity.this, imageURL, Toast.LENGTH_LONG).show();
	      new LoadProfileImage().execute(imageURL);
	     
	      
	    
	    	//  final String birthday = profile.getString("birthday");
	          final  String  mUserEmail = profile.getString("email");
	          
	          
	     
	
	     final String gender = profile.getString("gender");
	      
	    
           Database db =  new Database(MainActivity.this);
           db.insertUserDetail(mUserEmail,mUserName, gender, "undefined");
          // Toast.makeText(MainActivity.this, "values", Toast.LENGTH_LONG).show();
          
	      

	    } catch(Exception e){
	    	
	    	Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
	    }

	}
	
  
  //http://www.androidhub4you.com/2013/02/sign-up-with-facebook-in-android.html#ixzz3hTrNu92H
    
    public void logoutFromFacebook() {
		mAsyncRunner.logout(this, new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Logout from Facebook", response);
				
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}

 

	
 
    @Override
    protected void onActivityResult(int requestCode, int responseCode,
            Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }
 
            mIntentInProgress = false;
 
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }
 
    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        
        //Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
 
        // Get user's information
        Intent i= new Intent(MainActivity.this, Loading.class);
        startActivity(i);
        finish();
        getProfileInformation();
 
        // Update the UI after signin
       // updateUI(true);
       
    }
 
    /**
     * Updating the UI, showing/hiding buttons and profile layout
     * */
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
        	
        	Intent i= new Intent(MainActivity.this, HomePage.class);
        	startActivity(i);
         
        } else {
          
        	Toast.makeText(MainActivity.this, "Login not successfull", Toast.LENGTH_SHORT).show();
        	
        }
    }
 
    /**
     * Fetching user's information name, email, profile pic
     * */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            	
            	//Toast.makeText(MainActivity.this, "getInformation called", Toast.LENGTH_SHORT).show();
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
             String personPhotoUrl = currentPerson.getImage().getUrl();
               // String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                String dob= currentPerson.getBirthday();
                String birthday;
                if(dob == null)
                {
                	birthday = "not specified";
                	
                }
                else
                {
                	birthday = dob;
                	
                }
                int genderValue = currentPerson.getGender();
                
                 Database db= new Database(MainActivity.this);
                 boolean check = db.checkDatabasePresent();
                 if(!check){
                if(genderValue == 1)
                 {
                	 db.insertUserDetail(email, personName, "Female", birthday);
                	 
                 }
                 else
                	 if(genderValue == 0)
                	 {
                		 db.insertUserDetail(email, personName, "Male", birthday);
                    	  
                		 
                	}
                	else
                	 if(genderValue == 2)
                	{
                		 db.insertUserDetail(email, personName, "Other", birthday);
                        	 
                			 
                 }
                personPhotoUrl = personPhotoUrl.substring(0,
						personPhotoUrl.length() - 2)
						+ PROFILE_PIC_SIZE;
               
                new LoadProfileImage().execute(personPhotoUrl);
                
                signOutFromGplus();
              //  Toast.makeText(MainActivity.this, "Value inserted", Toast.LENGTH_SHORT).show();
                 
          //       Toast.makeText(MainActivity.this, personName+dob+email, Toast.LENGTH_LONG).show();
                 }
              
 
              
            
 
                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
            
            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        updateUI(false);
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    /**
     * Button on click listener
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.imgGooglelogo:
            // Signin button clicked
            signInWithGplus();
            break;
      
        }
    }
 
    /**
     * Sign-in into google
     * */
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
           
            Session.session = true;
           // Toast.makeText(MainActivity.this,"Inside Sign in method", Toast.LENGTH_SHORT).show();
        }
        else
        {
        	Toast.makeText(MainActivity.this,"NotInside Sign in method", Toast.LENGTH_SHORT).show();
        }
    }
 
    /**
     * Sign-out from google
     * */
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
          //  mGoogleApiClient.connect();
          //  updateUI(false);
        }
    }
  
    public void signout() {
    	
    	signOutFromGplus();
    	
    }
    /**
     * Revoking access from google
     * */
    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e(TAG, "User access revoked!");
                            mGoogleApiClient.connect();
                            updateUI(false);
                        }
 
                    });
        }
    }
 
    /**
     * Background Async task to load user profile picture from url
     */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
 
        public LoadProfileImage() {
           
        }
 
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
 
        protected void onPostExecute(Bitmap result) {
           // bmImage.setImageBitmap(result);
        	File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "profile_drinksomewhere.jpg");
        	 if (file.exists ()) file.delete (); 
        	    try {
        	           FileOutputStream out = new FileOutputStream(file);
        	           result.compress(Bitmap.CompressFormat.JPEG, 90, out);
        	           out.flush();
        	           out.close();

        	    } catch (Exception e) {
        	           e.printStackTrace();
        	    }
        	
        }
    }
 
}
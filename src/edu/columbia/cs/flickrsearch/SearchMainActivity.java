package edu.columbia.cs.flickrsearch;

import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchMainActivity extends Activity {

	//Set variables
	String contact;
	String name;
	String textname;
	String newname;

   
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       
       //button1 is the Search EditText button 
        Button searchtext=(Button)findViewById(R.id.button1);
                
        searchtext.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		Log.i("MainActivity", "EditText Button Clicked");
        		
        		
        		TextView textView = (TextView)findViewById(R.id.textView1);
        		//Get tag name from the EditText
        		EditText text = (EditText)findViewById(R.id.editText1);
        	    textname = text.getText().toString();
        	        
        		
        		name=textname;
        		
        		//If the tag name contacts places, replace them by "+"
        		if(name.contains(" ")){
        			newname= name.replaceAll(" ", "+");
        		}else{
        			newname = name;
        		}
        		
        		//Show name on the TextView 
        		textView.setText(name);
        		
        		
        		//Save the name and send it to the next activity
        		Intent intent = new Intent();
        		intent.putExtra("msg",newname);
        		intent.setClass(SearchMainActivity.this, SecondActivity.class);
        		startActivityForResult(intent,1);
        		
        	}
        	
        });
        
        //Similar procedures, here button is "search contact" 
        
        Button searchcontact=(Button)findViewById(R.id.button3);
        
        searchcontact.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		Log.i("MainActivity", "Contact Button Clicked");
        		        		
        		TextView textView = (TextView)findViewById(R.id.textView1);  
        		
        		name=contact;
        		
        		if(name.contains(" ")){
        			newname= name.replaceAll(" ", "+");
        		}else{
        			newname = name;
        		}
        		       		
        		textView.setText(name);
        		
        		Intent intent = new Intent();
        		intent.putExtra("msg",newname);
        		intent.setClass(SearchMainActivity.this, SecondActivity.class);
        		startActivityForResult(intent,1);
        		
        	}
        	
        });
        
        //Set button to get name from contact book       
        Button button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener(){
        	
        	@Override
        	public void onClick(View v){
        		//Use Intent to save the name from the contact book
        		Intent intent=new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
        		startActivityForResult(intent, 2);
        		         	    
        	}
    		
        });
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
   //Here is method about how to get the name from the contact book
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	
    	if(requestCode == 1){
    		if(resultCode == RESULT_OK)
    			if(data!=null)
    				Log.i("MainActivity", data.getStringExtra("returned value"));
    	}
    	
    	 if (resultCode == Activity.RESULT_OK &&requestCode == 2) {
    		 
    		    System.out.println("data.getData() " + data.getData());    		 
    		      Cursor cursor = getContentResolver().query(data.getData(),  new String[] {Contacts.DISPLAY_NAME}, null, null, null);
    		      if (cursor.moveToFirst()) {
    		 
    		          int columnIndex = cursor.getColumnIndex(Contacts.DISPLAY_NAME);
    		 
    		          contact = cursor.getString(columnIndex);
    		 
    		          System.out.println(contact);
    		 
    		      } else{
    		    	  contact = null;
    		    	  
    		    	  System.out.println(contact);
    		    	  
    		      }
    	 }
    }
    
}

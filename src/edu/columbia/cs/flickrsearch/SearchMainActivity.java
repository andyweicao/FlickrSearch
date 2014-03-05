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

	
	String contact;
	String name;
	String textname;
	String newname;

   
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       
        
        Button searchtext=(Button)findViewById(R.id.button1);
                
        searchtext.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		Log.i("MainActivity", "Button Clicked");
        		
        		
        		TextView textView = (TextView)findViewById(R.id.textView1);
        		
        		EditText text = (EditText)findViewById(R.id.editText1);
        	    textname = text.getText().toString();
        	        
        		
        		name=textname;
        		
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
        
        Button searchcontact=(Button)findViewById(R.id.button3);
        
        searchcontact.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		Log.i("MainActivity", "Button Clicked");
        		
        		
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
        
        Button button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener(){
        	
        	@Override
        	public void onClick(View v){
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

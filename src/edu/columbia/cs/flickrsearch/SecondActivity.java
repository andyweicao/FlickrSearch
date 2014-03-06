package edu.columbia.cs.flickrsearch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;



public class SecondActivity extends Activity  {

    
        
	// All variables
	ArrayList<Photo> list=new ArrayList<Photo>();//set items
	private PullXmlAdapter adapter; //set adapter
    ProgressDialog pDialog;  //set a dialog
	private ListView lv;  //set listview
    int current_page = 1; //set current page of Flickr xml

 
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //Get Intent from previous activity to catch the tage name
        Intent intent=getIntent();
        String name= intent.getStringExtra("msg");
        //Link ListView to lv
        lv=(ListView)findViewById(R.id.list);
		
        try {
        	//Get XML url (10 items/page)
	        URL url = new URL("http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3be9461fba10e85e8d64a674c019a412&tags="+name+"&per_page=10&extras=date_taken,owner_name,description&page="+current_page);
	        //Process the data from url 
			new LoadFeedTaskUrl().execute(url);			
		
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
		// LoadMore button
        Button btnLoadMore = new Button(this);
        btnLoadMore.setText("Load More");
 
        // Adding Load More button to ListView at bottom
        lv.addFooterView(btnLoadMore);
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Starting a new async task to load more photos to ListView
                new loadMoreListView().execute();
            }
        });
        
      
 
    }
    


    private class LoadFeedTaskUrl extends
   	AsyncTask<URL, Void, ArrayList<Photo>> {

       	@Override
       	protected ArrayList<Photo> doInBackground(URL... args) {
       		//Set Parser
       		PullXml parser = new PullXml();
       		ArrayList<Photo> newlist = null;
       		try {
       			//Parse data
       			newlist = parser.parse(args[0].openStream());
       		} catch (XmlPullParserException e) {
       			// TODO Auto-generated catch block
       			e.printStackTrace();
       		} catch (IOException e) {
       			// TODO Auto-generated catch block
       			e.printStackTrace();
       		}
       		
       		//Now I have a list with 10 photos with their informations
       		return newlist;
       	}

       	@Override
       	protected void onPostExecute(ArrayList<Photo> newlist) {
       		//Set the adapter, initiate it if list is from first page
    		if(lv.getAdapter() == null){
    			adapter=new PullXmlAdapter(SecondActivity.this, newlist);
    			lv.setAdapter(adapter);
    		}else{
    		//Here add photos from another page to the original adapter
       		adapter.setListItems(newlist);
    		}
    		list.addAll(newlist);//Add current list to the whole list
    		//Set ListView clicks to get larger photo for each one in a browser
    		lv.setOnItemClickListener(new OnItemClickListener(){
    			@Override
    			public void onItemClick(AdapterView<?> l, View v, int position, long id) {
    				// TODO Auto-generated method stub
    				String content = SecondActivity.this.list.get(position).getLargeUrl();
    				Intent openFeed = new Intent(Intent.ACTION_VIEW);
    				openFeed.setData(Uri.parse(content));
    				startActivity(openFeed);
    			}
    		});

    	}

    } 
    
    
    
    // AsyncTask for lord more action
    
    private class loadMoreListView extends AsyncTask<Void, Void, Void> {
     
    	@Override
        protected void onPreExecute() {
            // Showing progress dialog 
    		pDialog = new ProgressDialog(
                    SecondActivity.this);
            pDialog.setMessage("Please wait..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        
        protected Void doInBackground(Void... unused) {
            runOnUiThread(new Runnable() {
                public void run() {
                    // increment current page
                    current_page += 1;
                    Intent intent=getIntent();
                    String name= intent.getStringExtra("msg");
                    // get listview current position - used to maintain scroll position
                    
                    int currentPosition = lv.getFirstVisiblePosition();
                    
                    // Setting new scroll position
                    lv.setSelectionFromTop(currentPosition + 10, 0);


                    try {
            	        URL url = new URL("http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3be9461fba10e85e8d64a674c019a412&tags="+name+"&per_page=10&extras=date_taken,owner_name,description&page="+current_page);
            			new LoadFeedTaskUrl().execute(url);			
            	       
            		
                    } catch (IOException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		} catch (Exception e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}	
                    
                }
            });
            return (null);
        }      
     
        protected void onPostExecute(Void unused) {
            // closing progress dialog
            pDialog.dismiss();
        }
    }
    
    
  
	
}

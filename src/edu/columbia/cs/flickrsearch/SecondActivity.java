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
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;



public class SecondActivity extends Activity {

    
        
	// All variables
	List<Photo> list=new ArrayList<Photo>();
	private PullXmlAdapter adapter;
    ProgressDialog pDialog;
	private ListView lv;
    int current_page = 1;

 
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        
        Intent intent=getIntent();
        String name= intent.getStringExtra("msg");
        lv=(ListView)findViewById(R.id.list);
		
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
		
		// LoadMore button
        Button btnLoadMore = new Button(this);
        btnLoadMore.setText("Load More");
 
        // Adding Load More button to listview at bottom
        lv.addFooterView(btnLoadMore);
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Starting a new async task
                new loadMoreListView().execute();
            }
        });
        
       
       
 
       
    }
    
    private class LoadFeedTaskUrl extends
	AsyncTask<URL, Void, List<Photo>> {

    	@Override
    	protected List<Photo> doInBackground(URL... args) {

    		PullXml parser = new PullXml();
    		List<Photo> newlist = null;
    		try {
    			newlist = parser.parse(args[0].openStream());
    			newlist.addAll(list);
    			list=newlist;
    		} catch (XmlPullParserException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		

    		return newlist;
    	}

    	@Override
    	protected void onPostExecute(List<Photo> newlist) {
    		

    		adapter=new PullXmlAdapter(SecondActivity.this, newlist);
    		lv.setAdapter(adapter);
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
    
    /**
     * Async Task that send a request to url
     * Gets new list view data
     * Appends to list view
     * */
    private class loadMoreListView extends AsyncTask<Void, Void, Void> {
     
    	@Override
        protected void onPreExecute() {
            // Showing progress dialog before sending http request
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
                    // get listview current position - used to maintain scroll position
                    
                    int currentPosition = lv.getFirstVisiblePosition();

                    
     
                    // Setting new scroll position
                    lv.setSelectionFromTop(currentPosition + 1, 0);
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

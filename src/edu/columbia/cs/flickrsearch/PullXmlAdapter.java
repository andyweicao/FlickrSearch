package edu.columbia.cs.flickrsearch;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
 
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class PullXmlAdapter extends BaseAdapter {
	private List<Photo> list;
	private Context context;
	private LayoutInflater inflater;
	private String url;
	private String owner="Ownername";
	private String date="Taken Date";
	private String des= "Description";
	
public PullXmlAdapter(Context context,List<Photo> list) {
	// TODO Auto-generated constructor stub
	this.context=context;
	this.list=list;
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}



	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}



	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	

	private static class ViewHolder {
		        ImageView imageView;
		        TextView textView;
		        String imageURL;
		        
		    }	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
		inflater=LayoutInflater.from(context);
        ViewHolder viewHolder = null;
		convertView=inflater.inflate(R.layout.list_item, null);
		viewHolder = new ViewHolder();
		viewHolder.textView =  (TextView)convertView.findViewById(R.id.name);
		viewHolder.imageView = (ImageView)convertView.findViewById(R.id.imageView1);		
		convertView.setTag(viewHolder);
		}
		ViewHolder viewHolder = (ViewHolder)convertView.getTag();
        
        Photo s = list.get(position);
        
        String t= "<b>"+ this.owner +"</b>" +": "+ s.getOwnername()+ "<br/>"+ "<b>"+ this.date +"</b>" +": "+ s.getDatetaken()+ "<br/>" + "<b>"+ this.des +"</b>" + ": "+s.getDescription();
		viewHolder.textView.setText(Html.fromHtml(t));
		url = s.getSmallUrl();
		viewHolder.imageURL = url;
		viewHolder.imageView.setTag(url);
		viewHolder.imageView.setImageDrawable(null); 
		new DownloadAsyncTask().execute(viewHolder);
		
		return convertView;
	}
	
	
	
	private class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, Boolean> {

		
		private ImageView Image;  
		private Bitmap av = null;  
		private String url;  
		
		protected Boolean doInBackground(ViewHolder... params) {
			//load image directly
			ViewHolder viewHolder = params[0];
			   boolean result = false;  
			try {
				Image=viewHolder.imageView;
				url= viewHolder.imageView.getTag().toString();
				URL imageURL= new URL(url);
				av = BitmapFactory.decodeStream(imageURL.openStream());
			    result = true;  
			} catch (IOException e) {
				// TODO: handle exception
			     e.printStackTrace();  
			}
			
			return result;
		}
		
		
		protected void onPostExecute(Boolean result) {
			   if(result && av != null) {  
				    if(Image.getTag().toString().equals(url)) {  
				     Image.setImageBitmap(av);  
				    }  
			
			   }
		}
	}

	public void setListItems(ArrayList<Photo> newList) {
	    list = newList;
	    notifyDataSetChanged();
	}
	
}


	
	
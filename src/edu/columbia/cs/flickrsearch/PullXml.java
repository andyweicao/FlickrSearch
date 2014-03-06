package edu.columbia.cs.flickrsearch;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class PullXml {
	// Parse the xml by using the XmlPullParser
	
	ArrayList<Photo> list;
	private Photo currentFeed = null;
	private String currentTag = null;
	//Call the input url to InputStream
	public ArrayList<Photo> parse(InputStream is)
			throws XmlPullParserException {
		XmlPullParser parser = XmlPullParserFactory.newInstance()
				.newPullParser();
		//Insert InputStream to the parser
		parser.setInput(is, "UTF-8");
		try {
			//Get event type
			int eventType = parser.getEventType();

				while (eventType != XmlPullParser.END_DOCUMENT) {
				
						//Set several cases to parse the data and save them into the list
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					list = new ArrayList<Photo>();
					break;
				
					//Identify each content type and save them
				case XmlPullParser.START_TAG:
					currentTag = parser.getName();
					if (currentTag.equalsIgnoreCase("photo")) {
						currentFeed = new Photo();
						String id = parser.getAttributeValue(null, "id");
						currentFeed.setId(id);
						String Datetaken = parser.getAttributeValue(null, "datetaken");
						currentFeed.setDatetaken(Datetaken);
						String Ownername = parser.getAttributeValue(null, "ownername");
						currentFeed.setOwnername(Ownername);
						String Farm = parser.getAttributeValue(null, "farm");
						currentFeed.setFarm(Farm);
						String Server = parser.getAttributeValue(null, "server");
						currentFeed.setServer(Server);
						String Secret = parser.getAttributeValue(null, "secret");
						currentFeed.setSecret(Secret);
						
					} else if (currentFeed != null) {
						
						if (currentTag.equalsIgnoreCase("description")) {
							currentFeed.setDescription(parser.nextText());
						} 
					}
					break;
				case XmlPullParser.END_TAG:
					currentTag = parser.getName();
					if (currentTag.equalsIgnoreCase("photo")
							&& currentFeed != null) {
						//Finish parsing information for one photo
						//Add it into the list
						list.add(currentFeed);
					}
					break;
				}
				//Prepare for the next photo
				eventType = parser.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Get the list in the end
		return list;

	}
}
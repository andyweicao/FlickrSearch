package edu.columbia.cs.flickrsearch;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class PullXml {
	// name of the XML tags
	
	ArrayList<Photo> list;
	private Photo currentFeed = null;
	private String currentTag = null;

	public ArrayList<Photo> parse(InputStream is)
			throws XmlPullParserException {
		XmlPullParser parser = XmlPullParserFactory.newInstance()
				.newPullParser();
		parser.setInput(is, "UTF-8");
		try {

			int eventType = parser.getEventType();

					while (eventType != XmlPullParser.END_DOCUMENT) {

				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					list = new ArrayList<Photo>();
					break;
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
						list.add(currentFeed);
					}
					break;
				}
				eventType = parser.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;

	}
}
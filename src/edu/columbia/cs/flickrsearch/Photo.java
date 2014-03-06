package edu.columbia.cs.flickrsearch;

public class Photo {

		//Set properties for each photo
		private String Datetaken;
		private String Ownername;
		private String Farm;
		private String Server;
		private String Id;
		private String Secret;
		private String Description;
		private String Url;


		
		//Use get to return each information
		//Use set to save each information
		
		
		public String getDatetaken() {
			return Datetaken;
		}

		public void setDatetaken(String Datetaken) {
			this.Datetaken = Datetaken;
		}

		public String getOwnername() {
			return Ownername;
		}

		public void setOwnername(String Ownername) {
			this.Ownername = Ownername;
		}
		
		
		public String getServer() {
			return Server;
		}

		public void setServer(String Server) {
			this.Server = Server;
		}
		
		public String getId() {
			return Id;
		}

		public void setId(String Id) {
			this.Id = Id;
		}
		public String getSecret() {
			return Secret;
		}

		public void setSecret(String Secret) {
			this.Secret = Secret;
		}
		public String getFarm() {
			return Farm;
		}

		public void setFarm(String Farm) {
			this.Farm = Farm;
		}
		
		public String getDescription(){
			return Description;
		}
		
		public void setDescription(String description){
			this.Description = description;
		}	
		
		
		//Get url of large view of each photo
		public String getLargeUrl(){
			this.Url= "http://farm" + this.Farm + ".staticflickr.com/"+this.Server+"/"+this.Id+"_"+this.Secret+"_c.jpg";
			return Url;
		}
		
		//Get url of small view of each photo
		public String getSmallUrl(){
			this.Url= "http://farm" + this.Farm + ".staticflickr.com/"+this.Server+"/"+this.Id+"_"+this.Secret+"_m.jpg";
			return Url;
		}
	
}

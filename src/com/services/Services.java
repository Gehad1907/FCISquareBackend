package com.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;

import com.models.DBConnection;
import com.models.UserModel;

@Path("/")
public class Services {

	/*
	 * @GET
	 * 
	 * @Path("/signup")
	 * 
	 * @Produces(MediaType.TEXT_HTML) public Response signUp(){ return
	 * Response.ok(new Viewable("/Signup.jsp")).build(); }
	 */

	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass) {
		UserModel user = UserModel.addNewUser(name, email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}
	

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}
	
	@POST
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id,
			@FormParam("lat") String lat, @FormParam("long") String lon) {
		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id), Double.parseDouble(lat), Double.parseDouble(lon));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello after editing";
		// Connection URL:
		// mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
	}
	//_______________________________________________________________________//
	@POST
	@Path("/follow")
	@Produces(MediaType.TEXT_PLAIN)
	public String follow(@FormParam("email1") String followeremail,
			@FormParam("email2") String followedemail, @FormParam("name") String name) {
		Boolean status = UserModel.addNewfolloer(followeremail, followedemail, name);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	//_____________________________________________________________________________//
	@POST
	@Path("/Unfollow")
	@Produces(MediaType.TEXT_PLAIN)
	public String unfollow(@FormParam("email1") String followeremail,
			@FormParam("email2") String followedemail) {
		Boolean status = UserModel.unfollow(followeremail, followedemail);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	//_____________________________________________________________________________//
		@POST
		@Path("/getfollowerslist")
		@Produces(MediaType.TEXT_PLAIN)
		public String getfollowerslist(@FormParam("email") String followeremail	) {
			UserModel model=new UserModel();
			String user = model.getfollowersList(followeremail);
			JSONObject json = new JSONObject();	
			json.put("followers list = ", user );
			return json.toJSONString();
		}
		//------------------------------------------------------------------------------//
		
		@POST
		@Path("/getUserPosition")
		@Produces(MediaType.TEXT_PLAIN)
		public String getUserPosition(@FormParam("id") String id ) {
			String status = UserModel.getUserPosition(Integer.parseInt(id));
			JSONObject json = new JSONObject();
			json.put("position", status );
			return json.toJSONString();
		}
}

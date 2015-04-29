package com.example.post;
 import com.example.userManagement.*;
 import com.example.Reply.*;

public class Post implements Comparable<Post>{
	
	private int postID;
	private String user;
	private String postMessage;
	private boolean isUserAnonymous;
	private String location;
	private String temp;
	private Reply[] replyToPost; 
	
	public Post(int postPK, String postMsg, String usr, String temp)
	{
		this.postID = postPK;
		this.postMessage = postMsg;
		this.user = usr;
		this.temp = temp;
		
	}
	
	/**
	 * This method is used to save a new post from a user in database server.
	 * Use can be anonymously posting this status, thus all the details 
	 * related to the post will be saved through this method call.
	 * @param username
	 * @param postMsg
	 * @param temp
	 * @param loc
	 * @param userAnonymous
	 * @return
	 */
	public int savePost(String username, String postMsg, String temp, String loc, boolean userAnonymous)
	{
		return getPostID();
	}
	
	/**
	 * This method will display all the posts to the user 
	 * on main activity (where all posts are displayed)
	 * @return
	 */
	public Post[] getAllPost()
	{
		Post[] postsList = null;
		
		return postsList;
	}

	public String getPostMessage() {
		return postMessage;
	}

	public void setPostMessage(String postMessage) {
		this.postMessage = postMessage;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public int getPostID() {
		return postID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
	}

	@Override
	public int compareTo(Post anotherPost) {
		
		int compareQuantity = ((Post) anotherPost).getPostID(); 
		//descending order
		return compareQuantity-this.postID;
 
		
	}

}

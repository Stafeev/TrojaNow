package com.example.userManagement;

public class User {
	
	private String firstName; 
	private String lastName;
	private String username;
	private String password;
	private boolean isActive;
	
	/**
	 * This method is used to create the user from the initial user screen.
	 * All the user info will be passed as arguments to this method and 
	 * saved in the database at server end.
	 * @param fName
	 * @param lName
	 * @param userName
	 * @param pwd
	 * @return
	 */
	public boolean createUser(String fName, String lName, String userName, String pwd)
	{
		return true;
	}
	
	/**
	 * This method is used to save the users updated details. 
	 * User can deactivate the account, that detail would be  
	 * reflected on server from this method call.
	 * @param username
	 * @param activeStatus
	 * @return
	 */
	public boolean saveUserInfo(String username, boolean activeStatus)
	{
		return true;
	}
	
	/**
	 * This method is used to validate the user on Login. 
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkUserLogin(String username, String password)
	{
		return true;
	}
	
	public User getUserDetails(String username)
	{
		return new User();
	}

}

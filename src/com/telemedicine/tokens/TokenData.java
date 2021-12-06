package com.telemedicine.tokens;

public class TokenData {

	private int userId;
	private int accountTypeId;
	private long expirationTime;

	public TokenData(int userId, int accountTypeId) {
	
		this.userId = userId;
		this.accountTypeId = accountTypeId;
		
		// 15 minutes from now
		expirationTime = System.currentTimeMillis() + 15 * 60 * 1000;
	}

	public int getUserId() {return userId;}

	public void setUserId(int userId) {this.userId = userId;}

	public int getAccountTypeId() {return accountTypeId;}

	public void setAccountTypeId(int accountTypeId) {this.accountTypeId = accountTypeId;}

	public long getExpirationTime() {return expirationTime;}

	public void setExpirationTime(long expirationTime) {this.expirationTime = expirationTime;}
}

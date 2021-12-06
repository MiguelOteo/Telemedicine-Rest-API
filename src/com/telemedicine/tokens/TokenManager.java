package com.telemedicine.tokens;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenManager {

	// Map of token (random string) to TokenData (userName, userId,
	// accountTypeId(patientId or userId) and expiration time)
	private Map<String, TokenData> tokenMap = new HashMap<>();

	// This class is a singleton and should not be instantiated directly!
	private static TokenManager instance = new TokenManager();

	public static TokenManager getInstance() {
		return instance;
	}

	// Private constructor so people know to use the getInstance() function instead
	private TokenManager() {}

	// Generates a token for the userName, stores that token along with an
	// expiration time, and then returns the token so clients can store it.
	public String putToken(int userId, int accountTypeId) {
		String token = UUID.randomUUID().toString();
		tokenMap.put(token, new TokenData(userId, accountTypeId));
		return token;
	}

	// Returns the userName mapped to the userName, or null
	// if the token isn't found or has expired.
	public TokenData getTokenData(String token) {

		if (tokenMap.containsKey(token)) {
			if (tokenMap.get(token).getExpirationTime() > System.currentTimeMillis()) {
				return tokenMap.get(token);
			} else {
				// the token has expired, delete it
				tokenMap.remove(token);
			}
		}
		return null;
	}
}

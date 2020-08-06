package twitter.DTO;

public class Auth {
	String userId;
	String accessToken;
	String accessTokenSecret;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public Auth(String userId, String accessToken, String accessTokenSecret) {
		this.userId = userId;
		this.accessToken = accessToken;
		this.accessTokenSecret = accessTokenSecret;
	}
}
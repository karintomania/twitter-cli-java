package twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter.DTO.Auth;
import twitter.DTO.AuthRepository;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class Authenticator {

	// トークンの取得・永続化用レポジトリ
	private AuthRepository ar = new AuthRepository();

	// ログイン済みならトークンをセット
	// 未ログインの場合、ログインを促す
	public void init(){

		// ユーザー名(@xxxのxxx)を適宜変えてください
		Auth auth = ar.getAuth("put-user-name-here");

		if(null == auth){
			System.out.println("use 'login' command to login.");
		}else{
			AccessToken at = new AccessToken(auth.getAccessToken(), auth.getAccessTokenSecret());
			Twitter twitter = TwitterFactory.getSingleton();
			twitter.setOAuthAccessToken(at);
		}

	}


	// OAuth認証
	public boolean authenticate(){
    // The factory instance is re-useable and thread safe.
    Twitter twitter = TwitterFactory.getSingleton();
	
	try{
		
		RequestToken requestToken = twitter.getOAuthRequestToken();
		
		AccessToken accessToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while (null == accessToken) {
			System.out.println("Open the following URL and grant access to your account:");
			System.out.println(requestToken.getAuthorizationURL());
			System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
			String pin = br.readLine();
		try{
			if(pin.length() > 0){
				accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			}else{
				accessToken = twitter.getOAuthAccessToken();
			}
		} catch (TwitterException te) {
			if(401 == te.getStatusCode()){
				System.out.println("Unable to get the access token.");
			}else{
				te.printStackTrace();
			}
		}
		}

		//persist to the accessToken for future reference.
		storeAccessToken(twitter.verifyCredentials().getScreenName(),accessToken);
	}catch(TwitterException te){
		te.printStackTrace();
	}catch(IOException ioe){
		ioe.printStackTrace();
	}

	return true;
  }


  // トークンの永続化
  private void storeAccessToken(String userName, AccessToken accessToken){
	Auth auth = new Auth(userName, accessToken.getToken(), accessToken.getTokenSecret());
	ar.insert(auth);
  }
}
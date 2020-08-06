package twitter;

import java.util.List;
import java.util.Scanner;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterService {

    Twitter twitter = TwitterFactory.getSingleton();
	
	// タイムライン表示
	public void showTimeLine(){
		Scanner in = new Scanner(System.in);
		String s = "";
		int page = 1;
		String commandMode = "TimeLine Mode (next:n, back:b, quit:q)>";

		// タイムライン表示
		showPagedTimeLine(page);

		do{
			System.out.print(commandMode);
			s = in.nextLine();
			
			switch (s){
				// タイムラインを終了
				case "q":
					break;
				//　次のページを表示
				case "n":
					showPagedTimeLine(++page);
					break;
				//　前のページを表示
				case "b":
					showPagedTimeLine((page > 1) ? --page : 0);
					break;
				default: 
					System.out.println(s + " command is not found.");
			}

			
		}while(!s.equals("q"));

		// Dont close the scanner.
		// It will close the scanner of App.java as well
		// in.close();
	}

	// ツイートを5件ずつ表示
	public void showPagedTimeLine(int page){

		Paging paging = new Paging(page, 5);
		try{
			List<Status> statuses = twitter.getHomeTimeline(paging);
			statuses.stream().forEach(status -> System.out.println(formatTweet(status)));
		}catch(TwitterException te){
			te.printStackTrace();
		}
	}

	// 表示用に整形
	public String formatTweet(Status s){
		String tweetText = s.getUser().getName() + " :\n\n" + s.getText();
		tweetText += "\n----------------------------";
		return tweetText;
	}

	// ツイートする
	public void tweet(){

		Scanner in = new Scanner(System.in);
		String tweetMode = "Tweet Mode >";
		System.out.print(tweetMode);

		String s = in.nextLine();

		try{
			twitter.updateStatus(s);
		}catch(TwitterException te){
			te.printStackTrace();
		}

		System.out.println(s);

		// Dont close the scanner.
		// It will close the scanner of App.java as well
		// in.close();

	}

}
package cl.citiaps.twitter.streaming;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bson.Document;

import org.apache.commons.io.IOUtils;


import twitter4j.*;

public class TwitterStreaming {

	private final TwitterStream twitterStream;
	private Set<String> keywords;
	public static MongoDB mongodb = new MongoDB();

	private TwitterStreaming() {
		this.twitterStream = new TwitterStreamFactory().getInstance();
		this.keywords = new HashSet<>();
		loadKeywords();
	}

	private void loadKeywords() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			keywords.addAll(IOUtils.readLines(classLoader.getResourceAsStream("words.dat"), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		StatusListener listener = new StatusListener() {

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			@Override
			public void onStallWarning(StallWarning arg0) {

			}

			@Override
			public void onStatus(Status status) {
				//obtener la cantidad de los n documentos en la bd de mongo
				//cada cierta cantidad de elementos reconstruir indices
				//consulta: db.twitterCollection.count()
				//Creacion del documento y se agrega a la coleccion

				if(status.getLang().equals("es"))
				{
				    long original_user_id = 0;

				    if(status.isRetweet()){
				        status = status.getRetweetedStatus();
                    }

                    String country;
                    try {
                        country = status.getUser().getLocation();
                    } catch (NullPointerException e){
                        country = "none";
                    }

					String profile_url;
                    profile_url = "https://twitter.com/" + status.getUser().getScreenName();

                    String latitude;
                    String longitude;
                    try {
                        latitude = Double.toString(status.getGeoLocation().getLatitude());
                        longitude = Double.toString(status.getGeoLocation().getLongitude());
                    } catch (NullPointerException e){
                        latitude = "none";
                        longitude = "none";
                    }
					System.out.println(status.getText());
                    Document tweet = new Document("id",status.getId())
                            .append("text", status.getText())
                            .append("tweet_country",country)
                            .append("tweet_latitude",latitude)
                            .append("tweet_longitude",longitude)
                            .append("retweet", status.getRetweetCount())
                            .append("user_id", status.getUser().getId())
                            .append("original_user_id",original_user_id)
                            .append("user_name", status.getUser().getName())
                            .append("user_screen_name",status.getUser().getScreenName())
                            .append("user_mentions",status.getUserMentionEntities().length)
                            .append("user_favourites_count", status.getUser().getFavouritesCount())
                            .append("user_followers_count",status.getUser().getFollowersCount())
                            .append("user_friend_count",status.getUser().getFriendsCount())
                            .append("user_listed_count",status.getUser().getListedCount())
                            .append("user_statuses_count",status.getUser().getStatusesCount())
                            .append("user_url",profile_url)
                            .append("user_profile_image_url",status.getUser().getOriginalProfileImageURL());
                    mongodb.getMongoCol().insertOne(tweet);

                    //Se obtienen los retweets del tweet original si es que este tiene retweets
                    if(status.getRetweetCount() > 0){

                        try {
                            Twitter twitter = new TwitterFactory().getInstance();
                            List<Status> statuses = twitter.getRetweets(status.getId());
                            for (Status s : statuses) {
                                String profile_url_s;
                                profile_url_s = "https://twitter.com/" + s.getUser().getScreenName();

                                String countryS;
                                try {
                                    countryS = s.getUser().getLocation();
                                } catch (NullPointerException e){
                                    countryS = "none";
                                }

                                String latitudeS;
                                String longitudeS;
                                try{
                                    latitudeS = Double.toString(s.getGeoLocation().getLatitude());
                                    longitudeS = Double.toString(s.getGeoLocation().getLongitude());
                                }
                                catch (NullPointerException e){
                                    latitudeS = "none";
                                    longitudeS = "none";
                                }

                                Document tweet_s = new Document("id",s.getId())
                                        .append("text", s.getText())
                                        .append("tweet_country",countryS)
                                        .append("tweet_latitude",latitudeS)
                                        .append("tweet_longitude",longitudeS)
                                        .append("retweet", s.getRetweetCount())
                                        .append("user_id", s.getUser().getId())
                                        .append("original_user_id",s.getRetweetedStatus().getUser().getId())
                                        .append("user_name", s.getUser().getName())
                                        .append("user_screen_name",s.getUser().getScreenName())
                                        .append("user_mentions",s.getUserMentionEntities().length)
                                        .append("user_favourites_count", s.getUser().getFavouritesCount())
                                        .append("user_followers_count",s.getUser().getFollowersCount())
                                        .append("user_friend_count",s.getUser().getFriendsCount())
                                        .append("user_listed_count",s.getUser().getListedCount())
                                        .append("user_statuses_count",s.getUser().getStatusesCount())
                                        .append("user_url",profile_url_s)
                                        .append("user_profile_image_url",s.getUser().getOriginalProfileImageURL());
                                mongodb.getMongoCol().insertOne(tweet_s);

                            }
                        } catch (TwitterException te) {
                            te.printStackTrace();
                        }
                    }
				}


	
			}
		};

		FilterQuery fq = new FilterQuery();

		fq.track(keywords.toArray(new String[0]));

		this.twitterStream.addListener(listener);
		this.twitterStream.filter(fq);
	}
	
	public static void main(String[] args) {
		new TwitterStreaming().init();

	}

}

class IllegalDateException extends IllegalArgumentException {
  IllegalDateException(String message) { super(message); }
}
class IllegalUserException extends IllegalArgumentException {
  IllegalUserException(String message) { super(message); }
}
class IllegalTweetException extends IllegalArgumentException {
  IllegalTweetException(String message) { super(message); }
}
class IllegalTweetServerException extends IllegalArgumentException {
  IllegalTweetServerException(String message) { super(message); }
}

class DateTime {
  int day;
  int month;
  int year;

  DateTime(int day, int month, int year) {
	// Fill
  }
}

class User {
  String username;
  String fullname;

  User(String username, String fullname) {
    // Fill
  }
}

class TextTweet {
  User user;
  DateTime timestamp;
  String content;
  String tweetId;
  int likes;

  TextTweet(User user, DateTime timestamp, String content, String tweetId, int likes) {
    // Fill
  }
}

interface TweetList {

}

class TLEmpty implements TweetList {
  TLEmpty() { }
}

class TLLink implements TweetList {
  TextTweet first;
  TweetList rest;
  TLLink(TextTweet first, TweetList rest) {
    this.first = first;
    this.rest = rest;
  }
}

class TweetServer {
  TweetList tweets;
  User[] user;
  TweetServer(TweetList tweets, User[] users) {
    // Insert checks here
  }
}

class ExamplesTweets {
  // Write your tests here
}

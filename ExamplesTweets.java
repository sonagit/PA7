/* Overview:
http://www.ccs.neu.edu/javalib/Tester/Overview.html

Directory:
http://www.ccs.neu.edu/javalib/World/Docs/tester/tester/Tester.html */
import tester.*;

/**
 * Date exception class
 *
 */
class IllegalDateException extends IllegalArgumentException {
  IllegalDateException(String message) { super(message); }
}
/**
 * User Exception Class
 */
class IllegalUserException extends IllegalArgumentException {
  IllegalUserException(String message) { super(message); }
}
/**
 * Tweet Exception class
 */
class IllegalTweetException extends IllegalArgumentException {
  IllegalTweetException(String message) { super(message); }

}
/**
 * TweetServer Exception class
 */
class IllegalTweetServerException extends IllegalArgumentException {
  IllegalTweetServerException(String message) { super(message); }
}
/**
 * DateTime
 */
class DateTime {
  int day;
  int month;
  int year;

  DateTime(int day, int month, int year) {
    switch (month) {
      case 1:
      case 3:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12:
        this.day = new checkRange(day, 1, 31, "Invalid date");
        break;
      case 4:
      case 6:
      case 9:
      case 11:
        this.day = new checkRange(day, 1, 30, "Invalid date");
        break;
      case 2:
        if (((year % 4 == 0) && !(year % 100 == 0))
          || (year % 400 == 0)) {
          this.day = new checkRange(day, 1, 29, "Invalid date");
          break;
        }
        else {
          this.day = new checkRange(day, 1, 28, "Invalid date");
          break;
        }
    }

    // month should be 1-12 inclusive
    this.month  = new checkRange(month, 1, 12, "Invalid date");

    // Year should be non-negative
    if (year >= 0) {
      this.year = year;
    }
    else {
      throw new IllegalArgumentException("Invalid date");
    }
  }
  /**
   * Checks if val is in range
   * @param  int    val           [description]
   * @param  int    min           [description]
   * @param  int    max           [description]
   * @param  String msg           [description]
   * @return        [description]
   */
  int checkRange(int val, int min, int max, String msg) {
    if (val >= min && val <= max) {
      return val;
    }
    else {
      throw new IllegalArgumentException(msg);
    }
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
  // will need new methods
  // count?
  // duplicate ID?

  /**
   * Test to see if a tweet with a certain tweetID exists
   *
   * @param String id
   * @return boolean
   */
  boolean idExists(String id) {

    // create an ID query
    IdQuery iq = new IdQuery(id);
    if(tweets.count(iq) > 0) { return true; }
    else { return false; }
  }
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
  boolean testException(Tester t) {
    DateTime gd1 = new DateTime(12,12,2012);
    DateTime gd2 = new DateTime(2,12,1999);
    DateTime bd1 = new DateTime(-12,12,2012);
    DateTime bd2 = new DateTime("jan",12,2012);
    t.checkConstructorException(
      new IllegalDateException("Invalid date"),
      "DateTime",
      12,12,2012);
    return true;
  }
}

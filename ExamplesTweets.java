/* Overview:
http://www.ccs.neu.edu/javalib/Tester/Overview.html

Directory:
http://www.ccs.neu.edu/javalib/World/Docs/tester/tester/Tester.html */
import tester.*;

/**
 * Date exception class
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

  // fields
  int day;
  int month;
  int year;

  /**
   * constructor initializes all fields while checking for correct ranges
   */
  DateTime(int day, int month, int year) {
    switch (month) {
      // Months with 31 days
      case 1:
      case 3:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12:
        this.day = new Utils().checkRange(day, 1, 31, "Invalid date");
        break;
      // Months with 30 days
      case 4:
      case 6:
      case 9:
      case 11:
        this.day = new Utils().checkRange(day, 1, 30, "Invalid date");
        break;

      // handle leap years
      case 2:
        if (((year % 4 == 0) && !(year % 100 == 0))
          || (year % 400 == 0)) {
          this.day = new Utils().checkRange(day, 1, 29, "Invalid date");
          break;
        }
        else {
          this.day = new Utils().checkRange(day, 1, 28, "Invalid date");
          break;
        }
    }

    // month should be 1-12 inclusive
    this.month  = new Utils().checkRange(month, 1, 12, "Invalid date");

    // Year should be non-negative
    if (year >= 0) {
      this.year = year;
    }
    else {
      throw new IllegalDateException("Invalid date");
    }
  }
}

/**
 * user class
 */
class User {

  // fields
  String username;
  String fullname;

  /**
   * constructor initializes all fields
   */
  User(String username, String fullname) {
    // flag for names being valid
    boolean validNames = true;

    if(username.length()<4
      || username.length()>30
      || fullname.length()<1
      || fullname.length()>100) {
        validNames = false;
    }

    for(int i=0; i<username.length(); i+=1) {
      if(!((username.charAt(i)>='a' && username.charAt(i)<='z')
      || (username.charAt(i)>='A' && username.charAt(i)<='Z')
      || (username.charAt(i)>='0' && username.charAt(i)<='9'))) {
        validNames = false;
      }
    }
    if(validNames) {
      this.username = username;
      this.fullname = fullname;
    }
    else { throw new IllegalUserException("Invalid User"); }
  }
}
/**
 * TextTweet class
 */
class TextTweet {

  // fields
  User user;
  DateTime timestamp;
  String content;
  String tweetId;
  int likes;

  /**
   * constructor initializes all fields
   */
  TextTweet(User user, DateTime timestamp, String content, String tweetId, int likes) {
    boolean validFields = true;
    if(content.length()<1 || content.length()>100) { validFields = false; }
    if(likes<0) { validFields = false; }
    for(int i=0; i<tweetId.length(); i+=1) {
      if(tweetId.charAt(i)<'0' || tweetId.charAt(i)>'9') {
        validFields = false;
        break;
      }
    }
    if(validFields) {
      this.user = user;
      this.timestamp = timestamp;
      this.content = content;
      this.tweetId = tweetId;
    }
    else { throw new IllegalTweetException("Invalid tweet"); }
  }
}

/**
 * TweetList interface for our TLLink and TLEmpty classes
 */
interface TweetList {
  // will need new methods
  // count?
  // duplicate ID?

  // function prototypes
  //boolean idExists(String id);
  //int count(IQuery q);

}

/**
 * TLEmpty class returns base cases for all functions
 */
class TLEmpty implements TweetList {
  /**
   * constructor initializes all fields
   */
  TLEmpty() { }
}

/**
 * TLLink class is for creating a TweetList
 */
class TLLink implements TweetList {

  // fields
  TextTweet first;
  TweetList rest;

  /**
   * constructor initializes all fields
   */
  TLLink(TextTweet first, TweetList rest) {
    this.first = first;
    this.rest = rest;
  }

  /**
   * Test to see if a tweet with a certain tweetID exists
   *
   * @param String id
   * @return boolean
   */
/*  boolean idExists(String id) {

    // create an ID query
    IdQuery iq = new IdQuery(id);
    if(tweets.count(iq) > 0) { return true; }
    else { return false; }
  }//*/

  /**
   * counts the number of instances in list
   * @param  query
   * @return int
   */
/*  public int count(int query){
    if(q.matches(tweet)){
      return 1 + rest.count(q);
    } else {
      return rest.count(q);
    }
  }//*/

}

class TweetServer {
  TweetList tweets;
  User[] user;
  TweetServer(TweetList tweets, User[] users) {
    // Insert checks here
  }
}

class Utils {
  /**
   * Checks if val is in range
   * @param  int    val
   * @param  int    min
   * @param  int    max
   * @param  String msg
   * @return int
   */
  int checkRange(int val, int min, int max, String msg) throws IllegalDateException {
    if (val >= min && val <= max) {
      return val;
    }
    else {
      throw new IllegalDateException(msg);
    }
  }

}
class ExamplesTweets {
  User gu1 = new User("stubaby", "Stu Baby");
  User gu2 = new User("kamala", "CAMALA");
  User gu3 = new User("zylvan", "Zylvanio Sonatina");
  DateTime gd1 = new DateTime(12,12,2012);
  DateTime gd2 = new DateTime(2,12,1999);
  DateTime gd3 = new DateTime(1,1,1);
  TextTweet gt1 = new TextTweet(gu1, gd1, "Con...tent", "1", 0001);
  TextTweet gt2 = new TextTweet(gu2, gd2, "Connnnn...tent", "99999", 0011);
  TextTweet gt3 = new TextTweet(gu3, gd3, "Con...tttttent", "00100", 0101);

  boolean testDateTime(Tester t) {
    return  t.checkExpect(new DateTime(12,12,2012), gd1) &&
          t.checkExpect(new DateTime(2,12,1999), gd2) &&
          t.checkExpect(new DateTime(1,1,1), gd3);
  }
  boolean testDateException(Tester t) {
    t.checkConstructorException(
      new IllegalDateException("Invalid date"),
      "DateTime",
      50,12,2012);
    t.checkConstructorException(
      new IllegalDateException("Invalid date"),
      "DateTime",
      29,2,2011);
    t.checkConstructorException(
      new IllegalDateException("Invalid date"),
      "DateTime",
      29,2,2011);
    return true;
  }

  boolean testUser(Tester t) {
    return  t.checkExpect(new User("stubaby", "Stu Baby"), gu1) &&
            t.checkExpect(new User("kamala", "CAMALA"), gu2) &&
            t.checkExpect(new User("zylvan", "Zylvanio Sonatina"), gu3);
  }//*/
  boolean testUserException(Tester t) {
    t.checkConstructorException(
      new IllegalUserException("Invalid User"),
      "User",
      "zyl", "Zylvanio");
    t.checkConstructorException(
      new IllegalUserException("Invalid User"),
      "User",
      "zyl-", "Zylvanio Zylvanio");
    t.checkConstructorException(
      new IllegalUserException("Invalid User"),
      "User",
      "zyl", "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
    return true;
  }

  boolean testTweet(Tester t) {
    return  t.checkExpect(new TextTweet(gu1, gd1, "Con...tent", "1", 0001), gt1) &&
            t.checkExpect(new TextTweet(gu2, gd2, "Connnnn...tent", "99999", 0011), gt2) &&
            t.checkExpect(new TextTweet(gu3, gd3, "Con...tttttent", "00100", 0101), gt3);
  }
  boolean testTweetException(Tester t) {
    t.checkConstructorException(
    new IllegalTweetException("Invalid tweet"),
    "TextTweet",
    gu1, gd1, "These are my thoughts", "wonky-id", 1000);
    t.checkConstructorException(
    new IllegalTweetException("Invalid tweet"),
    "TextTweet",
    gu1, gd1, "These are all of my thoughts. They aren't much, but they certainly extend past the measly 140 character limit placed on my by the always-watching bots of the internet.", "999999", 1000);
    t.checkConstructorException(
    new IllegalTweetException("Invalid tweet"),
    "TextTweet",
    gu1, gd1, "These are my thoughts", "1", -1000);
    return true;
  }
}

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

/******************************************************************************
 * DateTime
 *****************************************************************************/
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

/******************************************************************************
 * user class
 *****************************************************************************/
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

    // initialize fields iff conditions are met
    if(validNames) {
      this.username = username;
      this.fullname = fullname;
    }
    else { throw new IllegalUserException("Invalid User"); }
  }
}
/******************************************************************************
 * TextTweet class
 *****************************************************************************/
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

    // initialize fields iff conditions are met
    if(validFields) {
      this.user = user;
      this.timestamp = timestamp;
      this.content = content;
      this.tweetId = tweetId;
    }
    else { throw new IllegalTweetException("Invalid Tweet"); }
  }
}

/******************************************************************************
 * TweetList interface for our TLLink and TLEmpty classes
 *****************************************************************************/
interface TweetList {
  // function prototypes
  boolean idExists(String id);
  int count(String id);
  String getIds();
}

/******************************************************************************
 * TLEmpty class returns base cases for all functions
 *****************************************************************************/
class TLEmpty implements TweetList {
  /**
   * constructor initializes all fields
   */
  TLEmpty() { }

  /**
   * Return base case of idExists
   * @param  String id
   * @return int
   */
  public boolean idExists(String id) {
    return false;
  }

  /**
   * Return base case of count
   * @param  String id
   * @return int
   */
  public int count(String id) {
    return 0;
  }

  /**
   * Return base case of getIds
   * @return String
   */
  public String getIds() {
    return " ";
  }
}

/******************************************************************************
 * TLLink class is for creating a TweetList
 *****************************************************************************/
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
  public boolean idExists(String id) {
    if(this.first.tweetId == id) { return true; }
    else {
      this.rest.idExists(id);
      return false;
    }
  }//*/

  /**
   * counts the number of instances in list
   * @param String id
   * @return int
   */
  public int count(String id){
    if(this.first.tweetId == id) {
      return 1 + rest.count(id);
    }
    else { return rest.count(id); }
  }//*/

  /**
   * returns a String with all the IDs separated by spaces
   *
   * @return String
   */
  public String getIds() {
    return this.first.tweetId + "-" + this.rest.getIds();
  }
}

/******************************************************************************
 * TweetServer class
 *****************************************************************************/
class TweetServer {
  TweetList tweets;
  User[] users;
  TweetServer(TweetList tweets, User[] users) {
    // flag
    boolean validFields = true;

    // check for duplicate users or tweets
    if ( (new Utils().userDup(users))
      || (new Utils().tweetIdDup(tweets)) ) {
      validFields = false;
    }

    // initialize fields iff conditions are met
    if (validFields) {
      this.users = users;
      this.tweets = tweets;
    }
    else { throw new IllegalTweetServerException("Invalid Server"); }
  }
}

/******************************************************************************
 * Utility functions
******************************************************************************/
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

  /**
   * method to check if the user array contains duplicate users
   *
   * @param  Users[] users
   * @return boolean
   */
  boolean userDup(User[] users) {
    boolean duplicate = false;
    // iterate through the array and check each user against the others
    for (int j=0; j<users.length; j++) {
      for (int k=j+1; k<users.length; k++) {
        if ((k!=j) && users[j].username.equals(users[k].username)) {
          duplicate = true;
        }
      }
    }
    return duplicate;
  }

  /**
   * Check tweetlist for duplicate IDs
   * @param  TweetList tl
   * @return boolean
   */
  boolean tweetIdDup(TweetList tl) {
    boolean duplicate = false;
    String ids[] = tl.getIds().split("-");

    for (int j=0; j<ids.length; j++) {
      for (int k=j+1; k<ids.length; k++) {
        if ((k!=j) && ids[j].equals(ids[k])) {
          duplicate = true;
        }
      }
    }
    return duplicate;
  }
}
/******************************************************************************
 * Testing Tiiiiiiiiiiiime!
 *****************************************************************************/
class ExamplesTweets {
  User user1 = new User("stubaby", "Stu Baby");
  User user2 = new User("kamala", "CAMALA");
  User user3 = new User("zylvan", "Zylvanio Sonatina");
  User[] ua1 = {user1, user2, user3};
  User[] ua2 = {user1, user2, user3, user3};

  DateTime dt1 = new DateTime(12,12,2012);
  DateTime dt2 = new DateTime(2,12,1999);
  DateTime dt3 = new DateTime(1,1,1);

  TextTweet tt1 = new TextTweet(user1, dt1, "Con...tent", "1", 0001);
  TextTweet tt2 = new TextTweet(user2, dt2, "Connnnn...tent", "99999", 0011);
  TextTweet tt3 = new TextTweet(user3, dt3, "Con...tttttent", "00100", 0101);
  TextTweet tt4 = new TextTweet(user2, dt3, "Con...tttttent", "00100", 0101);

  TweetList tl =  new TLLink(tt1,
                  new TLLink(tt2,
                  new TLLink(tt3,
                  new TLEmpty())));
  TweetList tl1 = new TLLink(tt1, tl);
  TweetList tl2 = new TLLink(tt4, tl);

  TweetServer ts1 = new TweetServer(tl, ua1);
  TweetServer ts2 = new TweetServer(new TLEmpty(), new User[] {});

  boolean testDateTime(Tester t) {
    return  t.checkExpect(new DateTime(12,12,2012), dt1) &&
          t.checkExpect(new DateTime(2,12,1999), dt2) &&
          t.checkExpect(new DateTime(1,1,1), dt3);
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
    return  t.checkExpect(new User("stubaby", "Stu Baby"), user1) &&
            t.checkExpect(new User("kamala", "CAMALA"), user2) &&
            t.checkExpect(new User("zylvan", "Zylvanio Sonatina"), user3);
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
    return  t.checkExpect(new TextTweet(user1, dt1, "Con...tent", "1", 0001), tt1) &&
            t.checkExpect(new TextTweet(user2, dt2, "Connnnn...tent", "99999", 0011), tt2) &&
            t.checkExpect(new TextTweet(user3, dt3, "Con...tttttent", "00100", 0101), tt3);
  }
  boolean testTweetException(Tester t) {
    t.checkConstructorException(
    new IllegalTweetException("Invalid Tweet"),
    "TextTweet",
    user1, dt1, "These are my thoughts", "wonky-id", 1000);
    t.checkConstructorException(
    new IllegalTweetException("Invalid Tweet"),
    "TextTweet",
    user1, dt1, "These are all of my thoughts. They aren't much, but they certainly extend past the measly 140 character limit placed on my by the always-watching bots of the internet.", "999999", 1000);
    t.checkConstructorException(
    new IllegalTweetException("Invalid Tweet"),
    "TextTweet",
    user1, dt1, "These are my thoughts", "1", -1000);
    return true;
  }

  boolean testServer(Tester t) {
    return  t.checkExpect(new TLLink(tt1, tl), tl1) &&
            t.checkExpect(new TweetServer(tl, ua1), ts1) &&
            t.checkExpect(new TweetServer(new TLEmpty(), new User[] {}), ts2);
  }
  boolean testTweetServerException(Tester t) {
    t.checkConstructorException(
    new IllegalTweetServerException("Invalid Server"),
    "TweetServer",
    tl1, // duplicate tweets
    ua1);
    t.checkConstructorException(
    new IllegalTweetServerException("Invalid Server"),
    "TweetServer",
    tl,
    ua2); // duplicate user
    t.checkConstructorException(
    new IllegalTweetServerException("Invalid Server"),
    "TweetServer",
    tl2, // duplicate tweetId in tweetlist
    ua1);
    return true;
  }
}

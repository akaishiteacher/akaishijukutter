package net.akaishi_teacher.akaishijukutter;


/**
 * ページのアイテム.
 */
public class PageItem {
    
    public static final int TWEET = 0;
    public static final int DM = 1;
    
    public static final int HOME = 0;
    public static final int REPLY = 1;
    
    public static final int DMBODY = 0;
    
    /** ページ名. */
    public String title;
    /** Fragment の種類. */
    public int fragmentKind;
    
    public int TweetKind;

}
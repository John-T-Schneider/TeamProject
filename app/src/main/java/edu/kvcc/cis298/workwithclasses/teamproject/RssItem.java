package edu.kvcc.cis298.workwithclasses.teamproject;

/**
 * Created by John on 11/17/2015.
 */
public class RssItem {

    //Gets the title of the article and its corrisponding link to the content
    private final String title;
    private final String link;

    public RssItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

}

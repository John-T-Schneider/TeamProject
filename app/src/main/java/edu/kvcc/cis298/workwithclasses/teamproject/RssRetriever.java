package edu.kvcc.cis298.workwithclasses.teamproject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.SyncStateContract;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

/**
 * Created by John on 11/17/2015.
 */
public class RssRetriever extends IntentService {

    //retrieves the information form the rss feed.
    //Only goes to pcworld.com right now because im not sure how to change or add new feeds quite yet
    public static final String TAG = "RssApp";
    private static final String RSS_LINK = "http://pcworld.com/index.rss";
    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";

    public RssRetriever() {super("RssRetriever");}

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Service started");
        List<RssItem> rssItems = null;
        try {
            RssParser parser = new RssParser();
            rssItems = parser.parse(getInputStream(RSS_LINK));
        } catch (XmlPullParserException e) {
            Log.w(e.getMessage(), e);
        } catch (IOException e) {
            Log.w(e.getMessage(), e);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }

    public InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.w(TAG, "Exception while retrieving the input stream", e);
            return null;
        }
    }







}

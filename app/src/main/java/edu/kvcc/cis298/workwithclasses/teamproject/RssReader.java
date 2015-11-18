package edu.kvcc.cis298.workwithclasses.teamproject;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 11/17/2015.
 */
public class RssReader extends Fragment implements OnItemClickListener {


    //uses other fragments to put the chosen rss feed on the screen so it can be read

    private ListView listView;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.listoffeeds, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnClickListener(this);
        startService();
        return view;


       // return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void startService() {
        Intent intent = new Intent(getActivity(), RssRetriever.class);
        intent.putExtra(RssRetriever.RECEIVER, resultReceiver);
        getActivity().startService(intent);
    }

    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssRetriever.ITEMS);
            if (items != null) {
                RssModifier adapter = new RssModifier(getActivity(), items);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "An error occured while downloading the rss feed.",
                        Toast.LENGTH_LONG).show();
            }
        };
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RssModifier adapter = (RssModifier) parent.getAdapter();
        RssItem item = (RssItem) adapter.getItem(position);
        Uri uri = Uri.parse(item.getLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}

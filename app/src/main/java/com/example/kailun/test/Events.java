package com.example.kailun.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by kailun on 2016/5/27.
 */
public class Events extends Fragment {
    private ListView eventListView;
    private MyAdapter2 adapter;
    private Firebase myFirebaseRef;

    ArrayList<CustomEventList> arrayList = new ArrayList<>();
    private static final String FIREBASE_URL = "https://radiant-inferno-4091.firebaseio.com/Events";

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_events, container, false);
        Firebase.setAndroidContext(view.getContext());
        eventListView = (ListView) view.findViewById(R.id.eventList);
        myFirebaseRef = new Firebase(FIREBASE_URL);
        initListView();
        // Inflate the layout for this fragment
        return view;
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        initListView();



    }*/
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void initListView(){

        try{


            adapter = new MyAdapter2(view.getContext(),arrayList);
            eventListView.setAdapter(adapter);

            //get date from firebase
            myFirebaseRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                    System.out.println(snapshot.getValue());
                    EventsChat newEvents = snapshot.getValue(EventsChat.class);
                    arrayList.add(new CustomEventList(snapshot.getKey(),newEvents.getName(),newEvents.getLocation(),newEvents.getDate()));
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    //System.out.println();



                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }



    }

}


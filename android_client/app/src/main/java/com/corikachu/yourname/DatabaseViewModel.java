package com.corikachu.yourname;

import com.corikachu.yourname.models.DTOFeed;
import com.corikachu.yourname.models.DTOSuggestions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Default File Header.
 * Need a comments!
 */

public class DatabaseViewModel {

    private static DatabaseViewModel instance;

    public static final String FEEDS = "feeds";
    public static final String SUGGESTIONS = "suggestions";
    public static final String RATING = "rating";
    private long lastFeedId = 0;
    private long lastSuggestionId = 0;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference feedRef;

    private Map<String, DTOFeed> updateFeeds = new HashMap<>();
    private Map<String, Object> updateSuggestions = new HashMap<>();

    private DatabaseViewModel() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        feedRef = databaseReference.child(FEEDS);

        Query updateIdQuery = feedRef.orderByChild("id").limitToLast(1);
        updateIdQuery.addChildEventListener(new FeedIdUpdateEventListener());
    }

    public static DatabaseViewModel getInstance () {
        if ( instance == null )
            instance = new DatabaseViewModel();
        return instance;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public DatabaseReference getFeedRef() {
        return feedRef;
    }

    public long getLastFeedId() {
        return lastFeedId;
    }

    public void updateFeed(long updateFeedId, DTOFeed feed) {
        updateFeeds.put(String.valueOf(updateFeedId), feed);
        feedRef.setValue(updateFeeds);
        updateFeeds.clear();
    }

    public void addSuggestion(long updateFeedId, final DTOSuggestions suggestions) {
        final String suggestionValue = String.valueOf(updateFeedId) + "/" + SUGGESTIONS;
        Query updateSuggestionIdQuery = feedRef.child(suggestionValue).orderByChild("id").limitToLast(1);
        updateSuggestionIdQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DTOSuggestions suggestion = dataSnapshot.getValue(DTOSuggestions.class);
                lastSuggestionId = suggestion.getId();

                updateSuggestions.put(suggestionValue + String.valueOf(lastSuggestionId + 1), suggestions);
                feedRef.updateChildren(updateSuggestions);
                updateSuggestions.clear();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateSuggestion(long updateFeedId, long updateSuggestionId, final DTOSuggestions suggestions) {
        final String suggestionValue = String.valueOf(updateFeedId) + "/" + SUGGESTIONS + String.valueOf(updateSuggestionId);

        updateSuggestions.put(suggestionValue, suggestions);
        feedRef.updateChildren(updateSuggestions);
        updateSuggestions.clear();
    }

    private class FeedIdUpdateEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            DTOFeed feed = dataSnapshot.getValue(DTOFeed.class);
            lastFeedId = feed.getId();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    private class SuggestionIdUpdateEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

}

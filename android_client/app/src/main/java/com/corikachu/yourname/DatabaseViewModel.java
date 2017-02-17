package com.corikachu.yourname;

import com.corikachu.yourname.models.DTOFeed;
import com.corikachu.yourname.models.DTORating;
import com.corikachu.yourname.models.DTOSuggestion;
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

    private Map<String, Object> feedHashMap = new HashMap<>();
    private Map<String, Object> suggestionHashMap = new HashMap<>();

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

    public void addFeed(DTOFeed feed) {
        feedHashMap.put(String.valueOf(lastFeedId + 1), feed);
        feed.setId(lastFeedId + 1);
        feedRef.updateChildren(feedHashMap);
        feedHashMap.clear();
    }

    public void updateFeed(long updateFeedId, DTOFeed feed) {
        feedHashMap.put(String.valueOf(updateFeedId), feed);
        feed.setId(updateFeedId);
        feedRef.updateChildren(feedHashMap);
        feedHashMap.clear();
    }

    public void addSuggestion(final long targetFeedId, final DTOSuggestion newSuggestion) {
        final String suggestionValue = String.valueOf(targetFeedId) + "/" + SUGGESTIONS + "/";
        final Query updateSuggestionIdQuery = feedRef.child(suggestionValue).orderByChild("id").limitToLast(1);

        updateSuggestionIdQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    final ChildEventListener eventListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            DTOSuggestion suggestion = dataSnapshot.getValue(DTOSuggestion.class);
                            lastSuggestionId = suggestion.getId() + 1;
                            newSuggestion.setFeedId(targetFeedId);
                            newSuggestion.setId(lastSuggestionId);
                            suggestionHashMap.put(String.valueOf(lastSuggestionId), newSuggestion);
                            DatabaseReference suggestionRef = databaseReference.child(FEEDS + "/" + suggestionValue + "/");
                            suggestionRef.updateChildren(suggestionHashMap);
                            suggestionHashMap.clear();
                            updateSuggestionIdQuery.removeEventListener(this);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) { }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

                        @Override
                        public void onCancelled(DatabaseError databaseError) { }
                    };
                    updateSuggestionIdQuery.addChildEventListener(eventListener);
                } else {
                    lastSuggestionId = 0;
                    newSuggestion.setFeedId(targetFeedId);
                    suggestionHashMap.put(String.valueOf(lastSuggestionId), newSuggestion);
                    DatabaseReference suggestionRef = databaseReference.child(FEEDS + "/" + suggestionValue);
                    suggestionRef.updateChildren(suggestionHashMap);
                    suggestionHashMap.clear();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public void updateSuggestion(long targetFeedId, long targetSuggestionId, final DTOSuggestion suggestions) {
        final String suggestionValue = String.valueOf(targetFeedId) + "/" + SUGGESTIONS + "/" + String.valueOf(targetSuggestionId);

        suggestionHashMap.put(suggestionValue, suggestions);
        feedRef.updateChildren(suggestionHashMap);
        suggestionHashMap.clear();
    }

    private void addEmptyRating(long targetFeedId, long targetSuggestionId) {
        DTORating emptyRating = new DTORating(targetFeedId, targetSuggestionId, 0);
        String targetRefString = FEEDS + "/" + String.valueOf(targetFeedId) +
                "/" + SUGGESTIONS + "/" + String.valueOf(targetSuggestionId) + "/" + RATING;
        DatabaseReference ratingRef = databaseReference.child(targetRefString);
        ratingRef.setValue(emptyRating);
    }

    public void upVoteRating(long targetFeedId, long targetSuggestionId) {
        final String targetRefString = FEEDS + "/" + String.valueOf(targetFeedId) +
                "/" + SUGGESTIONS + "/" + String.valueOf(targetSuggestionId) + "/";
        Query updateRatingQuery = databaseReference.child(targetRefString).limitToLast(1);
        updateRatingQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DTOSuggestion suggestion = dataSnapshot.getValue(DTOSuggestion.class);
                suggestion.setLikeCount(suggestion.getLikeCount() + 1);
                DatabaseReference sugRef = databaseReference.child(targetRefString);

                suggestionHashMap.put(String.valueOf(suggestion.getId()), suggestion);
                sugRef.updateChildren(suggestionHashMap);
                suggestionHashMap.clear();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

    public void downVoteRating(long targetFeedId, long targetSuggestionId) {
        final String targetRefString = FEEDS + "/" + String.valueOf(targetFeedId) +
                "/" + SUGGESTIONS + "/" + String.valueOf(targetSuggestionId) + "/";
        Query updateRatingQuery = databaseReference.child(targetRefString).limitToLast(1);
        updateRatingQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DTOSuggestion suggestion = dataSnapshot.getValue(DTOSuggestion.class);
                suggestion.setLikeCount(suggestion.getLikeCount() - 1);
                DatabaseReference sugRef = databaseReference.child(targetRefString);

                suggestionHashMap.put(String.valueOf(suggestion.getId()), suggestion);
                sugRef.updateChildren(suggestionHashMap);
                suggestionHashMap.clear();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    private class FeedIdUpdateEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            DTOFeed feed = dataSnapshot.getValue(DTOFeed.class);
            lastFeedId = feed.getId();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) { }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

        @Override
        public void onCancelled(DatabaseError databaseError) { }
    }

}

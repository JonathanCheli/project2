package com.example.likeanddislikemodelu;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.airbnb.lottie.LottieAnimationView;
import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.likeanddislikemodelu.AdapterSS.CardStackAdapter;
import com.example.likeanddislikemodelu.Model.ItemModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.List;

public class SelectionScreen extends AppCompatActivity {


    Boolean like = false;
    Boolean dislike =false;
    List<String> keys;
    int j=0;

    int numOfLikes = 0;
    int numOFDislikes =0;

    DatabaseReference databaseReferenceLikes;
    Button button;
    ImageButton btnRight, btnLeft;

    private static final String TAG = "MainActivity";

    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;

    private List<ItemModel> itemModelList;
    private RequestQueue requestQueue;

    CardStackView cardStackView;

    private DatabaseReference reference;
    TextView textAmountPics1, textAmountPics2;
    int sum = 0;

    LottieAnimationView lottieAnimationView, lottieLike, lottieDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_screen);

        lottieAnimationView = findViewById(R.id.lottieAni_id);
        lottieDislike = findViewById(R.id.dislikeAni);
        lottieLike= findViewById(R.id.likeAni);


        btnLeft = findViewById(R.id.id_left);
        btnRight = findViewById(R.id.id_right);
        button =  findViewById(R.id.btnReviewButton);

        textAmountPics1 = findViewById(R.id.totalAmount);
        textAmountPics2 = findViewById(R.id.totalAmount2);


        itemModelList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        getItems();

        reference = FirebaseDatabase.getInstance().getReference().child("images");

        cardStackView = findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right) {
                    Toast.makeText(SelectionScreen.this, "Liked!", Toast.LENGTH_SHORT).show();
                }

                if (direction == Direction.Left) {
                    Toast.makeText(SelectionScreen.this, "Disliked!", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Top) {
                    Toast.makeText(SelectionScreen.this, "Liked!", Toast.LENGTH_SHORT).show();
                }

                if (direction == Direction.Bottom) {
                    Toast.makeText(SelectionScreen.this, "Disliked!", Toast.LENGTH_SHORT).show();
                }

                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 5) {

                }

            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_title);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_title);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }
    private void getItems() {
        String url = "https://api-mobile.home24.com/api/v2.0/articles/000000001000062031?appDomain=1&locale=de_DE";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("images");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                ItemModel itemModel = new ItemModel();

                                itemModel.setImages(jsonArray.getString(i));

                                itemModelList.add(itemModel);

                                adapter = new CardStackAdapter(itemModelList);
                                cardStackView.setLayoutManager(manager);
                                cardStackView.setAdapter(adapter);
                                cardStackView.setItemAnimator(new DefaultItemAnimator());

                                reference.setValue(itemModelList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){

                                        }
                                    }
                                });

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){

                                            sum = (int) snapshot.getChildrenCount();
                                            textAmountPics1.setText(Integer.toString(sum));
                                            textAmountPics2.setText(Integer.toString(sum));
                                        }else{

                                            textAmountPics1.setText("0 images");
                                            textAmountPics2.setText("0 images");


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                btnRight.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder()
                                                .setDirection(Direction.Right)
                                                .setDuration(Duration.Normal.duration)
                                                .setInterpolator(new AccelerateInterpolator())
                                                .build());
                                        cardStackView.swipe();
                                        lottieLike.setVisibility(View.VISIBLE);
                                        lottieLike.playAnimation();
                                        lottieDislike.setVisibility(View.INVISIBLE);

                                        lottieLike.addAnimatorListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                lottieLike.setVisibility(View.INVISIBLE);

                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animation) {

                                            }
                                        });

                                        lottieLike.setOnClickListener(this);


                                        keys = new ArrayList<>();
                                        databaseReferenceLikes = FirebaseDatabase.getInstance().getReference("images");
                                        databaseReferenceLikes.keepSynced(true);
                                        like = true;
                                        databaseReferenceLikes.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    for (DataSnapshot child : snapshot.getChildren()) {
                                                        keys.add(child.getKey());

                                                    }
                                                    if (like) {
                                                        if(j<keys.size()){
                                                            databaseReferenceLikes.child(keys.get(j)).child("likes").setValue(1);
                                                            like = false;
                                                        }
                                                        getLikes();
                                                    }
                                                }
                                            }
                                            private void getLikes() {
                                                DatabaseReference likesRef = FirebaseDatabase.getInstance().getReference().child("images").child(keys.get(j));
                                                likesRef.addValueEventListener(new ValueEventListener(){
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            if (dataSnapshot.hasChild("likes")) {
                                                                int numOfL = dataSnapshot.child("likes").getValue(Integer.class);
                                                                numOfLikes += numOfL;
                                                                TextView textView = findViewById(R.id.likescounter);
                                                                textView.setText(String.valueOf(numOfLikes));

                                                                if (j<keys.size())
                                                                    keys.set(j, keys.get(j++));

                                                                if((numOfLikes + numOFDislikes) == (itemModelList.size())){
                                                                    btnRight.setEnabled(false);
                                                                    btnRight.setColorFilter(R.color.grey);
                                                                    btnLeft.setEnabled(false);
                                                                    btnLeft.setColorFilter(R.color.grey);
                                                                    button.setEnabled(true);
                                                                    lottieAnimationView.setVisibility(View.VISIBLE);
                                                                    TextView textView1 = findViewById(R.id.txt_loading);
                                                                    textView1.setVisibility(View.VISIBLE);
                                                                }else{
                                                                    lottieAnimationView.setVisibility(View.GONE);

                                                                }

                                                            }
                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError){
                                                    }
                                                });
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                });
                                btnLeft.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder()
                                                .setDirection(Direction.Left)
                                                .setDuration(Duration.Normal.duration)
                                                .setInterpolator(new AccelerateInterpolator())
                                                .build());
                                        cardStackView.swipe();
                                        lottieDislike.setVisibility(View.VISIBLE);
                                        lottieDislike.playAnimation();
                                        lottieLike.setVisibility(View.INVISIBLE);

                                        lottieDislike.addAnimatorListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                lottieDislike.setVisibility(View.INVISIBLE);

                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animation) {

                                            }
                                        });

                                        lottieDislike.setOnClickListener(this);


                                        List<String> keys1 = new ArrayList<>();
                                        databaseReferenceLikes = FirebaseDatabase.getInstance().getReference("images");
                                        databaseReferenceLikes.keepSynced(true);
                                        dislike = true;

                                        databaseReferenceLikes.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    for (DataSnapshot child : snapshot.getChildren()) {
                                                        keys1.add(child.getKey());
                                                    }
                                                    if (dislike){
                                                        if(j<keys1.size()) {
                                                            databaseReferenceLikes.child(keys1.get(j)).child("dislikes").setValue(1);
                                                            dislike = false;
                                                        }
                                                        getDislikes();
                                                    }
                                                }
                                            }
                                            private void getDislikes() {
                                                DatabaseReference DislikesRef = FirebaseDatabase.getInstance().getReference().child("images").child(keys1.get(j));
                                                DislikesRef.addValueEventListener(new ValueEventListener(){
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            if (dataSnapshot.hasChild("dislikes")) {
                                                                int numOfL = dataSnapshot.child("dislikes").getValue(Integer.class);
                                                                numOFDislikes += numOfL;
                                                                TextView textView = findViewById(R.id.dislikescounter);
                                                                textView.setText(String.valueOf(numOFDislikes));


                                                                if (j<keys1.size())
                                                                    keys1.set(j, keys1.get(j++));

                                                                if((numOFDislikes + numOfLikes)== (itemModelList.size())){
                                                                    btnRight.setEnabled(false);
                                                                    btnRight.setColorFilter(R.color.grey);
                                                                    btnLeft.setEnabled(false);
                                                                    btnLeft.setColorFilter(R.color.grey);
                                                                    button.setEnabled(true);
                                                                }


                                                            }
                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                });
                            }
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(SelectionScreen.this, ReviewScreen.class);
                                    startActivity(intent);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
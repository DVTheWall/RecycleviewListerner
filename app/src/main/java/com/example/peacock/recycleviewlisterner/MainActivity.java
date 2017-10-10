package com.example.peacock.recycleviewlisterner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Activity activity;
    private RecyclerView recyclerView;
    private ArrayList<User> list;
    private LinearLayoutManager layoutManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;

        recyclerView = (RecyclerView) findViewById(R.id.rv_recycleView);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {

            User user = new User();

            user.setName("Hari Vigensh Jayapalan");
            user.setContent("Hello RecyclerView! item: " + i);
            user.setTime("10:45PM");

            list.add(user);
        }

        setUpRecyclerView();
//        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL,
//                false));


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView
                , new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(MainActivity.this, "Single Click on position :" + position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Long press on position :" + position,
                        Toast.LENGTH_LONG).show();

            }
        }));


        //recyclerView.setAdapter(new Adapter(MainActivity.this, list));
    }

    private void autoScroll() {

        final int speedScroll = 1000;

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {

            int count = 0;

            @Override
            public void run() {

                if (count == recyclerView.getAdapter().getItemCount())
                    count = 0;
                if (count <= recyclerView.getAdapter().getItemCount()) {

                    recyclerView.smoothScrollToPosition(++count);

                    handler.postDelayed(this, speedScroll);

                }
            }
        }, speedScroll);
    }

    private void setUpRecyclerView() {

        layoutManager = new LinearLayoutManager(activity) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                               int position) {

                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(activity) {

                    private static final float SPEED = 2000f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {

                        return SPEED / displayMetrics.densityDpi;

                    }
                };

                smoothScroller.setTargetPosition(recyclerView.getAdapter().getItemCount());
                //smoothScroller.setTargetPosition(position); //This Will Scroll Recyclerview From Top to Bottom And Automatically Bottom To top

                startSmoothScroll(smoothScroller);

            }
        };

        autoScroll();

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new Adapter(activity, list));

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }


    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}


package com.tinkoff.androidcourse;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import static com.tinkoff.androidcourse.WorkerGenerator.generateWorkers;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private MyAdapter myAdapter;
    private RecyclerView mRecyclerView;
    private MyItemTouchHelper myItemTouchHelper;
    private ItemTouchHelper itemTouchHelper;
    private FloatingActionButton fab;

    private ArrayList<Worker> workers;
    private ArrayList<Worker> workersNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workers=new ArrayList<>();
        workers.addAll(generateWorkers(2));

        workersNew=new ArrayList<>();
        workersNew.addAll(workers);
        workersNew.addAll(generateWorkers(5));


        mRecyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(workers);

        myItemTouchHelper=new MyItemTouchHelper(myAdapter);

        mRecyclerView.setAdapter(myAdapter);

       itemTouchHelper = new ItemTouchHelper(myItemTouchHelper);
       mRecyclerView.addItemDecoration(new CustomItemDecorator(ContextCompat
                .getDrawable(getApplicationContext(),R.drawable.divider)));
       itemTouchHelper.attachToRecyclerView(mRecyclerView);

       fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



               myAdapter.updateList(workersNew);

               
            }
        });

    }
}

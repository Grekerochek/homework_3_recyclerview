package com.tinkoff.androidcourse;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import static com.tinkoff.androidcourse.WorkerGenerator.generateWorkers;

import java.util.ArrayList;
import java.util.List;




public class MainActivity extends AppCompatActivity {

    private MyAdapter myAdapter;
    private RecyclerView mRecyclerView;
    private MyItemTouchHelper myItemTouchHelper;
    private ItemTouchHelper itemTouchHelper;
    private FloatingActionButton fab;

    private ArrayList<Worker> workers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //создание нового списка
        workers = new ArrayList<>();
        workers.addAll(generateWorkers(2));


        //присваивание ресайкл и кнопке
        mRecyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);

        //установка лэйаутменеджера на ресайклвью
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //адаптер
        myAdapter = new MyAdapter(workers);

        //итемтачхелпер
        myItemTouchHelper = new MyItemTouchHelper(myAdapter);

        //установка адаптера на ресайклвью
        mRecyclerView.setAdapter(myAdapter);

        itemTouchHelper = new ItemTouchHelper(myItemTouchHelper);

        //добавление декоратора к ресайклвью
        mRecyclerView.addItemDecoration(new CustomItemDecorator(ContextCompat
                .getDrawable(getApplicationContext(), R.drawable.divider)));

        //применение итемтачхелпер к ресайковью
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //создание нового списка
                workers=new ArrayList<>(workers);
                workers.addAll(generateWorkers(2));

                // расчет
                new Task(workers).execute();

            }
        });

    }

    class Task extends AsyncTask<Void, Void, String> {

        List<Worker> workersNew;
        DiffUtil.DiffResult diffResult;

        Task(List<Worker> workersNew){
            this.workersNew=workersNew;
        }

        @Override
        protected String doInBackground(Void... voids) {
            MyDiffUtilCallback diffCallback = new MyDiffUtilCallback(myAdapter.getData(), workersNew);
            diffResult = DiffUtil.calculateDiff(diffCallback);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            myAdapter.setData(workersNew);
            diffResult.dispatchUpdatesTo(myAdapter);
        }
    }
}

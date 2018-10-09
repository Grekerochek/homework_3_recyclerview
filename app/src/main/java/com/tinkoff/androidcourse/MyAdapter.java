package com.tinkoff.androidcourse;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import static com.tinkoff.androidcourse.WorkerGenerator.generateWorker;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements
        MyItemTouchHelper.ItemTouchHelperAdapter  {
    private List<Worker> workers;

    @Override
    public void onViewMoved(int oldPosition, int newPosition) {

        Worker worker = workers.get(oldPosition);
        workers.remove(oldPosition);
        workers.add(newPosition, worker);
        notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    public void onViewSwiped(int position) {

        workers.remove(position);
        notifyItemRemoved(position);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView age;
        public TextView position;
        public AppCompatImageView photo;

        public MyViewHolder(View v) {
            super(v);

            name = v.findViewById(R.id.name);
            age = v.findViewById(R.id.age);
            position = v.findViewById(R.id.position);
            photo = v.findViewById(R.id.photo);
        }
    }


    public MyAdapter(List<Worker> workers) {
        this.workers = workers;
    }

    public List<Worker> getData() {
        return workers;
    }

    public void setData(List<Worker> workers) {
        this.workers = workers;
    }



    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {

        Log.d("NOV", "bind, position = " + i);

        holder.name.setText("Name: "+ workers.get(i).getName());
        holder.age.setText("Age: "+ workers.get(i).getAge());
        holder.position.setText("Position: " + workers.get(i).getPosition());
        holder.photo.setImageResource(workers.get(i).getPhoto());

    }
    public void addItem() {
        workers.add(generateWorker());
    }


    @Override
    public int getItemCount() {
        return workers.size();
    }

    public void updateList(List<Worker> workers) {
        final MyDiffUtilCallback diffCallback = new MyDiffUtilCallback(this.workers, workers);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.workers.clear();
        this.workers.addAll(workers);


        diffResult.dispatchUpdatesTo(this);




    }
}

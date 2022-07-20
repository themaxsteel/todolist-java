package com.example.todolist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.model.TaskResponse;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskAdapterVH> {

    private List<TaskResponse> taskResponseList;
    private ItemClickListener mItemClickListener;
    private Context context;

    public TaskAdapter(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setData(List<TaskResponse> taskResponseList){
        this.taskResponseList = taskResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new TaskAdapter.TaskAdapterVH(LayoutInflater.from(context).inflate(R.layout.task_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapterVH holder, int position) {
        TaskResponse taskResponse = taskResponseList.get(position);

        String name = taskResponse.getName();
        String level = taskResponse.getLevel();
        String category = taskResponse.getCategory();
        String date = taskResponse.getDate();
        String id = taskResponse.getId();

        holder.tvName.setText(name);
        holder.tvLevel.setText(level);
        holder.tvCategory.setText(category);
        holder.tvDate.setText(date);
        holder.tvId.setText(id);

        holder.itemView.setOnClickListener(view -> {
            mItemClickListener.onItemClick(taskResponse);
        });
    }

    @Override
    public int getItemCount() {
        return taskResponseList.size();
    }

    public interface ItemClickListener{
        void onItemClick(TaskResponse task);
    }

    public class TaskAdapterVH extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvLevel;
        TextView tvCategory;
        TextView tvDate;
        TextView tvId;

        public TaskAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvLevel = itemView.findViewById(R.id.tvLevel);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvId = itemView.findViewById(R.id.tvId);;
        }
    }
}

package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confidante.NotesActivity;
import com.example.confidante.R;
import com.example.confidante.SongsActivity;

import java.util.List;

import Model.Category;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private Context context;
    private List<Category> categories;

    public CategoriesAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.title.setText(category.getTitle());
    }

    @Override
    public int getItemCount() {

        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.category);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Category category = categories.get(position);
            String title = category.getTitle();
            switch (title){
                case "Notes": context.startActivity(new Intent(context.getApplicationContext(), NotesActivity.class));
                    break;
                case "Songs": context.startActivity(new Intent(context.getApplicationContext(), SongsActivity.class));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + title);
            }
        }
    }
}

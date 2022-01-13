package com.inim.canteenmeal;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inim.canteenmeal.DRVinterface.LoadMore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

class LoadingViewHolder extends RecyclerView.ViewHolder{

    public ProgressBar progressBar;


    public LoadingViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        progressBar= itemView.findViewById(R.id.progress_bar);
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder{

    public TextView name;
    public ImageView img;

   public RelativeLayout rel_card;
    


    public ItemViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.tv_name);
        img=itemView.findViewById(R.id.food_img);
        rel_card=itemView.findViewById(R.id.rel_card);
        

    }
}

public class DynamicRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private  final int VIEW_TYPE_ITEM =0, VIEW_TYPE_LOADING =1;
    LoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<DynamicRVModel> items;
    int visibleThreshold = 5;
    int lastVisibleItem, totalItemCount;

    public DynamicRVAdapter(RecyclerView recyclerView,Activity activity, List<DynamicRVModel> items) {
        this.activity = activity;
        this.items = items;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                totalItemCount = linearLayoutManager.getItemCount();
//                lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
//                if(!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)){
//                    if(loadMore!=null)
//                        loadMore.onLoadMore();
//                    isLoading=true;
//                }
//            }
//        });
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public  void setLoadMore(LoadMore loadMore){
        this.loadMore=loadMore;

    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity).inflate(R.layout.dashboardlayout,parent,false);
            return new ItemViewHolder(view);
        }
        else if(viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity).inflate(R.layout.dynamic_rv_progress_bar,parent,false);
            return new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof  ItemViewHolder){
            DynamicRVModel item = items.get(position);
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            //viewHolder.name.setText(items.get(position).getName());
            //Drawable drawable = viewHolder.itemView.getContext().getResources().getDrawable(item.getImage());
            //viewHolder.img.setImageDrawable(drawable);

            //LinearLayout li_card = viewHolder.li_card;

            viewHolder.rel_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, new FoodDetailsFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //Toast.makeText(v.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });


        }
        else if(holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public  void setLoaded(){
        isLoading=false;
    }
}

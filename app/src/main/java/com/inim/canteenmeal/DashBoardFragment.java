package com.inim.canteenmeal;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.inim.canteenmeal.DRVinterface.LoadMore;

import java.util.ArrayList;
import java.util.List;

public class DashBoardFragment extends Fragment {


    private RecyclerView recyclerView;
  //  private StaticRvAdapter staticRvAdapter;

    ViewFlipper flipper;

    List<DynamicRVModel> items = new ArrayList<>();
    DynamicRVAdapter dynamicRVAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup v=(ViewGroup) inflater.inflate(R.layout.fragment_dashboard1, container, false);

        int imgArray []={R.drawable.icecream,R.drawable.pizza,R.drawable.burger,R.drawable.icecream,R.drawable.khaja,R.drawable.khana};
        flipper=v.findViewById(R.id.flipper);
        for(int i=0;i<imgArray.length;i++)
            showImage(imgArray[i]);


//        ArrayList<StaticRvModel> item = new ArrayList<>();
//        item.add(new StaticRvModel(R.drawable.pizza,"Pizza"));
//        item.add(new StaticRvModel(R.drawable.burger,"Burger"));
//        item.add(new StaticRvModel(R.drawable.fries,"Fries"));
//        item.add(new StaticRvModel(R.drawable.sandwich,"Sandwich"));
//        item.add(new StaticRvModel(R.drawable.icecream,"Ice Cream"));
//
//        recyclerView = v.findViewById(R.id.rv_1);
//        staticRvAdapter= new StaticRvAdapter(item);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
//        recyclerView.setAdapter(staticRvAdapter);


        items.add(new DynamicRVModel(R.drawable.khaja,"Khaja"));
        items.add(new DynamicRVModel(R.drawable.khana,"Khana"));
        items.add(new DynamicRVModel(R.drawable.drinks,"Drinks"));
        items.add(new DynamicRVModel(R.drawable.momos,"Momos"));
        items.add(new DynamicRVModel(R.drawable.dessert,"Dessert"));


        RecyclerView drv = v.findViewById(R.id.rview_2);
        drv.setLayoutManager(new LinearLayoutManager(getContext()));
        dynamicRVAdapter= new DynamicRVAdapter(drv,getActivity(),items);
        drv.setAdapter(dynamicRVAdapter);

//        dynamicRVAdapter.setLoadMore(new LoadMore() {
//            @Override
//            public void onLoadMore() {
//                if(items.size()<=10){
//                    items.add(null);
//                    dynamicRVAdapter.notifyItemInserted(items.size()-1);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            items.remove(items.size()-1);
//                            dynamicRVAdapter.notifyItemRemoved(items.size());
//
////                            int index = items.size();
////                            int end = index +10;
////                            for(int i = index ; i<end;i++){
////                                String name = UUID.randomUUID().toString();
////                                DynamicRVModel item = new DynamicRVModel(name);
////                                items.add(item);
////                            }
//                            dynamicRVAdapter.notifyDataSetChanged();
//                            dynamicRVAdapter.setLoaded();
//
//                        }
//                    },4000);
//                }
////                else
////                    Toast.makeText(getActivity(),"Data Completed",Toast.LENGTH_SHORT).show();
//            }
//        });


        return v;
    }

    private void showImage(int img) {
        ImageView imageView=new ImageView(getContext());
        imageView.setBackgroundResource(img);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);
        flipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        flipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }
}
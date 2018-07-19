package com.example.shirayama.yarukimanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class MenuFragment extends Fragment {

    public MenuFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TextViewをひも付けます
        RelativeLayout menu1 = (RelativeLayout)view.findViewById(R.id.menu1);
        RelativeLayout menu2 = (RelativeLayout)view.findViewById(R.id.menu2);
        RelativeLayout menu3 = (RelativeLayout)view.findViewById(R.id.menu3);
        Activity activity = getActivity();
        String activity_class = activity.getLocalClassName();
        if(activity_class.equals("MainActivity")){
            menu2.setBackgroundColor(0x66e6e6fa);
            menu3.setBackgroundColor(0x66ffff99);
        }else if(activity_class.equals("TaskActivity")){
            menu1.setBackgroundColor(0x66ffcccc);
            menu3.setBackgroundColor(0x66ffff99);
        }else if(activity_class.equals("ReportActivity")){
            menu1.setBackgroundColor(0x66ffcccc);
            menu2.setBackgroundColor(0x66e6e6fa);
        }

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();
                String activity_class = activity.getLocalClassName();
                if(!activity_class.equals("MainActivity")){
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                }else{
                    Log.d("tag","already here");
                }

            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();
                String activity_class = activity.getLocalClassName();
                if(!activity_class.equals("TaskActivity")){
                    Intent intent = new Intent(activity, TaskActivity.class);
                    startActivity(intent);
                }else{
                    Log.d("tag","already here");
                }

            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();
                String activity_class = activity.getLocalClassName();
                if(!activity_class.equals("ReportActivity")){
                    Intent intent = new Intent(activity, ReportActivity.class);
                    startActivity(intent);
                }else{
                    Log.d("tag","already here");
                }
            }
        });
    }

}

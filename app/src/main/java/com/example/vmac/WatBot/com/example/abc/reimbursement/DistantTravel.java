package com.example.vmac.WatBot.com.example.abc.reimbursement;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vmac.WatBot.R;

public class DistantTravel extends AppCompatActivity implements View.OnClickListener {
    String expenseName;
    String category;
    public static EditText  mTravelFrom;
    public static EditText mTravelTo;

    public static String travelFrom;
    public static String travelTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distant_travel);
        Intent intent = getIntent();
        expenseName = intent.getStringExtra("expenseName");
        category = intent.getStringExtra("category");

        mTravelFrom = (EditText) findViewById(R.id.from);
        mTravelTo = (EditText)findViewById(R.id.to);

        //travelFrom = mTravelFrom.getText().toString();
        //travelTo = mTravelTo.getText().toString();


        /*mTravelFrom.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                    travelFrom = mTravelFrom.getText().toString();
            }
        });

        mTravelTo.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    travelTo = mTravelTo.getText().toString();
            }
        });*/





        Fragment fragment = new com.example.vmac.WatBot.com.example.abc.reimbursement.DistantTravelStayFragment();
        Bundle bundle = new Bundle();

        bundle.putString("expenseName",expenseName);
        bundle.putString("category",category);
        bundle.putString("travelFrom",travelFrom);

        bundle.putString("travelTo", travelTo);

        fragment.setArguments(bundle);
            /*FragmentManager manager = getFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.add(R.id.fragment2, fragment);
            fragmentTransaction.commit();*/
        FragmentManager manager = getFragmentManager();
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment2, fragment);
        transaction.addToBackStack(null);
        transaction.commit();



        Button btn12 =  findViewById(R.id.button12);
        btn12.setOnClickListener(this);
        Button btn22 = findViewById(R.id.button22);
        btn22.setOnClickListener(this);
        Button btn32 =  findViewById(R.id.button32);
        btn32.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        if (v.getId() == R.id.button12) {
            Bundle bundle = new Bundle();
            bundle.putString("expenseName",expenseName);
            bundle.putString("category",category);
            bundle.putString("travelFrom",travelFrom);
            bundle.putString("travelTo", travelTo);
            fragment = new com.example.vmac.WatBot.com.example.abc.reimbursement.DistantTravelMealFragment();
            fragment.setArguments(bundle);

        } else if (v.getId() == R.id.button22) {
            Bundle bundle = new Bundle();
            bundle.putString("expenseName",expenseName);
            bundle.putString("category",category);
            bundle.putString("travelFrom",travelFrom);
            bundle.putString("travelTo", travelTo);
            fragment = new com.example.vmac.WatBot.com.example.abc.reimbursement.DistantTravelTravelActivity();
            fragment.setArguments(bundle);

        } else if (v.getId() == R.id.button32) {
            Bundle bundle = new Bundle();
            bundle.putString("expenseName",expenseName);
            bundle.putString("category",category);
            bundle.putString("travelFrom",travelFrom);
            bundle.putString("travelTo", travelTo);
            fragment = new com.example.vmac.WatBot.com.example.abc.reimbursement.DistantTravelStayFragment();
            fragment.setArguments(bundle);

        }
        FragmentManager manager = getFragmentManager();
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment2, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public static String getTravelFrom(){
        travelFrom = mTravelFrom.getText().toString();
        return travelFrom;
    }
    public static String getTravelTo(){
        travelTo = mTravelTo.getText().toString();
        return travelTo;
    }

}




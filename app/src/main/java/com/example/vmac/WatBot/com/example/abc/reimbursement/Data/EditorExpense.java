package com.example.vmac.WatBot.com.example.abc.reimbursement.Data;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vmac.WatBot.R;
//import com.example.vmac.WatBot.com.example.abc.reimbursement.Data.R;

import java.util.Calendar;

public class EditorExpense extends AppCompatDialogFragment {
    EditText mNameEditText;
    TextView mStartDateEditText;
    TextView mEndDateText;
    String user;
    private Uri mCurrentExpenseUri;

    DatePickerDialog.OnDateSetListener mDateSetListener;
    DatePickerDialog.OnDateSetListener mDateSetListener2;

//    @SuppressLint("ValidFragment")
//    public EditorExpense(String u) {
//        user = u;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflator = getActivity().getLayoutInflater();
        View view =inflator.inflate(R.layout.activity_editor_expense , null);

        user = getArguments().getString("user");

        mNameEditText = (EditText) view.findViewById(R.id.expensename);
        mStartDateEditText = (TextView) view.findViewById(R.id.startdate);
        mEndDateText = (TextView) view.findViewById(R.id.enddate);



        mStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog2 = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light,
                        mDateSetListener2 ,
                        year,month,day);

                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();

            }

        } );
        mDateSetListener2= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = dayOfMonth + "/" + month +"/" + year;
                mStartDateEditText.setText(date);

            }
        } ;


        mEndDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light,
                        mDateSetListener ,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }

        } );
        mDateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = dayOfMonth + "/" + month +"/" + year;
                mEndDateText.setText(date);

            }
        } ;

        /*Button positiveButton = builder.create().getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = builder.create().getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setTextColor(Color.parseColor("#2AB0D9"));
        positiveButton.setTextColor(Color.parseColor("#2AB0D9"));*/


        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Read from input fields
                        // Use trim to eliminate leading or trailing white space
                        String nameString = mNameEditText.getText().toString().trim();
                        String StartDateString = mStartDateEditText.getText().toString().trim();
                        String EndDateString = mEndDateText.getText().toString().trim();

                        ContentValues values = new ContentValues();

                        values.put(BillContract.BillEntry.USER, nameString);
                        values.put(BillContract.BillEntry.USER, user);
                        values.put(BillContract.BillEntry.COLUMN_EXPENSE_NAME, nameString);
                        values.put(BillContract.BillEntry.COLUMN_EXPENSE_STARTDATE, StartDateString);
                        values.put(BillContract.BillEntry.COLUMN_EXPENSE_ENDDATE, EndDateString);

                        // Uri newUri = getContentResolver().insert(BillContract.BillEntry.CONTENT_URI, values);
                        Uri newUri = getActivity().getContentResolver().insert(BillContract.BillEntry.CONTENT_URI, values);


                        //saveExpense();


                    }
                });


        /*Button positive = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);
        positive.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorPrimary));
        Button negative = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE);
        negative.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorPrimary));*/

        return builder.create();

    }

    private void saveExpense() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String StartDateString = mStartDateEditText.getText().toString().trim();
        String EndDateString = mEndDateText.getText().toString().trim();

        // Check if this is supposed to be a new pet
        // and check if all the fields in the editor are blank
        if (mCurrentExpenseUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(StartDateString) &&
                TextUtils.isEmpty(EndDateString)) {
            // Since no fields were modified, we can return early without creating a new pet.
            // No need to create ContentVlues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(BillContract.BillEntry.COLUMN_EXPENSE_NAME, nameString);

        values.put(BillContract.BillEntry.USER, user);
        values.put(BillContract.BillEntry.COLUMN_EXPENSE_STARTDATE, StartDateString);
        values.put(BillContract.BillEntry.COLUMN_EXPENSE_ENDDATE, EndDateString);

        // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
        if (mCurrentExpenseUri == null) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getActivity().getContentResolver().insert(BillContract.BillEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(getActivity(), "Insertion Failed Try Again",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(getActivity(), "Inserted Successfully",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getActivity().getContentResolver().update(mCurrentExpenseUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(getActivity(), "Editing Failed",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(getActivity(), "Editing Successfull",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
        /*super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_editor_expense);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;


        getWindow().setLayout((int) (width * .60), (int) (height * .30));
        Button button = (Button) findViewById(R.id.ok);
        mNameEditText = (EditText) findViewById(R.id.expensename);
        mStartDateEditText = (TextView) findViewById(R.id.startdate);
        mEndDateText = (TextView) findViewById(R.id.enddate);


        mStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.YEAR);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog2 = new DatePickerDialog(EditorExpense.this,
                        android.R.style.Theme_Holo_Light,
                        mDateSetListener2 ,
                        year,month,day);

                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();

            }

        } );
        mDateSetListener2= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = dayOfMonth + "/" + month +"/" + year;
                mStartDateEditText.setText(date);

            }
        } ;


        mEndDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.YEAR);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(EditorExpense.this,
                        android.R.style.Theme_Holo_Light,
                        mDateSetListener ,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }

        } );
        mDateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = dayOfMonth + "/" + month +"/" + year;
                mEndDateText.setText(date);

            }
        } ;


        Intent intent = getIntent();
        mCurrentExpenseUri = intent.getData();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Read from input fields
                // Use trim to eliminate leading or trailing white space
                String nameString = mNameEditText.getText().toString().trim();
                String StartDateString = mStartDateEditText.getText().toString().trim();
                String EndDateString = mEndDateText.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(BillContract.BillEntry.COLUMN_EXPENSE_NAME, nameString);
                values.put(BillContract.BillEntry.COLUMN_EXPENSE_STARTDATE, StartDateString);
                values.put(BillContract.BillEntry.COLUMN_EXPENSE_ENDDATE, EndDateString);

              // Uri newUri = getContentResolver().insert(BillContract.BillEntry.CONTENT_URI, values);
                Uri newUri = getContentResolver().insert(BillContract.BillEntry.CONTENT_URI, values);


                //saveExpense();
                finish();

            }


        });
        Button button2 = (Button) findViewById(R.id.Back);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }

            ;


        });


    }

    private void saveExpense() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String StartDateString = mStartDateEditText.getText().toString().trim();
        String EndDateString = mEndDateText.getText().toString().trim();

        // Check if this is supposed to be a new pet
        // and check if all the fields in the editor are blank
        if (mCurrentExpenseUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(StartDateString) &&
                TextUtils.isEmpty(EndDateString)) {
            // Since no fields were modified, we can return early without creating a new pet.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(BillContract.BillEntry.COLUMN_EXPENSE_NAME, nameString);
        values.put(BillContract.BillEntry.COLUMN_EXPENSE_STARTDATE, StartDateString);
        values.put(BillContract.BillEntry.COLUMN_EXPENSE_ENDDATE, EndDateString);

        // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
        if (mCurrentExpenseUri == null) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(BillContract.BillEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, "Insertion Failed Try Again",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, "Inserted Successfully",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentExpenseUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, "Editing Failed",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, "Editing Successfull",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
*/
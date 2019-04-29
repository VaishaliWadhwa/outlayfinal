package com.example.vmac.WatBot.com.example.abc.reimbursement;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vmac.WatBot.R;
import com.example.vmac.WatBot.com.example.abc.reimbursement.Data.BillContract;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TeamExpenseActivity extends Activity {

    double result;

    EditText  mFinalAmountEditText;;

    TextView mBillDateEditText;
    EditText mPurposeEditText;
    EditText mTeamMembersEditText;

    //EditText finalAmount;

    String expenseName;
    String startDate;
    String endDate;
    String category;
    String user;
    BillCursorAdapter mCursorAdapter;

    DatePickerDialog.OnDateSetListener mDateSetListener;

    byte[] photo;

    String uri;

    Bitmap bitmap;

    String imageString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_expense);

        mFinalAmountEditText = (EditText) findViewById(R.id.final_amount);
        mTeamMembersEditText = (EditText) findViewById(R.id.team_member);

        mBillDateEditText = (TextView) findViewById(R.id.bill_date);
        mPurposeEditText = (EditText) findViewById(R.id.purpose);

        mCursorAdapter = new BillCursorAdapter(this, null);

        Intent intent = getIntent();
        expenseName = intent.getStringExtra("expenseName");
        category = intent.getStringExtra("category");

        user = intent.getStringExtra("user");


        //final TextView mDisplayDate = (TextView)findViewById(R.id.date);
        mBillDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(TeamExpenseActivity.this,
                        android.R.style.Theme_Holo_Light,
                        mDateSetListener ,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

            }

        } );
        mDateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = dayOfMonth + "/" + month +"/" + year;
                Date d1 = null;
                Date d2 = null;
                Date d3 = null;
                try {
                    d2 = new SimpleDateFormat("dd/MM/yyy").parse(startDate);
                    d1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    d3 = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
                    if (d1.after(d3) || d1.before(d2)) {
                        Toast.makeText(getApplicationContext(), "Insert date properly", Toast.LENGTH_SHORT).show();


                    } else {
                        mBillDateEditText.setText(date);



                    }

                } catch (ParseException ex) {
                }




            }
        } ;

        Button buttonScan =(Button)findViewById(R.id.button_scan);

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageToTextIntent = new Intent (TeamExpenseActivity.this , ImageToTextConverter.class );
                //startActivity(imageToTextIntent);
                startActivityForResult(imageToTextIntent , 1);
            }
        });

        Button buttonFinalSubmit = (Button) findViewById(R.id.final_submit);
        buttonFinalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBill();
                Intent intent = new Intent(TeamExpenseActivity.this, ExpenseReport.class);
                intent.putExtra("category","Meal");
                intent.putExtra("expenseName", expenseName);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    private void saveBill() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        /*String nameString = mNameEditText.getText().toString().trim();
        String category = mCategoryEditText.getText().toString().trim();*/
        if(uri!=null)
            compress();

        String billDate = mBillDateEditText.getText().toString().trim();
        String teamMember = mTeamMembersEditText.getText().toString().trim();
        String purpose = mPurposeEditText.getText().toString().trim();
        String finalAmount = mFinalAmountEditText.getText().toString().trim();

        String edited;

        if(finalAmount.equals(String.valueOf(result))){
            edited = "No";
        }
        else{
            edited = "Yes";
        }


        ContentValues values = new ContentValues();
        values.put(BillContract.BillEntry.COLUMN_EXPENSE_NAME, expenseName);
        values.put(BillContract.BillEntry.COLUMN_EXPENSE_CAT, category);

        values.put(BillContract.BillEntry.USER, user);
        values.put(BillContract.BillEntry.STATUS, "Not Approved");

        values.put(BillContract.BillEntry.COLUMN_EXPENSE_BILLDATE, billDate);
        //values.put(BillContract.BillEntry.COLUMN_EXPENSE_MEMBERS, teamMember);
        values.put(BillContract.BillEntry.COLUMN_EXPENSE_PURPOSE, purpose);
        values.put(BillContract.BillEntry.COLUMN_EXPENSE_FINAL_AMOUNT, finalAmount);
        values.put(BillContract.BillEntry.COLUMN_EXPENSE_BILL_IMAGE, photo);
        values.put(BillContract.BillEntry.COLUMN_EXPENSE_AMOUNT_EDITED, edited);


        // Uri newUri = getContentResolver().insert(BillContract.BillEntry.CONTENT_URI, values);
        Uri newUri = getContentResolver().insert(BillContract.BillEntry.CONTENT_URI, values);


        //saveExpense();
        finish();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //EditText finalAmount = (EditText) findViewById(R.id.final_amount);
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {


                uri = data.getStringExtra("uri");
                double result = data.getDoubleExtra("result" , 0.00);
                mFinalAmountEditText.setText(String.valueOf(result));
            }
            if(resultCode == RESULT_CANCELED){

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_all_activity, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                //showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.


                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(TeamExpenseActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                //showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void compress() {
        File image = new File(uri);
        //BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        long length = image.length();
        bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (length < (499 * 1024)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            photo = baos.toByteArray();
        }
        if (length > (499 * 1024) && length <(999*1024)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            photo = baos.toByteArray();
        }

        if (length > (999 * 1024) && length <(1999*1024)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            photo = baos.toByteArray();
        }

        if (length > (1999 * 1024) && length <(2999*1024)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 16, baos);
            photo = baos.toByteArray();
        }

        if (length > (2999 * 1024) && length <(4999*1024)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
            photo = baos.toByteArray();
        }

        if (length > (4999 * 1024) && length <(6999*1024)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 7, baos);
            photo = baos.toByteArray();
        }
        if (length > (6999 * 1024) && length <(9999*1024)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 5, baos);
            photo = baos.toByteArray();
        }

    }

}


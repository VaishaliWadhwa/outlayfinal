package com.example.vmac.WatBot.com.example.abc.reimbursement;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vmac.WatBot.R;
import com.example.vmac.WatBot.com.example.abc.reimbursement.Data.BillContract;

import static com.example.vmac.WatBot.com.example.abc.reimbursement.Data.BillContract.BillEntry._ID;

public class ExpenseReport extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private Uri mCurrentExpenseUri;
    /** Identifier for the pet data loader */
    private static final int EXISTING_EXPENSE_LOADER = 0;

    String expenseName;
    String category;
    String startDate;
    String endDate;
    String user;
    private static final int EXPENSE_LOADER = 0;

    CategoryCursorAdapter mCursorAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_report);

        Intent intent = getIntent();
        expenseName = intent.getStringExtra("expenseName");
        category = intent.getStringExtra("category");
        endDate = intent.getStringExtra("endDate");
        startDate = intent.getStringExtra("startDate");
        user = intent.getStringExtra("user");


        setTitle(expenseName);

        ListView billListView = (ListView) findViewById(R.id.bill_list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        // View emptyView = findViewById(R.id.empty_view);
        //expenseListView.setEmptyView(emptyView);

        mCursorAdapter = new CategoryCursorAdapter(this, null);
        billListView.setAdapter(mCursorAdapter);

        billListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(ExpenseReport.this, DisplayActivity.class);
                intent.putExtra("userWho", "notadmin");

                // Form the content URI that represents the specific pet that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link PetEntry#CONTENT_URI}.
                // For example, the URI would be "content://com.example.android.pets/pets/2"
                // if the pet with ID 2 was clicked on.
                Uri currentPetUri = ContentUris.withAppendedId(BillContract.BillEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentPetUri);

                // Launch the {@link EditorActivity} to display the data for the current pet.
                startActivity(intent);
            }
        });

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent intent = new Intent(ExpenseReport.this, ChoiceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("expenseName", expenseName);
                startActivity(intent);*/
                openDialog();
            }
        });



        getLoaderManager().initLoader(EXPENSE_LOADER, null, this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_expensereport, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_bills:
                deleteAllBills();
                return true;
            case R.id.action_delete_expense:
                deleteExpense();
                finish();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.


        String[] projection = {
                _ID,
                BillContract.BillEntry.COLUMN_EXPENSE_CAT,
                BillContract.BillEntry.COLUMN_EXPENSE_PURPOSE,
                BillContract.BillEntry.COLUMN_EXPENSE_SUBCAT,
                BillContract.BillEntry.COLUMN_EXPENSE_FROM,
                BillContract.BillEntry.COLUMN_EXPENSE_TO,
                BillContract.BillEntry.COLUMN_EXPENSE_BILLDATE};

        //String selection = BillContract.BillEntry.COLUMN_EXPENSE_CAT + "!= 'NoCategory' AND"
        //        + BillContract.BillEntry.COLUMN_EXPENSE_NAME + " = " + expenseName ;

        String selection = "category != ? AND name = ? AND user = ?";
        String selectionArgs [] = new String[] { "NoCategory", expenseName, user };


        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                BillContract.BillEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                selection,                   // No selection clause
                selectionArgs,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link PetCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
    private void deleteAllBills() {
        String selection = "category != ? AND name = ?";
        String selectionArgs [] = new String[] { "NoCategory", expenseName };
        int rowsDeleted = getContentResolver().delete(BillContract.BillEntry.CONTENT_URI,selection , selectionArgs);



    }
    private void deleteExpense() {
        String selection = "name = ?";
        String selectionArgs [] = new String[] {  expenseName };
        int rowsDeleted = getContentResolver().delete(BillContract.BillEntry.CONTENT_URI,selection , selectionArgs);

    }

    public void openDialog(){

        ChoiceActivity choiceActivityDialog = new ChoiceActivity(expenseName,user,startDate,endDate);
        choiceActivityDialog.show(getSupportFragmentManager(), "Editor Expense Dialog");

    }
}
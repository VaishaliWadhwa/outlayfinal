package com.example.vmac.WatBot.com.example.abc.reimbursement;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.vmac.WatBot.com.example.abc.reimbursement.Data.BillContract;
import com.example.vmac.WatBot.R;


/**
 * {@link BillCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */
public class CategoryCursorAdapter extends CursorAdapter {

    LayoutInflater mInflater;


    public CategoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.expense_list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);
        TextView dateTextView = (TextView) view.findViewById(R.id.date);

        // Find the columns of pet attributes that we're interested in
        int categoryColumnIndex = cursor.getColumnIndex(BillContract.BillEntry.COLUMN_EXPENSE_CAT);
        int purposeDateColumnIndex = cursor.getColumnIndex(BillContract.BillEntry.COLUMN_EXPENSE_PURPOSE);
        int billDateColumnIndex = cursor.getColumnIndex(BillContract.BillEntry.COLUMN_EXPENSE_BILLDATE);
        int fromColumnIndex = cursor.getColumnIndex(BillContract.BillEntry.COLUMN_EXPENSE_FROM);
        int toColumnIndex = cursor.getColumnIndex(BillContract.BillEntry.COLUMN_EXPENSE_TO);
        int subCategoryColumnIndex = cursor.getColumnIndex(BillContract.BillEntry.COLUMN_EXPENSE_SUBCAT);



        // Read the pet attributes from the Cursor for the current pet
        String expenseCategory = cursor.getString(categoryColumnIndex);
        String expensePurpose = cursor.getString(purposeDateColumnIndex);
        String expenseBillDate = cursor.getString(billDateColumnIndex);
        String subCategory = cursor.getString(subCategoryColumnIndex);
        String from = cursor.getString(fromColumnIndex);
        String to = cursor.getString(toColumnIndex);

        // If the pet breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.

        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(expenseCategory);
        dateTextView.setText(expenseBillDate);
        summaryTextView.setText(expensePurpose);
/*
        if(expenseCategory.equalsIgnoreCase("Distant Travel")||
                expenseCategory.equalsIgnoreCase("Local travel")){
            summaryTextView.setText(from +" - "+ to);

            nameTextView.setText(expenseCategory + " - "+ subCategory);
            }*/

    }
}
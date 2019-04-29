package com.example.vmac.WatBot.com.example.abc.reimbursement.Data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.vmac.WatBot.R;

public class AdminCursorAdapter extends CursorAdapter {

    LayoutInflater mInflater;

    /**
     * Constructs a new {@link AdminCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public AdminCursorAdapter(Context context, Cursor c) {
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
        return LayoutInflater.from(context).inflate(R.layout.admin_list_item, parent, false);
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
        TextView userTextView = (TextView) view.findViewById(R.id.user);
        TextView statusTextView = (TextView) view.findViewById(R.id.status);
        TextView expenseNameTextView = (TextView) view.findViewById(R.id.expense_name1);
        TextView dateTextView = (TextView) view.findViewById(R.id.date);

        // Find the columns of pet attributes that we're interested in
        int userColumnIndex = cursor.getColumnIndex(BillContract.BillEntry.USER);
        int statusColumnIndex = cursor.getColumnIndex(BillContract.BillEntry.STATUS);
        int expenseNameColumnIndex = cursor.getColumnIndex(BillContract.BillEntry.COLUMN_EXPENSE_NAME);
        int dateColumnIndex = cursor.getColumnIndex(BillContract.BillEntry.COLUMN_EXPENSE_BILLDATE);




        // Read the pet attributes from the Cursor for the current pet
        String userName = cursor.getString(userColumnIndex);
        String status = cursor.getString(statusColumnIndex);
        String expenseName = cursor.getString(expenseNameColumnIndex);
        String date = cursor.getString(dateColumnIndex);

        // If the pet breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.

        // Update the TextViews with the attributes for the current pet
        userTextView.setText(userName);
        statusTextView.setText(status);
        expenseNameTextView.setText(expenseName);
        dateTextView.setText(date);

    }
}

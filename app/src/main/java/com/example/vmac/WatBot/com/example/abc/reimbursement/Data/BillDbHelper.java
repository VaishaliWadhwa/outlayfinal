package com.example.vmac.WatBot.com.example.abc.reimbursement.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.vmac.WatBot.com.example.abc.reimbursement.Data.BillContract.BillEntry.COLUMN_EXPENSE_NAME;
import static com.example.vmac.WatBot.com.example.abc.reimbursement.Data.BillContract.BillEntry.COLUMN_EXPENSE_NAME;

public class BillDbHelper extends SQLiteOpenHelper {


    public static final String TAG = BillDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "department_buddy.db";

    //private static final String TAG = BillDbHelper.class.getName();

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 13;



    String SQL_CREATE_EXPENSE_TABLE;
    private final Context context;

    /**
     * Constructs a new instance of {@link BillDbHelper}.
     *
     * @param context of the app
     */
    public BillDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        SQL_CREATE_EXPENSE_TABLE = "CREATE TABLE " + BillContract.BillEntry.TABLE_NAME + " ("
                // + BillContract.BillEntry.COLUMN_EXPENSE_ID + " INTEGER , "
                + COLUMN_EXPENSE_NAME + " TEXT NOT NULL  ,"
                + BillContract.BillEntry.COLUMN_EXPENSE_STARTDATE + " TEXT, "
                + BillContract.BillEntry.USER + " TEXT, "
                + BillContract.BillEntry.STATUS + " TEXT, "

                + BillContract.BillEntry.COLUMN_EXPENSE_ENDDATE + " TEXT, "
                + BillContract.BillEntry.COLUMN_EXPENSE_BILLDATE + " TEXT, "
                + BillContract.BillEntry.COLUMN_EXPENSE_RESTNAME + " TEXT , "
                + BillContract.BillEntry.COLUMN_EXPENSE_CLIENTNAME + " TEXT ,"
                + BillContract.BillEntry.COLUMN_EXPENSE_MEMBERS + " TEXT ,"
                + BillContract.BillEntry.COLUMN_EXPENSE_PURPOSE + " TEXT,"
                + BillContract.BillEntry.COLUMN_EXPENSE_FINAL_AMOUNT + " REAL, "
                + BillContract.BillEntry.COLUMN_EXPENSE_CAT + " TEXT DEFAULT 'NoCategory',"
                //+BillContract.BillEntry.COLUMN_EXPENSE_BILL_ID+ " INTEGER  PRIMARY KEY   AUTOINCREMENT , "

                + BillContract.BillEntry._ID + " INTEGER  PRIMARY KEY   AUTOINCREMENT , "
                + BillContract.BillEntry.COLUMN_EXPENSE_BILL_IMAGE + " BLOB ,"
                //+ BillContract.BillEntry.COLUMN_EXPENSE_KEY_NAME + "TEXT,"
                + BillContract.BillEntry.COLUMN_EXPENSE_FROM + " TEXT,"
                + BillContract.BillEntry.COLUMN_EXPENSE_TO + " TEXT,"
                +BillContract.BillEntry.COLUMN_EXPENSE_VENUE + " TEXT,"

                +BillContract.BillEntry.COLUMN_EXPENSE_AMOUNT_EDITED + " TEXT,"
                + BillContract.BillEntry.COLUMN_EXPENSE_SUBCAT + " TEXT)";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_EXPENSE_TABLE);
    }


    /**
     * This is called when the database needs to be upgraded.
     */
        /*@Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // The database is still at version 1, so there's nothing to do be done here.
            switch(oldVersion) {
                case 1:
                    db.execSQL("DROP TABLE Expenses");
                    db.execSQL(SQL_CREATE_EXPENSE_TABLE);
                    // we want both updates, so no break statement here...
                case 2:
                    db.execSQL(DATABASE_CREATE_someothertable);
            }


        }*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /*Log.e(TAG, "Updating table from " + oldVersion + " to " + newVersion);
            // You will not need to modify this unless you need to do some android specific things.
            // When upgrading the database, all you need to do is add a file to the assets folder and name it:
            // from_1_to_2.sql with the version that you are upgrading to as the last version.
            try {
                for (int i = oldVersion; i < newVersion; ++i) {
                    String migrationName = String.format("from_%d_to_%d.sql", i, (i + 1));
                    Log.d(TAG, "Looking for migration file: " + migrationName);
                    readAndExecuteSQLScript(db, context, migrationName);
                }
            } catch (Exception exception) {
                Log.e(TAG, "Exception running upgrade script:", exception);
            }*/

    }


    /*private void readAndExecuteSQLScript(SQLiteDatabase db, Context ctx, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            Log.d(TAG, "SQL script file name is empty");
            return;
        }

        Log.d(TAG, "Script found. Executing...");
        AssetManager assetManager = ctx.getAssets();
        BufferedReader reader = null;

        try {
            InputStream is = assetManager.open(fileName);
            InputStreamReader isr = new InputStreamReader(is);
            reader = new BufferedReader(isr);
            executeSQLScript(db, reader);
        } catch (IOException e) {
            Log.e(TAG, "IOException:", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "IOException:", e);
                }
            }
        }

    }

    private void executeSQLScript(SQLiteDatabase db, BufferedReader reader) throws IOException {
        String line;
        StringBuilder statement = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            statement.append(line);
            statement.append("\n");
            if (line.endsWith(";")) {
                db.execSQL(statement.toString());
                statement = new StringBuilder();
            }
        }
    }*/

}

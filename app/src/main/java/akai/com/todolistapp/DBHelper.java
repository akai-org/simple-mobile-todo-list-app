package akai.com.todolistapp;

/**
 * Created by Michal on 13.11.2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "contactsManager";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_BOOL = "bool";
    private static final String KEY_PRIORITY = "priority";

    private static final String TABLE_LIST = "list";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DATE + " TEXT," + KEY_BOOL + " INTEGER," + KEY_PRIORITY + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int a, int b)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);

        // Create tables again
        onCreate(db);
    }

    public void add(Task c){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, c.getTitle()); // Contact Name
        values.put(KEY_DATE, c.getDate().toString());//contact.getPhoneNumber()); // Contact Phone Number
        boolean x = c.getStatus();
        if(x)
            values.put(KEY_BOOL, 1);
        else
            values.put(KEY_BOOL, 0);
        boolean pr = c.getPriority();
        if(pr)
            values.put(KEY_PRIORITY, 1);
        else
            values.put(KEY_PRIORITY, 0);
        db.insert(TABLE_LIST, null, values);
        db.close();
    }

    public void update(Task updatedTask) throws Exception {
        int id = updatedTask.getId();
        if(id==-1) {
            throw new Exception("Incorrect id");
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, updatedTask.getTitle());
        values.put(KEY_DATE, updatedTask.getDate().toString());
        values.put(KEY_BOOL, updatedTask.getStatus() ? 1 : 0);
        values.put(KEY_PRIORITY, updatedTask.getPriority() ? 1 : 0);
        db.update(TABLE_LIST, values, KEY_ID + "=?", new String[]{Integer.toString(id)});
        db.close();
    }

    public Task get(int id) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LIST, new String[] { KEY_ID,
                        KEY_NAME, KEY_DATE, KEY_BOOL, KEY_PRIORITY }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            Task C = new Task(cursor.getString(1), strToCal(cursor.getString(2)), strToBool(cursor.getString(3)));
            C.setPriority(strToBool(cursor.getString(4)));
            C.setId(cursor.getInt(0));
            cursor.close();
            return C;
        }
        else {
            throw new Exception("Incorrect id");
        }
    }

    private boolean strToBool(String s) {
        if(s.equals("1"))
            return true;
        else
            return false;
    }

    private Calendar strToCal(String str) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        cal.setTime(sdf.parse(str));

        return cal;
    }

    public List<Task> getAll() throws Exception{
        List<Task> list = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task c = new Task(cursor.getString(1), strToCal(cursor.getString(2)),strToBool(cursor.getString(3)));
                c.setPriority(strToBool(cursor.getString(cursor.getColumnIndex(KEY_PRIORITY))));
                c.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                list.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public String[] getAllStrings() throws Exception{
        //TODO
        String[] list = new String[5];

        return list;
    }
}

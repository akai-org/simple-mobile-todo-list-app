package akai.com.todolistapp;

/**
 * Created by Michal on 13.11.2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    private static int sortmode = 0;

    public int getSortmode() {
        return sortmode;
    }

    public void setSortmodeDate() {
        DBHelper.sortmode = 1;
    }

    public void setSortmodeKey() {
        DBHelper.sortmode = 0;
    }

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
        values.put(KEY_NAME, c.getTitle());
        values.put(KEY_DATE, calToStr(c.getDate()));
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
        values.put(KEY_DATE, calToStr(updatedTask.getDate()));
        values.put(KEY_BOOL, updatedTask.getStatus() ? 1 : 0);
        values.put(KEY_PRIORITY, updatedTask.getPriority() ? 1 : 0);
        db.update(TABLE_LIST, values, KEY_ID + "=?", new String[]{Integer.toString(id)});
        db.close();
    }

    public void delete(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_LIST, KEY_ID + "=?", new String[]{Integer.toString(task.getId())});
        db.close();
    }

    public void delete(List<Task> tasks) {
        SQLiteDatabase db = getWritableDatabase();
        StringBuilder where = new StringBuilder(KEY_ID + " IN (");
        for(Task t : tasks) {
            where.append(t.getId()).append(", ");
        }
        where.replace(where.length()-2, where.length(), ")");
        db.delete(TABLE_LIST, where.toString(), null);
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

    private String calToStr(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    private Calendar strToCal(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(str));
        return cal;
    }

    public List<Task> getAll() throws Exception{
        List<Task> list = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LIST + " ORDER BY " + KEY_PRIORITY + " DESC, " + KEY_DATE;
        String selectQuery2 = "SELECT  * FROM " + TABLE_LIST + " ORDER BY " + KEY_DATE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        //Log.d("DBHelper",String.valueOf(DBHelper.sortmode));
        if(DBHelper.sortmode == 0){
            cursor = db.rawQuery(selectQuery, null);
        } else{
            cursor = db.rawQuery(selectQuery2, null);
        }

        if (cursor.moveToFirst()) {
            do {
                //Log.d("DBhelper",cursor.getString(2));
                Task c = new Task(cursor.getString(1), strToCal(cursor.getString(2)),strToBool(cursor.getString(3)));
                c.setPriority(strToBool(cursor.getString(cursor.getColumnIndex(KEY_PRIORITY))));
                c.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                list.add(c);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return list;
    }

}

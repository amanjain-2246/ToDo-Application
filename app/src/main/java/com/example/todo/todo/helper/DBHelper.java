package com.example.todo.todo.helper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class DBHelper extends SQLiteOpenHelper {

    public static  final String KEY_ID="_id";
    public static  final String TITLE="ToDo_Title";
    public static  final String DESCRIPTION="ToDo_Description";
    public static  final String DUE_DATE="ToDo_Due_date";
    public static  final String DUE_TIME="ToDo_Due_time";
    public static  final String STATUS="ToDo_Status";
    public static  final String PRIORITY="ToDo_Priority";
    public static  final String START_DATE="ToDo_Start_date_time";
    public static  final String END_DATE="ToDo_End_date_time";
    public static  final String EMAIL="ToDo_Email_id";
    public static  final String DB_NAME="NewToDo8.db";
    public static  final String TAB_NAME="ToDo_List";
    public static  final int DB_VER=1;
    Context context;
    public static final String SQL_QUERY=" create table "+TAB_NAME+" ( "
            +KEY_ID+" integer primary key autoincrement, "
            +TITLE+" text , "
            +DESCRIPTION+" text, "
            +DUE_DATE+" text NOT NULL, "
            +DUE_TIME+" TIME NOT NULL, "
            +STATUS+" text, "
            +PRIORITY+" number, "
            +START_DATE+" text, "
            +END_DATE+" text, "
            +EMAIL+" text ); ";




    public DBHelper(Context con)
    {
        super(con,DB_NAME,null,DB_VER);
        context=con;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_QUERY);

        Log.d("hello", "onCreate");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXIST " +TAB_NAME);
        Log.d("hello","onUpgrade");
        onCreate(db);
    }

    public void insert(DBHelper mydb,String title,String description,String dueDate,String dueTime,int Priority)
    {
        SQLiteDatabase sq=mydb.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TITLE,title);
        cv.put(DESCRIPTION,description);
        cv.put(DUE_DATE,dueDate);
        cv.put(DUE_TIME,dueTime);
        cv.put(PRIORITY,Priority);
        cv.put(STATUS,"CREATED");
        cv.putNull(START_DATE);
        cv.putNull(END_DATE);
        sq.insert(TAB_NAME,null,cv);
        Log.d("hello","oninserted");
     //   Toast.makeText(context, "Entered Succcessfully", Toast.LENGTH_LONG).show();;
    }

    public Cursor getALlRows(DBHelper mydb)
    {
        String cols[]={KEY_ID,TITLE,DUE_DATE,DUE_TIME,DESCRIPTION,STATUS,PRIORITY};
        SQLiteDatabase sq=mydb.getWritableDatabase();
        String where=STATUS + " LIKE ? ";
        String args[]= {"CREATED"};
        Cursor cob=sq.query(TAB_NAME,cols,where,args,null,null,null);
        if(cob!=null)
        {
            cob.moveToFirst();

        }
        return cob;
    }

    public Cursor getALlRowsCompleted(DBHelper mydb)
    {
        String cols[]={KEY_ID,TITLE,DUE_DATE,DUE_TIME,DESCRIPTION};
        SQLiteDatabase sq=mydb.getWritableDatabase();
        String where=STATUS + " LIKE ? ";
        String args[]= {"COMPLETED"};
        Cursor cob=sq.query(TAB_NAME,cols,where,args,null,null,null);
        if(cob!=null)
        {
            cob.moveToFirst();

        }
        return cob;
    }

    public Cursor getALlRowsRemaining(DBHelper mydb)
    {
        String cols[]={KEY_ID,TITLE,DUE_DATE,DUE_TIME,DESCRIPTION};
        SQLiteDatabase sq=mydb.getWritableDatabase();
        String where=STATUS + " LIKE ? ";
        String args[]= {"REMAINING"};
        Cursor cob=sq.query(TAB_NAME,cols,where,args,null,null,null);
        if(cob!=null)
        {
            cob.moveToFirst();

        }
        return cob;
    }


    public Cursor shoedesc(DBHelper mydb,String title)
    {
        String cols[]={TITLE,DESCRIPTION,DUE_DATE,DUE_TIME,PRIORITY};
        SQLiteDatabase sq=mydb.getReadableDatabase();
        String where=TITLE + " LIKE ? ";
        String args[]= {title};
        Cursor cob=sq.query(TAB_NAME,cols,where,args,null,null,null);
        if(cob!=null)
        {
            cob.moveToFirst();
        }
        return cob;
    }



    public  void update(DBHelper mob,String title,String newdescription,String newdate,String newtime,int newpriority )
    {
        String selection=TITLE + " LIKE ? ";
        String args[]={title};
        SQLiteDatabase sq=mob.getWritableDatabase();
        ContentValues CV=new ContentValues();
        CV.put(DESCRIPTION,newdescription);
        CV.put(DUE_DATE,newdate);
        CV.put(DUE_TIME,newtime);
        CV.put(PRIORITY,newpriority);
        sq.update(TAB_NAME,CV,selection,args);
    }

    public  void statusUpdate(DBHelper mob,String title,String status)
    {
        String selection=TITLE + " LIKE ? ";
        String args[]={title};
        SQLiteDatabase sq=mob.getWritableDatabase();
        ContentValues CV=new ContentValues();
        CV.put(STATUS,status);
        sq.update(TAB_NAME,CV,selection,args);
    }

    public void delete(DBHelper mob,String title)
    {
        String selection=TITLE + " LIKE ? ";
        String args[]={title};
        SQLiteDatabase sq=mob.getWritableDatabase();
        sq.delete(TAB_NAME,selection,args);

    }


    public Cursor prioritySort(DBHelper mydb)
    {
        String cols[]={KEY_ID,TITLE,DUE_DATE,DUE_TIME,DESCRIPTION,PRIORITY};
        SQLiteDatabase sq=mydb.getReadableDatabase();
        //String sortpriority=PRIORITY + " DESC ";
        Cursor cob=sq.query(TAB_NAME,cols,null,null,null,null,null);
        if(cob!=null)
        {
            cob.moveToFirst();

        }
        return cob;
    }

    public Cursor getTitle(DBHelper mydb)
    {
        String cols[]={TITLE};
        SQLiteDatabase sq=mydb.getReadableDatabase();
        Cursor cob=sq.query(TAB_NAME,cols,null,null,null,null,null);
        if(cob!=null)
        {
            cob.moveToFirst();

        }
        return cob;
    }

    public Cursor sendToServer(DBHelper dbHelper)
    {
        String cols[]={KEY_ID,TITLE,DUE_DATE,DUE_TIME,DESCRIPTION,STATUS,PRIORITY};
        SQLiteDatabase sq=dbHelper.getWritableDatabase();
        Cursor cob=sq.query(TAB_NAME,cols,null,null,null,null,null);
        if(cob!=null)
        {
            cob.moveToFirst();

        }
        return cob;
    }

    public void deleteuser(DBHelper mob)
    {
        SQLiteDatabase sq=mob.getWritableDatabase();
        sq.delete(TAB_NAME,null,null);

    }




}

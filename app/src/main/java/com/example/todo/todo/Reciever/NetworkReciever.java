package com.example.todo.todo.Reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import com.example.todo.todo.Connection.AddTaskConnection;
import com.example.todo.todo.Connection.DeleteTaskConnection;
import com.example.todo.todo.helper.DBHelper;

/**
 * Created by .hp on 03-07-2017.
 */

public class NetworkReciever extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if(checkInternet(context))
        {
           // Toast.makeText(context, "Network Available Do operations",Toast.LENGTH_LONG).show();
            DBHelper myhelper = new DBHelper(context);
            Cursor cursorOB = myhelper.sendToServer(myhelper);
            new DeleteTaskConnection(context).execute();
            if(cursorOB.moveToFirst())
            {
                do {
                    String title = cursorOB.getString(1);
                    String duedate = cursorOB.getString(2);
                    String duetime = cursorOB.getString(3);
                    String description=cursorOB.getString(4);
                    String status=cursorOB.getString(5);
                    String priority = String.valueOf(cursorOB.getInt(6));
                    new AddTaskConnection(context).execute(title,description,duedate,duetime,status,priority);
                }while (cursorOB.moveToNext());

            }
        }

    }

    boolean checkInternet(Context context) {
        ServiceManager serviceManager = new ServiceManager(context);
        if (serviceManager.isNetworkAvailable()) {
            return true;
        } else {
            return false;
        }
    }

}

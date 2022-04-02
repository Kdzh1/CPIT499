package com.example.kaue_parking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "DB2022", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {

    }

    public boolean insertData(String tableName, String data []){

        if (tableName.equalsIgnoreCase("driver")){
            return driverTable("insert",data);
        }else if (tableName.equalsIgnoreCase("security")){
            return securityTable("insert",data);
        }else if (tableName.equalsIgnoreCase("ticket")){
            return ticketTable("insert",data);
        }else
        return false;

    }
    public boolean deleteData(String tableName, String data []){

        if (tableName.equalsIgnoreCase("driver")){
            return driverTable("delete",data);
        }else if (tableName.equalsIgnoreCase("security")){
            return securityTable("delete",data);
        }else if (tableName.equalsIgnoreCase("ticket")){
            return ticketTable("delete",data);
        }else
            return false;

    }
    private boolean driverTable(String operation, String data[]){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (operation.equalsIgnoreCase("insert")){                                     // INSERT

            contentValues.put("id",data[0]);
            contentValues.put("password", data[1]);
            contentValues.put("name",data[2]);                                              // <---- CONTENT OF THE ARRAY
            contentValues.put("phone",data[3]);
            contentValues.put("ticketID",Integer.parseInt(data[4]));


            long result = db.insert("driver", null, contentValues);
            if (result==-1){ return false; }
            else { return true; }

        }else if(operation.equalsIgnoreCase("updateTicket")){                          // UPDATE

            contentValues.put("ticketID", data[0]);                                               // [0] = TICKET ID, [1] = USER ID
            Cursor cursor = db.rawQuery("select * from driver where id = ?", new String[] {data[1]} ); /* to check if the given id is exist or not   */
            if (cursor.getCount()>0) {
                long result = db.update("driver", contentValues, "id=?", new String[]{data[1]}); /* update will always return true even if it is null value */
                if (result == -1) {
                    return false;
                } else {
                    return true;
                }
            }
        }else if(operation.equalsIgnoreCase("delete")){                                // DELETE

                                                                                                  // [0] = USER ID
            Cursor cursor = db.rawQuery("select * from driver where id = ?", new String[] {data[0]} ); /* to check if the given id is exist or not   */
            if (cursor.getCount()>0) {
                long result = db.delete("driver", "id=?", new String[]{data[0]}); /* will always return true even if it is null value */
                if (result == -1) {
                    return false;
                } else {
                    return true;
                }
            }
        }
            return false;

    }


    private boolean securityTable(String operation, String data[]){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (operation.equalsIgnoreCase("insert")){                                     // INSERT

            contentValues.put("id",data[0]);
            contentValues.put("password", data[1]);
            contentValues.put("name",data[2]);                                                    // CONTENT OF THE ARRAY
            contentValues.put("phone",data[3]);


            long result = db.insert("security", null, contentValues);
            if (result==-1){ return false; }
            else { return true; }

        }else if(operation.equalsIgnoreCase("delete")){                                // DELETE

                                                                                                  // [0] = SECURITY ID
            Cursor cursor = db.rawQuery("select * from security where id = ?", new String[] {data[0]} ); /* to check if the given id is exist or not   */
            if (cursor.getCount()>0) {
                long result = db.delete("security", "id=?", new String[]{data[0]}); /* delete will always return true even if it is null value */
                if (result == -1) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;

    }

    private boolean ticketTable(String operation, String data[]){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (operation.equalsIgnoreCase("insert")){                                      // INSERT
            contentValues.put("id",Integer.parseInt(data[0]));
            contentValues.put("plate", data[1]);
            contentValues.put("price",data[2]);                                                    // CONTENT OF THE ARRAY
            contentValues.put("location",data[3]);
            contentValues.put("status",Integer.parseInt(data[4]));
            contentValues.put("status",Integer.parseInt(data[5]));
            contentValues.put("driverID",data[6]);
            long result = db.insert("ticket", null, contentValues);
            if (result==-1){ return false; }
            else { return true; }
        }

        return false;
    }

    public Object getData(String tableName, String id){
            if (tableName.equalsIgnoreCase("driver")){
                return getDriver(id);
            }else if (tableName.equalsIgnoreCase("security")){

            }else if (tableName.equalsIgnoreCase("ticket")){

            }
                return null;

    }
    private Driver getDriver(String id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM driver where id=? ", new String[] {id});
        if (c.moveToFirst()){

                Driver d = new Driver();
                d.setId(c.getString(0));
                d.setPassword(c.getString(1));
                d.setName(c.getString(2));
                d.setPhone(c.getString(3));
                d.setTicketID(c.getInt(4));
                return d;

        }
        return null;
    }

    private Security getSecurity(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM security where id=? ", new String[] {id});
        if (c.moveToFirst()){

            Security s = new Security();
            s.setId(c.getString(0));
            s.setPassword(c.getString(1));
            s.setName(c.getString(2));
            s.setPhone(c.getString(3));

            return s;

            }
        return null;
        }

    private Ticket getTicket(){

    }


}

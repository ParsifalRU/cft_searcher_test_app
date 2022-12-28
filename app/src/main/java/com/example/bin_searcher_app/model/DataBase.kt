package com.example.bin_searcher_app.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import io.reactivex.rxjava3.core.Single

class DataBase(context: Context): SQLiteOpenHelper(context, DbConfig.DATABASE_NAME, null, DbConfig.DATABASE_VERSION) {


    private val allHint: ArrayList<String> = arrayListOf()


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DbConfig.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DbConfig.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun addAuto(hint: String): Single<ArrayList<String>>{
        return Single.create{ subscriber ->
            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put(DbConfig.COLUMN_NAME_0, hint)
            db.insert(DbConfig.TABLE_NAME, null, cv)
            val cursor = db.query(DbConfig.TABLE_NAME, null, null, null, null, null, null)
            cursor(cursor)
            cursor.close()
            db.close()
            subscriber.onSuccess(allHint)
        }
    }

    fun delete(): Single<String> {
        return Single.create {
            val db = this.writableDatabase
            db.delete(DbConfig.TABLE_NAME, null, null)
            db.close()
        }
    }

    fun rvList(): Single<ArrayList<String>> {
        return Single.create{ subscriber ->
        val db = this.writableDatabase
        val cursor = db.query(DbConfig.TABLE_NAME, null, null, null, null, null, null)
        cursor(cursor)
        cursor.close()
        db.close()
        subscriber.onSuccess(allHint)
        }
    }

    private fun cursor(cursor: Cursor){

        if (cursor.moveToFirst()) {
            val col0ColIndex = cursor.getColumnIndex(DbConfig.COLUMN_NAME_0)
            do {
                val col0 = cursor.getString(col0ColIndex)
                allHint.add(col0)
            } while (cursor.moveToNext())
        }
    }
}

private object DbConfig{

    const val DATABASE_NAME = "HINT_DB"
    const val DATABASE_VERSION = 1

    const val TABLE_NAME = "HINT_TABLE"

    const val COLUMN_NAME_0 = "Word"

    const val SQL_CREATE_ENTRIES =  "CREATE TABLE $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_0 Text)"

    const val SQL_DELETE_ENTRIES = "DROP IF NOT EXISTS $TABLE_NAME"

}
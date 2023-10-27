package com.example.databasehw

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBGateway(context: Context?): SQLiteOpenHelper(context, "SpareParts.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("Create Table Categories(ID Integer PRIMARY KEY AUTOINCREMENT, definition TEXT);")
        db.execSQL("Create Table Parts(ID Integer PRIMARY KEY AUTOINCREMENT, categoryID Integer, name Text, stockCount Integer, price Integer);")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}
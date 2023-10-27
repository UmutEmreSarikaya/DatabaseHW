package com.example.databasehw

import android.content.ContentValues
import android.content.Context

data class Category(var categoryID: Int = 0, var definition: String = "")

data class SparePart(
    var sparePartID: Int = 0,
    var categoryID: Int = 0,
    var name: String = "",
    var stockCount: Int = 0,
    var price: Long = 0L
)

class CategoryRepository(c: Context) {
    private var context: Context? = null
    private var dbGateway: DBGateway? = null

    init {
        context = c
        dbGateway = DBGateway(c)
    }

    fun createCategories() {
        val db = dbGateway!!.writableDatabase

        val cursor = db.rawQuery("Select * from Categories", null)

        if (cursor.count == 0) {
            var cv = ContentValues()
            cv.put("definition", "Motor")
            db.insert("Categories", null, cv)

            cv = ContentValues()
            cv.put("definition", "Bodywork")
            db.insert("Categories", null, cv)

            cv = ContentValues()
            cv.put("definition", "Electronic")
            db.insert("Categories", null, cv)

            cv = ContentValues()
            cv.put("definition", "Accessory")
            db.insert("Categories", null, cv)
        }
        cursor.close()
        db.close()
    }

    fun getCategories(): MutableList<Category> {
        val db = dbGateway!!.readableDatabase

        val cursor = db.rawQuery("Select * from Categories", null)

        val lst = mutableListOf<Category>()

        while (cursor.moveToNext()) {
            lst.add(
                Category(
                    cursor.getInt(0), cursor.getString(1)
                )
            )
        }
        cursor.close()
        return lst
    }
}

class SparePartRepository(c: Context) {
    private var context: Context? = null
    private var dbGateway: DBGateway? = null

    init {
        context = c
        dbGateway = DBGateway(c)
    }

    fun addSparePart(sparePart: SparePart) {
        val db = dbGateway!!.writableDatabase

        val cv = ContentValues()
        cv.put("categoryID", sparePart.categoryID)
        cv.put("name", sparePart.name)
        cv.put("stockCount", sparePart.stockCount)
        cv.put("price", sparePart.price)

        db.insert("Parts", null, cv)

        db.close()
    }

    fun createSpareParts() {
        val db = dbGateway!!.writableDatabase
        val cursor = db.rawQuery("Select * from Parts", null)
        if (cursor.count == 0) {
            addSparePart(SparePart(-1, 1, "Piston Ring", 10, 300L))
            addSparePart(SparePart(-1, 1, "Timing Belt", 15, 1300L))
            addSparePart(SparePart(-1, 1, "Crank Main Bearing", 90, 700L))

            addSparePart(SparePart(-1, 2, "Door", 10, 300L))
            addSparePart(SparePart(-1, 2, "Travers", 15, 1300L))
            addSparePart(SparePart(-1, 2, "Bracket", 90, 700L))

            addSparePart(SparePart(-1, 3, "Lambda Sensor", 15, 1300L))
            addSparePart(SparePart(-1, 4, "Pandizot", 90, 700L))
        }
        cursor.close()
        db.close()
    }

    fun partsByCategoryID(kid: Int): MutableList<SparePart> {
        val lst = mutableListOf<SparePart>()

        val db = dbGateway!!.readableDatabase

        val cursor =
            db.rawQuery("Select * from Parts Where categoryID = ?", arrayOf(kid.toString()))

        while (cursor.moveToNext()) {
            lst.add(
                SparePart(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getLong(4)
                )
            )
        }
        cursor.close()
        db.close()
        return lst
    }
}
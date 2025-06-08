package kikicom.mobile.forensictil.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecordDBHelper(context: Context)
    : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DATE TEXT,
                $COLUMN_TOPIC TEXT,
                $COLUMN_TOOL TEXT,
                $COLUMN_CONTENT TEXT,
                $COLUMN_TIME_SPENT INTEGER
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    companion object {
        const val DB_NAME = "forensic_til.db"
        const val DB_VERSION = 1

        const val TABLE_NAME = "records"
        const val COLUMN_ID = "id"
        const val COLUMN_DATE = "date"
        const val COLUMN_TOPIC = "topic"
        const val COLUMN_TOOL = "tool"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_TIME_SPENT = "time_spent"
    }
}
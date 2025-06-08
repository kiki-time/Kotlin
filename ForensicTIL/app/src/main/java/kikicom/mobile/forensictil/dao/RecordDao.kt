package kikicom.mobile.forensictil.dao

import android.content.ContentValues
import android.content.Context
import kikicom.mobile.forensictil.data.ForensicRecord
import kikicom.mobile.forensictil.data.RecordDBHelper

class RecordDao(context: Context) {
    private val dbHelper = RecordDBHelper(context)

    fun getAllRecords(): List<ForensicRecord> {
        val recordList = mutableListOf<ForensicRecord>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            RecordDBHelper.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "${RecordDBHelper.COLUMN_DATE} DESC"
        )

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(RecordDBHelper.COLUMN_ID))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(RecordDBHelper.COLUMN_DATE))
            val topic = cursor.getString(cursor.getColumnIndexOrThrow(RecordDBHelper.COLUMN_TOPIC))
            val tool = cursor.getString(cursor.getColumnIndexOrThrow(RecordDBHelper.COLUMN_TOOL))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(RecordDBHelper.COLUMN_CONTENT))
            val timeSpent = cursor.getInt(cursor.getColumnIndexOrThrow(RecordDBHelper.COLUMN_TIME_SPENT))

            val record = ForensicRecord(id, date, topic, tool, content, timeSpent)
            recordList.add(record)
        }
        cursor.close()
        db.close()
        return recordList
    }

    fun insertRecord(record: ForensicRecord) : Long{
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(RecordDBHelper.COLUMN_DATE, record.date)
            put(RecordDBHelper.COLUMN_TOPIC, record.topic)
            put(RecordDBHelper.COLUMN_TOOL, record.tool)
            put(RecordDBHelper.COLUMN_CONTENT, record.content)
            put(RecordDBHelper.COLUMN_TIME_SPENT, record.timeSpent)
        }
        val rowId = db.insert(RecordDBHelper.TABLE_NAME, null, values)
        db.close()
        return rowId
    }

    fun updateRecord(record: ForensicRecord): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(RecordDBHelper.COLUMN_DATE, record.date)
            put(RecordDBHelper.COLUMN_TOPIC, record.topic)
            put(RecordDBHelper.COLUMN_TOOL, record.tool)
            put(RecordDBHelper.COLUMN_CONTENT, record.content)
            put(RecordDBHelper.COLUMN_TIME_SPENT, record.timeSpent)
        }
        val result = db.update(
            RecordDBHelper.TABLE_NAME,
            values,
            "${RecordDBHelper.COLUMN_ID} = ?",
            arrayOf(record.id.toString())
        )
        db.close()
        return result
    }

    fun deleteRecord(id: Int): Int {
        val db = dbHelper.writableDatabase
        val result = db.delete(
            RecordDBHelper.TABLE_NAME,
            "${RecordDBHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
        db.close()
        return result
    }
}
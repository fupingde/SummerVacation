package com.example.module.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MyDatabase.db"
        private const val TABLE_NAME = "Songs"
        private const val COLUMN_ID = "id"
        private const val COLUMN_SONG_ID = "song_id"
        private const val COLUMN_SONG_NAME = "song_name"
        private const val COLUMN_SONG_ARTIST = "song_artist"
        private const val COLUMN_SONG_PICTUREURL = "song_pictureurl"

        private const val TABLE_CREATE =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_SONG_ID INTEGER, " +
                    "$COLUMN_SONG_NAME TEXT, " +
                    "$COLUMN_SONG_ARTIST TEXT, " +
                    "$COLUMN_SONG_PICTUREURL TEXT);"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertSong(songId: Long, songName: String, songArtist: String, songPictureUrl: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SONG_ID, songId)
            put(COLUMN_SONG_NAME, songName)
            put(COLUMN_SONG_ARTIST, songArtist)
            put(COLUMN_SONG_PICTUREURL, songPictureUrl)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
}

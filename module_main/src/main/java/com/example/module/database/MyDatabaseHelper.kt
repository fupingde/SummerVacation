package com.example.module.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import com.example.network.Bean.CollctedSong
import com.example.network.Bean.MyrvBean

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "MyDatabase.db"
        private const val TABLE_COLLECTED_NAME = "CollectedSongs"
        private const val TABLE_DOWNLOADED_NAME = "DownloadedSongs"
        private const val TABLE_COLLECTED_PLAYLISTS = "CollectedPlaylists"
        private const val COLUMN_ID = "id"
        private const val COLUMN_SONG_ID = "song_id"
        private const val COLUMN_SONG_NAME = "song_name"
        private const val COLUMN_SONG_ARTIST = "song_artist"
        private const val COLUMN_SONG_PICTUREURL = "song_pictureurl"
        private const val COLUMN_PLAYLIST_ID = "playlist_id"
        private const val COLUMN_PLAYLIST_NAME = "playlist_name"
        private const val COLUMN_PLAYLIST_IMAGEURL = "playlist_imageurl"

        private const val TABLE_COLLECTED_CREATE =
            "CREATE TABLE IF NOT EXISTS $TABLE_COLLECTED_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_SONG_ID INTEGER, " +
                    "$COLUMN_SONG_NAME TEXT, " +
                    "$COLUMN_SONG_ARTIST TEXT, " +
                    "$COLUMN_SONG_PICTUREURL TEXT);"

        private const val TABLE_DOWNLOADED_CREATE =
            "CREATE TABLE IF NOT EXISTS $TABLE_DOWNLOADED_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_SONG_ID INTEGER, " +
                    "$COLUMN_SONG_NAME TEXT, " +
                    "$COLUMN_SONG_ARTIST TEXT, " +
                    "$COLUMN_SONG_PICTUREURL TEXT);"

        private const val TABLE_COLLECTED_PLAYLISTS_CREATE =
            "CREATE TABLE IF NOT EXISTS $TABLE_COLLECTED_PLAYLISTS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_PLAYLIST_ID INTEGER, " +
                    "$COLUMN_PLAYLIST_NAME TEXT, " +
                    "$COLUMN_PLAYLIST_IMAGEURL TEXT);"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_COLLECTED_CREATE)
        db.execSQL(TABLE_DOWNLOADED_CREATE)
        db.execSQL(TABLE_COLLECTED_PLAYLISTS_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COLLECTED_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DOWNLOADED_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COLLECTED_PLAYLISTS")
        onCreate(db)
    }

    fun insertCollectedSong(songId: Long, songName: String, songArtist: String, songPictureUrl: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SONG_ID, songId)
            put(COLUMN_SONG_NAME, songName)
            put(COLUMN_SONG_ARTIST, songArtist)
            put(COLUMN_SONG_PICTUREURL, songPictureUrl)
        }
        db.insert(TABLE_COLLECTED_NAME, null, values)
        db.close()
    }
    fun getAllCollectedSongs(): List<CollctedSong> {
        val songs = mutableListOf<CollctedSong>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_COLLECTED_NAME, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_SONG_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_SONG_NAME))
                val artist = getString(getColumnIndexOrThrow(COLUMN_SONG_ARTIST))
                val songPictureUrl=getString(getColumnIndexOrThrow(COLUMN_SONG_PICTUREURL))
                songs.add(CollctedSong(id, name, artist,songPictureUrl))
            }
        }
        cursor.close()
        db.close()
        return songs
    }
    fun getAllDownloadedSongs(): List<CollctedSong> {
        val songs = mutableListOf<CollctedSong>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_DOWNLOADED_NAME, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_SONG_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_SONG_NAME))
                val artist = getString(getColumnIndexOrThrow(COLUMN_SONG_ARTIST))
                val songPictureUrl=getString(getColumnIndexOrThrow(COLUMN_SONG_PICTUREURL))
                songs.add(CollctedSong(id, name, artist,songPictureUrl))
            }
        }
        cursor.close()
        db.close()
        return songs
    }


    fun deleteCollectedSong(songId: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_COLLECTED_NAME, "$COLUMN_SONG_ID=?", arrayOf(songId.toString()))
        db.close()
    }

    fun isSongCollected(songId: Long): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_COLLECTED_NAME, arrayOf(COLUMN_ID),
            "$COLUMN_SONG_ID=?", arrayOf(songId.toString()),
            null, null, null
        )
        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return exists
    }
    fun getCollectedCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_COLLECTED_NAME", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        db.close()
        return count
    }

    fun getDownloadedCount(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_DOWNLOADED_NAME", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        db.close()
        return count
    }

    fun insertDownloadedSong(songId: Long, songName: String, songArtist: String, songPictureUrl: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SONG_ID, songId)
            put(COLUMN_SONG_NAME, songName)
            put(COLUMN_SONG_ARTIST, songArtist)
            put(COLUMN_SONG_PICTUREURL, songPictureUrl)
        }
        db.insert(TABLE_DOWNLOADED_NAME, null, values)
        db.close()
    }

    fun isSongDownloaded(songId: Long): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_DOWNLOADED_NAME, arrayOf(COLUMN_ID),
            "$COLUMN_SONG_ID=?", arrayOf(songId.toString()),
            null, null, null
        )
        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return exists
    }

    fun insertCollectedPlaylist(playlistId: Long, playlistName: String, playlistImageUrl: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PLAYLIST_ID, playlistId)
            put(COLUMN_PLAYLIST_NAME, playlistName)
            put(COLUMN_PLAYLIST_IMAGEURL, playlistImageUrl)
        }
        db.insert(TABLE_COLLECTED_PLAYLISTS, null, values)
        db.close()
    }

    fun deleteCollectedPlaylist(playlistId: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_COLLECTED_PLAYLISTS, "$COLUMN_PLAYLIST_ID=?", arrayOf(playlistId.toString()))
        db.close()
    }

    fun isPlaylistCollected(playlistId: Long): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_COLLECTED_PLAYLISTS, arrayOf(COLUMN_ID),
            "$COLUMN_PLAYLIST_ID=?", arrayOf(playlistId.toString()),
            null, null, null
        )
        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return exists
    }
    fun getAllCollectedPlaylists(): List<MyrvBean> {
        val playlists = mutableListOf<MyrvBean>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_COLLECTED_PLAYLISTS, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_PLAYLIST_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_PLAYLIST_NAME))
                val imageUrl = getString(getColumnIndexOrThrow(COLUMN_PLAYLIST_IMAGEURL))
                playlists.add(MyrvBean(id, name, imageUrl))
            }
        }
        cursor.close()
        db.close()
        return playlists
    }

}

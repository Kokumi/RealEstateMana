package com.openclassrooms.realestatemanager.model.contentProvider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.openclassrooms.realestatemanager.model.AppDatabase
import com.openclassrooms.realestatemanager.model.Entity.Image
import com.openclassrooms.realestatemanager.model.Entity.RealEstate
import java.lang.IllegalArgumentException

/**
 * Created by Debruyckère Florian on 20/01/2020.
 */
class ItemContentProvider : ContentProvider() {

    val AUTHORITY = "com.openclassrooms.realestatemanager.provider"
    val TABLE_NAME = Image::class.simpleName
    val URI_ITEM = Uri.parse("content://$AUTHORITY/$TABLE_NAME")

    override fun onCreate(): Boolean { return true }


    override fun query(pUri: Uri, projection: Array<String>?, selection: String?, selectionArg: Array<String>?, sortOrder: String?): Cursor? {

        /*if (this.context != null){
            val userId : Long = ContentUris.parseId(pUri)
            val db = Room.databaseBuilder(this.context!!, AppDatabase::class.java, "database").build()
            val cursor = db.realEstateDao().getRealEstateWithCursor(userId)
            cursor.setNotificationUri(context!!.contentResolver, pUri)

            return cursor
        }*/

        throw IllegalArgumentException("Failed to query row for uri $pUri")
    }

    override fun insert(pUri: Uri, pContentValues: ContentValues?): Uri? {

        /*if(context != null) {
            val id : Long = Room.databaseBuilder(this.context!!, AppDatabase::class.java, "database").build()
                    .realEstateDao().insertImageWReturn(Image(0,"").fromContentValues(pContentValues!!))

            if(id != 0.toLong()){
                context!!.contentResolver.notifyChange(pUri,null)
                return ContentUris.withAppendedId(pUri,id)
            }
        }*/

        throw IllegalArgumentException("Failed to insert row into $pUri")
    }

    override fun delete(pUri: Uri, pString: String?, pArrayString: Array<String>?): Int {
        /*if(context != null){
            val count = Room.databaseBuilder(this.context!!, AppDatabase::class.java, "database").build()
                    .realEstateDao().deleteImage(ContentUris.parseId(pUri).toInt())
            context!!.contentResolver.notifyChange(pUri,null)
            return count
        }*/

        throw IllegalArgumentException("Failed to delete row into: $pUri")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<String>?): Int {
        return 0
    }

    override fun getType(p0: Uri): String? {
        return ""//"vnd.android.cursor.image/$AUTHORITY.$TABLE_NAME"
    }
}
package com.allinone.feature.storage.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri

class DemoContentProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.allinone.demo.provider"
        private const val BASE_PATH = "items"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$BASE_PATH")

        private const val ITEMS = 1
        private const val ITEM_ID = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, BASE_PATH, ITEMS)
            addURI(AUTHORITY, "$BASE_PATH/#", ITEM_ID)
        }
    }

    // In-memory data store for demo
    private val data = mutableListOf<Map<String, Any>>()
    private var nextId = 1L

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        val columns = projection ?: arrayOf("id", "title", "description", "category")

        return MatrixCursor(columns).apply {
            when (uriMatcher.match(uri)) {
                ITEMS -> {
                    data.forEach { item ->
                        newRow().apply {
                            add("id", item["id"] as Long)
                            add("title", item["title"] as? String ?: "")
                            add("description", item["description"] as? String ?: "")
                            add("category", item["category"] as? String ?: "")
                        }
                    }
                }
                ITEM_ID -> {
                    val id = uri.lastPathSegment?.toLongOrNull()
                    data.find { it["id"] as Long == id }?.let { item ->
                        newRow().apply {
                            add("id", item["id"] as Long)
                            add("title", item["title"] as? String ?: "")
                            add("description", item["description"] as? String ?: "")
                            add("category", item["category"] as? String ?: "")
                        }
                    }
                }
            }
        }
    }

    override fun getType(uri: Uri): String {
        return when (uriMatcher.match(uri)) {
            ITEMS -> "vnd.android.cursor.dir/vnd.$AUTHORITY.$BASE_PATH"
            ITEM_ID -> "vnd.android.cursor.item/vnd.$AUTHORITY.$BASE_PATH"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val item = mutableMapOf<String, Any>(
            "id" to nextId,
            "title" to (values?.getAsString("title") ?: ""),
            "description" to (values?.getAsString("description") ?: ""),
            "category" to (values?.getAsString("category") ?: "")
        )
        data.add(item)
        val newItemUri = Uri.parse("$CONTENT_URI/$nextId")
        nextId++
        context?.contentResolver?.notifyChange(uri, null)
        return newItemUri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val count = when (uriMatcher.match(uri)) {
            ITEMS -> {
                val oldSize = data.size
                data.clear()
                oldSize
            }
            ITEM_ID -> {
                val id = uri.lastPathSegment?.toLongOrNull()
                val removed = data.removeIf { it["id"] as Long == id }
                if (removed) 1 else 0
            }
            else -> 0
        }
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return when (uriMatcher.match(uri)) {
            ITEMS -> {
                data.forEachIndexed { index, item ->
                    val updated = item.toMutableMap()
                    values?.let { v ->
                        v.keySet().forEach { key ->
                            updated[key] = v[key] as Any
                        }
                    }
                    data[index] = updated
                }
                data.size
            }
            ITEM_ID -> {
                val id = uri.lastPathSegment?.toLongOrNull()
                val index = data.indexOfFirst { it["id"] as Long == id }
                if (index >= 0) {
                    val updated = data[index].toMutableMap()
                    values?.let { v ->
                        v.keySet().forEach { key ->
                            updated[key] = v[key] as Any
                        }
                    }
                    data[index] = updated
                    1
                } else 0
            }
            else -> 0
        }.also {
            context?.contentResolver?.notifyChange(uri, null)
        }
    }
}

package com.naveen.mynewsapp.service.data.local

import android.app.Application
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

class ApiService {
    fun loadJSONFromAsset(application: Application): String? {
        var json: String? = null
        json = try {
            val `is` = application.assets.open("media_item.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun getList(application: Application): ArrayList<HashMap<String, String>> {
        val formList = ArrayList<HashMap<String, String>>()
        try {
            val obj = JSONObject(loadJSONFromAsset(application))
            val m_jArry = obj.getJSONArray("MediaItems")
            var m_li: HashMap<String, String>
            for (i in 0 until m_jArry.length()) {
                val jo_inside = m_jArry.getJSONObject(i)
                val title = jo_inside.getString("Title")
                val image = jo_inside.getString("Image")
                val url = jo_inside.getString("Url")

                //Add your values in your `ArrayList` as below:
                m_li = HashMap()
                m_li["title"] = title
                m_li["image_url"] = image
                m_li["url"] = url
                formList.add(m_li)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return formList
    }
}
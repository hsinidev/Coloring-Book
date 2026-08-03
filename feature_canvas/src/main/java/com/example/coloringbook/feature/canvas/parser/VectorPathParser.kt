package com.example.coloringbook.feature.canvas.parser

import android.graphics.Path
import androidx.core.graphics.PathParser
import com.example.coloringbook.core.ui.components.CanvasPathData
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

object VectorPathParser {
    fun parse(inputStream: InputStream): List<CanvasPathData> {
        val paths = mutableListOf<CanvasPathData>()
        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(inputStream, "UTF-8")
            
            var eventType = parser.eventType
            var pathIndex = 0
            
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.name == "path") {
                    var pathId: String? = null
                    var pathData: String? = null
                    var fillColor: Int? = null
                    var strokeColor: Int? = null
                    var strokeWidth: Float? = null
                    
                    for (i in 0 until parser.attributeCount) {
                        val attrName = parser.getAttributeName(i)
                        val attrValue = parser.getAttributeValue(i)
                        
                        when (attrName) {
                            "id", "name" -> pathId = attrValue
                            "pathData" -> pathData = attrValue
                            "fillColor" -> fillColor = parseColor(attrValue)
                            "strokeColor" -> strokeColor = parseColor(attrValue)
                            "strokeWidth" -> strokeWidth = attrValue.toFloatOrNull()
                        }
                        // Support android namespace attributes
                        if (parser.getAttributeNamespace(i).contains("android") || attrName.startsWith("android:")) {
                            val cleanName = attrName.substringAfter(":")
                            when (cleanName) {
                                "id" -> pathId = attrValue.substringAfterLast("/")
                                "pathData" -> pathData = attrValue
                                "fillColor" -> fillColor = parseColor(attrValue)
                                "strokeColor" -> strokeColor = parseColor(attrValue)
                                "strokeWidth" -> strokeWidth = attrValue.toFloatOrNull()
                            }
                        }
                    }
                    
                    if (pathData != null) {
                        val finalId = pathId ?: "path_$pathIndex"
                        val parsedPath = PathParser.createPathFromPathData(pathData)
                        paths.add(
                            CanvasPathData(
                                id = finalId,
                                path = parsedPath,
                                originalFillColor = fillColor,
                                strokeColor = strokeColor,
                                strokeWidth = strokeWidth
                            )
                        )
                        pathIndex++
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: Exception) {
                // Ignore
            }
        }
        return paths
    }

    private fun parseColor(colorStr: String): Int? {
        if (colorStr.isEmpty()) return null
        return try {
            if (colorStr.startsWith("#")) {
                val hex = colorStr.substring(1)
                when (hex.length) {
                    6 -> (0xFF000000 or hex.toLong(16)).toInt()
                    8 -> hex.toLong(16).toInt()
                    3 -> {
                        val r = hex[0].toString()
                        val g = hex[1].toString()
                        val b = hex[2].toString()
                        (0xFF000000 or "$r$r$g$g$b$b".toLong(16)).toInt()
                    }
                    else -> null
                }
            } else {
                colorStr.toIntOrNull()
            }
        } catch (e: Exception) {
            null
        }
    }

    fun computeBounds(paths: List<CanvasPathData>): TemplateBounds {
        if (paths.isEmpty()) return TemplateBounds(0f, 0f, 0f, 0f)
        var minX = Float.MAX_VALUE
        var minY = Float.MAX_VALUE
        var maxX = Float.MIN_VALUE
        var maxY = Float.MIN_VALUE
        
        val rect = android.graphics.RectF()
        paths.forEach { pathData ->
            pathData.path.computeBounds(rect, true)
            if (rect.left < minX) minX = rect.left
            if (rect.top < minY) minY = rect.top
            if (rect.right > maxX) maxX = rect.right
            if (rect.bottom > maxY) maxY = rect.bottom
        }
        return TemplateBounds(minX, minY, maxX, maxY)
    }
}

data class TemplateBounds(
    val minX: Float,
    val minY: Float,
    val maxX: Float,
    val maxY: Float
) {
    val width: Float get() = maxX - minX
    val height: Float get() = maxY - minY
}

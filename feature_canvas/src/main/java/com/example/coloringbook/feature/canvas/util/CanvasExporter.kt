package com.example.coloringbook.feature.canvas.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path as AndroidPath
import android.graphics.RectF
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.example.coloringbook.core.data.model.ColorFill
import com.example.coloringbook.core.ui.components.CanvasPathData
import com.example.coloringbook.core.ui.util.BrushProvider
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object CanvasExporter {

    /**
     * Rasterizes the drawing to a high-res 300 DPI Bitmap (3000 x 3000px).
     */
    fun exportToBitmap(
        context: Context,
        paths: List<CanvasPathData>,
        coloredPaths: Map<String, ColorFill>
    ): Bitmap {
        val size = 3000
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        
        // Draw background
        canvas.drawColor(android.graphics.Color.WHITE)
        
        if (paths.isEmpty()) return bitmap

        // Calculate template bounds to center the drawing
        val bounds = RectF()
        paths.forEachIndexed { index, pathData ->
            val tempBounds = RectF()
            pathData.path.computeBounds(tempBounds, true)
            if (index == 0) {
                bounds.set(tempBounds)
            } else {
                bounds.union(tempBounds)
            }
        }

        val scale = (size * 0.9f) / maxOf(bounds.width(), bounds.height())
        val dx = (size - bounds.width() * scale) / 2f - bounds.left * scale
        val dy = (size - bounds.height() * scale) / 2f - bounds.top * scale

        canvas.save()
        canvas.translate(dx, dy)
        canvas.scale(scale, scale)

        // Draw fills
        val fillPaint = Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        paths.forEach { pathData ->
            val fill = coloredPaths[pathData.id]
            if (fill != null) {
                applyColorFillToPaint(context, fill, fillPaint)
                canvas.drawPath(pathData.path, fillPaint)
            }
        }

        // Draw outlines
        val outlinePaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 2f
            color = android.graphics.Color.BLACK
            isAntiAlias = true
        }

        paths.forEach { pathData ->
            canvas.drawPath(pathData.path, outlinePaint)
        }

        canvas.restore()
        return bitmap
    }

    /**
     * Exports the drawing as a vector PDF document.
     */
    fun exportToPdf(
        context: Context,
        paths: List<CanvasPathData>,
        coloredPaths: Map<String, ColorFill>
    ): PdfDocument {
        val pdfDocument = PdfDocument()
        
        // Standard A4 Portrait size: 595 x 842 points
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        // Calculate template bounds to center
        val bounds = RectF()
        paths.forEachIndexed { index, pathData ->
            val tempBounds = RectF()
            pathData.path.computeBounds(tempBounds, true)
            if (index == 0) {
                bounds.set(tempBounds)
            } else {
                bounds.union(tempBounds)
            }
        }

        val scale = (595 * 0.9f) / maxOf(bounds.width(), bounds.height())
        val dx = (595 - bounds.width() * scale) / 2f - bounds.left * scale
        val dy = (842 - bounds.height() * scale) / 2f - bounds.top * scale

        canvas.save()
        canvas.translate(dx, dy)
        canvas.scale(scale, scale)

        // Draw fills
        val fillPaint = Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        paths.forEach { pathData ->
            val fill = coloredPaths[pathData.id]
            if (fill != null) {
                applyColorFillToPaint(context, fill, fillPaint)
                canvas.drawPath(pathData.path, fillPaint)
            }
        }

        // Draw outlines
        val outlinePaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 2f
            color = android.graphics.Color.BLACK
            isAntiAlias = true
        }

        paths.forEach { pathData ->
            canvas.drawPath(pathData.path, outlinePaint)
        }

        canvas.restore()
        pdfDocument.finishPage(page)
        return pdfDocument
    }

    private fun applyColorFillToPaint(context: Context, fill: ColorFill, paint: Paint) {
        when (fill) {
            is ColorFill.Solid -> {
                paint.shader = null
                paint.color = fill.color
            }
            is ColorFill.LinearGradient -> {
                // Approximate with linear shader or simple gradient
                paint.shader = null
                paint.color = fill.colors.firstOrNull() ?: android.graphics.Color.TRANSPARENT
            }
            is ColorFill.RadialGradient -> {
                paint.shader = null
                paint.color = fill.colors.firstOrNull() ?: android.graphics.Color.TRANSPARENT
            }
            is ColorFill.Texture -> {
                // Fallback to solid baseColor for off-screen rasterization
                // (BrushProvider.createBrush is Compose-only; Android Paint uses color fallback)
                paint.shader = null
                paint.color = fill.baseColor
            }
        }
    }

    /**
     * Saves a Bitmap to the gallery using MediaStore.
     */
    fun saveBitmapToGallery(context: Context, bitmap: Bitmap, fileName: String): Uri? {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.png")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/ChromaMind")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }

        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (imageUri != null) {
            try {
                resolver.openOutputStream(imageUri).use { outputStream ->
                    if (outputStream != null) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.clear()
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(imageUri, contentValues, null, null)
                }
            } catch (e: Exception) {
                resolver.delete(imageUri, null, null)
                return null
            }
        }
        return imageUri
    }

    /**
     * Saves a PdfDocument to the Documents directory.
     */
    fun savePdfToDocuments(context: Context, pdfDocument: PdfDocument, fileName: String): Uri? {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.pdf")
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/ChromaMind")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }

        val pdfUri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
        if (pdfUri != null) {
            try {
                resolver.openOutputStream(pdfUri).use { outputStream ->
                    if (outputStream != null) {
                        pdfDocument.writeTo(outputStream)
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.clear()
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(pdfUri, contentValues, null, null)
                }
            } catch (e: Exception) {
                resolver.delete(pdfUri, null, null)
                return null
            } finally {
                pdfDocument.close()
            }
        }
        return pdfUri
    }
}

package com.example.coloringbook.core.ui.util

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.platform.LocalContext
import com.example.coloringbook.core.data.model.ColorFill
import com.example.coloringbook.core.data.model.TextureType

object BrushProvider {

    @Composable
    fun rememberBrush(colorFill: ColorFill): Brush {
        val context = LocalContext.current
        return remember(colorFill) {
            createBrush(colorFill, context)
        }
    }

    fun createBrush(colorFill: ColorFill, context: android.content.Context): Brush {
        return when (colorFill) {
            is ColorFill.Solid -> {
                Brush.linearGradient(
                    colors = listOf(Color(colorFill.color), Color(colorFill.color))
                )
            }
            is ColorFill.LinearGradient -> {
                Brush.linearGradient(
                    colors = colorFill.colors.map { Color(it) }
                )
            }
            is ColorFill.RadialGradient -> {
                Brush.radialGradient(
                    colors = colorFill.colors.map { Color(it) }
                )
            }
            is ColorFill.Texture -> {
                val bitmap = getTextureBitmap(colorFill.type, colorFill.baseColor, context)
                val shader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
                ShaderBrush(shader)
            }
        }
    }

    private fun getTextureBitmap(type: TextureType, baseColor: Int, context: android.content.Context): Bitmap {
        return when (type) {
            TextureType.GLITTER -> generateGlitterBitmap(baseColor)
            TextureType.METALLIC -> generateMetallicBitmap(baseColor)
            TextureType.PAPER -> generatePaperBitmap(baseColor)
            TextureType.BRUSH_STROKE -> generateBrushStrokeBitmap(baseColor)
        }
    }

    private fun generateGlitterBitmap(baseColor: Int): Bitmap {
        val size = 128
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(baseColor) // Base color under glitter
        
        val paint = Paint().apply {
            isAntiAlias = true
        }
        
        val random = java.util.Random(42)
        for (i in 0 until 120) {
            val x = random.nextFloat() * size
            val y = random.nextFloat() * size
            val radius = 0.8f + random.nextFloat() * 1.8f
            
            // Bright sparkling colors
            val colors = listOf(0xFFFFF099, 0xFFFFFFFF, 0xFFFFB3BA, 0xFFBAE1FF, 0xFFE8F5E9)
            paint.color = colors[random.nextInt(colors.size)].toInt()
            paint.alpha = 140 + random.nextInt(116)
            
            canvas.drawCircle(x, y, radius, paint)
            
            if (random.nextFloat() > 0.85f) {
                paint.strokeWidth = 0.4f
                canvas.drawLine(x - radius * 2, y, x + radius * 2, y, paint)
                canvas.drawLine(x, y - radius * 2, x, y + radius * 2, paint)
            }
        }
        return bitmap
    }

    private fun generateMetallicBitmap(baseColor: Int): Bitmap {
        val size = 256
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        
        // Blend metallic highlight with base color
        val paint = Paint()
        val c = baseColor
        // Generate shade variants for shiny reflection
        val darkShade = blendColors(c, 0xFF333333.toInt(), 0.4f)
        val lightShade = blendColors(c, 0xFFFFFFFF.toInt(), 0.6f)
        val midShade = c
        
        val colors = intArrayOf(
            darkShade, midShade, lightShade, midShade, darkShade, lightShade, darkShade
        )
        val positions = floatArrayOf(0f, 0.2f, 0.4f, 0.6f, 0.75f, 0.9f, 1f)
        
        val shader = android.graphics.LinearGradient(
            0f, 0f, size.toFloat(), size.toFloat(),
            colors, positions, Shader.TileMode.REPEAT
        )
        paint.shader = shader
        canvas.drawRect(0f, 0f, size.toFloat(), size.toFloat(), paint)
        return bitmap
    }

    private fun generatePaperBitmap(baseColor: Int): Bitmap {
        val size = 128
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(baseColor)
        
        val paint = Paint()
        val random = java.util.Random(13)
        for (x in 0 until size) {
            for (y in 0 until size) {
                if (random.nextFloat() > 0.88f) {
                    val noise = random.nextInt(12) - 6
                    val alpha = 6 + random.nextInt(8)
                    paint.color = if (noise > 0) 0xFFFFFFFF.toInt() else 0xFF000000.toInt()
                    paint.alpha = alpha
                    canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
                }
            }
        }
        return bitmap
    }

    private fun generateBrushStrokeBitmap(baseColor: Int): Bitmap {
        val size = 128
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(baseColor)
        
        val paint = Paint().apply {
            strokeWidth = 0.8f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
        val random = java.util.Random(77)
        for (i in 0 until 50) {
            val y = random.nextFloat() * size
            val length = 40f + random.nextFloat() * 70f
            // Slightly lighter and darker stroke overlays
            val isLight = random.nextBoolean()
            paint.color = if (isLight) 0xFFFFFFFF.toInt() else 0xFF000000.toInt()
            paint.alpha = 8 + random.nextInt(15) // 8 to 23
            canvas.drawLine(0f, y, length, y, paint)
            canvas.drawLine(size - length, y, size.toFloat(), y, paint)
        }
        return bitmap
    }

    private fun blendColors(color1: Int, color2: Int, ratio: Float): Int {
        val a = ((color1 shr 24 and 0xff) * (1 - ratio) + (color2 shr 24 and 0xff) * ratio).toInt()
        val r = ((color1 shr 16 and 0xff) * (1 - ratio) + (color2 shr 16 and 0xff) * ratio).toInt()
        val g = ((color1 shr 8 and 0xff) * (1 - ratio) + (color2 shr 8 and 0xff) * ratio).toInt()
        val b = ((color1 and 0xff) * (1 - ratio) + (color2 and 0xff) * ratio).toInt()
        return (a shl 24) or (r shl 16) or (g shl 8) or b
    }
}

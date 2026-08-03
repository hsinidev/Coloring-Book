package com.example.coloringbook.core.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
sealed interface ColorFill {
    
    @Serializable
    data class Solid(val color: Int) : ColorFill
    
    @Serializable
    data class LinearGradient(
        val colors: List<Int>,
        val angle: Float = 0f
    ) : ColorFill
    
    @Serializable
    data class RadialGradient(
        val colors: List<Int>
    ) : ColorFill
    
    @Serializable
    data class Texture(
        @SerialName("texture_type") val type: TextureType,
        val baseColor: Int = 0xFFFFFFFF.toInt()
    ) : ColorFill
}

@Serializable
enum class TextureType {
    GLITTER, METALLIC, PAPER, BRUSH_STROKE
}

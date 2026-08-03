package com.example.coloringbook.feature.canvas.viewmodel

import com.example.coloringbook.core.data.model.ColorFill

class CanvasHistoryManager {
    private val undoStack = java.util.ArrayDeque<Map<String, ColorFill>>(50)
    private val redoStack = java.util.ArrayDeque<Map<String, ColorFill>>(50)

    fun pushState(currentState: Map<String, ColorFill>) {
        if (undoStack.size >= 50) {
            undoStack.pollFirst() // Remove oldest
        }
        undoStack.addLast(currentState.toMap()) // Defensive copy
        redoStack.clear()
    }

    fun undo(currentState: Map<String, ColorFill>): Map<String, ColorFill>? {
        if (undoStack.isEmpty()) return null
        val previousState = undoStack.pollLast()
        redoStack.addLast(currentState.toMap())
        return previousState
    }

    fun redo(currentState: Map<String, ColorFill>): Map<String, ColorFill>? {
        if (redoStack.isEmpty()) return null
        val nextState = redoStack.pollLast()
        undoStack.addLast(currentState.toMap())
        return nextState
    }
    
    fun getUndoSize(): Int = undoStack.size
    fun getRedoSize(): Int = redoStack.size
    
    fun clear() {
        undoStack.clear()
        redoStack.clear()
    }
}

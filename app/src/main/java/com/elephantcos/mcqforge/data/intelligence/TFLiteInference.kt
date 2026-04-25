package com.elephantcos.mcqforge.data.intelligence
import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
class TFLiteInference(private val ctx: Context) {
    private var interpreter: Interpreter? = null
    var ready = false
    init { try { interpreter = Interpreter(load("mcq_model.tflite")); ready = true } catch (_: Exception) { ready = false } }
    private fun load(path: String): MappedByteBuffer {
        val fd = ctx.assets.openFd(path); val istr = FileInputStream(fd.fileDescriptor); val ch = istr.channel
        return ch.map(FileChannel.MapMode.READ_ONLY, fd.startOffset, fd.declaredLength)
    }
    fun close() { interpreter?.close() }
}

package com.elephantcos.mcqforge.nlp
import com.elephantcos.mcqforge.native.NativeTokenizer
object NlpBridge {
    fun split(text: String): List<String> {
        val native = if(NativeTokenizer.ready()) NativeTokenizer.sentences(text).toList() else emptyList()
        return native.ifEmpty { com.elephantcos.mcqforge.nlp.NlpProcessor().split(text) }
    }
    fun keywords(text: String, limit: Int = 5): List<String> {
        val native = NativeTokenizer.keywords(text, limit).toList()
        return native.ifEmpty { com.elephantcos.mcqforge.nlp.NlpProcessor().keywords(text, limit) }
    }
    fun generateMcq(sentence: String): com.elephantcos.mcqforge.nlp.NlpProcessor.MCQ {
        return com.elephantcos.mcqforge.nlp.NlpProcessor().generate(sentence)
    }
}

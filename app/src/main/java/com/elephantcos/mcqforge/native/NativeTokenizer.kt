package com.elephantcos.mcqforge.native
object NativeTokenizer {
    private var ptr: Long = 0
    private var ok = false
    init { try { System.loadLibrary("mcqforge_native"); ptr = createTokenizer(); ok = true } catch (_: UnsatisfiedLinkError) { ok = false } }
    fun ready(): Boolean = ok
    fun sentences(text: String): Array<String> = if (ok) splitSentencesNative(ptr, text) else arrayOf(text)
    fun keywords(text: String, limit: Int = 5): Array<String> = if (ok) extractKeywordsNative(ptr, text, limit) else arrayOf()
    fun phrases(text: String, limit: Int = 5): Array<String> = if (ok) extractKeyPhrasesNative(ptr, text, limit) else arrayOf()
    private external fun createTokenizer(): Long
    private external fun destroyTokenizer(ptr: Long)
    private external fun splitSentencesNative(ptr: Long, text: String): Array<String>
    private external fun extractKeywordsNative(ptr: Long, text: String, limit: Int): Array<String>
    private external fun extractKeyPhrasesNative(ptr: Long, text: String, limit: Int): Array<String>
}

#include <jni.h>
#include <string>
#include <vector>
#include "include/tokenizer_core.h"
using namespace mcqforge;
std::wstring j2w(JNIEnv* e, jstring s){const jchar* r=e->GetStringChars(s,0);jsize l=e->GetStringLength(s);std::wstring w((const wchar_t*)r,l);e->ReleaseStringChars(s,r);return w;}
jstring w2j(JNIEnv* e,const std::wstring& w){return e->NewString((const jchar*)w.c_str(),w.length());}
extern "C" {
JNIEXPORT jlong JNICALL Java_com_elephantcos_mcqforge_native_NativeTokenizer_createTokenizer(JNIEnv*,jclass){return(jlong)(new TokenizerCore());}
JNIEXPORT void JNICALL Java_com_elephantcos_mcqforge_native_NativeTokenizer_destroyTokenizer(JNIEnv*,jclass,jlong p){delete(TokenizerCore*)p;}
JNIEXPORT jobjectArray JNICALL Java_com_elephantcos_mcqforge_native_NativeTokenizer_splitSentencesNative(JNIEnv* e,jclass,jlong p,jstring t){auto* tk=(TokenizerCore*)p;auto ws=j2w(e,t);auto sents=tk->splitSentences(ws);jclass sc=e->FindClass("java/lang/String");jobjectArray a=e->NewObjectArray(sents.size(),sc,0);for(size_t i=0;i<sents.size();++i)e->SetObjectArrayElement(a,i,w2j(e,sents[i].original));return a;}
JNIEXPORT jobjectArray JNICALL Java_com_elephantcos_mcqforge_native_NativeTokenizer_extractKeywordsNative(JNIEnv* e,jclass,jlong p,jstring t,jint l){auto* tk=(TokenizerCore*)p;auto ws=j2w(e,t);auto kw=tk->extractKeywords(ws,l);jclass sc=e->FindClass("java/lang/String");jobjectArray a=e->NewObjectArray(kw.size(),sc,0);for(size_t i=0;i<kw.size();++i)e->SetObjectArrayElement(a,i,w2j(e,kw[i]));return a;}
JNIEXPORT jobjectArray JNICALL Java_com_elephantcos_mcqforge_native_NativeTokenizer_extractKeyPhrasesNative(JNIEnv* e,jclass,jlong p,jstring t,jint l){auto* tk=(TokenizerCore*)p;auto ws=j2w(e,t);auto ph=tk->extractKeyPhrases(ws,l);jclass sc=e->FindClass("java/lang/String");jobjectArray a=e->NewObjectArray(ph.size(),sc,0);for(size_t i=0;i<ph.size();++i)e->SetObjectArrayElement(a,i,w2j(e,ph[i]));return a;}
}

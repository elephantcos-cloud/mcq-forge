#include "include/tokenizer_core.h"
#include <algorithm>
using namespace mcqforge;
TokenizerCore::TokenizerCore() { initRules(); }
TokenizerCore::~TokenizerCore() {}
void TokenizerCore::initRules() {
    posRules.known[L"বলে"] = L"VERB"; posRules.known[L"গেল"] = L"VERB";
    posRules.known[L"করেন"] = L"VERB"; posRules.known[L"না"] = L"NEG";
    posRules.known[L"এবং"] = L"CONJ"; posRules.known[L"ও"] = L"CONJ";
    posRules.known[L"তার"] = L"PRON"; posRules.known[L"তিনি"] = L"PRON";
}
bool TokenizerCore::isBangla(wchar_t c) { return (c >= 0x0980 && c <= 0x09FF); }
bool TokenizerCore::isSentenceEnd(wchar_t c) { return (c == L'।' || c == L'.' || c == L'?' || c == L'!'); }
std::wstring TokenizerCore::normalize(const std::wstring& t) { std::wstring r; for(auto c:t) r+=(c==L'\n'||c==L'\r')?L' ':c; return r; }
std::vector<Sentence> TokenizerCore::splitSentences(const std::wstring& text) {
    std::vector<Sentence> out; auto n = normalize(text); std::wstring cur;
    for(size_t i=0;i<n.size();++i){ cur+=n[i]; if(isSentenceEnd(n[i])){ if(!cur.empty()){Sentence s;s.original=cur;s.tokens=tokenize(cur);out.push_back(s);cur.clear();} } }
    if(!cur.empty()){Sentence s;s.original=cur;s.tokens=tokenize(cur);out.push_back(s);}
    return out;
}
std::vector<Token> TokenizerCore::tokenize(const std::wstring& s) {
    std::vector<Token> tok; std::wstring cur; size_t pos=0;
    for(size_t i=0;i<s.size();++i){ auto c=s[i]; if(c==L' '||c==L'\t'){ if(!cur.empty()){Token t;t.text=cur;t.position=pos; if(isBangla(cur[0]))t.type=TokenType::BANGLA_WORD; else if(isdigit(cur[0]))t.type=TokenType::NUMBER; else if(isSentenceEnd(cur[0]))t.type=TokenType::SENTENCE_END; else t.type=TokenType::WORD; tok.push_back(t);cur.clear();pos++;} }else{cur+=c;} }
    if(!cur.empty()){Token t;t.text=cur;t.position=pos; if(isBangla(cur[0]))t.type=TokenType::BANGLA_WORD; else if(isdigit(cur[0]))t.type=TokenType::NUMBER; else t.type=TokenType::WORD; tok.push_back(t);}
    return tok;
}
std::vector<std::wstring> TokenizerCore::extractKeywords(const std::wstring& text, int limit) {
    std::vector<std::wstring> kw; auto sents = splitSentences(text);
    std::vector<std::wstring> all; for(auto& s:sents) for(auto& t:s.tokens) if(t.type==TokenType::BANGLA_WORD||t.type==TokenType::WORD) all.push_back(t.text);
    std::unordered_map<std::wstring,double> sc; for(auto& w:all) sc[w]+=1.0;
    std::vector<std::pair<std::wstring,double>> sorted(sc.begin(),sc.end());
    std::sort(sorted.begin(),sorted.end(),[](auto& a,auto& b){return a.second>b.second;});
    for(int i=0;i<std::min(limit,(int)sorted.size());++i) kw.push_back(sorted[i].first);
    return kw;
}
std::vector<std::wstring> TokenizerCore::extractKeyPhrases(const std::wstring& text, int limit) {
    std::vector<std::wstring> phr; auto sents = splitSentences(text);
    std::unordered_map<std::wstring,int> freq; for(auto& s:sents) for(size_t i=0;i+1<s.tokens.size();++i) freq[s.tokens[i].text+L" "+s.tokens[i+1].text]++;
    std::vector<std::pair<std::wstring,int>> sorted(freq.begin(),freq.end());
    std::sort(sorted.begin(),sorted.end(),[](auto& a,auto& b){return a.second>b.second;});
    for(int i=0;i<std::min(limit,(int)sorted.size());++i) phr.push_back(sorted[i].first);
    return phr;
}

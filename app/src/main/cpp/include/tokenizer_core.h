#ifndef MCQFORGE_TOKENIZER_H
#define MCQFORGE_TOKENIZER_H
#include <string>
#include <vector>
#include <unordered_map>
#include <cctype>
namespace mcqforge {
enum class TokenType { WORD, NUMBER, PUNCTUATION, BANGLA_WORD, SENTENCE_END, WHITESPACE };
struct Token { std::wstring text; TokenType type; size_t position; };
struct Sentence { std::vector<Token> tokens; std::wstring original; };
struct TaggedToken : public Token { std::wstring posTag; int tfidfScore = 0; };
class TokenizerCore {
public:
    TokenizerCore(); ~TokenizerCore();
    std::vector<Sentence> splitSentences(const std::wstring& text);
    std::vector<Token> tokenize(const std::wstring& sentence);
    std::vector<std::wstring> extractKeywords(const std::wstring& text, int limit = 5);
    std::vector<std::wstring> extractKeyPhrases(const std::wstring& text, int limit = 5);
private:
    struct Rules { std::unordered_map<std::wstring, std::wstring> known; };
    Rules posRules;
    void initRules();
    bool isBangla(wchar_t c);
    bool isSentenceEnd(wchar_t c);
    std::wstring normalize(const std::wstring& text);
};
}
#endif

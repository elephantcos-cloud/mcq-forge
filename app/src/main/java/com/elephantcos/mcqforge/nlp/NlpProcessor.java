package com.elephantcos.mcqforge.nlp;
import java.util.*;
public class NlpProcessor {
    public List<String> split(String text) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        for(int i=0;i<text.length();++i){ char c=text.charAt(i); cur.append(c); if(c=='।'||c=='.'||c=='?'||c=='!'){ String s=cur.toString().trim(); if(!s.isEmpty())out.add(s); cur.setLength(0); } }
        if(cur.length()>0){ String s=cur.toString().trim(); if(!s.isEmpty())out.add(s); }
        return out;
    }
    public List<String> keywords(String text, int limit) {
        Map<String,Integer> freq=new HashMap<>();
        for(String w:text.split("[\\s,;:]+")){ w=w.replaceAll("[।.,!?]","").trim(); if(w.length()>1)freq.put(w,freq.getOrDefault(w,0)+1); }
        Set<String> stop=new HashSet<>(Arrays.asList("এবং","ও","একটি","করে","হয়","আছে","ছিল","এই","সে","তা","তিনি","আপনি","আমি","তুমি","কি","না"));
        stop.forEach(freq::remove);
        return freq.entrySet().stream().sorted(Map.Entry.<String,Integer>comparingByValue().reversed()).limit(limit).map(Map.Entry::getKey).collect(java.util.stream.Collectors.toList());
    }
    public MCQ generate(String sentence) {
        MCQ m=new MCQ(); List<String> kw=keywords(sentence,1);
        String key=kw.isEmpty()?"তথ্য":kw.get(0);
        m.question="নিচের বাক্যে শূন্যস্থান পূরণ করুন:\n"+sentence.replace(key,"___________");
        m.options=new String[]{key,key+"টি",key.substring(0,Math.min(2,key.length()))+"...",key+key.charAt(key.length()-1)};
        m.correct=0;
        Collections.shuffle(Arrays.asList(m.options));
        m.correct=Arrays.asList(m.options).indexOf(key);
        return m;
    }
    public static class MCQ { public String question; public String[] options; public int correct; }
}

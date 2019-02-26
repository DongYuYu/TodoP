package com.example.android.roomwordssample.main;

import com.example.android.roomwordssample.data.Word;

import java.util.List;

public interface MainContract {

    public interface View {

    void setWord(List<Word> words);
    }


    public interface Presenter {
        void insert(Word word);
        void getAllWords();
    }
}

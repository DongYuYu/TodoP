package com.example.android.roomwordssample.main;

import android.app.Application;

import com.example.android.roomwordssample.data.source.DataSource;
import com.example.android.roomwordssample.data.Word;
import com.example.android.roomwordssample.data.source.WordRepository;

import java.util.List;

public class MainPresenter implements MainContract.Presenter, DataSource.OnInsertCallBack {
    WordRepository repository;
    MainContract.View view;

    public MainPresenter(MainContract.View view, Application context) {
        this.repository = new WordRepository(context);
        this.view = view;
    }

    @Override
    public void insert(Word word) {



        repository.insert(word, this);

    }
    public void getAllWords() {
        List<Word> words = repository.getStaticAllWords();
        view.setWord(words);
    }
    @Override
    public void onWordInserted(List<Word> words) {
        view.setWord(words);
    }


}

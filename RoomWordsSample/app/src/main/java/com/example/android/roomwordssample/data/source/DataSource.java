package com.example.android.roomwordssample.data.source;

import com.example.android.roomwordssample.data.Word;

import java.util.List;

public interface DataSource {




    public interface OnInsertCallBack{
            void onWordInserted(List<Word> words);
    }

}

package com.example.submisi5.model;


import com.example.submisi5.model.Items.Items;

import java.util.ArrayList;

public interface LoadCallback {
    void preExecute();
    void postExecute(ArrayList<Items> mMovieTvItems);
}

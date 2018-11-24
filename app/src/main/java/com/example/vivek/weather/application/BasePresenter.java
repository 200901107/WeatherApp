package com.example.vivek.weather.application;

public interface BasePresenter<T> {

    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    void subscribeWithView(T view);

    /**
     * Drops the reference to the view when destroyed
     */
    void unsubscribeWithView();

}

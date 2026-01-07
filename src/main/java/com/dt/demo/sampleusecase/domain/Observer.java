package com.dt.demo.sampleusecase.domain;


public interface Observer {
    void update(String latestNews);

    void subscribe(Subject subject);

    void unsubscribe(Subject subject);
}

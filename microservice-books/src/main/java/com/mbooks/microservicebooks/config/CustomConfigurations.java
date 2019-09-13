package com.mbooks.microservicebooks.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("myconfig")
public class CustomConfigurations {
    private int limitBooksInCategoryBookList;

    public int getLimitBooksInCategoryBookList() {
        return limitBooksInCategoryBookList;
    }

    public void setLimitBooksInCategoryBookList(int limitBooksInCategoryBookList) {
        this.limitBooksInCategoryBookList = limitBooksInCategoryBookList;
    }
}

package com.devsou.formation.springbatch;

import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class MyItemProcessor implements ItemProcessor<User, User> {

    String url ;
    private List<String> list;

    public MyItemProcessor(String url, List<String> list){
        this.url = url;
        this.list = list;
    }

    @Override
    public User process(User user) throws Exception {
        return new User(user.getId(), user.getFirstName().toUpperCase(), user.getLastName().toUpperCase());
    }
}

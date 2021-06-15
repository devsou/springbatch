package com.devsou.formation.springbatch;

import org.springframework.batch.item.ItemProcessor;

public class MyItemProcessor implements ItemProcessor<User, User> {



    @Override
    public User process(User user) throws Exception {
        return new User(user.getId(), user.getFirstName().toUpperCase(), user.getLastName().toUpperCase());
    }
}

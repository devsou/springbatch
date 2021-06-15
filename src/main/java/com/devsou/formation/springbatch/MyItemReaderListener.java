package com.devsou.formation.springbatch;

import org.springframework.batch.core.ItemReadListener;

public class MyItemReaderListener implements ItemReadListener<User> {

    @Override
    public void beforeRead()  {
        System.out.println("beforeRead");
    }

    @Override
    public void afterRead(User u)  {
        System.out.println("afterRead " + u.toString());
    }

    @Override
    public void onReadError(Exception e)  {
        System.out.println("onReadError " + e.getMessage());
    }
}

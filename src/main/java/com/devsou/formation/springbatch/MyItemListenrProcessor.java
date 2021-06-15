package com.devsou.formation.springbatch;

import org.springframework.batch.core.ItemProcessListener;

public class MyItemListenrProcessor implements ItemProcessListener<User, User> {

    @Override
    public void beforeProcess(User user) {
        System.out.println("["+Thread.currentThread().getName()+"] : beforeProcess");
    }

    @Override
    public void afterProcess(User user, User user2) {
        System.out.println("["+Thread.currentThread().getName()+"] : afterProcess");
    }

    @Override
    public void onProcessError(User user, Exception e) {
        System.out.println("["+Thread.currentThread().getName()+"] : onProcessError");
    }
}

package com.devsou.formation.springbatch;

import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

public class MyItemWriterListener implements ItemWriteListener<User> {

    @Override
    public void beforeWrite(List<? extends User> list) {
        System.out.println("["+Thread.currentThread().getName()+"] : beforeWrite");
    }

    @Override
    public void afterWrite(List<? extends User> list) {
        System.out.println("["+Thread.currentThread().getName()+"] : afterWrite");
    }

    @Override
    public void onWriteError(Exception e, List<? extends User> list) {
        System.out.println("["+Thread.currentThread().getName()+"] : onWriteError");
    }
}

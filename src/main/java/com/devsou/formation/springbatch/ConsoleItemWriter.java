package com.devsou.formation.springbatch;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ConsoleItemWriter implements ItemWriter<User> {

    @Override
    public void write(List<? extends User> list) throws Exception {





        list.forEach(e-> System.out.println("[" + Thread.currentThread().getName() + "]: " + e.toString()));
    }
}

package com.bassblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * Created by dmytro on 19/02/16.
 */
@Service
public class DBStoreService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void storeLastNotificationTime(Date date) {
        jdbcTemplate.update("INSERT INTO lastModified (lastModified) VALUES (" + date.getTime() + ");");
    }

    public Date getLastNotificationTime() {
        return new Date(jdbcTemplate.queryForObject("SELECT lastModified from lastModified;", Long.class));
    }
}


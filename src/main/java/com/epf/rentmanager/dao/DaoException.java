package com.epf.rentmanager.dao;

import org.springframework.stereotype.Repository;

public class DaoException extends Exception {
     public DaoException(String message) {
        super(message);
    }
     public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}

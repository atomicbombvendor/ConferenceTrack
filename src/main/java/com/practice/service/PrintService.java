package com.practice.service;

import com.practice.model.Conference;

import java.util.List;

public interface PrintService {

    void printSessionDay(List<List<Conference>> sessionDays);
}

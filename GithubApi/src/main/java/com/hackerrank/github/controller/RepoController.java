package com.hackerrank.github.controller;

import com.hackerrank.github.dao.RepoDAO;

public class RepoController {

    private RepoDAO repoDAO;

    public RepoController(RepoDAO repoDAO) {
        this.repoDAO = repoDAO;
    }
}

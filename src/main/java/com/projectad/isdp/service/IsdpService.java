package com.projectad.isdp.service;

import com.projectad.isdp.dao.IsdpSuperDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class IsdpService {
    private final IsdpSuperDao sDao;

    public static boolean inProgress;

    @Autowired
    public IsdpService(@Qualifier("actionCode") IsdpSuperDao sDao) {
        this.sDao = sDao;
    }

    public String callId() {
        if (inProgress)
            return "alreadyInProgress";
        else
            inProgress = true;
        return sDao.callId();
    }
}

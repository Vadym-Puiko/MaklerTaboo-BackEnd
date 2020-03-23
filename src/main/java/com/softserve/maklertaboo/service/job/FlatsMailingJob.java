package com.softserve.maklertaboo.service.job;

import com.softserve.maklertaboo.service.CachingService;
import com.softserve.maklertaboo.service.MailingService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlatsMailingJob implements Job {

    @Autowired
    private MailingService mailingService;

    @Autowired
    private CachingService cachingService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        mailingService.checkFlatsByUserRequests();
        cachingService.evictAllCacheValues("flats");
    }
}

package com.softserve.maklertaboo.service.job;

import com.softserve.maklertaboo.service.CachingService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CacheRefreshJob implements Job {

    @Autowired
    private CachingService cachingService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Cache refreshed");
        cachingService.evictAllCacheValues("flats");
    }
}

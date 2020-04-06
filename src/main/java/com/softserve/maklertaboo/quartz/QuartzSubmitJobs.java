package com.softserve.maklertaboo.quartz;

import com.softserve.maklertaboo.service.job.CacheRefreshJob;
import com.softserve.maklertaboo.service.job.FlatsMailingJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzSubmitJobs {
    private static final String CRON_EVERY_ONE_MINUTE = "0 0/1 * ? * * *";
    private static final String CRON_EVERY_HALF_AN_HOUR = "0 0/30 * ? * * *";

    @Bean(name = "FlatsMailingJob")
    public JobDetailFactoryBean jobFlatsMailingJob() {
        return QuartzConfig.createJobDetail(FlatsMailingJob.class, "Flats Mailing Job");
    }

    @Bean(name = "FlatsMailingJobTrigger")
    public CronTriggerFactoryBean triggerFlatsMailingJob(@Qualifier("FlatsMailingJob") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_HALF_AN_HOUR, "Flats Mailing Job Trigger");
    }

    @Bean(name = "CacheRefreshJob")
    public JobDetailFactoryBean jobCacheRefreshJob() {
        return QuartzConfig.createJobDetail(CacheRefreshJob.class, "Cache Refresh Job");
    }

    @Bean(name = "CacheRefreshJobTrigger")
    public CronTriggerFactoryBean triggerCacheRefreshJob(@Qualifier("CacheRefreshJob") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_ONE_MINUTE, "Cache Refresh Job Trigger");
    }
}

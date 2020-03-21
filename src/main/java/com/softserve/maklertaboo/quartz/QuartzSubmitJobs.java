package com.softserve.maklertaboo.quartz;

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
    public JobDetailFactoryBean jobMemberClassStats() {
        return QuartzConfig.createJobDetail(FlatsMailingJob.class, "Flats Mailing Job");
    }

    @Bean(name = "FlatsMailingJobTrigger")
    public CronTriggerFactoryBean triggerMemberClassStats(@Qualifier("FlatsMailingJob") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_ONE_MINUTE, "Flats Mailing Job Trigger");
    }
}

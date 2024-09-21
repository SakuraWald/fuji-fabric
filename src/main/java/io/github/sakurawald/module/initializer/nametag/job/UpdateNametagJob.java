package io.github.sakurawald.module.initializer.nametag.job;

import io.github.sakurawald.core.job.abst.CronJob;
import io.github.sakurawald.core.manager.Managers;
import io.github.sakurawald.module.initializer.nametag.NametagInitializer;
import org.quartz.JobExecutionContext;

public class UpdateNametagJob extends CronJob {

    public UpdateNametagJob() {
        super(() -> NametagInitializer.config.getModel().update_cron);
    }

    @Override
    public void execute(JobExecutionContext context) {
        NametagInitializer.updateNametags();
    }
}

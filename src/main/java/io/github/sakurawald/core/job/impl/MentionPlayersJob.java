package io.github.sakurawald.core.job.impl;

import io.github.sakurawald.core.job.abst.FixedIntervalJob;
import lombok.NoArgsConstructor;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
public class MentionPlayersJob extends FixedIntervalJob {

    public MentionPlayersJob(JobDataMap jobDataMap, int intervalMs, int repeatCount) {
        super(null, null, jobDataMap, intervalMs, repeatCount);
    }

    public static void requestJob(MentionPlayer setup, List<ServerPlayerEntity> players) {
        int intervalMs = setup.interval_ms;
        int repeatCount = setup.repeat_count;

        new MentionPlayersJob(new JobDataMap() {
            {
                this.put(ArrayList.class.getName(), players);
                this.put(MentionPlayer.class.getName(), setup);
            }
        }, intervalMs, repeatCount).schedule();
    }

    public static void requestJob(MentionPlayer setup, ServerPlayerEntity serverPlayer) {
        requestJob(setup, new ArrayList<>(Collections.singletonList(serverPlayer)));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(@NotNull JobExecutionContext context) {
        List<ServerPlayerEntity> players = (ArrayList<ServerPlayerEntity>) context.getJobDetail().getJobDataMap().get(ArrayList.class.getName());
        MentionPlayer setup = (MentionPlayer) context.getJobDetail().getJobDataMap().get(MentionPlayer.class.getName());

        for (ServerPlayerEntity player : players) {
            if (player == null) continue;

            SoundEvent soundEvent = SoundEvent.of(Identifier.of(setup.sound));
            SoundCategory soundCategory = SoundCategory.MUSIC;
            player.playSoundToPlayer(soundEvent, soundCategory, setup.volume, setup.pitch);
        }
    }

    public static class MentionPlayer {
        public @NotNull String sound = "entity.experience_orb.pickup";
        public float volume = 100f;
        public float pitch = 1f;
        public int repeat_count = 3;
        public int interval_ms = 1000;
    }
}

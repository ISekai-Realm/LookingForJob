package net.biryeongtrain.lookingforjob.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.biryeongtrain.lookingforjob.LookingForJob;
import net.biryeongtrain.lookingforjob.job.exp.JobExpLevelContainer;
import net.biryeongtrain.lookingforjob.job.Jobs;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class JobDataLoader implements SimpleSynchronousResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return null;
    }

    @Override
    public void reload(ResourceManager manager) {
        JobExpLevelContainer.clear();
        Map<Identifier, Resource> resources = manager.findResources("job_data", (path) -> path.getPath().endsWith(".json"));

        for (var entry : resources.entrySet()) {
            var id = entry.getKey();
            var resource = entry.getValue();

            var jobId = Identifier.of(id.getNamespace(), id.getPath().replace("job_data/", "").replace(".json", ""));

            var job = Jobs.getJob(jobId);
            if (job.isEmpty()) {
                LookingForJob.LOGGER.warn("Unknown Job id: {} skipping this information", jobId);
                continue;
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

                try {
                    JsonElement json = JsonParser.parseReader(reader);
                    DataResult<JobExpLevelContainer> result = JobExpLevelContainer.CODEC.parse(JsonOps.INSTANCE, json);

                    result.result().ifPresent((data) -> JobExpLevelContainer.register(job.get(), data));
                    result.error().ifPresent((error) -> LookingForJob.LOGGER.error("Failed to parse job data at {}: {}", id, error));
                } catch (Throwable ex) {
                    try {
                        reader.close();
                    } catch (Throwable ex2) {
                        ex2.addSuppressed(ex);
                    }
                }
            } catch (Throwable ex) {
                LookingForJob.LOGGER.error("Failed to load job data at {}", id, ex);
            }
        }
    }
}

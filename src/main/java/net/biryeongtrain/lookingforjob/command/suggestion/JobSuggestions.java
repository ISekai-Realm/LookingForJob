package net.biryeongtrain.lookingforjob.command.suggestion;

import net.biryeongtrain.lookingforjob.job.Jobs;

import java.util.List;

public class JobSuggestions extends QfSuggestionProvider{
    @Override
    List<String> getSuggestionList() {
        return Jobs.getJobs().stream().map(job -> job.getJobId().toString()).toList();
    }
}

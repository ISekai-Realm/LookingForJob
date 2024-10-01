package net.biryeongtrain.lookingforjob.job;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import java.util.Map;

public abstract class AbstractBlockRelatedJob implements Job<Block>{
    private final Identifier id;
    private Map<Block,Double> pointsMap;

    public AbstractBlockRelatedJob(Identifier id) {
        this.id = id;
    }
    
    @Override
    public Identifier getJobId() {
        return this.id;
    }

    @Override
    public Map<Block, Double> getExpPoints() {
        return Map.copyOf(this.pointsMap);
    }

    @Override
    public void addExp(Block target, PlayerJobData<Job<Block>> data) {
        
    }
}

package importerservice;


import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ImporterService {
    private AtomicInteger load;

    public ImporterService() {
        this.load = new AtomicInteger();
    }

    public Integer getCurrentLoad() {
        return this.load.get();
    }

    public void addTask(int complexity) {
        this.load.getAndAdd(complexity);
    }

    public CompletableFuture<Void> doImport(Integer importComplexity) {
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(importComplexity * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenAccept(nop -> load.getAndAdd(-importComplexity));
    }
}

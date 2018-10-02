package masterservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static methods.CommonMethods.getResponse;

@EnableDiscoveryClient
@Component
public class MasterTask {
    public static Integer MAXLOAD = 5;
    public static Integer MINCOMPLEXITY = 1;
    public static Integer MAXCOMPLEXITY = 5;
    @Autowired
    private DiscoveryClient discoveryClient;

    @Scheduled(fixedDelay = 1 * 1000) // every second
    public void process() {
        List<ServiceInstance> activeImporterInstances = discoveryClient.getInstances("importer-service");
        if (activeImporterInstances.size() == 0){
            return;
        }
        Integer newTaskComplexity = MINCOMPLEXITY + (int) (Math.random() * ((MAXCOMPLEXITY - MINCOMPLEXITY) + 1));
        System.out.println("New task complexity = " + newTaskComplexity);
        Boolean taskStarted = false;
        while (!taskStarted) {
            for (ServiceInstance importerInstance : activeImporterInstances) {
                if (Integer.valueOf(getResponse(importerInstance.getUri() + "/currentload").getBody().toString()) + newTaskComplexity <= MAXLOAD) {
                    getResponse(importerInstance.getUri() + "/start/" + newTaskComplexity);
                    taskStarted = true;
                    break;
                }
            }
        }
    }
}

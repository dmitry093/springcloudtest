package importerservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static methods.CommonMethods.getResponse;

@EnableDiscoveryClient
@RestController
public class ImporterServiceController {
    @Autowired
    DiscoveryClient discoveryClient;
    private ImporterService importerService;

    public ImporterServiceController() {
        importerService = new ImporterService();
    }

    @RequestMapping("/masters")
    public List<ServiceInstance> getAllMasterInstances() {
        return this.discoveryClient.getInstances("master-service");
    }

    @GetMapping(value = "/start/{complexity}")
    public String startImport(@PathVariable Integer complexity) {
        importerService.addTask(complexity);
        importerService.doImport(complexity).thenAccept(nop -> {
            ServiceInstance masterInstance = this.getAllMasterInstances()
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (masterInstance != null) {
                String finishImportUrl = masterInstance.getUri() + "/finish/" + complexity;
                getResponse(finishImportUrl);
            }
        });
        return "Import [" + complexity + "] started at " + new Date();
    }

    @RequestMapping("/currentload")
    public Integer getCurrentLoad() {
        return importerService.getCurrentLoad();
    }
}

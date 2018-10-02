package masterservice;


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

@EnableDiscoveryClient
@RestController
public class MasterServiceController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/importers")
    public List<ServiceInstance> getAllImporterInstances() {
        return this.discoveryClient.getInstances("importer-service");
    }

    @GetMapping(value = "/finish/{complexity}")
    public String startImport(@PathVariable Integer complexity)
    {
        return "Import [" + complexity + "] registered as finished at " + new Date();
    }
}

package za.co.span.assessment.fixtures.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import za.co.span.assessment.fixtures.dto.TeamDTO;
import za.co.span.assessment.fixtures.service.DefaultFixturesService;

import java.util.List;

@RestController
@RequestMapping("/fixtures")
public class DefaultFixturesController {

    //TODO: ADD/Instrumentation Metrics - Counter - Gauge - Tracing - Prometheus

    private static final Logger log = LoggerFactory.getLogger(DefaultFixturesController.class);

    private DefaultFixturesService defaultFixtures;

    @Autowired
    public DefaultFixturesController(DefaultFixturesService defaultFixtures) {
        this.defaultFixtures = defaultFixtures;
    }

    @Secured("ROLE_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/result/{result}", method = RequestMethod.POST)
    public ResponseEntity<String> captureResult(@PathVariable("result") String result) {
        defaultFixtures.processResult(result);
        return new ResponseEntity("Results Captured", HttpStatus.OK);
    }

    @Secured("ROLE_GUEST")
    @ResponseBody
    @RequestMapping(value = "/ranking", method = RequestMethod.GET)
    public ResponseEntity<List<TeamDTO>> ranking() {
        return new ResponseEntity<>(defaultFixtures.getOrderedRankingTable(), HttpStatus.OK);
    }
}

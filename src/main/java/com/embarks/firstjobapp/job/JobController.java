package com.embarks.firstjobapp.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;


    @GetMapping
    public ResponseEntity<List<Job>> findAll() {
        //return new ResponseEntity(jobService.findAll(), HttpStatus.OK);
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> crateJob(@RequestBody Job job) {
        jobService.createJob(job);
        return new ResponseEntity<>("job added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findJobById(@PathVariable Long id) {
        Job job = jobService.findJobById(id);

        if (job != null)
            return new ResponseEntity<>(job, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id) {

        boolean deleted = jobService.deleteJobById(id);

        if (deleted)
            return new ResponseEntity<>("job deleted successfully", HttpStatus.OK);

        return new ResponseEntity<>("job not found", HttpStatus.NOT_FOUND);
    }

    //@RequestMapping(value = "{/id}", method = RequestMethod.PUT)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {
        boolean updated = jobService.updateJob(id, updatedJob);
        if (updated)
            return new ResponseEntity<>("job updated successfully", HttpStatus.OK);

        return new ResponseEntity<>("job not found", HttpStatus.NOT_FOUND);
    }
}

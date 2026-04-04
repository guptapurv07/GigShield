package com.gigshield.controller;

import com.gigshield.model.Policy;
import com.gigshield.service.PolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/policy")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @PostMapping("/purchase/{workerId}")
    public ResponseEntity<Policy> purchasePolicy(
            @PathVariable Long workerId,
            @RequestParam(required = false, defaultValue = "unknown") String hyperLocalZone) {
        
        Policy policy = policyService.issueWeeklyPolicy(workerId, hyperLocalZone);
        return ResponseEntity.ok(policy);
    }
}

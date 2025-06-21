package com.Chronicles.CounsellingService.Controller;

import com.Chronicles.CounsellingService.DTO.ApiResponse;
import com.Chronicles.CounsellingService.Entity.*;
import com.Chronicles.CounsellingService.Service.DataMigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/data-migration")
public class DataMigrationController {
    @Autowired
    private DataMigrationService dataMigrationService;

    @GetMapping("/check")
    public String helloWorld()
    {
        return "Hello Chronicle!!!";
    }

    @PostMapping("/comedk")
    public ResponseEntity<ApiResponse> saveComedKData(@RequestBody List<ComedK> comedKList)  {
        ApiResponse apiResponse=dataMigrationService.migrateComedK(comedKList);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/ggsipu")
    public ResponseEntity<ApiResponse> saveGGSIPUData(@RequestBody List<GGSIPU> ggsipuList) {
        ApiResponse apiResponse=dataMigrationService.migrateGGSIPU(ggsipuList);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/jac-chandigarh")
    public ResponseEntity<ApiResponse> saveJacChandigarhData(@RequestBody List<JacChandigarh> jacChandigarhList) {
        ApiResponse apiResponse=dataMigrationService.migrateJacChandigarh(jacChandigarhList);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/jac-delhi")
    public ResponseEntity<ApiResponse> saveJacDelhiData(@RequestBody List<JacDelhi> jacDelhiList) {
        ApiResponse apiResponse=dataMigrationService.migrateJacDelhi(jacDelhiList);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/mhtcet")
    public ResponseEntity<ApiResponse> saveMHTCETKData(@RequestBody List<MHTCET> mhtcetList) {
        ApiResponse apiResponse=dataMigrationService.migrateMHTCET(mhtcetList);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/uptac")
    public ResponseEntity<ApiResponse> saveUPTACData(@RequestBody List<UPTAC> uptacList) {
        ApiResponse apiResponse=dataMigrationService.migrateUPTAC(uptacList);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/wbjee")
    public ResponseEntity<ApiResponse> saveWBJEEData(@RequestBody List<WBJEE> wbjeeList) {
        ApiResponse apiResponse=dataMigrationService.migrateWBJEE(wbjeeList);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/josaa-csab")
    public ResponseEntity<ApiResponse> saveJosaCsabData(@RequestBody List<JosaaCsab> josaaCsabList) {
        ApiResponse apiResponse=dataMigrationService.migrateJosaaCsab(josaaCsabList);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


    @PostMapping("/home-state")
    public ResponseEntity<ApiResponse> saveHomeStateData(@RequestBody List<HomeState> homeStateList){
        ApiResponse apiResponse=dataMigrationService.migrateHomeState(homeStateList);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


    @GetMapping("/comedKList")
    public ResponseEntity<List<ComedK>> getComedKData() {
        List<ComedK> comedKList=dataMigrationService.getAllComedKData();
        return new ResponseEntity<>(comedKList,HttpStatus.OK);
    }
}


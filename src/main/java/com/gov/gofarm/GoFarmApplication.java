package com.gov.gofarm;

import com.gov.gofarm.repository.MasterCropDetailRepository;
import com.gov.gofarm.repository.MasterTalukDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoFarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoFarmApplication.class, args);


    }
}

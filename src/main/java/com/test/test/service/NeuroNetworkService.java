package com.test.test.service;

import com.test.test.model.NeuroStat;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
@Log4j2
@RequiredArgsConstructor
public class NeuroNetworkService {
    public synchronized void takeStat(NeuroStat neuroStat) {
        try(FileWriter f = new FileWriter("data.csv", true);
            BufferedWriter b = new BufferedWriter(f);
            PrintWriter pw = new PrintWriter(b);
        ) {
            String foundHacker = neuroStat.getFlag() ? "1" : "0";
            pw.println("0," + "0" + "," +
                    neuroStat.getCurvature() + "," +
                    neuroStat.getAccaverage() + "," +
                    neuroStat.getAcc1() + "," +
                    neuroStat.getAcc2() + "," +
                    neuroStat.getAcc3() + "," +
                    neuroStat.getAcc4() + "," +
                    neuroStat.getAcc5() + "," +
                    neuroStat.getAcc6() + "," +
                    neuroStat.getSpeedaverage() + "," +
                    neuroStat.getSpeed1() + "," +
                    neuroStat.getSpeed2() + "," +
                    neuroStat.getSpeed3() + "," +
                    neuroStat.getSpeed4() + "," +
                    neuroStat.getSpeed5());
        }
        catch (IOException ex) {
            ex.getStackTrace();
        }
        log.info(neuroStat.toString());
    }
}

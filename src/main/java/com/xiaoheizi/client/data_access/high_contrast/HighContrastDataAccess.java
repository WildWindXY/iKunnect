package com.xiaoheizi.client.data_access.high_contrast;

import com.xiaoheizi.client.use_case.high_contrast.HighContrastDataAccessInterface;
import com.xiaoheizi.client.use_case.high_contrast.HighContrastOutputData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HighContrastDataAccess implements HighContrastDataAccessInterface {

    private final String csvPath = "./options.csv";

    private boolean HC = false;

    public HighContrastDataAccess() {
    }

    @Override
    public void toggle(int index) {
//        try {
//            Path path = Paths.get(csvPath);
//            List<String> lines = new ArrayList<>(Files.readAllLines(path));
//            System.out.println("ODAO File Read Success");
//
//            for (int i = 0; i < lines.size(); ) {
//                if (i == index) {
//                    String[] parsedLine = lines.get(i).split(",");
//                    String newData = parsedLine[0] + ", " + (Integer.parseInt(parsedLine[1].strip()) == 0 ? 1 : 0);
//                    lines.set(i, newData);
//                    break;
//                } else {
//                    i++;
//                }
//            }
//
//            Files.write(path, lines);
//            System.out.println("Data written to the nth line successfully.");
//        } catch (IOException ignored) {
//        }
        HC = !HC;
    }


    @Override
    public HighContrastOutputData get(int index) {
//        switch (index) {
//            case HIGH_CONTRAST:
//                System.out.println("OptionsDAO get HIGH_CONTRAST");
//                return new HighContrastOutputData(csvGet(index));
//            case 2:
//        }
//        return null;
        return new HighContrastOutputData(HC);
    }

    private int csvGet(int index) {
        try {
            String nthLine = Files.lines(Paths.get(csvPath)).skip(index).findFirst().orElse(null);
            if (nthLine != null) {
                String[] parsedNthLine = nthLine.split(",");
                return Integer.parseInt(parsedNthLine[1].strip());
            } else {
                System.out.println("OptionsDataAccess: Line not found (file may have fewer lines)");
                return -1;
            }
        } catch (IOException ignored) {
            return -1;
        }
    }
}

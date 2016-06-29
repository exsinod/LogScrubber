package org.singular.creator;

import javafx.util.Pair;
import org.joda.time.DateTime;
import org.singular.dto.*;
import org.singular.files.FileReader;
import org.singular.parser.LogParser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class TableChartCreator extends AbstractCreator<RangeDataset> {

    @Override
    protected Data createDataset(Map.Entry<String, List<Pair<DateTime, Long>>> metrics) {
        double average = 0;
        for (Pair<DateTime, Long> pair : metrics.getValue()) {
            average = average + pair.getValue();
        }
        average = average / metrics.getValue().size();
        TagData averageTagData = new TagData();
        double stdDev = calculateStdDev(average, metrics.getValue());

        averageTagData.setTag(metrics.getKey());
        averageTagData.setMin(minValues.get(metrics.getKey()));
        averageTagData.setMax(maxValues.get(metrics.getKey()));
        averageTagData.setAverage(new BigDecimal(average).setScale(1, RoundingMode.HALF_UP).doubleValue());
        averageTagData.setStdDev(new BigDecimal(stdDev).setScale(1, RoundingMode.HALF_UP).doubleValue());
        averageTagData.setCount(metrics.getValue().size());

        return averageTagData;
    }

    @Override
    protected List<File> getLogs(String host, String from, int range) throws IOException {
        return fileManager.getFilteredFilesWithinRange(host, from, range);
    }

    @Override
    protected List<RangeDataset> calculate(List<List<File>> filesList) throws IOException {
        List<RangeDataset> rangeDatasets = new LinkedList<RangeDataset>();
        for(List<File> fileList : filesList) {
            DateTime fromTime = new DateTime(fileManager.getStartParsable(fileList.get(0).getName()));
            DateTime tillTime = new DateTime(fileManager.getEndParsable(fileList.get(fileList.size()-1).getName()));

            String logs = FileReader.getContent(fileList);
            List<LogLine> logLines = LogParser.parseLogs(logs);
            calculateMetrics(logLines);

            RangeDataset rangeDataset = new RangeDataset();
            rangeDataset.setRange(new Range(fromTime, tillTime));
            createAndAdd(rangeDataset);
            rangeDatasets.add(rangeDataset);
        }
        return rangeDatasets;
    }
}

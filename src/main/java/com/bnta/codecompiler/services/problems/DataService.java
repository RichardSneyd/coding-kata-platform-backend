package com.bnta.codecompiler.services.problems;

import com.bnta.codecompiler.models.problems.Data;
import com.bnta.codecompiler.models.problems.DataType;
import com.bnta.codecompiler.repositories.problems.IDataRepository;
import com.bnta.codecompiler.utilities.SrcParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DataService {
    @Autowired
    private IDataRepository dataRepo;

    public Data add(Data data) {
        return dataRepo.save(data);
    }

    public Data update(Data data) throws Exception {
        if (data.getId() == null) throw new Exception("id is null");
        if (!dataRepo.findById(data.getId()).isPresent())
            throw new Exception(String.format("No data with id %s", data.getId()));

        var old = dataRepo.findById(data.getId()).get();
        old.setDataType(data.getDataType());
        old.setValue(data.getValue());
        return dataRepo.save(old);
    }


    public Data of(String value) {
        return add(new Data(value, DataType.STRING));
    }

    public Data of(Integer value) {
        return add(new Data(value.toString(), DataType.INT));
    }

    public Data of(Float value) {
        return add(new Data(value.toString(), DataType.FLOAT));
    }

    public Data of(Boolean value) {
        return add(new Data(value.toString(), DataType.BOOLEAN));
    }

    public Data of(String[] arr) {
        return add(new Data(SrcParser.standardiseArgFormat(Arrays.toString(arr)), DataType.STRING_ARRAY));
    }

    public Data of(Integer[] arr) {
        return add(new Data(SrcParser.standardiseArgFormat(Arrays.toString(arr)), DataType.INT_ARRAY));
    }

    public Data of(Float[] arr) {
        return add(new Data(SrcParser.standardiseArgFormat(Arrays.toString(arr)), DataType.FLOAT_ARRAY));
    }

    public Data of(Boolean[] arr) {
        return add(new Data(SrcParser.standardiseArgFormat(Arrays.toString(arr)), DataType.BOOLEAN_ARRAY));
    }

}

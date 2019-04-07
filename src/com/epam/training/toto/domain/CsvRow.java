package com.epam.training.toto.domain;

import com.sun.rowset.internal.Row;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CsvRow extends Row {
    public CsvRow(int i) {
        super(i);
    }

    public CsvRow(int i, Object[] objects) {
        super(i, objects);
    }


    public List<Object> getRowElements(int beginIndex, int endIndex) throws SQLException {
        List<Object> elements = new ArrayList<>();
        for (int i = beginIndex; i < endIndex; i++) {
            elements.add(super.getColumnObject(i));
        }
        return elements;
    }
}

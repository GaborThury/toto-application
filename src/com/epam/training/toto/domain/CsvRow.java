package com.epam.training.toto.domain;

import java.util.List;

public class CsvRow{
    //private int numberOfElements;
    private List<String> elements;


    public CsvRow(List<String> elements) {
        this.elements = elements;
    }

    public int getElementAsInt(int index) throws NumberFormatException {
        return Integer.parseInt(this.elements.get(index));
    }

    public String getElement(int index) {
        return this.elements.get(index);
    }

    public List<String> getElements(int fromIndex, int toIndex) {
        return this.elements.subList(fromIndex, toIndex);
    }

    public List<String> getElements() {
        return elements;
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    /*
    public List<String> getRowElements(int beginIndex, int endIndex){
        List<Object> elements = new ArrayList<>();
        for (int i = beginIndex; i < endIndex; i++) {
            elements.add(super.getColumnObject(i));
        }
        return elements;
    }
   */

}

package ir;

import java.util.LinkedList;

public class Document{
    public int docID;
    public LinkedList<Integer> positions;

    public Document(int docID) {
        this.docID = docID;
        positions = new LinkedList<Integer>();
    }

    public void addPosition(int positoin){
        positions.add(positoin);
    }


    public LinkedList<Integer> getPositionsList(){
        return positions;
    }
}
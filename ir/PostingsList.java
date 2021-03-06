/*  
 *   This file is part of the computer assignment for the
 *   Information Retrieval course at KTH.
 * 
 *   First version:  Johan Boye, 2010
 *   Second version: Johan Boye, 2012
 */  

package ir;

import java.util.LinkedList;
import java.io.Serializable;

/**
 *   A list of postings for a given word.
 */
public class PostingsList implements Serializable {
    
    /** The postings list as a linked list. */
    private LinkedList<PostingsEntry> list = new LinkedList<PostingsEntry>();


    /**  Number of postings in this list  */
    public int size() {
	return list.size();
    }

    /**  Returns the ith posting */
    public PostingsEntry get( int i ) {
	return list.get( i );
    }

    public LinkedList<PostingsEntry> getWholeList(){

        // return the uniqe values
        return list;

    }

    public PostingsEntry getEntryByID(PostingsEntry entry){
        int index = list.indexOf(entry);

        return get(index);
    }


    public boolean containEntry(PostingsEntry entry){
        return list.contains(entry);
    }
    public void addEntry(PostingsEntry postingEntry){
        if(!list.contains(postingEntry)){
            list.add(postingEntry);
        }else {
            System.out.println("found existing value");

        }
    }
}
	

			   

/*
 *   This file is part of the computer assignment for the
 *   Information Retrieval course at KTH.
 *
 *   First version:  Johan Boye, 2010
 *   Second version: Johan Boye, 2012
 *   Additions: Hedvig Kjellström, 2012-14
 */


package ir;

import java.util.HashMap;
import java.util.Iterator;


/**
 * Implements an inverted index as a Hashtable from words to PostingsLists.
 */
public class HashedIndex implements Index {

    /**
     * The index as a hashtable.
     */
    private HashMap<String, PostingsList> index = new HashMap<String, PostingsList>();


    /**
     * Inserts this token in the index.
     */
    public void insert(String token, int docID, int offset) {

        if (index.containsKey(token)) {
            PostingsList existingList = index.get(token);

            PostingsEntry entry = new PostingsEntry(docID, 0);
            existingList.addEntry(entry);
        } else {

            PostingsList list = new PostingsList();
            PostingsEntry entry = new PostingsEntry(docID, 0);

            list.addEntry(entry);
            index.put(token, list);

        }

        System.out.println("inserted and the new size is "+index.size());
    }

    public int size() {
        return index.size();
    }

    /**
     * Returns all the words in the index.
     */
    public Iterator<String> getDictionary() {
        //
        //  REPLACE THE STATEMENT BELOW WITH YOUR CODE
        //
        return null;
    }


    /**
     * Returns the postings for a specific term, or null
     * if the term is not in the index.
     */
    public PostingsList getPostings(String token) {
        //
        //  REPLACE THE STATEMENT BELOW WITH YOUR CODE
        //
        return index.get(token);
    }


    /**
     * Searches the index for postings matching the query.
     */
    public PostingsList search(Query query, int queryType, int rankingType, int structureType) {
        //
        //  REPLACE THE STATEMENT BELOW WITH YOUR CODE
        //
        System.out.println("index size "+ index.size());

        String token = query.terms.get(0);
        return index.get(token);
    }


    /**
     * No need for cleanup in a HashedIndex.
     */
    public void cleanup() {
    }
}

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
import java.util.ArrayList;

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

        // if query has more than one term

        PostingsList result = null;

        // intersection with more than one term
        if(query.terms.size()>1 && queryType == Index.INTERSECTION_QUERY){

            ArrayList<PostingsList> termsPositngsList = new ArrayList<PostingsList>();

            for (String term: query.terms) {
                if(index.get(term) == null)
                    return null;

                termsPositngsList.add(index.get(term));
            }

            if(termsPositngsList.size() >1)
                result = compareGroupOfPostingsLists(termsPositngsList);

            return result;

        }

        if(query.terms.size() ==1){
            String token = query.terms.get(0);
            return index.get(token);
        }

        return null;

    }

    public PostingsList compareGroupOfPostingsLists(ArrayList<PostingsList> postingsListCollection){

        PostingsList result;

        System.out.println("hell" +postingsListCollection.size());

        if(postingsListCollection.size() == 2){
             result = compareTwoLists(postingsListCollection.get(0), postingsListCollection.get(1));
        }else {
            ArrayList<PostingsList> tempResult = new ArrayList<PostingsList>();

            int i;
            if(postingsListCollection.size()%2 == 0){
                i=0;
            }else{
                tempResult.add(postingsListCollection.get(0));
                i =1;
            }

            for (i=i; i+1< postingsListCollection.size(); i +=2){

                tempResult.add(compareTwoLists(postingsListCollection.get(i),postingsListCollection.get(i+1)));
            }
            result = compareGroupOfPostingsLists(tempResult);
        }


        return result;
    }
    public PostingsList compareTwoLists(PostingsList firstList, PostingsList secondList){
        PostingsList result = new PostingsList();

        int i=0, j=0;

        while(i<firstList.size() && j< secondList.size()){

            if(firstList.get(i).docID == secondList.get(j).docID){
                result.addEntry(firstList.get(i));
                i++;
                j++;
            }else{
                if(firstList.get(i).docID < secondList.get(j).docID){
                    i++;
                }else{
                    j++;
                }
            }
        }

        return result;
    }

    /**
     * No need for cleanup in a HashedIndex.
     */
    public void cleanup() {
    }
}

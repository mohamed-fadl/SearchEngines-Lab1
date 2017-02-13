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

    private boolean isWholePhraseExist = false;

    /**
     * Inserts this token in the index.
     */
    public void insert(String token, int docID, int offset) {

        if (index.containsKey(token)) {
            PostingsList existingList = index.get(token);

            PostingsEntry entry = new PostingsEntry(docID, offset,0);
            if(existingList.containEntry(entry)){
                PostingsEntry currentEntry =existingList.getEntryByID(entry);
                currentEntry.doc.addPosition(offset);
            }else{
                existingList.addEntry(entry);
            }

        } else {

            PostingsList list = new PostingsList();
            PostingsEntry entry = new PostingsEntry(docID,offset, 0);

            list.addEntry(entry);
            index.put(token, list);

        }

    }

    public int size() {
        return index.size();
    }

    /**
     * Returns all the words in the index.
     */
    public Iterator<String> getDictionary() {

        return index.keySet().iterator();
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

        System.out.println("\nnew search results\n#########################\n\n");
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

        // intersection with more than one term
        if(query.terms.size()>1 && queryType == Index.PHRASE_QUERY){

            ArrayList<PostingsList> termsPositngsList = new ArrayList<PostingsList>();

            for (String term: query.terms) {
                if(index.get(term) == null)
                    return null;

                termsPositngsList.add(index.get(term));
            }

            if(termsPositngsList.size() >1)
                result = compareGroupOfPostingsListsPhrase(termsPositngsList);

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

    public PostingsList compareGroupOfPostingsListsPhrase(ArrayList<PostingsList> postingsListCollection){

        PostingsList result;

        isWholePhraseExist = true;

        if(postingsListCollection.size() == 2){

            result = compareTwoListsPhrase(postingsListCollection.get(0), postingsListCollection.get(1),1);
        }else {
            ArrayList<PostingsList> tempResult = new ArrayList<PostingsList>();

            for (int i=0; i+1< postingsListCollection.size(); i++){

                tempResult.add(compareTwoListsPhrase(postingsListCollection.get(i),postingsListCollection.get(i+1),1));
                System.out.format("compared %s times\n", tempResult.size());
            }
            result = unionOfEntries(tempResult);

        }


            return result;

        //return null;
    }

    public PostingsList unionOfEntries(ArrayList<PostingsList> postings){

        PostingsList result = new PostingsList();

        for(int i=0; i<postings.size();i++){
            for(int k=0;k<postings.get(0).size();k++){

                PostingsEntry entry = postings.get(0).get(k);
                boolean entryIsInEveryList = true;

                for(int j=1; j<postings.size();j++){
                    if(!postings.get(j).containEntry(entry)){
                        entryIsInEveryList = false;
                        break;
                    }
                }

                if(entryIsInEveryList){
                    result.addEntry(entry);
                }
            }

        }

        return result;
    }
    public PostingsList compareTwoLists(PostingsList firstList, PostingsList secondList){
        PostingsList result = new PostingsList();

        int i=0, j=0;

        while(i<firstList.size() && j< secondList.size()){

            if(firstList.get(i).doc.docID == secondList.get(j).doc.docID){
                result.addEntry(firstList.get(i));
                i++;
                j++;
            }else{
                if(firstList.get(i).doc.docID < secondList.get(j).doc.docID){
                    i++;
                }else{
                    j++;
                }
            }
        }

        return result;
    }

    public PostingsList compareTwoListsPhrase(PostingsList firstList, PostingsList secondList,int offset){
        PostingsList result = new PostingsList();

        int i=0, j=0;

        while(i<firstList.size() && j< secondList.size()) {

            // terms positions in this document
            Document firstDocument = firstList.get(i).doc;
            Document secondDocument = secondList.get(j).doc;

            boolean isPhraseExist = false;

            if (firstDocument.docID == secondDocument.docID) {
                System.out.println("first word positions "+firstDocument.positions.toString());
                System.out.println("second word positions "+secondDocument.positions.toString());

                int k = 0, l = 0;


                while (k < firstDocument.positions.size() && l < secondDocument.positions.size()) {
                    int firstTermPosition = firstDocument.positions.get(k);
                    int secondTermPosition = secondDocument.positions.get(l);

                    if ((secondTermPosition - firstTermPosition) == offset) {
                        isPhraseExist = true;
                        break;
                    } else {
                        if (firstTermPosition == secondTermPosition) {
                            k++;
                            l++;
                        }
                        if ((secondTermPosition - firstTermPosition) < offset) {
                            l++;
                        }

                        if ((secondTermPosition - firstTermPosition) > offset) {
                            k++;
                        }
                    }

                }

            }

            if (isPhraseExist) {
                System.out.println("found phrase");
                result.addEntry(firstList.get(i));
                i++;
                j++;
            } else {
                if (firstList.get(i).doc.docID < secondList.get(j).doc.docID) {
                    i++;
                } else {
                    j++;
                }
            }
        }
//        if (result.size() == 0) {
//            isWholePhraseExist = false;
//            return null;
//        }
        return result;
    }
    /**
     * No need for cleanup in a HashedIndex.
     */
    public void cleanup() {
    }
}

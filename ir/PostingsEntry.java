/*
 *   This file is part of the computer assignment for the
 *   Information Retrieval course at KTH.
 *
 *   First version:  Johan Boye, 2010
 *   Second version: Johan Boye, 2012
 */

package ir;

import java.io.Serializable;

public class PostingsEntry implements Comparable<PostingsEntry>, Serializable {

    public Document doc;
    public double score;

    /**
     *  PostingsEntries are compared by their score (only relevant
     *  in ranked retrieval).
     *
     *  The comparison is defined so that entries will be put in
     *  descending order.
     */
    public int compareTo( PostingsEntry other ) {
	return Double.compare( other.score, score );
    }

    //
    //  YOUR CODE HERE
    //


	/**
	* Default empty PostingsEntry constructor
	*/
	public PostingsEntry() {
		super();
	}

	/**
	* Default PostingsEntry constructor
	*/
	public PostingsEntry(int docID, double score) {
		super();
		this.doc = new Document(docID);
		this.score = score;
	}

	public PostingsEntry(int docID, int offset, double score) {
		super();
		this.doc = new Document(docID);
		this.doc.addPosition(offset);
		this.score = score;
	}

	@Override
	public boolean equals(Object object) {

		if (object == this) return true;
		if (!(object instanceof PostingsEntry)) {
			return false;
		}

		PostingsEntry entry = (PostingsEntry) object;

		return entry.doc.docID == doc.docID;
	}


	//Idea from effective Java : Item 9
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + doc.docID;
		return result;
	}

}


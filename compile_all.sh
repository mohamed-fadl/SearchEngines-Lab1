#!/bin/sh
if ! [ -d classes ];
then
   mkdir classes
fi
javac -Xlint:unchecked -cp .:pdfbox:gson-2.8.0.jar -d classes ir/Tokenizer.java ir/TokenTest.java ir/Index.java ir/Indexer.java ir/HashedIndex.java ir/Query.java ir/PostingsList.java ir/PostingsEntry.java ir/SearchGUI.java

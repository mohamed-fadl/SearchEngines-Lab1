#!/bin/sh
if ! [ -d classes ];
then
   mkdir classes
fi
javac -Xlint:unchecked -cp .:/info/DD2476/ir17/lab/pdfbox -d classes ir/Tokenizer.java ir/TokenTest.java ir/Index.java ir/Indexer.java ir/HashedIndex.java ir/Query.java ir/PostingsList.java ir/PostingsEntry.java ir/SearchGUI.java

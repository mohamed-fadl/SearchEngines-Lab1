#!/bin/sh
java -cp classes:pdfbox -Xmx1g ir.SearchGUI -d davisWiki/davisWiki  -l ir17.gif -p patterns.txt

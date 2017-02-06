#!/bin/sh
java -cp classes:pdfbox -Xmx1g ir.SearchGUI -d davisWiki/temp  -l ir17.gif -p patterns.txt

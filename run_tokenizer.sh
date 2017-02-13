#!/bin/sh
java -cp classes ir.TokenTest -f testfile.txt -p patterns.txt -rp -cf > tokenized_result.txt

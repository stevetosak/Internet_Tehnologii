#!/bin/bash

file="$1"

num=$(wc -l < "$file")

pos=$(java -cp ./javacode/src/ gen.WordGenerator "$num")


pos=$(echo "$pos" | tr -d '[:space:]')

word=`cat -n $file | awk -v pos="$pos" '$1 == pos {print $2}'`

echo "$word"

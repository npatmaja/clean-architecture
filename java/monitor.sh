#!/usr/bin/env bash
pid=$1
echo "%CPU,RSS"
while sleep 1; do
    ps -p $1 -o %cpu,rss | awk -v OFS="," 'NR==2{print $1,$2}'
done
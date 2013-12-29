#!/bin/bash

convert -size 256x256 "xc:#FFFFFF00" \
 -stroke "#FFFFFFFF" -strokewidth 1 \
 -fill "#FFFFFF00" \
 -draw "circle 127.5,127.5 127.5,0.5" \
 $1

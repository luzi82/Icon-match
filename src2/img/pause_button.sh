#!/bin/bash

convert \
	-size 256x256 \
	xc:\#00000000 \
	\
	-stroke \#ffffff7f \
	-fill none \
	-strokewidth 16 \
	-draw "circle 128,128 128,16" \
	\
	-stroke none \
	-fill \#ffffff3f \
	-draw "circle 128,128 128,24" \
	\
	-stroke none \
	-fill \#ffffff7f \
	-draw "rectangle 64,64 112,192" \
	-draw "rectangle 144,64 192,192" \
	\
	${1}

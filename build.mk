NDK_PATH=/home/luzi82/project/android/software/android-ndk-r3
ANDROIDKIT_PATH=/home/luzi82/project/android/tool/android_kit

.PHONY : all clean

all : .i18n_timestamp

.i18n_timestamp : i18n.ods
	${ANDROIDKIT_PATH}/ods2xml.sh iconmatch_loc_strings
	touch .i18n_timestamp

clean :

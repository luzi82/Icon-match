.PHONY : all clean

TOOL_PATH = tool
TOOL_I18N_PATH = ${TOOL_PATH}/i18n

all : .i18n_timestamp res/drawable/pause_button.png

.i18n_timestamp : src2/i18n/i18n.ods
	java \
		-jar ${TOOL_I18N_PATH}/CodeTemplate.jar \
		-g ${TOOL_I18N_PATH}/code_template_global_config.xml \
		-t ${TOOL_I18N_PATH}/ods2xml.xml \
		src2/i18n/i18n.ods iconmatch_loc_strings
	touch .i18n_timestamp

res/drawable/pause_button.png : src2/img/pause_button.sh
	./src2/img/pause_button.sh res/drawable/pause_button.png

clean :
	rm .i18n_timestamp
	rm res/drawable/pause_button.png

.PHONY : all clean

TOOL_PATH = tool
TOOL_I18N_PATH = ${TOOL_PATH}/i18n

all : .i18n_timestamp

.i18n_timestamp : i18n.ods
	java \
		-jar ${TOOL_I18N_PATH}/CodeTemplate.jar \
		-g ${TOOL_I18N_PATH}/code_template_global_config.xml \
		-t ${TOOL_I18N_PATH}/ods2xml.xml \
		iconmatch_loc_strings
	touch .i18n_timestamp

clean :

#!/bin/sh
#
# KUAL KPVBooklet helper
#

# KOReader's working directory
KOREADER_DIR="/mnt/us/koreader"

# Load our helper functions...
if [ -f "${KOREADER_DIR}/libkohelper.sh" ] ; then
	source "${KOREADER_DIR}/libkohelper.sh"
else
	echo "Can't source helper functions, aborting!"
	exit 1
fi

## Handle logging...
logmsg()
{
	# Use the right tools for the platform
	if [ "${INIT_TYPE}" == "sysv" ] ; then
		msg "koreader: ${1}" "I"
	elif [ "${INIT_TYPE}" == "upstart" ] ; then
		f_log I koreader kual "" "${1}"
	fi

	# And handle user visual feedback via eips...
	eips_print_bottom_centered "${1}" 1
}


# Handle cre's settings...
set_cre_prop()
{
	# We need at least two args
	if [ $# -lt 2 ] ; then
		logmsg "not enough arg passed to set_cre_prop"
		return
	fi

	cre_prop_key="${1}"
	cre_prop_value="${2}"

	cre_config="/mnt/us/extensions/kpvbooklet/bin/booklet.ini"

	touch ${cre_config}

	# Check that the config exists...
	if [ -f "${cre_config}" ] ; then
		# dos2unix
		sed -e "s/$(echo -ne '\r')$//g" -i "${cre_config}"

		grep ^${cre_prop_key}= "${cre_config}">/dev/null
		if [ $? -eq 1 ] ; then
			echo "${cre_prop_key}=${cre_prop_value}">>"${cre_config}"
			logmsg "Set ${cre_prop_key} to ${cre_prop_value}"
			return
		fi
		
		# And finally set the prop
		sed -re "s/^(${cre_prop_key})(=)(.*?)$/\1\2${cre_prop_value}/" -i "${cre_config}"
		if [ $? -eq 0 ] ; then
			logmsg "Set ${cre_prop_key} to ${cre_prop_value}"
		else
			logmsg "Failed to set ${cre_prop_key}"
		fi
	fi
}
																													
#eips_print_bottom_centered "test now" 1
# logmsg "its working OK $@"

set_cre_prop ${1} ${2}



.  $(find . -name overrides.inc)
# ========================================================================
# Special Deployment Parameters needed for the backup instance.
# ------------------------------------------------------------------------
# The generated config map is used to update the Backup configuration.
# ========================================================================
CONFIG_MAP_NAME=blacklist-conf
SOURCE_FILE=$( dirname "$0" )/config/blacklist.conf

OUTPUT_FORMAT=json
OUTPUT_FILE=${CONFIG_MAP_NAME}-configmap_DeploymentConfig.json

printStatusMsg "Generating ConfigMap; ${CONFIG_MAP_NAME} ..."
generateConfigMap "${CONFIG_MAP_NAME}" "${SOURCE_FILE}" "${OUTPUT_FORMAT}" "${OUTPUT_FILE}"

unset SPECIALDEPLOYPARMS
echo ${SPECIALDEPLOYPARMS}
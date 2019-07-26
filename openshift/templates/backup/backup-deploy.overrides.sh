.  $(find . -name overrides.inc)
# ========================================================================
# Special Deployment Parameters needed for the backup instance.
# ------------------------------------------------------------------------
# The generated config map is used to update the Backup configuration.
# ========================================================================
CONFIG_MAP_NAME=backup-conf
SOURCE_FILE=$( dirname "$0" )/config/backup.conf

OUTPUT_FORMAT=json
OUTPUT_FILE=${CONFIG_MAP_NAME}-configmap_DeploymentConfig.json

printStatusMsg "Generating ConfigMap; ${CONFIG_MAP_NAME} ..."
generateConfigMap "${CONFIG_MAP_NAME}" "${SOURCE_FILE}" "${OUTPUT_FORMAT}" "${OUTPUT_FILE}"

# Get the webhook URL
readParameter "WEBHOOK_URL - Please provide the webhook endpoint URL.  If left blank, the webhook integration feature will be disabled:" WEBHOOK_URL "" 

SPECIALDEPLOYPARMS="--param-file=${_overrideParamFile}"
echo ${SPECIALDEPLOYPARMS}
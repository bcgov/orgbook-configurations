.  $(find . -name overrides.inc)
# ================================================================================================================
# Special deployment parameters needed for injecting a user supplied settings into the deployment configuration
# ----------------------------------------------------------------------------------------------------------------
# The results need to be encoded as OpenShift template parameters for use with oc process.
# ================================================================================================================
CONFIG_MAP_NAME=msg-queue-indy-cat-enabled-plugins
SOURCE_FILE=$( dirname "$0" )/enabled_plugins

OUTPUT_FORMAT=json
OUTPUT_FILE=${CONFIG_MAP_NAME}-configmap_DeploymentConfig.json

printStatusMsg "Generating ConfigMap; ${CONFIG_MAP_NAME} ..."
generateConfigMap "${CONFIG_MAP_NAME}" "${SOURCE_FILE}" "${OUTPUT_FORMAT}" "${OUTPUT_FILE}"

# Randomly generate a set of credentials without asking ...
printStatusMsg "Creating a set of random user credentials ..."
writeParameter "DEFAULT_USER_NAME" $(generateUsername) "false"
writeParameter "DEFAULT_PASSWORD" $(generatePassword) "false"

SPECIALDEPLOYPARMS="--param-file=${_overrideParamFile}"
echo ${SPECIALDEPLOYPARMS}
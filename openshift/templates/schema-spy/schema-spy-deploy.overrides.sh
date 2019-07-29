.  $(find . -name overrides.inc)
# ========================================================================
# Special Deployment Parameters needed for the SchemaSpy instance.
# ------------------------------------------------------------------------
# The results need to be encoded as OpenShift template
# parameters for use with oc process.
#
# The generated config map is used to update the Caddy configuration
# ========================================================================
CONFIG_MAP_NAME=schema-spy-caddy-conf-indy-cat
SOURCE_FILE=$( dirname "$0" )/Caddyfile
OUTPUT_FORMAT=json
OUTPUT_FILE=${CONFIG_MAP_NAME}-configmap_DeploymentConfig.json

printStatusMsg "Generating ConfigMap; ${CONFIG_MAP_NAME} ..."
generateConfigMap "${CONFIG_MAP_NAME}" "${SOURCE_FILE}" "${OUTPUT_FORMAT}" "${OUTPUT_FILE}"

# Randomly generate a set of credentials without asking ...
printStatusMsg "Creating a set of random user credentials ..."
writeParameter "SCHEMASPY_USER" $(generateUsername) "false"
writeParameter "SCHEMASPY_PASSWORD" $(generatePassword) "false"

SPECIALDEPLOYPARMS="--param-file=${_overrideParamFile}"
echo ${SPECIALDEPLOYPARMS}
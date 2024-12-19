#! /bin/bash
_includeFile=$(type -p overrides.inc)
if [ ! -z ${_includeFile} ]; then
  . ${_includeFile}
else
  _red='\033[0;31m'; _yellow='\033[1;33m'; _nc='\033[0m'; echo -e \\n"${_red}overrides.inc could not be found on the path.${_nc}\n${_yellow}Please ensure the openshift-developer-tools are installed on and registered on your path.${_nc}\n${_yellow}https://github.com/BCDevOps/openshift-developer-tools${_nc}"; exit 1;
fi

# ========================================================================
# Special Deployment Parameters needed for the SchemaSpy instance.
# ------------------------------------------------------------------------
# The results need to be encoded as OpenShift template
# parameters for use with oc process.
#
# The generated config map is used to update the Caddy configuration
# ========================================================================
CONFIG_MAP_NAME=${CONFIG_MAP_NAME:-schema-spy-caddy-conf}
SOURCE_FILE=$( dirname "$0" )/Caddyfile
OUTPUT_FORMAT=json
OUTPUT_FILE=${CONFIG_MAP_NAME}-configmap_Deployment.json

printStatusMsg "Generating ConfigMap; ${CONFIG_MAP_NAME} ..."
generateConfigMap "${CONFIG_MAP_NAME}${SUFFIX}" "${SOURCE_FILE}" "${OUTPUT_FORMAT}" "${OUTPUT_FILE}"

if createOperation; then
  # Randomly generate a set of credentials without asking ...
  printStatusMsg "Creating a set of random user credentials ..."
  writeParameter "SCHEMASPY_USER" $(generateUsername) "false"
  writeParameter "SCHEMASPY_PLAIN_PASSWORD" $(generatePassword) "false"
  writeParameter "SCHEMASPY_PASSWORD" "needs-to-be-hashed-and-base64-encoded" "false"
else
  # Secrets are removed from the configurations during update operations ...
  printStatusMsg "Update operation detected ...\nSkipping the generation of random user credentials ...\n"
  writeParameter "SCHEMASPY_USER" "generation_skipped" "false"
  writeParameter "SCHEMASPY_PLAIN_PASSWORD" "generation_skipped" "false"
  writeParameter "SCHEMASPY_PASSWORD" "generation_skipped" "false"
fi

SPECIALDEPLOYPARMS="--param-file=${_overrideParamFile}"
echo ${SPECIALDEPLOYPARMS}

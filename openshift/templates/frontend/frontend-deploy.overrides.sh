#! /bin/bash
_includeFile=$(type -p overrides.inc)
if [ ! -z ${_includeFile} ]; then
  . ${_includeFile}
else
  _red='\033[0;31m'; _yellow='\033[1;33m'; _nc='\033[0m'; echo -e \\n"${_red}overrides.inc could not be found on the path.${_nc}\n${_yellow}Please ensure the openshift-developer-tools are installed on and registered on your path.${_nc}\n${_yellow}https://github.com/BCDevOps/openshift-developer-tools${_nc}"; exit 1;
fi

# ========================================================================
# Special Deployment Parameters needed for the backup instance.
# ------------------------------------------------------------------------
# The generated config map is used to update the Backup configuration.
# ========================================================================
CONFIG_MAP_NAME=${NAME}-${CADDY_CONFIG_MAP_NAME:-caddy-conf}
SOURCE_FILE=$( dirname "$0" )/config/Caddyfile
OUTPUT_FORMAT=json
OUTPUT_FILE=${CONFIG_MAP_NAME}-configmap_Deployment.json

printStatusMsg "Generating ConfigMap; ${CONFIG_MAP_NAME} ..."
generateConfigMap "${CONFIG_MAP_NAME}${SUFFIX}" "${SOURCE_FILE}" "${OUTPUT_FORMAT}" "${OUTPUT_FILE}"

if createOperation; then
  # Ask the user to supply the sensitive parameters ...
  readParameter "TRUSTED_PROXIES - Please provide the list of trusted proxies for the frontend:" TRUSTED_PROXIES "" "false"
  readParameter "BLOCK_LIST - Please provide the list of IP addresses to block for the frontend:" BLOCK_LIST "" "false"
  readParameter "USER_AGENT_REGEX - Please provide the regex to use to identify the user agents to block for the frontend:" USER_AGENT_REGEX "" "false"
  readParameter "FILTER_EXPRESSION - Please provide the expression to use for filtering requests for the frontend:" FILTER_EXPRESSION "" "false"
else
  # Secrets are removed from the configurations during update operations ...
  printStatusMsg "Update operation detected ...\nSkipping the prompts for TRUSTED_PROXIES, BLOCK_LIST, USER_AGENT_REGEX, and FILTER_EXPRESSION secrets ... \n"
  writeParameter "TRUSTED_PROXIES" "prompt_skipped" "false"
  writeParameter "BLOCK_LIST" "prompt_skipped" "false"
  writeParameter "USER_AGENT_REGEX" "prompt_skipped" "false"
  writeParameter "FILTER_EXPRESSION" "prompt_skipped" "false"
fi

SPECIALDEPLOYPARMS="--param-file=${_overrideParamFile}"
echo ${SPECIALDEPLOYPARMS}

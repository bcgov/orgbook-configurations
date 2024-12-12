#! /bin/bash
_includeFile=$(type -p overrides.inc)
if [ ! -z ${_includeFile} ]; then
  . ${_includeFile}
else
  _red='\033[0;31m'; _yellow='\033[1;33m'; _nc='\033[0m'; echo -e \\n"${_red}overrides.inc could not be found on the path.${_nc}\n${_yellow}Please ensure the openshift-developer-tools are installed on and registered on your path.${_nc}\n${_yellow}https://github.com/BCDevOps/openshift-developer-tools${_nc}"; exit 1;
fi

# ===================================================================================
# Special Deployment Parameters needed for DB Deployment
# -----------------------------------------------------------------------------------
# The generated config maps are used to support logging and autovacuum configuration
# ==================================================================================
CONFIG_MAP_NAME=${LOGGING_CONFIG_MAP_NAME:-db-logging-conf-bc}
SOURCE_FILE=$( dirname "$0" )/config/postgresql-cfg/logging.conf
OUTPUT_FORMAT=json
OUTPUT_FILE=${CONFIG_MAP_NAME}-configmap_Deployment.json
printStatusMsg "Generating ConfigMap; ${CONFIG_MAP_NAME} ..."
generateConfigMap "${CONFIG_MAP_NAME}" "${SOURCE_FILE}" "${OUTPUT_FORMAT}" "${OUTPUT_FILE}"

CONFIG_MAP_NAME=${AUTOVACUMM_CONFIG_MAP_NAME:-db-autovacuum-conf-bc}
SOURCE_FILE=$( dirname "$0" )/config/postgresql-cfg/autovacuum.conf
OUTPUT_FORMAT=json
OUTPUT_FILE=${CONFIG_MAP_NAME}-configmap_Deployment.json
printStatusMsg "Generating ConfigMap; ${CONFIG_MAP_NAME} ..."
generateConfigMap "${CONFIG_MAP_NAME}" "${SOURCE_FILE}" "${OUTPUT_FORMAT}" "${OUTPUT_FILE}"

CONFIG_MAP_NAME=${DBCONNECTIONS_CONFIG_MAP_NAME:-db-connections-conf-bc}
SOURCE_FILE=$( dirname "$0" )/config/postgresql-cfg/dbconnections.conf
OUTPUT_FORMAT=json
OUTPUT_FILE=${CONFIG_MAP_NAME}-configmap_Deployment.json
printStatusMsg "Generating ConfigMap; ${CONFIG_MAP_NAME} ..."
generateConfigMap "${CONFIG_MAP_NAME}" "${SOURCE_FILE}" "${OUTPUT_FORMAT}" "${OUTPUT_FILE}"

if createOperation; then
  # Randomly generate a set of credentials without asking ...
  printStatusMsg "Creating a set of random user credentials ..."
  writeParameter "POSTGRESQL_USER" $(generateUsername) "false"
  writeParameter "POSTGRESQL_PASSWORD" $(generatePassword) "false"
  writeParameter "POSTGRESQL_ADMIN_PASSWORD" $(generatePassword) "false"
else
  # Secrets are removed from the configurations during update operations ...
  printStatusMsg "Update operation detected ...\nSkipping the generation of random user credentials ...\n"
  writeParameter "POSTGRESQL_USER" "generation_skipped" "false"
  writeParameter "POSTGRESQL_PASSWORD" "generation_skipped" "false"
  writeParameter "POSTGRESQL_ADMIN_PASSWORD" "generation_skipped" "false"
fi

SPECIALDEPLOYPARMS="--param-file=${_overrideParamFile}"
echo ${SPECIALDEPLOYPARMS}

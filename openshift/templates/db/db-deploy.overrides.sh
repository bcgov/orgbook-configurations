.  $(find . -name overrides.inc)
# ======================================================
# Special Deployment Parameters needed for DB Deployment
# ------------------------------------------------------
# The results need to be encoded as OpenShift template
# parameters for use with oc process.
# ======================================================

# Randomly generate a set of credentials without asking ...
printStatusMsg "Creating a set of random user credentials ..."
writeParameter "POSTGRESQL_USER" $(generateUsername) "false"
writeParameter "POSTGRESQL_PASSWORD" $(generatePassword) "false"
writeParameter "POSTGRESQL_ADMIN_PASSWORD" $(generatePassword) "false"

SPECIALDEPLOYPARMS="--param-file=${_overrideParamFile}"
echo ${SPECIALDEPLOYPARMS}
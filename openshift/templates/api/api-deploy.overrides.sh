.  $(find . -name overrides.inc)
# ================================================================================================================
# Special deployment parameters needed for injecting a user supplied settings into the deployment configuration
# ----------------------------------------------------------------------------------------------------------------
# The results need to be encoded as OpenShift template parameters for use with oc process.
# ================================================================================================================

# Randomly generate a set of credentials without asking ...
printStatusMsg "Creating a set of random user credentials ..."
writeParameter "USER_ID" $(generateUsername) "false"
writeParameter "USER_PASSWORD" $(generatePassword) "false"
writeParameter "ADMIN_USER_ID" $(generateUsername) "false"
writeParameter "ADMIN_PASSWORD" $(generatePassword) "false"

# Ask the user to supply the sensitive parameters ...
readParameter "WALLET_ENCRYPTION_KEY - Please provide the wallet encryption key for the environment.  If left blank, a 48 character long base64 encoded value will be randomly generated using openssl:" WALLET_ENCRYPTION_KEY $(generateKey) "false"

_walletPrefix="TB"
readParameter "INDY_WALLET_SEED - Please provide the indy wallet seed for the environment.  If left blank, a seed will be randomly generated using openssl:" INDY_WALLET_SEED $(generateSeed ${_walletPrefix}) "false"
readParameter "INDY_WALLET_DID - Please provide the indy wallet did for the environment.  The default is an empty string:" INDY_WALLET_DID "" "false"

# Get the settings for delivering user feedback to the business
readParameter "FEEDBACK_TARGET_EMAIL - Please provide the target email address where user feedback will be sent.  The default is a blank string." FEEDBACK_TARGET_EMAIL "false"
readParameter "SMTP_SERVER_ADDRESS - Please provide the address of the outgoing smtp server.  The default is a blank string." SMTP_SERVER_ADDRESS "false"

SPECIALDEPLOYPARMS="--param-file=${_overrideParamFile}"
echo ${SPECIALDEPLOYPARMS}
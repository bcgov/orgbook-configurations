# Description: A profile to create an offline wallet instance.
# Setting for the OrgBook-BC environments.
# Uses the existing `8ad0ea-tools` environment for builds and images
export TOOLS="8ad0ea-tools"
export PROJECT_NAMESPACE="8ad0ea"
export SKIP_PIPELINE_PROCESSING=1

export ignore_templates=""
export include_templates="wallet-deploy"

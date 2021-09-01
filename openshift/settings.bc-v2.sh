# Description: A profile to create an offline indexing environment.
# Setting for the TheOrgBook-BC environments.
# Uses the existing `8ad0ea-tools` environment for builds and images
export TOOLS="8ad0ea-tools"
export PROJECT_NAMESPACE="8ad0ea"
export SKIP_PIPELINE_PROCESSING=1

export ignore_templates=""
export include_templates="frontend-v2-deploy"
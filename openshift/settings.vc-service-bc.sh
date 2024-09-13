# Setting for the TheOrgBook-BC environments.
# Uses the existing `8ad0ea-tools` environment for builds and images
export TOOLS="8ad0ea-tools"
export PROJECT_NAMESPACE="8ad0ea"
export GIT_URI="https://github.com/bcgov/aries-vcr-vc-service.git"
export GIT_REF="main"

export SKIP_PIPELINE_PROCESSING=1

export ignore_templates=""
export include_templates="vc-service-build vc-service-deploy"
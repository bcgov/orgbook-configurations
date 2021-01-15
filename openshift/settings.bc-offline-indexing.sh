# Description: A profile to create an offline indexing environment.
# Setting for the TheOrgBook-BC environments.
# Uses the existing `devex-von-tools` environment for builds and images
export TOOLS="devex-von-tools"
export PROJECT_NAMESPACE="devex-von-bc-tob"
export SKIP_PIPELINE_PROCESSING=1

export ignore_templates=""
export include_templates="offline-indexer-deploy search-engine-deploy"
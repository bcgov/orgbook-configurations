export PROJECT_NAMESPACE="devex-von"
export GIT_URI="https://github.com/hyperledger/aries-vcr.git"
export GIT_REF="master"

# The templates that should not have their GIT referances(uri and ref) over-ridden
# Templates NOT in this list will have they GIT referances over-ridden
# with the values of GIT_URI and GIT_REF
export skip_git_overrides="schema-spy-build.json search-engine-base-build.json backup-build.json"

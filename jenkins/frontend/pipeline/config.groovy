// Pipeline Configuration
// Import this file into another pipeline using 'load'.

// Wait timeout in minutes
def WAIT_TIMEOUT = 10

// Source repository configuration
def SOURCE_REPO = "https://github.com/bcgov/indy-catalyst.git"
def SOURCE_REPO_BRANCH = "master"
def SOURCE_REPO_CONTEXT_DIRECTORY = "credential-registry/client/tob-web/"
def SOURCE_REPO_WORKING_DIRECTORY = "indy-catalyst"
def SOURCE_REPO_THEME_DIRECTORY = "credential-registry/client/tob-web/src/themes/"

// Theme repository configuration
def THEME_REPO = "https://github.com/bcgov/indy-catalyst.git"
def THEME_REPO_BRANCH = "master"
def THEME_REPO_CONTEXT_DIRECTORY = "starter-kits/credential-registry/client/tob-web/themes/"
def THEME_REPO_WORKING_DIRECTORY = "themes"

// Theme configuration
def TOB_THEME_NAME = "bcgov"

// Build configuration
def APP_NAME = "frontend"
def ARTIFACTS_NAME = "${APP_NAME}-artifacts"
def RUNTIME_NAME = "${APP_NAME}-runtime"

// Deployment Environment TAGs
def DEPLOYMENT_ENVIRONMENT_TAGS = ['dev', 'test', 'prod']

// The name of the project namespace(s).
def NAME_SPACE = 'devex-von-bc-tob'

// Instance Suffix
def SUFFIX = '-indy-cat'

return this;
// Pipeline Configuration
// Import this file into another pipeline using 'load'.

// Wait timeout in minutes
WAIT_TIMEOUT = 10

// Source repository configuration
SOURCE_REPO = "https://github.com/bcgov/indy-catalyst.git"
SOURCE_REPO_BRANCH = "master"
SOURCE_REPO_CONTEXT_DIRECTORY = "credential-registry/client/tob-web/"
SOURCE_REPO_WORKING_DIRECTORY = "indy-catalyst"
SOURCE_REPO_THEME_DIRECTORY = "credential-registry/client/tob-web/src/themes/"

// Theme repository configuration
THEME_REPO = "https://github.com/bcgov/indy-catalyst.git"
THEME_REPO_BRANCH = "master"
THEME_REPO_CONTEXT_DIRECTORY = "starter-kits/credential-registry/client/tob-web/themes/"
THEME_REPO_WORKING_DIRECTORY = "themes"

// Theme configuration
TOB_THEME_NAME = "bcgov"

// Build configuration
APP_NAME = "frontend"
ARTIFACTS_NAME = "${APP_NAME}-artifacts"
RUNTIME_NAME = "${APP_NAME}-runtime"

// Deployment Environment TAGs
DEPLOYMENT_ENVIRONMENT_TAGS = ['dev', 'test', 'prod']

// The name of the project namespace(s).
NAME_SPACE = 'devex-von-bc-tob'

// Instance Suffix
SUFFIX = '-indy-cat'
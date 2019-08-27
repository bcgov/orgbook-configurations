// Utility functions
// Import this file into another pipeline using 'load'.
class config {
  // Wait timeout in minutes
  public static final int WAIT_TIMEOUT = 10

  // Source repository configuration
  public static final String SOURCE_REPO = "https://github.com/bcgov/indy-catalyst.git"
  public static final String  SOURCE_REPO_BRANCH = "master"
  public static final String  SOURCE_REPO_CONTEXT_DIRECTORY = "credential-registry/client/tob-web/"
  public static final String  SOURCE_REPO_WORKING_DIRECTORY = "indy-catalyst"
  public static final String  SOURCE_REPO_THEME_DIRECTORY = "credential-registry/client/tob-web/src/themes/"

  // Theme repository configuration
  public static final String  THEME_REPO = "https://github.com/bcgov/indy-catalyst.git"
  public static final String  THEME_REPO_BRANCH = "master"
  public static final String  THEME_REPO_CONTEXT_DIRECTORY = "starter-kits/credential-registry/client/tob-web/themes/"
  public static final String  THEME_REPO_WORKING_DIRECTORY = "themes"

  // Theme configuration
  public static final String  TOB_THEME_NAME = "bcgov"

  // Build configuration
  public static final String  APP_NAME = "frontend"
  public static final String  ARTIFACTS_NAME = "${APP_NAME}-artifacts"
  public static final String  RUNTIME_NAME = "${APP_NAME}-runtime"

  // Deployment Environment TAGs
  public static final String[] DEPLOYMENT_ENVIRONMENT_TAGS = ['dev', 'test', 'prod']

  // The name of the project namespace(s).
  public static final String  NAME_SPACE = 'devex-von-bc-tob'

  // Instance Suffix
  public static final String  SUFFIX = '-indy-cat'
}
// Pipeline Configuration Properties
// Import this file into the pipeline using 'load'.
class config extends bc.baseConfig {
  // Source repository configuration
  public static final String SOURCE_REPO = "https://github.com/hyperledger/aries-vcr.git"
  public static final String  SOURCE_REPO_BRANCH = "master"
  public static final String  SOURCE_REPO_CONTEXT_DIRECTORY = "client/"
  public static final String  SOURCE_REPO_WORKING_DIRECTORY = "aries-vcr"
  public static final String  SOURCE_REPO_THEME_DIRECTORY = "client/src/themes/"

  // Theme repository configuration
  public static final String  THEME_REPO = "https://github.com/hyperledger/aries-vcr.git"
  public static final String  THEME_REPO_BRANCH = "master"
  public static final String  THEME_REPO_CONTEXT_DIRECTORY = "client/themes/"
  public static final String  THEME_REPO_WORKING_DIRECTORY = "themes"

  // Theme configuration
  public static final String  THEME_NAME = "bcgov"

  // Build configuration
  public static final String  APP_NAME = "frontend"
  public static String  ARTIFACTS_NAME = "${this.APP_NAME}-artifacts"
  public static String  RUNTIME_NAME = "${this.APP_NAME}-runtime"
  public static final String[] BUILDS = ["${this.RUNTIME_NAME}", "${this.APP_NAME}"]
}

return new config();
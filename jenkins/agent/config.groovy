// Pipeline Configuration Properties
// Import this file into the pipeline using 'load'.
import ../baseConfig

class config extends baseConfig {
  // // Wait timeout in minutes
  // public static final int WAIT_TIMEOUT = 10

  // Build configuration
  public static final String  APP_NAME = "agent"
  public static String BASE_NAME = "${this.APP_NAME}-base"
  public static final String[] BUILDS = ["${this.BASE_NAME}", "${this.APP_NAME}"]

  // // Deployment Environment TAGs
  // public static final String[] DEPLOYMENT_ENVIRONMENT_TAGS = ['dev', 'test', 'prod']

  // // The name of the project namespace(s).
  // public static final String  NAME_SPACE = 'devex-von-bc-tob'

  // // Instance Suffix
  // public static final String  SUFFIX = '-indy-cat'
}

return new config();
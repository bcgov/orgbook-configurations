// Pipeline Configuration Properties
// Import this file into the pipeline using 'load'.
class baseConfig {
  // Wait timeout in minutes
  public static final int WAIT_TIMEOUT = 10

  // Deployment Environment TAGs
  public static final String[] DEPLOYMENT_ENVIRONMENT_TAGS = ['dev', 'test', 'prod']

  // The name of the project namespace(s).
  public static final String  NAME_SPACE = 'devex-von-bc-tob'

  // Instance Suffix
  public static final String  SUFFIX = '-indy-cat'
}
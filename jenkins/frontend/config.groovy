// Pipeline Configuration Properties
// Import this file into the pipeline using 'load'.
class config extends bc.baseConfig {
  // Build configuration
  public static final String  APP_NAME = "frontend"
  public static String  ARTIFACTS_NAME = "${this.APP_NAME}-artifacts"
  public static String  RUNTIME_NAME = "${this.APP_NAME}-runtime"
  public static final String[] BUILDS = ["${this.RUNTIME_NAME}", "${this.ARTIFACTS_NAME}", "${this.APP_NAME}"]
}

return new config();
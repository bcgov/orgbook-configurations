// Pipeline Configuration Properties
// Import this file into the pipeline using 'load'.
class config extends bc.baseConfig {
  // Build configuration
  public static final String  APP_NAME = "search-engine"
  public static String BASE_NAME = "${this.APP_NAME}-base"
  public static final String[] BUILDS = ["${this.BASE_NAME}", "${this.APP_NAME}"]
}

return new config();
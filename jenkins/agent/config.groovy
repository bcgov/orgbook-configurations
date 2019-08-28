// Pipeline Configuration Properties
// Import this file into the pipeline using 'load'.
class config extends orgbook.baseConfig {
  // Build configuration
  public static final String  APP_NAME = "agent"
  public static String BASE_NAME = "${this.APP_NAME}-base"
  public static final String[] BUILDS = ["${this.BASE_NAME}", "${this.APP_NAME}"]
}

return new config();
// Pipeline Configuration Properties
// Import this file into the pipeline using 'load'.
class config extends orgbook.baseConfig {
  // Build configuration
  public static final String  APP_NAME = "backup"
  public static final String[] BUILDS = ["${this.APP_NAME}"]
}

return new config();
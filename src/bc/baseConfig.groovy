    
package bc;

class baseConfig {
  // Wait timeout in minutes
  public static final int WAIT_TIMEOUT = 20

  // Deployment Environment TAGs
  public static final String[] DEPLOYMENT_ENVIRONMENT_TAGS = ['dev', 'test', 'prod']

  // The name of the project namespace(s).
  public static final String  NAME_SPACE = '8ad0ea'

  // Instance Suffix
  public static final String  SUFFIX = '-bc'

  // Apps - Listed in the order they should be tagged
  public static final String[] APPS = ['db', 'search-engine', 'msg-queue', 'frontend', 'agent', 'backup', 'schema-spy', 'api']
}
package guiceberry2_junit3.tutorial_1_server;

public interface Tutorial1Envs {

  String PACKAGE = "guiceberry2_junit3.tutorial_1_server.";

  String PET_STORE_ENV_0_SIMPLE = PACKAGE + 
    "PetStoreEnv0Simple";
  
  // Example 1 uses the same env as example 0
  
  String PET_STORE_ENV_2_GLOBAL_STATIC_POTM = PACKAGE + 
    "PetStoreEnv2GlobalStaticControllablePotm";
  
  String PET_STORE_ENV_3_COOKIES_BASED_POTM = PACKAGE + 
    "PetStoreEnv3CookiesControlledPotm";

  String PET_STORE_ENV_4_CANONICAL_SAME_JVM_CONTROLLABLE_POTM = PACKAGE + 
    "PetStoreEnv4CanonicalSameJvmControllablePotm";
}

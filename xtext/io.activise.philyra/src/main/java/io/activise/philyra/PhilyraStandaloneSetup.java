/*
 * generated by Xtext 2.23.0
 */
package io.activise.philyra;


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class PhilyraStandaloneSetup extends PhilyraStandaloneSetupGenerated {

  public static void doSetup() {
    new PhilyraStandaloneSetup().createInjectorAndDoEMFRegistration();
  }
}
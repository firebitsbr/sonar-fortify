Fortify SCA Plugin for SonarQube
================================

A plugin to integrate Fortify SCA reports into SonarQube.

## Install

SonarQube server loads rule definitions from Fortify rulepacks. Rulepacks are :

* XML files implemented by end-users to define custom rules.
* BIN files provided by HP. They are encrypted XML files.  


The SonarQube plugin is able to load the XML files, so BIN files must be beforehand manually decrypted (to be documented). Paths to the XML files (or to their parent directory) must be set in the property "sonar.fortify.rulepackPaths" of conf/sonar.properties. Value is a comma-separated list of absolute paths.
As a consequence SonarQube server must be restarted each time a rulepack is updated in Fortify.

When server is restarted, the Fortify rules are listed in the "Quality Profiles" page.

## Analyze

The SCA command-line, named "sourceanalyzer", must be executed before SonarQube analyzer. The generated report (FPR or VFDL file) is parsed to convert Fortify vulnerabilities to SonarQube issues. 
By nature SonarQube issues relate to rules that are activated in Quality profiles. For this reason don't forget to activate the Fortify rules in the selected Quality profiles.

The path to the Fortify report must be set by the property "sonar.fortify.reportPath". If the property is missing then plugin is disabled.

For example :
```
  sonar-runner -Dsonar.fortify.reportPath=/path/to/project.fpr
```
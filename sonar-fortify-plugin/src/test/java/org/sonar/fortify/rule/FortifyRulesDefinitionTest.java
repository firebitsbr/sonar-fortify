/*
 * Fortify Plugin for SonarQube
 * Copyright (C) 2014 Vivien HENRIET and SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.fortify.rule;

import org.junit.Test;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.AbstractLanguage;
import org.sonar.api.resources.Languages;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;
import org.sonar.fortify.base.FortifyConstants;

import static org.fest.assertions.Assertions.assertThat;

public class FortifyRulesDefinitionTest {

  @Test
  public void create_rules_from_xml_definition_and_rulepack() {
    Settings settings = new Settings();
    settings.setProperty(FortifyConstants.RULEPACK_PATHS_PROPERTY, "src/test/resources/rulepack,src/test/resources/rulepack/other-rulepack.xml");

    RulePackParser parser = new RulePackParser(settings);

    Languages languages = new Languages(language("java"), language("plsql"));

    RulesDefinition.Context context = new RulesDefinition.Context();
    new FortifyRulesDefinition(parser, languages, new RulesDefinitionXmlLoader()).define(context);

    assertThat(context.repository("fortify-java")).isNotNull();
    assertThat(context.repository("fortify-java").rules()).isNotEmpty();
    assertThat(context.repository("fortify-plsql")).isNotNull();
    assertThat(context.repository("fortify-plsql").rules()).isNotEmpty();
  }

  private AbstractLanguage language(String key) {
    return new AbstractLanguage(key) {
      @Override
      public String[] getFileSuffixes() {
        return null;
      }
    };
  }

  @Test
  public void create_flex_rules() {
    Settings settings = new Settings();
    settings.setProperty(FortifyConstants.RULEPACK_PATHS_PROPERTY, "src/test/resources/rulepack/actionscript-rulepack.xml");

    RulePackParser parser = new RulePackParser(settings);

    Languages languages = new Languages(language("flex"));

    RulesDefinition.Context context = new RulesDefinition.Context();
    new FortifyRulesDefinition(parser, languages, new RulesDefinitionXmlLoader()).define(context);

    assertThat(context.repository("fortify-flex").rules()).isNotEmpty();
  }

  @Test
  public void create_js_rules() {
    Settings settings = new Settings();
    settings.setProperty(FortifyConstants.RULEPACK_PATHS_PROPERTY, "src/test/resources/rulepack/javascript-rulepack.xml");

    RulePackParser parser = new RulePackParser(settings);

    Languages languages = new Languages(language("js"));

    RulesDefinition.Context context = new RulesDefinition.Context();
    new FortifyRulesDefinition(parser, languages, new RulesDefinitionXmlLoader()).define(context);

    assertThat(context.repository("fortify-js").rules()).isNotEmpty();
  }

}

/*
 * MIT License
 *
 * Copyright (c) 2020 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.github.portlek.tomlgration;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.ConfigurationSection;

/**
 * a class that helps to conversion processes.
 */
public final class Helper {

  /**
   * ctor.
   */
  private Helper() {
  }

  /**
   * loads the given contents of the file into the configuration
   *
   * @param configuration the configuration to load.
   * @param contents the contents to load.
   */
  public static void loadFromString(@NotNull final TomlConfiguration configuration, @NotNull final String contents) {
    if (contents.isEmpty()) {
      return;
    }
    Helper.convertMapToSection(configuration.getToml().read(contents).toMap(), configuration);
  }

  /**
   * converts the given configuration section into a {@link Map}.
   *
   * @param input the input to convert.
   * @param section the section to convert.
   */
  private static void convertMapToSection(@NotNull final Map<?, ?> input, @NotNull final ConfigurationSection section) {
    for (final Map.Entry<?, ?> entry : input.entrySet()) {
      final String key = entry.getKey().toString();
      final Object value = entry.getValue();
      if (value instanceof Map<?, ?>) {
        Helper.convertMapToSection((Map<?, ?>) value, section.createSection(key));
      } else {
        section.set(key, value);
      }
    }
  }
}

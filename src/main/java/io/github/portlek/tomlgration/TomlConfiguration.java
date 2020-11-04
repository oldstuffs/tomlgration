/*
 * MIT License
 *
 * Copyright (c) 2020 Shiru ka
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

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import java.io.File;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.file.FileConfiguration;
import org.simpleyaml.exceptions.InvalidConfigurationException;

/**
 * a TOML implementation for {@link FileConfiguration}.
 */
public final class TomlConfiguration extends FileConfiguration {

  /**
   * the TOMl writer.
   */
  private static final TomlWriter WRITER = new TomlWriter();

  /**
   * the TOML instance.
   */
  private final Toml toml = new Toml();

  /**
   * loads the given file and converts into the TOML configuration instance.
   *
   * @param file the file to load.
   *
   * @return the TOML configuration instance.
   */
  @NotNull
  public static TomlConfiguration loadConfiguration(@NotNull final File file) {
    return TomlConfiguration.loadConfiguration(new TomlConfiguration(), file);
  }

  /**
   * loads the given file and returns the given TOML configuration.
   *
   * @param config the configuration to load.
   * @param file the file to load.
   *
   * @return the given config instance.
   */
  @NotNull
  private static TomlConfiguration loadConfiguration(@NotNull final TomlConfiguration config,
                                                     @NotNull final File file) {
    try {
      config.load(file);
    } catch (final IOException | InvalidConfigurationException exception) {
      throw new IllegalStateException(exception);
    }
    return config;
  }

  @NotNull
  @Override
  public String saveToString() {
    return TomlConfiguration.WRITER.write(this.getMapValues(false));
  }

  @Override
  public void loadFromString(@NotNull final String contents) {
    Helper.loadFromString(this, contents);
  }

  @NotNull
  @Override
  public TomlConfigurationOptions options() {
    if (this.options == null) {
      this.options = new TomlConfigurationOptions(this);
    }
    return (TomlConfigurationOptions) this.options;
  }

  @Override
  protected String buildHeader() {
    return "";
  }

  @NotNull
  Toml getToml() {
    return this.toml;
  }
}

/******************************************************************************
 * Copyright 2016 Edinson E. Padrón Urdaneta
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************/

/* ***************************************************************************/
package com.github.epadronu.balin.core
/* ***************************************************************************/

/* ***************************************************************************/
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait
/* ***************************************************************************/

/* ***************************************************************************/
internal class BrowserImpl(val driver: WebDriver) : Browser, WebDriver by driver {
  override val js = object : JavaScriptExecutor {
    override fun execute(vararg args: Any, async: Boolean, script: () -> String): Any? {
      if (driver is JavascriptExecutor) {
        return when (async) {
          false -> driver.executeScript(script(), *args)
          else -> driver.executeAsyncScript(script(), *args)
        }
      }

      throw UnsupportedOperationException()
    }
  }

  override fun <T> waitFor(timeOutInSeconds: Long, sleepInMillis: Long, isTrue: () -> ExpectedCondition<T>): T {
    return WebDriverWait(driver, timeOutInSeconds, sleepInMillis)
      .until(isTrue())
  }
}
/* ***************************************************************************/

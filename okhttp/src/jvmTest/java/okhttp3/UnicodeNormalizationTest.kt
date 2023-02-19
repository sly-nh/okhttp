/*
 * Copyright (C) 2023 Block, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package okhttp3

import assertk.assertThat
import assertk.assertions.isEqualTo
import java.text.Normalizer
import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

/** Runs the web platform ToAscii tests. */
class UnicodeNormalizationTest {
  @TestFactory
  fun testFactory(): List<DynamicNode> {
    val lines = UnicodeNormalizationTestData.load()
    val result = mutableListOf<DynamicNode>()

    for ((partName, partLines) in lines.groupBy { it.part }) {
      val partTests = mutableListOf<DynamicNode>()
      for (line in partLines) {
        val lineTests = mutableListOf<DynamicTest>()
        for (form in Normalizer.Form.values()) {
          lineTests += dynamicTest("$form") {
            assertThat(Normalizer.normalize(line.source, form), line.comment).isEqualTo(line[form])
          }
        }
        partTests += dynamicContainer("${line.lineNumber} ${line.source}", lineTests)
      }
      result += dynamicContainer(partName, partTests)
    }
    return result
  }
}

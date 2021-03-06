/*
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
 */
package org.joor.test;

import java.io.Serializable;
import java.util.Collections;

/* [java-8] */

import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import org.joor.CompileOptions;
import org.joor.Reflect;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Lukas Eder
 */
public class CompileOptionsTest {

    @Test
    public void testCompileWithAnnotationProcessors() throws Exception {
        AProcessor p = new AProcessor();
        Reflect.compile(
            "org.joor.test.CompileWithAnnotationProcessors",
            "package org.joor.test; "
          + "@A "
          + "public class CompileWithAnnotationProcessors {"
          + "}",
            new CompileOptions().processors(p)
            ).create().get();

        assertTrue(p.processed);
    }

    static class AProcessor implements Processor {
        boolean processed;

        @Override
        public Set<String> getSupportedOptions() {
            return Collections.emptySet();
        }

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return Collections.singleton("*");
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.RELEASE_8;
        }

        @Override
        public void init(ProcessingEnvironment processingEnv) {
        }

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
            this.processed = true;
            return false;
        }

        @Override
        public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
            return Collections.emptyList();
        }
    }
}

@interface A {}

/* [/java-8] */
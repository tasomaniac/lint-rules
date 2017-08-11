package com.vanniktech.lintrules.android

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import org.fest.assertions.api.Assertions.assertThat
import org.intellij.lang.annotations.Language

class AnnotationOrderDetectorTest : LintDetectorTest() {
  fun testOverrideComesFirstOnVariables() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        void something() {
          @Test @Override int something;
        }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:5: Warning: Annotations are in wrong order. Should be @Override @Test [WrongAnnotationOrder]
        |          @Test @Override int something;
        |                              ~~~~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testOverrideComesFirstOnFields() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        @Test @Override int something;
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @Override @Test [WrongAnnotationOrder]
        |        @Test @Override int something;
        |                            ~~~~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testOverrideComesFirstOnParameters() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        public void myTest(@Test @Override int something) { }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @Override @Test [WrongAnnotationOrder]
        |        public void myTest(@Test @Override int something) { }
        |                                               ~~~~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testOverrideComesFirst() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        @Test @Override public void myTest() { }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @Override @Test [WrongAnnotationOrder]
        |        @Test @Override public void myTest() { }
        |                                    ~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testNullableBeforeStringRes() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        @StringRes @Nullable public void myTest() { }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @Nullable @StringRes [WrongAnnotationOrder]
        |        @StringRes @Nullable public void myTest() { }
        |                                         ~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testNullableBeforeNonNull() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        @NonNull @Nullable public void myTest() { }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @Nullable @NonNull [WrongAnnotationOrder]
        |        @NonNull @Nullable public void myTest() { }
        |                                       ~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testCheckResultBeforeCheckReturnValue() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        @CheckReturnValue @CheckResult public void myTest() { }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @CheckResult @CheckReturnValue [WrongAnnotationOrder]
        |        @CheckReturnValue @CheckResult public void myTest() { }
        |                                                   ~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testInjectBeforeNullable() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        @Nullable @Inject public void myTest() { }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @Inject @Nullable [WrongAnnotationOrder]
        |        @Nullable @Inject public void myTest() { }
        |                                      ~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testInjectBeforeNonNull() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        @NonNull @Inject public void myTest() { }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @Inject @NonNull [WrongAnnotationOrder]
        |        @NonNull @Inject public void myTest() { }
        |                                     ~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testInjectBeforeCustom() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        @Custom @Inject public void myTest() { }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @Inject @Custom [WrongAnnotationOrder]
        |        @Custom @Inject public void myTest() { }
        |                                    ~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testJsonBeforeJsonQualifier() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        @JsonQualifier @Json public void myTest() { }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @Json @JsonQualifier [WrongAnnotationOrder]
        |        @JsonQualifier @Json public void myTest() { }
        |                                         ~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testDocumentedBeforeRetention() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        @Retention @Documented public void myTest() { }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @Documented @Retention [WrongAnnotationOrder]
        |        @Retention @Documented public void myTest() { }
        |                                           ~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testRetentionBeforeIntDef() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        @IntDef @Retention public void myTest() { }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @Retention @IntDef [WrongAnnotationOrder]
        |        @IntDef @Retention public void myTest() { }
        |                                       ~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testSuppressBeforeSuppressLintBeforeSuppressWarnings() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        @SuppressWarnings @SuppressLint @Suppress public void myTest() { }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @Suppress @SuppressLint @SuppressWarnings [WrongAnnotationOrder]
        |        @SuppressWarnings @SuppressLint @Suppress public void myTest() { }
        |                                                              ~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  fun testKeepBeforeRestrictToBeforeTargetApi() {
    @Language("java") val source = """
      package foo;

      public class MyTest {
        @TargetApi @RestrictTo @Keep public void myTest() { }
      }""".trimMargin()

    assertThat(lintProject(java(source))).startsWith("""src/foo/MyTest.java:4: Warning: Annotations are in wrong order. Should be @Keep @RestrictTo @TargetApi [WrongAnnotationOrder]
        |        @TargetApi @RestrictTo @Keep public void myTest() { }
        |                                                 ~~~~~~
        |0 errors, 1 warnings""".trimMargin())
  }

  override fun getDetector() = AnnotationOrderDetector()

  override fun getIssues() = listOf(ISSUE_WRONG_ANNOTATION_ORDER)

  override fun allowCompilationErrors() = true // Does not require setting up all of the annotations.
}

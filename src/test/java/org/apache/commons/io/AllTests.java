
package org.apache.commons.io;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IOUtilsTest.class // 这里可以添加更多测试类
})
public class AllTests {
    // 该类用于批量运行 JUnit 4 测试
}

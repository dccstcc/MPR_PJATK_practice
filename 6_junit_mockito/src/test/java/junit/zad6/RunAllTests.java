package junit.zad6;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestRemoteClientRepository.class, TestUnitOfWork.class, TestUnitOfWorkWithoutMockito.class})
public class RunAllTests {

}

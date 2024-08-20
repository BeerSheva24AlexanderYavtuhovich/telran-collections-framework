package telran.util;

public abstract class ListTest extends CollectionTest {
    List<Integer> list;

    @Override
    void setUp() {
        super.setUp();
        list = (List<Integer>) collection;
    }

    // TODO
    // specific tests for list
    // where list is the reference to collection beign filled in the method setup of
    // CollectionTest
}

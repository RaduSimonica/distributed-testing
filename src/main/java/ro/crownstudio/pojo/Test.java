package ro.crownstudio.pojo;

import lombok.Getter;
import lombok.Setter;
import ro.crownstudio.enums.Status;

import java.util.Objects;

@Getter
public class Test {

    private final String id;
    @Setter
    private Status status;

    public Test(String id) {
        this.id = id;
        status = Status.NOT_STARTED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return Objects.equals(id, test.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

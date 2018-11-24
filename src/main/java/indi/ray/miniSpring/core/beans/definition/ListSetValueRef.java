package indi.ray.miniSpring.core.beans.definition;

import java.util.ArrayList;
import java.util.List;

public class ListSetValueRef extends DefaultValueOrRef {
    private List<ValueOrRef> contents = new ArrayList<ValueOrRef>();

    public List<ValueOrRef> getContents() {
        return contents;
    }

    public void setContents(List<ValueOrRef> contents) {
        this.contents = contents;
    }
}

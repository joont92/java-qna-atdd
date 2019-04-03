package nextstep.dto;

import java.util.List;

public class ListResponse<T> {
    private List<T> data;

    public ListResponse() {
    }

    public ListResponse(List<T> data) {
        this.data = data;
    }
}

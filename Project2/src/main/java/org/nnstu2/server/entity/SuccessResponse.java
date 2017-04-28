package org.nnstu2.server.entity;

import org.nnstu2.server.Const;

/**
 * Created by rmv52 on 28.04.2017.
 */
public class SuccessResponse<T> extends Response<T> {
    private T object;

    public SuccessResponse(T object) {
        super(Const.SUCCESS);
        this.object = object;
    }

    public T getObject() {
        return object;
    }
}

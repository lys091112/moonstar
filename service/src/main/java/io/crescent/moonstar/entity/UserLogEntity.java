package io.crescent.moonstar.entity;

import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liuhongjun
 * @since 2019-06-26
 */
@Data
@Accessors(chain = true)
public class UserLogEntity {

    private long id;
    private long userId;
    private String operatorType;
    private Date addDate;
    private Date updateDate;
}

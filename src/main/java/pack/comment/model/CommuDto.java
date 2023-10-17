package pack.comment.model;

import lombok.Data;

@Data
public class CommuDto {
    private int num, readcnt,commentcount;
    private String customerid, title, cont, filename, cdate,customernickname;
}

package domain;

import javax.persistence.*;

/**
 * Created by sangzhenya on 2016/3/29.
 */
@Entity
@Table(name = "myfollow")
public class MyFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int myfollow_id;

}
